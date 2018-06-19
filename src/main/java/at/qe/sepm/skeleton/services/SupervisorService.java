package at.qe.sepm.skeleton.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.model.Supervisor;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.model.UserRole;
import at.qe.sepm.skeleton.model.Message;
import at.qe.sepm.skeleton.model.Person;
import at.qe.sepm.skeleton.repositories.PersonRepository;
import at.qe.sepm.skeleton.repositories.ScheduledSupervisorRepository;
import at.qe.sepm.skeleton.repositories.SupervisorRepository;
import at.qe.sepm.skeleton.repositories.UserRepository;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.repositories.MessageRepository;


/**
 * Service for handling with supervisors.
 * @author Thomas Perteneder, Dominik Kuen (csat2284)
 */
@Component
@Scope("application")
public class SupervisorService {
	
	@Autowired
	private SupervisorRepository supervisorRepository;
	
	@Autowired
	private ScheduledSupervisorRepository scheduledSupervisorRepository;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private MessageRepository messageRepository;

	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERVISOR')")
	public Supervisor loadSupervisor(Long id){
		return supervisorRepository.getById(id);
	}
	
	public Supervisor loadFirstSupervisor(String fullName){
		String[] names = fullName.split("\\s+");
		 return this.supervisorRepository.getSupervisorsByName(names[0], names[1]).get(0);
	}

	public List<Supervisor> loadSupervisors() {
		return supervisorRepository.findAll();
	}

	public List<Supervisor> loadFirstSupervisors(List<String> fullNameList){
		List <Supervisor> supervisors = new ArrayList<Supervisor>();
		for(String fullName: fullNameList){
			supervisors.add(this.loadFirstSupervisor(fullName));
		}
		return supervisors;
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	public void saveSupervisor(Supervisor supervisor) {
		personRepository.save(supervisor.getPerson());
		userService.saveUser(supervisor.getUser());
		supervisorRepository.save(supervisor);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	public void saveNewSupervisor(Supervisor supervisor) {
		personRepository.save(supervisor.getPerson());
		userService.saveNewUser(supervisor.getUser());
		supervisorRepository.save(supervisor);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	public void deleteSupervisor(Supervisor supervisor) {
		Long pId = supervisor.getPerson().getId();
		Long uId = supervisor.getUser().getId();
		supervisorRepository.delete(supervisor);
		for (Message msg : messageRepository.getByAuthor(pId))
			messageRepository.delete(msg);
		personRepository.delete(personRepository.getById(pId));
		userRepository.delete(userRepository.getById(uId));
	}
	
	public List<Supervisor> loadSupervisorForDate(Date date){
		return this.scheduledSupervisorRepository.getSupervisorsForDate(date);
	}
	
	//should be implemented in repo
	public List<Supervisor> loadSupervisorNotForDate(Date date){
		List<Supervisor> allSupervisors = this.supervisorRepository.findAll();
		for(Supervisor s: this.loadSupervisorForDate(date))
			allSupervisors.remove(s);
		return allSupervisors;
	}
	
	public Supervisor getEmptySupervisor(){
		Supervisor supervisor = new Supervisor();
		User user = new User();
		user.setRole(UserRole.SUPERVISOR);
		supervisor.setUser(user);
		supervisor.setPerson(new Person());
		return supervisor;
	}
	
	public Supervisor getSupervisorByUser(Long userId){
		return this.supervisorRepository.getByUser(userId);
	}
}
