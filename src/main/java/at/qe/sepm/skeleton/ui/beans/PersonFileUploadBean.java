package at.qe.sepm.skeleton.ui.beans;

import at.qe.sepm.skeleton.repositories.SupervisorRepository;
import at.qe.sepm.skeleton.repositories.ParentRepository;
import at.qe.sepm.skeleton.repositories.PictureRepository;
import at.qe.sepm.skeleton.repositories.PersonRepository;

import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.model.Parent;
import at.qe.sepm.skeleton.model.Supervisor;
import at.qe.sepm.skeleton.model.UserRole;
import at.qe.sepm.skeleton.model.Person;
import at.qe.sepm.skeleton.model.Picture;

import at.qe.sepm.skeleton.ui.beans.ParentInfoBean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import java.util.List;

// https://www.primefaces.org/docs/api/6.0/org/primefaces/event/FileUploadEvent.html
@Component
@Scope("session")
@PreAuthorize("hasAuthority('PARENTS') or hasAuthority('SUPERVISOR') or hasAuthority('ADMIN')")
public class PersonFileUploadBean {

    @Autowired
    SessionInfoBean sessionInfoBean;

    @Autowired
    ParentRepository parentRepository;

    @Autowired
    SupervisorRepository supervisorRepository;

    @Autowired
    PictureRepository pictureRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ParentInfoBean parentInfoBean;

    private Long personId;
    private List<Person> people;

    public List<Person> getPeople() {
        // TODO: filter only accessible people (mother, father of current logged-in parent-account and it's children)
        Parent loggedInParent = parentInfoBean.getCurrentParent();
        people = personRepository.findAll();
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        if (personId == null)
            return;
        this.personId = personId;
    }

    private void saveProfilePicture(FileUploadEvent event, Person p){
        String filename = p.getLastName() + "_" + p.getFirstName() + "_" +  event.getFile().getFileName();
        FacesMessage message = new FacesMessage("We did it!", filename + " is uploaded!");
        FacesContext.getCurrentInstance().addMessage(null, message);
        // write file to .../sepm/skeleton/files/<filename>.<filetype>
        try {
            String path = "src/main/java/at/qe/sepm/skeleton/files/";
            event.getFile().write(path + filename);

            Picture pic = new Picture();
            pic.setName(filename);
            pic.setPath(path);
            pic.setUploadTime(new Timestamp(System.currentTimeMillis()));
            pic.setPerson(p);

            pictureRepository.save(pic);
            System.out.println("Info: uploaded file: " + filename);
        } catch (Exception e) {
            System.out.println("Error: Unable to write uploaded file: event.getFile().getFileName() (" + e + ")");
        }
    }
 
    public void handleFileUpload(FileUploadEvent event) {
        // get current User
        User currentUser = this.sessionInfoBean.getCurrentUser();;

        Person p;

        // figure out what we are dealing with; no input yet from xhtml, because I'm an idiot.
        if (currentUser.hasRole(UserRole.PARENTS)) {
            Parent par = parentRepository.getByUser(currentUser.getId());
            p = par.getMother(); 
        }
        else if (currentUser.hasRole(UserRole.SUPERVISOR)) {
            Supervisor sup = supervisorRepository.getByUser(currentUser.getId());
            p = sup.getPerson();
        }
        else {
            System.out.println("Admin can't upload a profile picture");
            return;
        }

        saveProfilePicture(event, p);

    }

}
