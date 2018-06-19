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
 * Tests for {@link LunchRepository}.
 * @author
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Main.class)
@WebAppConfiguration
public class LunchRepositoryTest {
	
	@Autowired
	private LunchRepository lunchRepository;
	
	@Autowired
	private ChildRepository childRepository;

	@Autowired
	private PersonRepository personRepository;
		
		
	@Test
	public void testGetByDate() {
		
		Date date = new Date(2000-1901, 0, 1);
		
		Lunch lun = new Lunch();
		Person p = new Person();
		Child ch = new Child();
		ArrayList ar = new ArrayList();
		
		p.setFirstName("Gustl");
		p.setLastName("Mayer");
		personRepository.save(p);
		ch.setPerson(p);
		lun.setDate(date);
		childRepository.save(ch);
		lun.setChild(ch);
		lunchRepository.save(lun);
		ar.add(lun);
		
		Assert.assertEquals("Received " + lunchRepository.getByDate(date) + " instead of : " + ar, lunchRepository.getByDate(date), ar);
	}
	
	@Test
	public void testGetById() {
		Date date = new Date(2000-1902, 0, 1);

		Lunch lun = new Lunch();
		Person p = new Person();
		Child ch = new Child();

		p.setFirstName("Hansi");
		p.setLastName("Müller");
		personRepository.save(p);
		ch.setPerson(p);
		lun.setDate(date);
		childRepository.save(ch);
		lun.setChild(ch);
		lunchRepository.save(lun);

		Assert.assertEquals("Received " + lunchRepository.getById(lun.getId()) + " instead of : " + lun , lunchRepository.getById(lun.getId()), lun);	
	} 

	@Test
	public void testGetByChild() {
		Date date = new Date(2000-1903, 0, 1);
		
		Lunch lun = new Lunch();
		Person p = new Person();
		Child ch = new Child();
		ArrayList ar = new ArrayList();
		
		p.setFirstName("Marta");
		p.setLastName("Merten");
		personRepository.save(p);
		ch.setPerson(p);
		lun.setDate(date);
		childRepository.save(ch);
		lun.setChild(ch);
		lunchRepository.save(lun);
		ar.add(lun);

		Assert.assertEquals("Received " + lunchRepository.getByChild(ch.getId()) + " instead of : " + ar , lunchRepository.getByChild(ch.getId()), ar);
	}
}
