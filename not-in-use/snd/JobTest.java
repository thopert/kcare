package snd;

import at.qe.sepm.skeleton.Main;
import at.qe.sepm.skeleton.model.*;
import at.qe.sepm.skeleton.repositories.*;
import at.qe.sepm.skeleton.services.*;
import at.qe.sepm.skeleton.ui.beans.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Date;

/**
 * Tests for {@link Job}.
 * @author
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Main.class)
@WebAppConfiguration
public class JobTest {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private JobService jobService;
    
    @Autowired
    private ParentService parentService;
    
    @Autowired
    private PersonService personService;

    @Autowired
    private UserService userService;

    @Test
    public void testEquality() {
        Job j1 = new Job();
        Job j2 = new Job();
        j1.setTitle("abc");
        j2.setTitle("abd");
        j1.setDate("2000-01-01");
        Parent p1 = new Parent();
        j2.setParents(p1);
        Assert.assertFalse("Wrong equality on different jobs", j1.equals(j2));
        j2.setTitle("abc");
        j2.setDate("2000-01-01");
        j1.setParents(p1);
        Assert.assertTrue("Wrong equality on same jobs", j1.equals(j2));
    }

    @Test
    public void testSaveJob() {
        Job j = new Job();
        j.setTitle("Test");
        j.setDescription("Write this test");
        j.setDate("2000-01-05");
        j.setParents(parentRepository.getById(1L));
        j.setDone(false);

        jobService.saveJob(j);
    }

    @Test
    public void testGetById() {
        Job j = jobRepository.getById(1L);
        Assert.assertNotNull("Job (1) is Null!", j);
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testGetByDate() {
        Job j1 = new Job();
        j1.setTitle("abc");
        j1.setDate("2000-01-01");
        jobService.saveJob(j1);
        
        List<Job> js = jobRepository.getByDate(j1.getStartDate());
        Assert.assertTrue("job-list for start-date is empty!", js.size() > 0);
        js = jobRepository.getByEndDate(j1.getEndDate());
        Assert.assertTrue("job-list for end-date is empty!", js.size() > 0);
    }

    @Test
    public void testSetGetStartDate(){
        Job j = new Job();
        Integer year = 2015;
        Integer month = 11;
        Integer day = 14;
        Date startDate = new Date(year, month, day);
        j.setStartDate(year, month, day);
        Assert.assertEquals("setRegistrationDate set wrong date", startDate, j.getStartDate());
        j.setStartDate(startDate.toString());
        Assert.assertEquals("setRegistrationDate set wrong date", startDate, j.getStartDate());
        j.setStartDate("Das ist ein falscher Input");
        j.setDate("Das ist ein falscher Input");
    }

    @Test
    public void testSetGetEndDate(){
        Job j = new Job();
        Integer year = 2015;
        Integer month = 11;
        Integer day = 14;
        Date endDate = new Date(year, month, day);
        j.setEndDate(year, month, day);
        Assert.assertEquals("setRegistrationDate set wrong date", endDate, j.getEndDate());
        j.setEndDate(endDate.toString());
        Assert.assertEquals("setRegistrationDate set wrong date", endDate, j.getEndDate());
        j.setEndDate("Das ist ein falscher Input");
    }

    @Test
    public void testSetGetDaysBefore(){
        Job j = new Job();
        Integer days = 3;
        j.setDaysBefore(days);
        Assert.assertEquals("setDaysBefore set wrong amount of days", days, j.getDaysBefore());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testSetGetParents(){
        Person p1 = new Person();
        Person p2 = new Person();
        User u = new User();
        Parent p = new Parent();
        Job j = new Job();

        u.setUserName("diesTestGut");
        p1.setFirstName("Hansi");
        p1.setLastName("Hat");
        p2.setFirstName("Heiße");
        p2.setLastName("Hüte");
        p.setMother(p1);
        p.setFather(p2);
        p.setUser(u);
        parentService.saveParent(p);
        j.setParents(p);
        Assert.assertEquals("setParents set the wrong parents", p, j.getParents());
    }

    @Test
    public void testSetGetTitle(){
        Job j = new Job();
        String title = "Bester Jobname";
        j.setTitle(title);
        Assert.assertEquals("set/getTitle returned wrong title", title, j.getTitle());
    }

    @Test
    public void testSetGetDone(){
        Job j = new Job();
        j.setDone(true);
        Assert.assertTrue(j.getDone());
    }

    @Test
    public void testHashCode(){
        Job j = new Job();
        String title = "String for hash";
        j.setTitle(title);
        int hashResult = -1119914319; //for this specific title
        Assert.assertEquals("hashCode returned wrong hash", hashResult, j.hashCode());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testEquals(){
        Job j = new Job();
        Assert.assertFalse(j.equals((Object) null));
        Assert.assertFalse(j.equals((Integer) 3));
        Job j1 = new Job();
        Job j2 = new Job();
        j1.setTitle("JobNr1");
        j2.setTitle("JobNr2");
        Assert.assertFalse(j1.equals(j2));
        j1 = new Job();
        j2 = new Job();
        j1.setStartDate(2016, 7, 5);
        j2.setStartDate(2015, 2, 12);
        Assert.assertFalse(j1.equals(j2));
        j1 = new Job();
        j2 = new Job();
        j1.setEndDate(2016, 7, 5);
        j2.setEndDate(2015, 2, 12);
        Assert.assertFalse(j1.equals(j2));
        Person p1 = new Person();
        Person p2 = new Person();
        User u = new User();
        Parent p = new Parent();
        u.setUserName("newUserForTest");
        p1.setFirstName("Alle");
        p1.setLastName("Affen");
        p2.setFirstName("Angeln");
        p2.setLastName("Aale");
        p.setMother(p1);
        p.setFather(p2);
        p.setUser(u);
        userService.saveUser(u);
        personService.savePerson(p1);
        personService.savePerson(p2);
        parentService.saveParent(p);
        j1.setParents(p);
        Assert.assertFalse(j1.equals(j2));
    }

    @Test
    public void testToString(){
        Job j = new Job();
        String title = "Der Titel";
        j.setTitle(title);
        jobService.saveJob(j);
        String correctString = "Job[ " + title + " ]";
        Assert.assertEquals("toString returned wrong String", correctString, j.toString());
    }

    @Test
    public void testSetGetDescription(){
        Job j = new Job();
        String description = "Beste Beschreibung";
        j.setDescription(description);
        Assert.assertEquals("set/getTitle returned wrong description", description, j.getDescription());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testFilterJobs() {
        Job j1 = new Job();
        j1.setTitle("abc");
        j1.setDate("2005-01-01");
        j1.setDone(false);
        jobService.saveJob(j1);
        
        Date date = j1.getStartDate();
        List<Job> js = jobRepository.getUndoneJobsForDate(date);
        Assert.assertTrue("job-list for undone jobs is empty!", js.size() > 0);
        j1.setDone(true);
        js = jobRepository.getDoneJobsForDate(date);
        Assert.assertNotNull("job-list is null!", js);
        
        Person p1 = new Person();
        Person p2 = new Person();
        User u = new User();
        u.setUserName("uniqueUsername");
        p1.setFirstName("And");
        p1.setLastName("Again");
        p2.setFirstName("Damn");
        p2.setLastName("Names");
        Parent p = new Parent();
        p.setUser(u);
        p.setFather(p1);
        p.setMother(p2);
        j1.setParents(p);
        personService.savePerson(p1);
        personService.savePerson(p2);
        parentService.saveParent(p);
        jobService.saveJob(j1);
        js = jobRepository.getDoneJobsForParent(p.getId());
        Assert.assertTrue("job-list is empty for parent p!", js.size() > 0);
    }

}
