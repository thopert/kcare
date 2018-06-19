package at.qe.sepm.skeleton.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.model.UserRole;
import at.qe.sepm.skeleton.repositories.UserRepository;

/**
 * Service for accessing and manipulating user data.
 *
 * This class is part of the skeleton project provided for students of the
 * course "Softwaredevelopment and Project Management" offered by the University
 * of Innsbruck.
 */
@Component
@Scope("application")
public class UserService {

    @Autowired
    private UserRepository userRepository;

	@Autowired
	private MailService mailService;

    /**
     * Returns a collection of all users.
     *
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public Collection<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Loads a single user identified by its id.
     *
     * @param id to search for
     * @return user with the given id
     */
    @PreAuthorize("hasAuthority('ADMIN') or principal.id eq #id")
    public User loadUser(Long id) {
        return userRepository.getById(id);
    }

    /**
     * Loads a single user identified by its username.
     *
     * @param username to search for
     * @return user with the given username
     */
    @PreAuthorize("hasAuthority('ADMIN') or principal.username eq #username")
    public User loadUser(String username) {
        return userRepository.getByUserName(username);
    }

    /**
     * Saves the user.
     *
     * @param user the user to save
     * @return the updated user
     */
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('PARENTS') or hasAuthority('SUPERVISOR')")
    public User saveUser(User user) {
        if (user.getUserName() == null)
            return null;
        return userRepository.save(user);
    }

    /**
     * Saves new user.
     *
     * @param user the user to save
     * @return the new user
     */
    public User saveNewUser(User user) {
        if (user.getUserName() == null)
            return null;
        String rpw = User.getRandomPassword();
        user.setPassword(rpw);
mailService.sendMail("kcare-login-system", "Hallo " + user.getUserName() + ",\nwir haben dich soeben in unser System eingetragen.\nFür die Eintragung in unser System haben wir ein zufälliges Passwort für diesen Account gewählt. Wir bitten dich daher, dass du dich mit den folgenden Benutzer-Daten bei kcare einloggst und das Passwort auf ein von dir gewähltes anderes Passwort änderst.\n\nDie Benutzer-Daten lauten:\nBenutzername: " + user.getUserName() + "\nPasswort: " + rpw + "\n\nWir wünschen dir viel Spaß mit unserer kcare-Plattform! :)\n\nViele Grüße,\nkcare-Administrator-Team", "domii894@gmail.com");
        return userRepository.save(user);
    }

    /**
     * Deletes the user.
     *
     * @param user the user to delete
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteUser(User user) {
        userRepository.delete(user);
    }
    
    public boolean isUsernameFree(String username){
    	return (this.loadUser(username) == null);
    }

    private User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.getByUserName(auth.getName());
    }
    
    public List <UserRole> getFreeRoles (UserRole role){
    	List <UserRole> roles = new ArrayList<UserRole>();
    	if (role != UserRole.ADMIN)
    		roles.add(UserRole.ADMIN);
    	if (role != UserRole.PARENTS)
    		roles.add(UserRole.PARENTS);
    	if (role != UserRole.SUPERVISOR)
    		roles.add(UserRole.SUPERVISOR);
    	return roles;	
    }

}
