package at.qe.sepm.skeleton.model;

import at.qe.sepm.skeleton.model.Child;
import at.qe.sepm.skeleton.model.Contact;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.Objects;
import java.util.List;
import java.util.Date;

/**
 * ContactPickup-Entity for storing ContactPickups.
 * @author Dominik Kuen (csat2284)
 */
@Entity
public class ContactPickup {

    /**
     * Generated unique id.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * contact.
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contact")
    private Contact contact;

    /**
     * Child for which the contact is contact for.
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "child")
    private Child child;

    /**
     * Date for when the contact picks up the child.
     */
    @Temporal(TemporalType.DATE)
    private Date date;

    /**
     * Get id.
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Get contact.
     * @return contact
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * Set contact.
     * @param contact
     * @return 
     */
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    /**
     * Get child.
     * @return child
     */
    public Child getChild() {
        return child;
    }

    /**
     * Set child.
     * @param child
     * @return
     */
    public void setChild(Child child) {
        this.child = child;
    }

    /**
     * Get date.
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Set date when contact picks up child.
     * @param date as a string in format of yyyy-mm-dd
     * @return 
     */
    public void setDate(String date) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            this.date = df.parse(date);
        } catch (Exception e) {
            System.out.println("Unable to parse given date: " + date);
        }
    }

    /**
     * Set startdate.
     * @param date
     * @return
     */
    public void setDate(Date date) {
        this.date = date;
    }


    /**
     * Get hash-code (taken from contact).
     * @return hash-code
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(contact);
        return hash;
    }

    /**
     * Compares two contact-fors. (compares contact and child)
     * @param obj of type {@link #ContactPickup}
     * @return true if equal or false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (!(obj instanceof ContactPickup))
            return false;

        final ContactPickup other = (ContactPickup) obj;
        if (!Objects.equals(contact, other.contact) || !Objects.equals(child, other.child))
            return false;

        return true;
    }

    /**
     * Get a string-version.
     * @return string-version
     */
    @Override
    public String toString() {
        return "Contact [" + contact + "] picks Child [" + child + "] on " + date;
    }

}
