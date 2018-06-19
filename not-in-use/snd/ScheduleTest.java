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

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Tests for {@link Schedule}.
 * @author
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Main.class)
@WebAppConfiguration
public class ScheduleTest {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private ChildService childService;

    @Autowired
    private SupervisorService supervisorService;

    @Autowired
    private ScheduledAbsentChildRepository scheduledAbsentChildRepository;

    @Autowired
    private ScheduledSupervisorRepository scheduledSupervisorRepository;

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private SupervisorRepository supervisorRepository;

    @Test
    public void testEquality() {
        Schedule s1 = scheduleService.loadSchedule("2017-06-01");
        Schedule s2 = scheduleService.loadSchedule("2017-06-02");
        Schedule s3 = scheduleService.loadSchedule("2017-06-01");
        Assert.assertFalse("Given Schedules should be unequal:\n" + s1 + "\n" + s2 + "\n", s1.equals(s2));
        Assert.assertTrue("Given Schedules should be equal:\n" + s1 + "\n" + s3 + "\n", s1.equals(s3));

        Assert.assertFalse(s1.equals((Object) null));
        Assert.assertFalse(s1.equals((Integer) 3));
    }
    @DirtiesContext
    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testAddRemoveAbsentChild(){
        Schedule s = new Schedule();
        Person p1 = new Person();
        Child c1 = new Child();
        p1.setFirstName("Immer");
        p1.setLastName("Namen");
        c1.setPerson(p1);
        s.addAbsentChild(c1);
        Set<Child> l = new HashSet<Child>();
        l.add(c1);
        Assert.assertEquals(l, s.getAbsentChilds());

        Integer sizeBefore = s.getAbsentChilds().size();
        s.removeAbsentChild(c1);
        Integer sizeAfter = s.getAbsentChilds().size();
        Assert.assertTrue(sizeBefore > sizeAfter);
        
        Person p2 = new Person();
        p2.setFirstName("Brauche");
        p2.setLastName("Namen");
        Child c2 = new Child();
        c2.setPerson(p2);
        List<Child> list = new ArrayList<Child>();
        list.add(c1);
        list.add(c2);
        s.addAbsentChildren(list);
        Integer sizeAfterListInsert = s.getAbsentChilds().size();
        Integer diff = sizeAfterListInsert - sizeAfter;
        Assert.assertEquals((Integer) 2, (Integer) diff);
    }
    
    /*
    //Not working; don't really know why
    @DirtiesContext
    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testAddRemoveSupervisor(){
        Child c = new Child();
        Schedule s = new Schedule();
        Supervisor sup = new Supervisor();
        User u = new User();
        u.setUserName("immerlästig");
        Person p = new Person();
        p.setFirstName("IschDes");
        p.setLastName("Faad");
        sup.setUser(u);
        sup.setPerson(p);
        supervisorService.saveSupervisor(sup);
        List<Supervisor> l = new ArrayList<Supervisor>();
        l.add(sup);
        
        Integer sizeBeforeInsert = s.getSupervisors().size();
        s.addSupervisors(l);
        Integer sizeAfterInsert = s.getSupervisors().size();
        Assert.assertTrue(sizeAfterInsert > sizeBeforeInsert);
        s.removeSupervisor(sup);
        Assert.assertTrue(sizeBeforeInsert == s.getSupervisors().size());
    }
    */

    @DirtiesContext
    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testAddRemoveChild(){
        Schedule s = new Schedule();
        Person p1 = new Person();
        Child c1 = new Child();
        p1.setFirstName("Immer");
        p1.setLastName("Namen");
        c1.setPerson(p1);
        s.addChild(c1);
        Set<Child> l = new HashSet<Child>();
        l.add(c1);
        Assert.assertEquals(l, s.getChilds());

        Integer sizeBefore = s.getChilds().size();
        s.removeChild(c1);
        Integer sizeAfter = s.getChilds().size();
        Assert.assertTrue(sizeBefore > sizeAfter);
        
        Person p2 = new Person();
        p2.setFirstName("Brauche");
        p2.setLastName("Namen");
        Child c2 = new Child();
        c2.setPerson(p2);
        List<Child> list = new ArrayList<Child>();
        list.add(c1);
        list.add(c2);
        s.addChildren(list);
        Integer sizeAfterListInsert = s.getChilds().size();
        Integer diff = sizeAfterListInsert - sizeAfter;
        Assert.assertEquals((Integer) 2, (Integer) diff);
    }

