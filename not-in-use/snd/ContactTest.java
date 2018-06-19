package snd;

import at.qe.sepm.skeleton.Main;
import at.qe.sepm.skeleton.model.*;
import at.qe.sepm.skeleton.repositories.*;
import at.qe.sepm.skeleton.services.*;
import at.qe.sepm.skeleton.ui.beans.*;

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

import java.util.List;
import java.util.ArrayList;

/**
 * Tests for {@link Contact}.
 * @author
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Main.class)
@WebAppConfiguration
public class ContactTest {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ContactForRepository contactForRepository;

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private ContactService contactService;
    
    @Autowired
    private ChildService childService;

    @Test
    public void testEquality() {
        Contact c1 = new Contact();
        Contact c2 = new Contact();
        Person p1 = new Person();
        Person p2 = new Person();
        c1.setPerson(p1);
        c2.setPerson(p2);
        p1.setFirstName("Bob");
        p1.setLastName("Test");
        p2.setFirstName("Bob");
        p2.setLastName("Alpha");
        Assert.assertFalse("Wrong equality on different contacts", c1.equals(c2));
        p2.setLastName("Test");
        Assert.assertTrue("Wrong equality on same contacts", c1.equals(c2));
    }

    @Test
    public void testGetContactsByChild() {
    	Contact c1 = new Contact();
    	Child c = new Child();
        Person p1 = new Person();
        Person p2 = new Person();
        c1.setPerson(p1);
        c.setPerson(p2);
        p1.setFirstName("Bob");
        p1.setLastName("Test");
        p2.setFirstName("Bob");
        p2.setLastName("Alpha");
        childService.saveChild(c);
        contactService.saveContact(c1);
        contactService.addChildToContact(c1, c);
        ContactFor confor = new ContactFor();
        confor.setChild(c);
        confor.setContact(c1);
        
        List<Contact> cts = contactRepository.getContactsByChild(c.getId());
        List<Contact> cts2 = new ArrayList<>();
        cts2.add(c1);
        Assert.assertEquals("getContactsByChild returned wrong list!", cts, cts2);
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void backendTest() {
    	Contact c1 = new Contact();
    	Child c = new Child();
        Person p1 = new Person();
        Person p2 = new Person();
        c1.setPerson(p1);
        c.setPerson(p2);
        p1.setFirstName("Bob");
        p1.setLastName("Test");
        p2.setFirstName("Bob");
        p2.setLastName("Alpha");
        childService.saveChild(c);
        contactService.saveContact(c1);
        contactService.addChildToContact(c1, c);
        ContactFor confor = new ContactFor();
        confor.setChild(c);
        confor.setContact(c1);
        Assert.assertEquals(confor, contactForRepository.getByContactAndChild(c1.getId(), c.getId()));
    }

    @DirtiesContext //resets database after this test, because other tests could fail because of that
    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testAddRemoveChildren() {
        Contact co = contactRepository.getById(2L);
        Child ch = childRepository.getById(1L);
        contactService.addChildToContact(co, ch);
        contactService.removeChildFromContact(co, ch);
    }

}
