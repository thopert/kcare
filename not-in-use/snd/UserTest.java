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

/**
 * Tests for {@link User}, {@link UserRole}, {@link UserService}.
 * @author
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Main.class)
@WebAppConfiguration
public class UserTest {

    @Autowired
    private SessionInfoBean sessionInfoBean;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ParentService parentService;

    @Test
    public void testEquality() {
        User user1 = new User();
        user1.setUserName("user1");
        User user2 = new User();
        user2.setUserName("user2");
        User user3 = new User();
        user3.setUserName("user1");
        Assert.assertFalse("Wrong equality on different usernames", user1.equals(user2));
        Assert.assertTrue("Wrong equality on same usernames", user1.equals(user3));
    }

    @Test
    public void testNotLoggedIn() {
        Assert.assertFalse("sessionInfoBean.isLoggedIn does return true for not authenticated user", sessionInfoBean.isLoggedIn());
        Assert.assertEquals("sessionInfoBean.getCurrentUserName does not return empty string when not logged in", "", sessionInfoBean.getCurrentUserName());
        Assert.assertNull("sessionInfoBean.getCurrentUser does not return null when not logged in", sessionInfoBean.getCurrentUser());
        Assert.assertEquals("sessionInfoBean.getCurrentUserRoles does not return empty string when not logged in", "", sessionInfoBean.getCurrentUserRoles());
        for (UserRole role : UserRole.values()) {
            Assert.assertFalse("sessionInfoBean.hasRole does not return false for all possible roales", sessionInfoBean.hasRole(role.name()));
        }
    }

    @Test
    @WithMockUser(username = "user1", authorities = {"SUPERVISOR"})
    public void testLoggedIn() {
        Assert.assertTrue("sessionInfoBean.isLoggedIn does not return true for authenticated user", sessionInfoBean.isLoggedIn());
        Assert.assertEquals("sessionInfoBean.getCurrentUserName does not return authenticated user's name", "user1", sessionInfoBean.getCurrentUserName());
        Assert.assertEquals("sessionInfoBean.getCurrentUser does not return authenticated user", "user1", sessionInfoBean.getCurrentUser().getUserName());
        Assert.assertEquals("sessionInfoBean.getCurrentUserRoles does not return authenticated user's roles", "SUPERVISOR", sessionInfoBean.getCurrentUserRoles());
        Assert.assertTrue("sessionInfoBean.hasRole does not return true for a role the authenticated user has", sessionInfoBean.hasRole("SUPERVISOR"));
        Assert.assertFalse("sessionInfoBean.hasRole does not return false for a role the authenticated user does not have", sessionInfoBean.hasRole("ADMIN"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testDatainitialization() {
        Assert.assertTrue("Insufficient amount of users initialized for test data source", userService.getAllUsers().size() >= 3);
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testLoadUser() {
        Assert.assertNotNull("Admin user could not be loaded from test data source", userService.loadUser("admin"));
    }

    @DirtiesContext
    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testCreateUser() {
        User u = new User();
        u.setUserName("newuser");
        u.setPassword("passwd");
        u.setRoles(Sets.newSet(UserRole.PARENTS, UserRole.SUPERVISOR));
        userService.saveUser(u);

        User lu = userService.loadUser("newuser");
        Assert.assertNotNull("New user could not be loaded from test data source after being saved", lu);
        Assert.assertEquals("User \"newuser\" does not have a the correct username attribute stored being saved", "newuser", lu.getUserName());
        Assert.assertEquals("User \"newuser\" does not have a the correct password attribute stored being saved", "30274c47903bd1bac7633bbf09743149ebab805f", lu.getPassword());
        Assert.assertTrue("User \"newuser\" does not have role SUPERVISOR", lu.hasRole(UserRole.SUPERVISOR));
        Assert.assertTrue("User \"newuser\" does not have role SUPERVISOR", lu.hasRole(UserRole.PARENTS));
    }

    @DirtiesContext
    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testDeleteUser() {
        User adminUser = userService.loadUser("admin");
        Assert.assertNotNull("Admin user could not be loaded from test data source", adminUser);
        User toBeDeletedUser = userService.loadUser("user1");
        Assert.assertNotNull("User1 could not be loaded from test data source", toBeDeletedUser);
        Integer oldNumberOfUsers = userService.getAllUsers().size();
        userService.deleteUser(toBeDeletedUser);
        Integer newNumberOfUsers = userService.getAllUsers().size();
        
        Assert.assertNotEquals("No user has been deleted after calling UserService.deleteUser", oldNumberOfUsers, newNumberOfUsers);
        User deletedUser = userService.loadUser("user1");
        Assert.assertNull("Deleted User1 could still be loaded from test data source via UserService.loadUser", deletedUser);

        for (User remainingUser : userService.getAllUsers()) {
            Assert.assertNotEquals("Deleted User1 could still be loaded from test data source via UserService.getAllUsers", toBeDeletedUser.getUserName(), remainingUser.getUserName());
        }
    }

    @DirtiesContext
    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testUpdateUser() {
        User adminUser = userService.loadUser("admin");
        Assert.assertNotNull("Admin user could not be loaded from test data source", adminUser);
        User toBeSavedUser = userService.loadUser("user1");
        Assert.assertNotNull("User1 could not be loaded from test data source", toBeSavedUser);

        toBeSavedUser.setUserName("user1_");
        userService.saveUser(toBeSavedUser);

        User freshlyLoadedUser = userService.loadUser("user1_");
        Assert.assertNotNull("User1 could not be loaded from test data source after being saved", freshlyLoadedUser);
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testForEmptyUsername() {
        User adminUser = userService.loadUser("admin");
        Assert.assertNotNull("Admin user could not be loaded from test data source", adminUser);

        User u = new User();
        Assert.assertEquals("User with empty username was saved", null, userService.saveUser(u));
    }

    @Test(expected = org.springframework.security.authentication.AuthenticationCredentialsNotFoundException.class)
    public void testUnauthenticateddLoadUsers() {
        for (User user : userService.getAllUsers()) {
            Assert.fail("Call to userService.getAllUsers should not work without proper authorization");
        }
    }

    @Test(expected = org.springframework.security.access.AccessDeniedException.class)
    @WithMockUser(username = "user", authorities = {"SUPERVISOR"})
    public void testUnauthorizedLoadUsers() {
        for (User user : userService.getAllUsers()) {
            Assert.fail("Call to userService.getAllUsers should not work without proper authorization");
        }
    }

    @Test(expected = org.springframework.security.access.AccessDeniedException.class)
    @WithMockUser(username = "user1", authorities = {"SUPERVISOR"})
    public void testUnauthorizedLoadUser() {
        User user = userService.loadUser("admin");
        Assert.fail("Call to userService.loadUser should not work without proper authorization for other users than the authenticated one");
    }

    @WithMockUser(username = "user1", authorities = {"SUPERVISOR"})
    public void testAuthorizedLoadUser() {
        User user = userService.loadUser("user1");
        Assert.assertEquals("Call to userService.loadUser returned wrong user", "user1", user.getUserName());
    }

    @Test(expected = org.springframework.security.access.AccessDeniedException.class)
    @WithMockUser(username = "user1", authorities = {})
    public void testUnauthorizedSaveUser() {
        User user = userService.loadUser("user1");
        Assert.assertEquals("Call to userService.loadUser returned wrong user", "user1", user.getUserName());
        userService.saveUser(user);
    }

    @Test(expected = org.springframework.security.access.AccessDeniedException.class)
    @WithMockUser(username = "user1", authorities = {"SUPERVISOR"})
    public void testUnauthorizedDeleteUser() {
        User user = userService.loadUser("user1");
        Assert.assertEquals("Call to userService.loadUser returned wrong user", "user1", user.getUserName());
        userService.deleteUser(user);
    }

    @Test
    public void testGetById() {
        Assert.assertNotNull("User 1 is null!", userRepository.getById(1L));
        Assert.assertNull("User 9999 was found but it shouldn't exist!", userRepository.getById(9999L));
    }

    @Test
    public void testGetByUserName() {
        Assert.assertNotNull("User with username 'admin' was not found!", userRepository.getByUserName("admin"));
        Assert.assertNull("User with username 'paul' was not found!", userRepository.getByUserName("paul"));
    }

    @Test
    public void testGetByRole() {
        Assert.assertNotNull("No user with role 'supervisor' was found!", userRepository.getByRole(UserRole.SUPERVISOR));
    }

    @Test
    public void testIsAdminUser() {
        Assert.assertTrue("User 'admin' is not actually an admin-user (Role)!", userRepository.isAdminUser(1L));
    }

    @Test
    public void testIsSupervisorUser() {
        Assert.assertTrue("User 'user1' is not actually an supervisor-user (Role)!", userRepository.isSupervisorUser(2L));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testIsParentUser() {
    	User u = new User();
    	u.setUserName("dieseUserNeu");
    	Person p1 = new Person();
    	Person p2 = new Person();
    	Parent p = new Parent();
    	p.setUser(u);
    	p.setMother(p1);
    	p.setFather(p2);
    	userService.saveUser(u);
    	parentService.saveParent(p);
        Assert.assertTrue("User is not actually a parent-user (Role)!", userRepository.isParentUser(p.getId()));
    }

    @Test
    public void testRandomPasswordGenerator() {
        User u = new User();
        String rpw = User.getRandomPassword();
        u.setPassword(rpw);
        Assert.assertNotNull(u.getPassword());
    }
}
