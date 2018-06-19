package at.qe.sepm.skeleton.ui.beans;
 
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
// https://www.primefaces.org/docs/api/6.0/org/primefaces/event/FileUploadEvent.html
@Component
@Scope("request")
@PreAuthorize("hasAuthority('PARENTS') or hasAuthority('SUPERVISOR') or hasAuthority('ADMIN')")
public class FileUploadBean {
 
    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        // write file to .../sepm/skeleton/files/<filename>.<filetype>
        try {
			event.getFile().write("src/main/java/at/qe/sepm/skeleton/files/" + event.getFile().getFileName());
			System.out.println("Info: uploaded file: " + event.getFile().getFileName());
		} catch (Exception e) {
			System.out.println("Error: Unable to write uploaded file: event.getFile().getFileName() (" + e + ")");
		}

    }

}
