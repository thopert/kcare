package tests;

import java.util.Date;

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

/**
 * Tests for {@link Person}.
 * @author
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Main.class)
@WebAppConfiguration
public class PersonTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testSetGetFirstName(){
        Person p = new Person();
        String name = "Manuel";
        p.setFirstName(name);
        Assert.assertEquals(name, p.getFirstName());
    }

    @Test
    public void testSetGetLastName(){
        Person p = new Person();
        String name = "Middleditch";
        p.setLastName(name);
        Assert.assertEquals(name, p.getLastName());
    }

    @Test
    public void testSetGetSex(){
        Person p = new Person();
        p.setSex(true);
        Assert.assertTrue(p.getSex());
    }

    @Test
    public void testSetGetMail(){
        Person p = new Person();
        String mail = "back@it.again";
        p.setMail(mail);
        Assert.assertEquals(mail, p.getMail());
    }

    @Test
    public void testSetGetPhone(){
        Person p = new Person();
        String phone = "18235671234";
        p.setPhone(phone);
        Assert.assertEquals(phone, p.getPhone());
        String businessPhone = "32457345";
        p.setBusinessPhone(businessPhone);
        Assert.assertEquals(businessPhone, p.getBusinessPhone());
    }

    @Test
    public void testSetGetBirthDate(){
        Person p = new Person();
        Integer year = 2013;
        Integer month = 4;
        Integer day = 5;
        Date date = new Date(year-1900, month, day);
        p.setBirthDate(date);
        Assert.assertEquals(date, p.getBirthDate());
        p = new Person();
        p.setBirthDate(year, month, day);
        Assert.assertEquals(date, p.getBirthDate());
        p.setBirthDate("Das ist ein falscher Input");
    }

    @Test
    public void testPictures(){
        //doesn't really do much yet
        Person p = new Person();
        Assert.assertNull(p.getDisplayPicture());
        Assert.assertNull(p.setPictures());
    }

    @Test
    public void testHashCode(){
        Person p = new Person();
        Integer hashResult = 413; //only for this id
        Assert.assertEquals(hashResult, (Integer) p.hashCode());
    }

    @Test
    public void testEquality() {
        Person p1 = new Person();
        p1.setFirstName("Bob");
        p1.setLastName("Test");
        Person p2 = new Person();
        p2.setLastName("Alpha");
        p2.setFirstName("Ted");
        Assert.assertFalse("Wrong equality on different people", p1.equals(p2));
        p2.setFirstName("Bob");
        Assert.assertFalse("Wrong equality on different people with same firstname", p1.equals(p2));
        p2.setFirstName("Ted");
        p2.setLastName("Test");
        Assert.assertFalse("Wrong equality on different people with same lastname", p1.equals(p2));
        p2.setFirstName("Bob");
        Assert.assertTrue("Wrong equality on same people", p1.equals(p2));

        Assert.assertFalse(p1.equals((Object) null));
        Assert.assertFalse(p1.equals((Integer) 3));
        p1 = new Person();
        Assert.assertFalse(p1.equals(p2));
        p1.setFirstName("Hans");
        Assert.assertFalse(p1.equals(p2));
        p1.setLastName("Peter");
        p2 = new Person();
        Assert.assertFalse(p1.equals(p2));
        p2.setFirstName("Gustl");
        Assert.assertFalse(p1.equals(p2));

    }

    @Test
    public void testGetById() {
        Assert.assertNotNull("Person with Id 1 is null!", personRepository.getById(1L));
        Assert.assertNull("Person with Id 3 is not null!", personRepository.getById(9999L));
    }

    @Test
    public void testGetByMail() {
        Assert.assertNotNull("Person not found with mail-address: rz@mail.com", personRepository.getByMail("rz@mail.com"));
        Assert.assertNull("Person found with non-existing mail-address: abcdefg@mail.com!", personRepository.getByMail("abcdefg@mail.com"));
    }

    @Test
    public void testGetByPhone() {
        Assert.assertNotNull("No person found with phone-number: 123456789!", personRepository.getByPhone("123456789"));
        Assert.assertNull("Person found with wrong phone-number: 111111111!", personRepository.getByPhone("111111111"));
    }

    @Test
    public void testGetByBusinessPhone() {
        Assert.assertNotNull("No person found with business-phone-number: 1234897235!", personRepository.getByBusinessphone("1234897235"));
        Assert.assertNull("Person found with wrong business-phone-number: 111111111!", personRepository.getByBusinessphone("111111111"));
    }

}