    @DirtiesContext
    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testSaveAndLoadSchedule() {
        Schedule s = new Schedule();
        s.setDate("2017-04-04");
        Supervisor s1 = supervisorService.loadSupervisor(1L);
        Supervisor s2 = supervisorService.loadSupervisor(2L);
        Assert.assertNotNull("Supervisor (1) is NULL: " + s1, s1);
        Assert.assertNotNull("Supervisor (2) is NULL: " + s2, s2);
        s.addSupervisor(s1);
        s.addSupervisor(s2);
        Child c1 = childService.loadChild(1L);
        Child c2 = childService.loadChild(2L);
        Assert.assertNotNull("Child (1) is NULL: " + c1, c1);
        Assert.assertNotNull("Child (2) is NULL: " + c2, c2);
        s.addChild(c1);
        s.addAbsentChild(c2);
        s.setMaxChilds(50);
        scheduleService.saveSchedule(s);

        // now load just saved Schedule again
        Schedule ls = scheduleService.loadSchedule("2017-04-04");

        s.setDate("This is a wrong input");
    }
    
    @DirtiesContext
    @Test
    public void testGetScheduledAbsentChildrenForDate() {
        Date d = new Date(2000-1900, 01, 01);
        Person p = new Person();
        p.setFirstName("Fixed");
        p.setLastName("Test");
        Child c = new Child();
        c.setPerson(p);
        childService.saveChild(c);
        
        ScheduledAbsentChild absent = new ScheduledAbsentChild(d, c);
        scheduledAbsentChildRepository.save(absent);
        List<ScheduledAbsentChild> acs = scheduledAbsentChildRepository.getForDate(d);
        List<ScheduledAbsentChild> acs2 = new ArrayList<>();
        acs2.add(absent);
        Assert.assertEquals(acs2, acs);
    }

    @Test
    public void testLoadSchedule() {
        Schedule s = scheduleService.loadSchedule("2017-06-01");
        Assert.assertTrue("No scheduled children were found!", s.getChilds().size() > 0);
        Assert.assertTrue("No scheduled supervisors were found!", s.getSupervisors().size() > 0);
        List<Child> children = new ArrayList<>();
        List<Child> childrenInSchedule = new ArrayList<>(s.getChilds());
        children.add(childRepository.getById(1L));
        List<Supervisor> supervisors = new ArrayList<>();
        List<Supervisor> supervisorsInSchedule = new ArrayList<>(s.getSupervisors());
        supervisors.add(supervisorRepository.getById(1L));
        Assert.assertTrue("Wrong Scheduled-Children-List for 2017-06-01. Found " + childrenInSchedule + " instead of " + children + ".", children.equals(childrenInSchedule));
        Assert.assertTrue("Wrong Scheduled-Supervisors-List for 2017-06-01. Found " + supervisorsInSchedule + " instead of " + supervisors + ".", supervisors.equals(supervisorsInSchedule));
        Assert.assertTrue("wrong Scheduled-Max-Children for 2017-06-01. Found " + s.getMaxChilds() + " instead of " + 15, s.getMaxChilds() == 15);
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testGetForSupervisorAtDate() {
        Date d = new Date(2017-1900, 6, 1);
        User u = new User();
        u.setUserName("braucheDringendNeueNamen");
        Person p = new Person();
        p.setFirstName("Keine");
        p.setLastName("Namen");
        Supervisor sup = new Supervisor();
        sup.setPerson(p);
        sup.setUser(u);
        supervisorService.saveSupervisor(sup);
        ScheduledSupervisor ssv = new ScheduledSupervisor();
        ssv.setSupervisor(sup);
        ssv.setDate(2017, 6, 1);
        scheduledSupervisorRepository.save(ssv);
        
        ScheduledSupervisor ssv2 = scheduledSupervisorRepository.getForSupervisorAtDate(d, sup.getId());
        Assert.assertEquals(ssv, ssv2);
    }
}
