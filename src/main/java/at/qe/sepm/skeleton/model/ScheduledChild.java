package at.qe.sepm.skeleton.model;

import at.qe.sepm.skeleton.model.Child;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToOne;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.Objects;
import java.util.Date;

/**
 * Schedule-Entity for storing scheduled child.
 * @author Dominik Kuen (csat2284)
 */
@Entity
public class ScheduledChild {

    /**
     * Generated unique id.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Date, for which this schedule is for.
     */
    @Temporal(TemporalType.DATE)
    private Date date;

    /**
     * Special notes for this schedule.
     */
    private String note;

    /**
     * registered child or null if not used.
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "child")
    private Child child;

    /**
     * Default constructor.
     */
    public ScheduledChild() {

    }

    /**
     * Constructor with already given data.
     */
    public ScheduledChild(Date date, Child child) {
        this.date = date;
        this.child = child;
    }

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
    public void setDate(int year, int month, int day) {
        date = new Date(year - 1900, month, day, 0, 0, 0);
    }

    /**
     * Get registered child.
     * @return child or null if not set
     */
    public Child getChild() {
        return child;
    }

    /**
     * Set registered child.
     * @param child
     * @return
     */
    public void setChild(Child child) {
        this.child = child;
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
     * Compares two scheduled children (compares date).
     * @param obj of type {@link #ScheduledChild}
     * @return true if equal or false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (!(obj instanceof ScheduledChild))
            return false;

        final ScheduledChild other = (ScheduledChild) obj;
        if (!Objects.equals(date, other.date))
            return false;

        return true;
    }

    /**
     * Get string-version
     * @return string-version
     */
    @Override
    public String toString() {
        return "ScheduledChild[ " + date + " ]";
    }

}
