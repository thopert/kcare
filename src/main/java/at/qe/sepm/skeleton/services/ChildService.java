package at.qe.sepm.skeleton.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.model.Child;
import at.qe.sepm.skeleton.model.Contact;
import at.qe.sepm.skeleton.model.ContactPickup;
import at.qe.sepm.skeleton.model.Parent;
import at.qe.sepm.skeleton.model.Person;
import at.qe.sepm.skeleton.model.ScheduledAbsentChild;
import at.qe.sepm.skeleton.repositories.ChildRepository;
import at.qe.sepm.skeleton.repositories.ContactRepository;
import at.qe.sepm.skeleton.repositories.ContactPickupRepository;
import at.qe.sepm.skeleton.repositories.ParentRepository;
import at.qe.sepm.skeleton.repositories.PersonRepository;
import at.qe.sepm.skeleton.repositories.ScheduledAbsentChildRepository;
import at.qe.sepm.skeleton.repositories.ScheduledChildRepository;
import at.qe.sepm.skeleton.utils.DateUtils;



/**
 * @author Thomas Perteneder
 *
 */
@Component
@Scope("application")
public class ChildService {
	@Autowired
	private ChildRepository childRepository;
	
	@Autowired
	private ScheduledChildRepository scheduledChildRepository;
	
	@Autowired
	private ScheduledAbsentChildRepository scheduledAbsentChildRepository;
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private ParentRepository parentRepository;
	
	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private ContactPickupRepository contactPickupRepository;

	//@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERVISOR')")
	public Child loadChild(Long id){
		return this.childRepository.getById(id);
	}

	public Child loadFirstChild(String fullName){
		String[] names = fullName.split("\\s+");
		 return this.childRepository.getChildrenByName(names[0], names[1]).get(0);
	}
	
	public List<Child> loadFirstChildren(List<String> fullNameList){
		List<Child> children = new ArrayList<Child>();
		for (String fullName : fullNameList){
			children.add(this.loadFirstChild(fullName));
		}
		return children;
	}
	
	//@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERVISOR')")
	public void saveChild(Child child){
		this.personRepository.save(child.getPerson());
		this.childRepository.save(child);
	}
	
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERVISOR')")	
	public void deleteChild(Child child){
		Long cid = child.getId();
		List<ContactPickup> cps = contactPickupRepository.getByChild(cid);
		for (ContactPickup cp : cps) {
			contactPickupRepository.delete(cp);
		}
		this.childRepository.delete(child);
	}
	
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERVISOR')")
	public List<Child> loadChildren(){
		return this.childRepository.findAll();
	}
	
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERVISOR')")
	public List<Child> loadChildrenNoParents(){
		return this.childRepository.getChildrenNoParents();
	}
	
	public List<Child> loadChildrenForDate(Date date){
		return this.scheduledChildRepository.getChildrenForDate(date);
	}
	
	//implementation should be in Repository
	public List<Child> loadChildrenNotForDate(Date date){
		List<Child> allChildren = this.loadChildren();
		List<Child> here = this.loadChildrenForDate(date);
		
		for (Child child: here)
			allChildren.remove(child);
		
		return allChildren;
	}
	
	public void setAbsentChild (Date date, Long childId){
		if (this.scheduledAbsentChildRepository.getForAbsentChildAtDate(date, childId) == null){
			ScheduledAbsentChild sac = new ScheduledAbsentChild(date, this.childRepository.getById(childId));
			this.scheduledAbsentChildRepository.save(sac);
		}
	}
	
	public void deleteAbsentChild (Date date, Long childId){
		ScheduledAbsentChild sac = this.scheduledAbsentChildRepository.getForAbsentChildAtDate(date, childId);
		if (sac != null){
			this.scheduledAbsentChildRepository.delete(sac);
		}
	}
	
	public boolean isAbsentChild (Date date, Long childId){
		return this.scheduledAbsentChildRepository.getForAbsentChildAtDate(date, childId) != null;
	}
	
	public List<Child> loadBirthDayChildren(Date date){
		return this.childRepository.getBirthDayChildren(date);
	}
	
	public List<Person> getContactList(Long childId){
		if (childId == null)
			return null;
		Parent parents = this.parentRepository.getParentByChild(childId);
		List<Contact> contacts = this.contactRepository.getContactsByChild(childId);
		List<Person> persons = new ArrayList<Person>();
		persons.add(parents.getMother());
		persons.add(parents.getFather());
		for (Contact c: contacts)
			persons.add(c.getPerson());
		return persons;
	}
	
	public Child getEmptyChild(){
		Child child = new Child();
		child.setPerson(new Person());
		child.setRegistrationDate(DateUtils.toSimpleDate(new Date()));
		child.setPickTimeBegin(11, 45);
		child.setPickTimeEnd(14, 00);
		child.setPutTimeBegin(7, 15);
		child.setPutTimeEnd(8, 45);
		return child;
	}
}
