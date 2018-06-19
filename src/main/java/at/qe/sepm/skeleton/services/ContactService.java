package at.qe.sepm.skeleton.services;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.model.Child;
import at.qe.sepm.skeleton.model.Contact;
import at.qe.sepm.skeleton.model.ContactFor;
import at.qe.sepm.skeleton.model.ContactPickup;
import at.qe.sepm.skeleton.model.Person;
import at.qe.sepm.skeleton.repositories.ChildRepository;
import at.qe.sepm.skeleton.repositories.ContactForRepository;
import at.qe.sepm.skeleton.repositories.ContactPickupRepository;
import at.qe.sepm.skeleton.repositories.ContactRepository;
import at.qe.sepm.skeleton.repositories.PersonRepository;


/**
 * @author Thomas Perteneder
 *
 */
@Component
@Scope("application")
public class ContactService {
	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private ContactForRepository contactForRepository;
	
	@Autowired
	private ContactPickupRepository contactPickupRepository;
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private ChildRepository childRepository;

	public List<Contact> loadContacts(){
		return this.contactRepository.findAll();
	}
	
	public Contact loadContact(Long id){
		return this.contactRepository.getById(id);
	}
	
	public void saveContact(Contact contact){
		this.personRepository.save(contact.getPerson());
		this.contactRepository.save(contact);
	}
	
	public void deleteContact(Contact contact){
		Long cid = contact.getId();
		List<ContactPickup> cps = contactPickupRepository.getByContact(cid);
		for (ContactPickup cp : cps)
			contactPickupRepository.delete(cp);
		Long pid = contact.getPerson().getId();
		contactRepository.delete(contact);
		personRepository.delete(personRepository.getById(pid));
	}
	
	public List<Child> loadChildrenByContact(Long id){
		return this.contactRepository.getChildrenByContact(id);
	}
	
	public List<Contact> loadContactsByChild(Long id){
		return this.contactRepository.getContactsByChild(id);
	}
	
	public Set<Contact> loadContactsByChildren(List<Child> children){
		Set<Contact> contacts = new HashSet<Contact>();
		
		for (Child ch: children){
			for(Contact co: this.loadContactsByChild(ch.getId()))
			contacts.add(co);
		}
		return contacts;
	}
	
	public Contact getPickup(Long childId, Date date){
		ContactPickup cp =  this.contactPickupRepository.getByDateChild(date, childId);
		if (cp != null)
			return cp.getContact();
		
		return null;
	}
	
	public boolean hasPickup(Long childId, Date date){
		return (this.contactPickupRepository.getByDateChild(date, childId) != null);
	}
	
	public void deletePickup(Long childId, Date date){
		ContactPickup cp = this.contactPickupRepository.getByDateChild(date, childId);
		if (cp != null)
			this.contactPickupRepository.delete(cp);
	}
	
	public void setPickup(Long contactId, Long childId, Date date){
		ContactPickup oldPickup = this.contactPickupRepository.getByDateChild(date, childId);
		if (oldPickup != null) 
			this.contactPickupRepository.delete(oldPickup);
		
		ContactPickup cp = new ContactPickup();
		cp.setChild(this.childRepository.getById(childId));
		cp.setContact(this.contactRepository.getById(contactId));
		cp.setDate(date);
		this.contactPickupRepository.save(cp);
	}
	
	// should check if an entry already exists
	public void savePickup(Contact contact, Child child, Date date){
		ContactPickup cp = contactPickupRepository.getByDateContactChild(date, contact.getId(), child.getId());
		if (cp == null)
			cp = new ContactPickup();
		cp.setChild(child);
		cp.setContact(contact);
		cp.setDate(date);
		contactPickupRepository.save(cp);
	}
	
	public void savePickup(Long contactId, Long childId, Date date){
		ContactPickup cp = contactPickupRepository.getByDateContactChild(date, contactId, childId);
		if (cp == null)
			cp = new ContactPickup();
		cp.setChild(this.childRepository.getById(childId));
		cp.setContact(this.contactRepository.getById(contactId));
		cp.setDate(date);
		contactPickupRepository.save(cp);
	}
	
	public void addChildToContact(Contact contact, Child child) {
		if (contactForRepository.getByContactAndChild(contact.getId(), child.getId()) == null) {
			ContactFor cf = new ContactFor();
			cf.setContact(contact);
			cf.setChild(child);
			contactForRepository.save(cf);
		}
	}

	public Contact getEmptyContact(){
		Contact contact = new Contact();
		contact.setPerson(new Person());
		contact.setVerification(false);
		return contact;
	}
	
	public void removeChildFromContact(Contact contact, Child child) {
		ContactFor cf = contactForRepository.getByContactAndChild(contact.getId(), child.getId());
		if (cf != null)
			contactForRepository.delete(cf);
	}
	
	public boolean isContact(Person person) {
		for (Contact c : this.contactRepository.findAll()) {
			if (c.getPerson().equals(person))
				return true;
		}
		return false;
	}

}
