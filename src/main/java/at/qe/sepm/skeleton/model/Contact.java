package at.qe.sepm.skeleton.model;

import at.qe.sepm.skeleton.model.Person;
import at.qe.sepm.skeleton.model.Child;
import at.qe.sepm.skeleton.model.ContactPickup;

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
import java.util.Date;

/**
 * Contact-Entity for storing contacts.
 * @author Dominik Kuen (csat2284)
 */
@Entity
public class Contact {

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
    @JoinColumn(name = "person")
    private Person person;

    /**
     * Verified status.
     */
    private Boolean verified = false;

    /**
     * Inverse connections.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contact", cascade = CascadeType.REMOVE)
    private Set<ContactFor> cfs;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contact", cascade = CascadeType.REMOVE)
    private Set<ContactPickup> cps;

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
     * @param person (= personalitites)
     * @return
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * Get verification.
     * @return verified
     */
    public Boolean getVerification() {
        return verified;
    }

    /**
     * Set verification.
     * @param verified
     * @return 
     */
    public void setVerification(Boolean verified) {
        this.verified = verified;
    }

    /**
     * Get hash-code (taken from id).
     * @return hash-code
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        return hash;
    }

    /**
     * Compares two contacts. (compares personalities)
     * @param obj of type {@link #Contact}
     * @return true if equal or false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (!(obj instanceof Contact))
            return false;

        final Contact other = (Contact) obj;
        if (person == null || other.person == null)
            return false;

        if (!Objects.equals(person, other.person))
            return false;

        return true;
    }

    /**
     * Get string-version.
     * @return string-version
     */
    @Override
    public String toString() {
        if (person != null)
            return this.person.toString();
        else
            return "Contact[ " + id + " ]";
    }

}
