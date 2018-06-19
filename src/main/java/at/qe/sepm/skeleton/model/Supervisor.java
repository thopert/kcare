package at.qe.sepm.skeleton.model;

import at.qe.sepm.skeleton.model.Person;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.model.ScheduledSupervisor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;

import java.util.Objects;
import java.util.Set;

/**
 * Supervisor-Entity for storing supervisors.
 * @author Dominik Kuen (csat2284)
 */
@Entity
public class Supervisor {

    /**
     * Generated unique id.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Personalities.
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person", unique = true)
    private Person person;

    /**
     * Login-data.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user", unique = true)
    private User user;

    /**
     * Inverse connections.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "supervisor", cascade = CascadeType.REMOVE)
    private Set<ScheduledSupervisor> schedules;

    /**
     * Get id.
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Get personalities.
     * @return person
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Set personalities.
     * @param person
     * @return
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * Get login-data.
     * @return user
     */
    public User getUser() {
        return user;
    }

    /**
     * Set login-data.
     * @param user
     * @return
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Get hash-code.
     * @return hash-code
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(id);
        return hash;
    }

    /**
     * Compares two supervisors (compares personalities).
     * @param obj of type {@link #Supervisor}
     * @return true if equal or false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (!(obj instanceof Supervisor))
            return false;

        final Supervisor other = (Supervisor) obj;

        if (!Objects.equals(person, other.person))
            return false;

        return true;
    }

    /**
     * Get string-version
     * @return string-version
     */
    @Override
    public String toString() {
            return "Supervisor(" + id + ")[ " + person + " ]";     
    }

}
