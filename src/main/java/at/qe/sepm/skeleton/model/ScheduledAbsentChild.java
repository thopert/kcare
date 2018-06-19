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
 * Schedule-Entity for storing scheduled absent children.
 * @author Dominik Kuen (csat2284)
 */
@Entity
public class ScheduledAbsentChild {

    /**
     * Generated unique id.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Date, for which this schedule is for
     */
    @Temporal(TemporalType.DATE)
    private Date date;

    /**
     * Absent child or null if not used.
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "absentchild")
    private Child absentchild;

    /**
     * Default constructor.
     */
    public ScheduledAbsentChild() {

    }

    /**
     * Constructor with already given data.
     */
    public ScheduledAbsentChild(Date date, Child absentchild) {
        this.date = date;
        this.absentchild = absentchild;
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
     * Get absent child.
     * @return absent child or null if not set
     */
    public Child getAbsentChild() {
        return absentchild;
    }

    /**
     * Set absent child.
     * @param child
     * @return
     */
    public void setAbsentChild(Child absentchild) {
        this.absentchild = absentchild;
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
     * Compares two scheduled absent children (compares date).
     * @param obj of type {@link #ScheduledAbsentChild}
     * @return true if equal or false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (!(obj instanceof ScheduledAbsentChild))
            return false;

        final ScheduledAbsentChild other = (ScheduledAbsentChild) obj;
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
        return "ScheduledAbsentChild[ " + date + " ]";
    }

}
