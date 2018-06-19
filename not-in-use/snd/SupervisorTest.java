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

/**
 * Tests for {@link Supervisor}.
 * @author
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Main.class)
@WebAppConfiguration
public class SupervisorTest {

    @Autowired
    private SupervisorService supervisorService;

    @Autowired
    private SupervisorRepository supervisorRepository;

    @Autowired
    private PersonService personService;

    @Autowired
    private UserService userService;

    @Test
    public void testEquality() {
        Supervisor s1 = new Supervisor();
        Person p1 = new Person();
        Supervisor s2 = new Supervisor();
        Person p2 = new Person();
        s1.setPerson(p1);
        s2.setPerson(p2);
        p1.setFirstName("Bob");
        p1.setLastName("Alpha");
        p2.setFirstName("Bob");
        p2.setLastName("Beta");
        Assert.assertFalse("Wrong equality on different supervisors", s1.equals(s2));
        p2.setLastName("Alpha");
        Assert.assertTrue("Wrong equality on same supervisors", s1.equals(s2));
        Assert.assertFalse("Wrong equality when passed null", s1.equals((Object) null));
        String wrongType = "This is the wrong type";
        Assert.assertFalse("Wrong equality when passed wrong type", s1.equals(wrongType));
    }

    @DirtiesContext
    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testCreateSupervisor() {
        Person p = new Person();
        p.setFirstName("Bob");
        p.setLastName("Doe");
        p.setSex(false); // male
        p.setMail("abc@mail.com");
        p.setPhone("123123415");
        p.setBusinessPhone("24363452");
        p.setBirthDate("2000-01-02");

        User u = new User();
        u.setUserName("newuser");
        String rpw = User.getRandomPassword();
        u.setPassword(rpw);
        u.setRole(UserRole.SUPERVISOR);

        Supervisor s = new Supervisor();
        s.setPerson(p);
        s.setUser(u);

        supervisorService.saveSupervisor(s);

        Supervisor ls = s;
        Assert.assertNotNull("New supervisor could not be loaded from test data source after being saved", ls);
        Assert.assertEquals("New Supervisor does not have a the correct person attribute stored being saved", p, ls.getPerson());
        Assert.assertEquals("New Supervisor does not have a the correct user attribute stored being saved", u, ls.getUser());
        Assert.assertTrue("New Supervisor does not have role SUPERVISOR", ls.getUser().hasRole(UserRole.SUPERVISOR));

        supervisorService.deleteSupervisor(s);

    }

    @Test
    public void testGetById() {
        Assert.assertNotNull("Got null for Supervisor(1)!", supervisorRepository.getById(1L));
    }

    @Test
    public void testGetByUser() {
        Supervisor s1 = supervisorRepository.getByUser(2L);
        Supervisor s2 = supervisorRepository.getById(1L);
        Assert.assertEquals("getByUser(3) got wrong user!", s2, s1);
    }

    @Test
    public void testGetByUserName() {
        Supervisor s1 = supervisorRepository.getByUserName("user1");
        Assert.assertNotNull("supervisor:getByUsername(\"user1\") is null!", s1);
        Supervisor s2 = supervisorRepository.getById(1L);
        Assert.assertEquals("getByUser(user1) got wrong user!", s2, s1);
    }

    @Test
    public void testGetAllSupervisors() {
        Assert.assertTrue("findAll() returned an empty list!", supervisorRepository.findAll().size() > 0);
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testTostring() {

        Person p = new Person();
        p.setFirstName("Johnni");
        p.setLastName("Boi");
        p.setSex(false); // male
        p.setMail("dieseMailBeschde@mail.com");
        p.setPhone("12312341512341234");
        p.setBusinessPhone("21389469187");
        p.setBirthDate("1973-03-05");

        User u = new User();
        u.setUserName("johnnyboi");
        String rpw = User.getRandomPassword();
        u.setPassword(rpw);
        u.setRole(UserRole.SUPERVISOR);

        Supervisor s = new Supervisor();
        s.setPerson(p);
        s.setUser(u);

        supervisorService.saveSupervisor(s);
        String toString = "Supervisor(" + s.getId() + ")[ " + p + " ]";
        Assert.assertEquals("ToString Test failed: ", s.toString(), toString);
    }

}
