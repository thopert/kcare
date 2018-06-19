package at.qe.sepm.skeleton.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.model.Child;
import at.qe.sepm.skeleton.model.Parent;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.ChildRepository;
import at.qe.sepm.skeleton.repositories.ParentRepository;
import at.qe.sepm.skeleton.repositories.PersonRepository;
import at.qe.sepm.skeleton.repositories.UserRepository;

import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.services.ChildService;

@Component
@Scope("application")
public class ParentService {

	@Autowired
	private ParentRepository parentRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private ChildRepository childRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private ChildService childService;

	//@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERVISOR')")
	public void saveParent(Parent parent) {
		this.userService.saveUser(parent.getUser());
		this.personRepository.save(parent.getMother());
		this.personRepository.save(parent.getFather());
		this.parentRepository.save(parent);

/*		if (parent.getChildren() != null) {
			for (Child c : parent.getChildren()) {
				this.childRepository.save(c);
			}
		}*/
	}

	public void saveNewParent(Parent parent) {
		this.userService.saveNewUser(parent.getUser());
		this.personRepository.save(parent.getMother());
		this.personRepository.save(parent.getFather());
		this.parentRepository.save(parent);

/*		if (parent.getChildren() != null) {
			for (Child c : parent.getChildren()) {
				this.childRepository.save(c);
			}
		}*/
	}

	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERVISOR')")
	public void deleteParent(Parent parent) {
		Long mpId = parent.getMother().getId();
		Long vpId = parent.getFather().getId();
		Long uId = parent.getUser().getId();
		List<Child> cdn = childRepository.getChildrenByParents(parent.getId());
		for (Child c : cdn) {
			childService.deleteChild(c);
		}
		this.parentRepository.delete(parent);
		personRepository.delete(personRepository.getById(mpId));
		personRepository.delete(personRepository.getById(vpId));
		userRepository.delete(userRepository.getById(uId));
	}

	//@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERVISOR')")
	public Parent loadParent(Long id) {
		return this.parentRepository.getById(id);
	}
	
	//@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERVISOR')")
	public List<Parent> loadParents(){
		return this.parentRepository.findAll();
	}

	//@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERVISOR')")
	public List<Parent> loadParentsNoChild() {
		return this.parentRepository.getParentsNoChild();
	}
	
	public Parent loadParentByUser(User user){
		return this.parentRepository.getByUser(user.getId());
	}
	
	public List<Child> loadChildrenByParent(Parent parent){
		return this.parentRepository.getChildrenByParent(parent.getId());
	}
	
	public Parent getParentByJob(Long jobId){
		return this.parentRepository.getParentByJob(jobId);
	}
}
