package at.qe.sepm.skeleton.model;

import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.model.Person;
import at.qe.sepm.skeleton.model.Child;
import at.qe.sepm.skeleton.model.Job;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Parent {

    /**
     * Generated unique id.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Active Parents (true if at least one child is registered or else false).
     */
    private Boolean active;

    /**
     * Personalities from mother.
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mother", unique = true)
    private Person mother;

    /**
     * Personalities from father.
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "father", unique = true)
    private Person father;

    /**
     * Login-Details.
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user", unique = true)
    private User user;

    /**
     * Inverse connections.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parents", cascade = CascadeType.REMOVE)
    private Set<Child> childs;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parents", cascade = CascadeType.REMOVE)
    private Set<Job> jobs;

    /**
     * Get id.
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Get active-status.
     * @return active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * Set active-status.
     * @param active
     * @return
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * Get personalities from mother.
     * @return mother
     */
    public Person getMother() {
        return mother;
    }

    /**
     * Set personalities for mother.
     * @param mother
     * @return
     */
    public void setMother(Person mother) {
        this.mother = mother;
    }

    /**
     * Get personalities from father.
     * @return father
     */
    public Person getFather() {
        return father;
    }

    /**
     * Set personalities for father.
     * @param father
     * @return
     */
    public void setFather(Person father) {
        this.father = father;
    }

    /**
     * Get user.
     * @return user
     */
    public User getUser() {
        return user;
    }

    /**
     * Set user.
     * @param user (= login-data)
     * @return
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Get hash-code (taken from id).
     * @return hash-code
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(id);
        return hash;
    }

    /**
     * Compares two parents. (compares mothers and fathers)
     * @param obj of type {@link #Parent}
     * @return true if equal or false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (!(obj instanceof Parent))
            return false;

        if (mother == null && father == null)
            return false;

        final Parent other = (Parent) obj;
        if (!Objects.equals(mother, other.mother) || !Objects.equals(father, other.father))
            return false;

        return true;
    }

    /**
     * Get string-version
     * @return string-version
     */
    @Override
    public String toString() {
        if (mother != null && father != null)
            return this.mother + "/" + this.father;
        else if (this.mother != null)
        	return this.mother.toString();
        else if (this.father != null)
        	return this.father.toString();
        else
            return "Parent[ " + id + " ]";
    }

}
