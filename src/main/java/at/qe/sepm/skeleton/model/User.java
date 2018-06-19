package at.qe.sepm.skeleton.model;

import at.qe.sepm.skeleton.model.Parent;
import at.qe.sepm.skeleton.model.Supervisor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.CollectionTable;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;

import java.security.SecureRandom;
import java.math.BigInteger;

import java.util.Objects;
import java.util.Set;
import java.util.HashSet;

import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

/**
 * User-Entity for storing login-data.
 * @author Dominik Kuen (csat2284)
 */
@Entity
public class User {

    /**
     * Generated unique id.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Username.
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * SHA1-encrypted password-hash.
     * Encrypted at {@link #setPassword}.
     */
    private String password;

    /**
     * Holds a set of user-roles each user can have.
     * Those are handling authorities and other security-critical tasks.
     * Look at {@link #UserRole} to view all possible roles.
     */
    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "userrole")
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles;

    /**
     * Inverse connections.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private Set<Parent> ps;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private Set<Supervisor> svs;

    /**
     * Get id.
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Get username.
     * @return username
     */
    public String getUserName() {
        return username;
    }

    /**
     * Set username.
     * @param username
     * @return 
     */
    public void setUserName(String username) {
        this.username = username;
    }

    /**
     * Get password-hash.
     * @return SHA1-encrypted password-hash
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set password.
     * @param password (plain-text (will be crypted internally))
     * @return
     */
    public void setPassword(String password) {
        this.password = new ShaPasswordEncoder().encodePassword(password, null);
    }

    /**
     * Set password without encryption.
     * @param password (plain-text or hashed)
     * @return
     */
    public void setPasswordWithoutEncryption(String password) {
        this.password = password;
    }

    /**
     * Get random generated password.
     * @return generated password
     */
    public static String getRandomPassword() {
        SecureRandom r = new SecureRandom();
        String s = new BigInteger(16, r).toString(32);
        return s;
    }

    /**
     * Get set of roles.
     * @return set of {@link #UserRole}s
     * @see UserRole
     */
    public Set<UserRole> getRoles() {
        return roles;
    }
    
    /**
     * Get user role.
     * @return @link #UserRole
     * @see UserRole
     */
    public UserRole getRole(){
    	if (this.roles.iterator().hasNext())
    		return this.roles.iterator().next();
    	return null;
    }

    /**
     * Check if user has the given role.
     * @param role of enum {@link #UserRole}
     * @return true if the user has the role, else false
     */
    public Boolean hasRole(UserRole role) {
        return getRoles().contains(role);
    }

    /**
     * Set set of roles.
     * @param set of {@link #UserRole}s
     * @return
     */
    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    /**
     * Set one role.
     * @param {@link #UserRole}
     * @return 
     */
    public void setRole(UserRole role) {
        roles = new HashSet<>();
        roles.add(role);
    }

    /**
     * Get hash-code (taken from username).
     * @return hash-code
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(username);
        return hash;
    }

    /**
     * Compares two users. (compares username)
     * @param obj of type {@link #User}
     * @return true if equal or false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (!(obj instanceof User))
            return false;

        final User other = (User) obj;
        if (!Objects.equals(username, other.username))
            return false;

        return true;
    }

    /**
     * Get a string-version.
     * @return string-version
     */
    @Override
    public String toString() {
        return "User[ " + username + " ]";
    }

}
