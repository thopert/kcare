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
 * Tests for {@link MessageRepository}.
 * @author
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Main.class)
@WebAppConfiguration
public class MessageRepositoryTest {

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private PersonRepository personRepository;

	@Test
	public void testGetById(){
		Person p = new Person();
		Message mes = new Message();

		Date date = new Date(2000-1902, 0, 1);
		java.sql.Date sqlDate = new java.sql.Date(date.getTime()); //Java dates...
		java.sql.Timestamp timestamp = new java.sql.Timestamp(sqlDate.getTime());
		p.setFirstName("Hansjörg");
		p.setLastName("Unterguggenhofer");
		personRepository.save(p);
		mes.setAuthor(p);
		mes.setDateTime(timestamp);
		messageRepository.save(mes);

		Assert.assertEquals("Received " + messageRepository.getById(mes.getId()) + " instead of : " + mes, messageRepository.getById(mes.getId()), mes);
	}

	@Test
	public void testGetByDate() {
		Person p = new Person();
		Message mes = new Message();
		ArrayList ar = new ArrayList();

		Date date = new Date(2000-1903, 0, 1);
		java.sql.Date sqlDate = new java.sql.Date(date.getTime()); //Java dates...
		java.sql.Timestamp timestamp = new java.sql.Timestamp(sqlDate.getTime());
		p.setFirstName("Ahmed");
		p.setLastName("Karapence");
		personRepository.save(p);
		mes.setAuthor(p);
		mes.setDateTime(timestamp);
		messageRepository.save(mes);
		ar.add(mes);

		Assert.assertEquals("Received " + messageRepository.getByDate(timestamp) + " instead of : " + ar, messageRepository.getByDate(timestamp), ar);
	}

	@Test
	public void testGetByAuthor() {
		Person p = new Person();
		Message mes = new Message();
		ArrayList ar = new ArrayList();

		Date date = new Date(2000-1904, 0, 1);
		java.sql.Date sqlDate = new java.sql.Date(date.getTime()); //Java dates...
		java.sql.Timestamp timestamp = new java.sql.Timestamp(sqlDate.getTime());
		p.setFirstName("Mustafa");
		p.setLastName("Mustafi");
		personRepository.save(p);
		mes.setAuthor(p);
		mes.setDateTime(timestamp);
		messageRepository.save(mes);
		ar.add(mes);
	
		Assert.assertEquals("Received " + messageRepository.getByAuthor(p.getId()) + " instead of : " + ar, messageRepository.getByAuthor(p.getId()), ar);
	}
}