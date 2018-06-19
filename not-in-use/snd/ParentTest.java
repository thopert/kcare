package tests;

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
 * Tests for {@link Parent}.
 * @author
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Main.class)
@WebAppConfiguration
public class ParentTest {

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ParentService parentService;
    
    @Autowired
    private ChildService childService;

    @Test
    public void testEquality() {
        Parent pr1 = new Parent();
        Parent pr2 = new Parent();
        Person p1 = new Person();
        Person p2 = new Person();
        Person p3 = new Person();
        Person p4 = new Person();
        pr1.setMother(p1);
        pr1.setFather(p2);
        pr2.setMother(p3);
        pr2.setFather(p4);
        p1.setFirstName("Bob");
        p1.setLastName("Test");
        p2.setFirstName("Bob");
        p2.setLastName("Alpha");
        p3.setFirstName("Bob");
        p3.setLastName("Test");
        p4.setFirstName("Bob");
        p4.setLastName("Beta");
        Assert.assertFalse("Wrong equality on different parents, where mother " + pr1.getMother() + ", father " + pr1.getFather() + " and mother " + pr2.getMother() + ", father " + pr2.getFather(), pr1.equals(pr2));
        p4.setLastName("Alpha");
        Assert.assertTrue("Wrong equality on same parents, where mother " + pr1.getMother() + ", father " + pr1.getFather() + " and mother " + pr2.getMother() + ", father " + pr2.getFather(), pr1.equals(pr2));
    }

    @Test
    public void testUser() {
        Parent pr1 = new Parent();
        /*Person p1 = new Person();
        Person p2 = new Person();*/
        User u1 = new User();
        /*p1.setFirstName("Bob");
        p1.setLastName("Test");
        p2.setFirstName("Bob");
        p2.setLastName("Alpha");*/
        u1.setUserName("lol");
        u1.setPassword("abc");
        pr1.setUser(u1);
        /*pr1.setMother(p1);
        pr1.setFather(p2);*/
        Assert.assertEquals("Got wrong username from parents: " + pr1.getUser().getUserName() + " instead of lol", pr1.getUser().getUserName(), "lol");
        Assert.assertEquals("Got wrong password-hash from parents: " + pr1.getUser().getPassword() + "instead of a9993e364706816aba3e25717850c26c9cd0d89d", pr1.getUser().getPassword(), "a9993e364706816aba3e25717850c26c9cd0d89d");
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testGetChildrenByParent() {
    	Parent p = new  Parent();
    	User u1 = new User();
    	Child c = new Child();
    	u1.setUserName("whaddup");
    	p.setUser(u1);
    	Person p1 = new Person();
        Person p2 = new Person();
        Person p3 = new Person();
        p1.setFirstName("So");
        p1.setLastName("many");
        p2.setFirstName("different");
        p2.setLastName("names");
        p3.setFirstName("its");
        p3.setLastName("annoyingAF");
        p.setMother(p1);
        p.setFather(p2);
        c.setParents(p);
        c.setPerson(p3);
        parentService.saveParent(p);
        childService.saveChild(c);

        List<Child> chs = new ArrayList<>();
        chs.add(c);
        List<Child> chs2 = parentRepository.getChildrenByParent(p.getId());
        Assert.assertEquals("getChildrenByParent didn't return correct children-list", chs, chs2);
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testGetByMotherOrFather() {
    	Parent p = new  Parent();
    	User u1 = new User();
    	u1.setUserName("ya-boi");
    	p.setUser(u1);
    	Person p1 = new Person();
        Person p2 = new Person();
        p1.setFirstName("How");
        p1.setLastName("Bout");
        p2.setFirstName("Dat");
        p2.setLastName("Son");
        p.setMother(p1);
        p.setFather(p2);
    	parentService.saveParent(p);
        Assert.assertNotNull("Person doesn't have a parent-entry", parentRepository.getByMotherOrFather(p1.getId()));
        Assert.assertNotNull("Person doesn't have a parent-entry", parentRepository.getByMotherOrFather(p2.getId()));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testGetByUser() {
    	Parent p = new  Parent();
    	User u1 = new User();
    	u1.setUserName("mitch-the-turtle");
    	p.setUser(u1);
    	Person p1 = new Person();
        Person p2 = new Person();
        p1.setFirstName("Tiny");
        p1.setLastName("Hands");
        p2.setFirstName("Love");
        p2.setLastName("Russia");
        p.setMother(p1);
        p.setFather(p2);
    	parentService.saveParent(p);
    	Assert.assertEquals("getByUser didn't return correct user!", p, parentRepository.getByUser(u1.getId()));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testGetUserByParent() {
    	Parent p = new  Parent();
    	User u1 = new User();
    	u1.setUserName("Userboi");
    	p.setUser(u1);
    	Person p1 = new Person();
        Person p2 = new Person();
        p1.setFirstName("Bob");
        p1.setLastName("Test");
        p2.setFirstName("Bob");
        p2.setLastName("Alpha");
        p.setMother(p1);
        p.setFather(p2);
    	parentService.saveParent(p);
        Assert.assertEquals("getUserByParent didn't return correct user!", u1, parentRepository.getUserByParent(p.getId()));
    }

    @Test
    public void testGetActiveParents() {
        List<Parent> ps = parentRepository.getActiveParents();
        Assert.assertNotNull("Invalid list of active parents!", ps);
    }

}
