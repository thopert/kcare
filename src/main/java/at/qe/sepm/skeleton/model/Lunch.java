package at.qe.sepm.skeleton.model;

import at.qe.sepm.skeleton.model.Child;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.Objects;

import java.util.Date;

/**
 * Lunch-Entity for storing lunch-days.
 * @author Dominik Kuen (csat2284)
 */
@Entity
public class Lunch {

    /**
     * Generated unique id.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Date, when the child was registered for lunch.
     */
    @Temporal(TemporalType.DATE)
    private Date date;

    /**
     * Child, which is registered for lunch.
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "child")
    private Child child;

    /**
     * Get id.
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Get date.
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Set date.
     * @param date
     * @return
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Set date.
     * @param year
     * @param month
     * @param day
     * @return
     */
    public void setDate(int year, int month, int day) {
        date = new Date(year, month, day);
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
     * Get hash-code (taken from date).
     * @return hash-code
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(date);
        return hash;
    }

    /**
     * Compares two lunchs (compares child and date).
     * @param obj of type {@link #Lunch}
     * @return true if equal or false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) 
            return false;

        if (!(obj instanceof Lunch))
			return false;
		
        final Lunch other = (Lunch) obj;
        if (child == null || other.child == null)
            return false;
		
        if (!Objects.equals(child, other.child) || !Objects.equals(date, other.date))
            return false;

        return true;
    }

    /**
     * Get string-version.
     * @return string-version
     */
    @Override
    public String toString() {
        return "Lunch[ " + child + " at " + date + " ]";
    }

}
