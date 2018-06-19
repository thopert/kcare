package snd;

import at.qe.sepm.skeleton.Main;
import at.qe.sepm.skeleton.model.*;
import at.qe.sepm.skeleton.services.*;
import at.qe.sepm.skeleton.ui.beans.*;
import at.qe.sepm.skeleton.repositories.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.internal.util.collections.Sets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;
import java.util.ArrayList;

/**
 * Tests for {@link ContactPickupRepository}.
 * @author
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Main.class)
@WebAppConfiguration
public class ContactPickupRepositoryTest {
	
	@Autowired
	private ContactPickupRepository contactPickupRepository;
	
	@Autowired
	private ChildRepository childRepository;

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private ContactService contactService;

	@Autowired
	private ChildService childService;

	@Test
	public void testGetById() {

		ContactPickup cP = new ContactPickup();
		Person p = new Person();
		Contact c = new Contact();
		Child ch = new Child();

		p.setFirstName("Manuel");
		p.setLastName("Müller");
		personRepository.save(p);
		c.setPerson(p);
		contactService.saveContact(c);
		
		cP.setContact(c);
		childRepository.save(ch);
		contactPickupRepository.save(cP);

		Assert.assertEquals("Received " + contactPickupRepository.getById(cP.getId()) + " instead of : " + cP , contactPickupRepository.getById(cP.getId()), cP);	
	} 
	
	@Test
	public void testGetByDate() {
		ContactPickup cP = new ContactPickup();
		Person p = new Person();
		Contact c = new Contact();
		ArrayList ar = new ArrayList();

		Date date = new Date(2000-1903, 0, 1);
		java.sql.Date sqlDate = new java.sql.Date(date.getTime()); //Java dates...
		p.setFirstName("Manuel");
		p.setLastName("Müller");
		personRepository.save(p);
		c.setPerson(p);
		contactService.saveContact(c);
		cP.setDate(sqlDate);
		contactPickupRepository.save(cP);
		ar.add(cP);

		Assert.assertEquals("Received " + contactPickupRepository.getByDate(sqlDate) + " instead of : " + ar, contactPickupRepository.getByDate(sqlDate), ar);
	}
 
	@Test
	public void testGetByChild() {
		ContactPickup cP = new ContactPickup();
		Person p = new Person();
		Person p2 = new Person();
		Contact c = new Contact();
		Child ch = new Child();
		ArrayList ar = new ArrayList();

		Date date = new Date(2000-1904, 0, 1);
		java.sql.Date sqlDate = new java.sql.Date(date.getTime()); //Java dates...
		p.setFirstName("Elias");
		p.setLastName("Günther");
		p2.setFirstName("Andreas");
		p2.setLastName("Westreicher");
		personRepository.save(p);
		personRepository.save(p2);

		c.setPerson(p);
		contactService.saveContact(c);
		ch.setPerson(p2);
		childService.saveChild(ch);
		cP.setDate(sqlDate);
		cP.setContact(c);
		cP.setChild(ch);
		contactPickupRepository.save(cP);
		ar.add(cP);

		Assert.assertEquals("Received " + contactPickupRepository.getByChild(ch.getId()) + " instead of : " + ar, contactPickupRepository.getByChild(ch.getId()), ar);
	}

	@Test
	public void testGetByContact() {
		ContactPickup cP = new ContactPickup();
		Person p = new Person();
		Person p2 = new Person();
		Contact c = new Contact();
		Child ch = new Child();
		ArrayList ar = new ArrayList();

		Date date = new Date(2000-1901, 0, 1);
		java.sql.Date sqlDate = new java.sql.Date(date.getTime()); //Java dates...
		p.setFirstName("Friedrich");
		p.setLastName("Göthe");
		p2.setFirstName("Frank");
		p2.setLastName("Mozart");
		personRepository.save(p);
		personRepository.save(p2);

		c.setPerson(p);
		contactService.saveContact(c);
		ch.setPerson(p2);
		childService.saveChild(ch);
		cP.setDate(sqlDate);
		cP.setContact(c);
		cP.setChild(ch);
		contactPickupRepository.save(cP);
		ar.add(cP);

		Assert.assertEquals("Received " + contactPickupRepository.getByContact(c.getId()) + " instead of : " + ar, contactPickupRepository.getByContact(c.getId()), ar);
	}

	@Test
	public void testGetByDateContactChild() {
		ContactPickup cP = new ContactPickup();
		Person p = new Person();
		Person p2 = new Person();
		Contact c = new Contact();
		Child ch = new Child();
		ArrayList ar = new ArrayList();

		Date date = new Date(2000-1901, 0, 1);
		java.sql.Date sqlDate = new java.sql.Date(date.getTime()); //Java dates...
		p.setFirstName("Gustl");
		p.setLastName("Göthe");
		p2.setFirstName("Hansi");
		p2.setLastName("Mozart");
		personRepository.save(p);
		personRepository.save(p2);

		c.setPerson(p);
		contactService.saveContact(c);
		ch.setPerson(p2);
		childService.saveChild(ch);
		cP.setDate(sqlDate);
		cP.setContact(c);
		cP.setChild(ch);
		contactPickupRepository.save(cP);

		ContactPickup cP2 = contactPickupRepository.getByDateContactChild(date, c.getId(), ch.getId());
		Assert.assertEquals(cP, cP2);
	}
}
