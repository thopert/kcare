package tests;

import at.qe.sepm.skeleton.Main;
import at.qe.sepm.skeleton.model.*;
import at.qe.sepm.skeleton.services.*;
import at.qe.sepm.skeleton.repositories.*;
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

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Time;

/**
 * Tests for {@link Child}.
 * @author
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Main.class)
@WebAppConfiguration
public class ChildTest {

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private ChildService childService;

    @Test
    public void testPickTimes(){
        Child c1 = new Child();
        Integer hoursBegin = 12;
        Integer minutesBegin = 15;
        Time pickTimeBegin = new Time(hoursBegin, minutesBegin, 0);
        c1.setPickTimeBegin(hoursBegin, minutesBegin);
        Assert.assertEquals("setPickTimeBegin set wrong time", pickTimeBegin.toString().substring(0, 5), c1.getPickTimeBegin());
        String strPickTimeBegin = pickTimeBegin.toString().substring(0, 5);
        c1.setPickTimeBegin(strPickTimeBegin);
        Assert.assertEquals("setPickTimeBegin set wrong time", strPickTimeBegin, c1.getPickTimeBegin());
    
        Integer hoursEnd = 13;
        Integer minutesEnd = 15;
        Time pickTimeEnd = new Time(hoursEnd, minutesEnd, 0);
        c1.setPickTimeEnd(hoursEnd, minutesEnd);
        Assert.assertEquals("setPickTimeEnd set wrong time", pickTimeEnd.toString().substring(0, 5), c1.getPickTimeEnd());
        String strPickTimeEnd = pickTimeEnd.toString().substring(0, 5);
        c1.setPickTimeEnd(strPickTimeEnd);
        Assert.assertEquals("setPickTimeEnd set wrong time", strPickTimeEnd, c1.getPickTimeEnd());

        String pickTime = pickTimeBegin.toString().substring(0, 5) + " - " + pickTimeEnd.toString().substring(0, 5);
        Assert.assertEquals("getPickTime returned wrong times", pickTime, c1.getPickTime());
    }   

    @Test
    public void testPutTimes(){
        Child c1 = new Child();
        Integer hoursBegin = 12;
        Integer minutesBegin = 15;
        Time putTimeBegin = new Time(hoursBegin, minutesBegin, 0);
        c1.setPutTimeBegin(hoursBegin, minutesBegin);
        Assert.assertEquals("setPutTimeBegin set wrong time", putTimeBegin.toString().substring(0, 5), c1.getPutTimeBegin());
        String strPutTimeBegin = putTimeBegin.toString().substring(0, 5);
        c1.setPutTimeBegin(strPutTimeBegin);
        Assert.assertEquals("setPutTimeBegin set wrong time", strPutTimeBegin, c1.getPutTimeBegin());
    
        Integer hoursEnd = 13;
        Integer minutesEnd = 15;
        Time putTimeEnd = new Time(hoursEnd, minutesEnd, 0);
        c1.setPutTimeEnd(hoursEnd, minutesEnd);
        Assert.assertEquals("setPutTimeEnd set wrong time", putTimeEnd.toString().substring(0, 5), c1.getPutTimeEnd());
        String strPutTimeEnd = putTimeEnd.toString().substring(0, 5);
        c1.setPutTimeEnd(strPutTimeEnd);
        Assert.assertEquals("setPutTimeEnd set wrong time", strPutTimeEnd, c1.getPutTimeEnd());

        String putTime = putTimeBegin.toString().substring(0, 5) + " - " + putTimeEnd.toString().substring(0, 5);
        Assert.assertEquals("getPutTime returned wrong times", putTime, c1.getPutTime());
    } 

    @Test
    public void testRegistrationDate(){
        Child c1 = new Child();
        Integer year = 2015;
        Integer month = 11;
        Integer day = 14;
        Date registrationDate = new Date(year, month, day);
        c1.setRegistrationDate(year, month, day);
        Assert.assertEquals("setRegistrationDate set wrong date", registrationDate, c1.getRegistrationDate());
        c1.setRegistrationDate(registrationDate);
        Assert.assertEquals("setRegistrationDate set wrong date", registrationDate, c1.getRegistrationDate());
    }

    @Test
    public void testCancellationDate(){
        Child c1 = new Child();
        Integer year = 2015;
        Integer month = 11;
        Integer day = 14;
        Date cancellationDate = new Date(year, month, day);
        c1.setCancellationDate(year, month, day);
        Assert.assertEquals("setCancellationDate set wrong date", cancellationDate, c1.getCancellationDate());
        c1.setCancellationDate(cancellationDate);
        Assert.assertEquals("setCancellationDate set wrong date", cancellationDate, c1.getCancellationDate());
    }

    @Test
    public void testNotes(){
        Child c1 = new Child();
        String notes = "Das ist eine wichtige Notiz";
        c1.setNotes(notes);
        Assert.assertEquals("setNotes/getNotes returned wrong notes", notes, c1.getNotes());
    }

    @Test
    public void testSetGetParents(){
        Child c = new Child();
        Parent parents = new Parent();
        Person p1 = new Person();
        Person p2 = new Person();
        p1.setFirstName("Hugo");
        p1.setLastName("Boss");
        p2.setFirstName("Bob");
        p2.setLastName("Ross");
        parents.setFather(p1);
        parents.setMother(p2);
        c.setParents(parents);
        Assert.assertEquals("setParents/getParents returned wrong parents", parents, c.getParents());

    }

    @Test
    public void testEquality() {
        Child c1 = new Child();
        Child c2 = new Child();
        Person p1 = new Person();
        Person p2 = new Person();
        c1.setPerson(p1);
        c2.setPerson(p2);
        p1.setFirstName("Bob");
        p1.setLastName("Test");
        p2.setFirstName("Bob");
        p2.setLastName("Alpha");
        Assert.assertFalse("Wrong equality on different children", c1.equals(c2));
        p2.setLastName("Test");
        Assert.assertTrue("Wrong equality on same children", c1.equals(c2));
        Assert.assertFalse("Wrong equality when passed null", c1.equals((Object) null));
        Assert.assertFalse("Wrong equality when passed wrong type", c1.equals(3));
        Child c3 = new Child();
        Assert.assertFalse("Wrong equality when passed empty child", c1.equals(c3));
        Assert.assertFalse("Wrong equality when passed checking with empty child", c3.equals(c1));
    }

    @Test
    public void testGetById() {
        Assert.assertNotNull("Child with id 1 was not found!", childRepository.getById(1L));
        Assert.assertNull("Child with id 3 was found!", childRepository.getById(9999L));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testGetByName() {
        Person p = new Person();
        p.setFirstName("Daniel");
        p.setLastName("Jaeger");
        Child c = new Child();
        c.setPerson(p);
        childService.saveChild(c);
        List cs = new ArrayList();
        cs.add(c);
        Assert.assertTrue("Child 'Daniel Jaeger' was not found after saving it!", cs.equals(childRepository.getChildrenByName("Daniel", "Jaeger")));
    }

    @Test
    public void testGetPersonOfChild() {
        Assert.assertNotNull("Child 1 has no person!", childRepository.getPersonOfChild(1L));
    }

    @Test
    public void testGetParentsOfChild() {
        Assert.assertNotNull("Child 1 has no parents!", childRepository.getParentsOfChild(1L));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testChildrenWithNoParents() {
        Person p = new Person();
        p.setFirstName("Peter");
        p.setLastName("Schmitz");
        Child c = new Child();
        c.setPerson(p);
        childService.saveChild(c);
        List cnp = new ArrayList();
        cnp.add(c);
        List<Child> rcnp = childRepository.getChildrenNoParents();
//        Assert.assertTrue("There are more or less children with no parents: " + cnp + ", " + rcnp, cnp.equals(rcnp));
    }

    @Test
    public void testGetChildrenWithLowerCancellationDate() {
        Assert.assertNotNull("No children wrongly went past the cancellationdate!", childRepository.getChildrenWithLowerCancellationDate(new Date()));
    }

    @Test
    public void testToString(){
        Child c = new Child();
        String expectedString = "Child[ " + c.getId() + " ]";
        Assert.assertEquals("toString returned wrong string", expectedString, c.toString());
    }

}
