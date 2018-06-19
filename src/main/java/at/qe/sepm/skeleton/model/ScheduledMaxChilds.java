package at.qe.sepm.skeleton.model;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.Objects;
import java.util.Date;

/**
 * Schedule-Entity for storing maximum childs for each day.
 * @author Dominik Kuen (csat2284)
 */
@Entity
public class ScheduledMaxChilds {

    /**
     * Generated unique id.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Date, for which this schedule is for
     */
    @Column(unique = true)
    @Temporal(TemporalType.DATE)
    private Date date;

    /**
     * Stores the maximum number of children, which can register for this schedule-day
     */
    private Long childslimit;

    /**
     * Default constructor.
     */
    public ScheduledMaxChilds() {

    }

    /**
     * Constructor with already given data.
     */
    public ScheduledMaxChilds(Date date, Long childslimit) {
        this.date = date;
        this.childslimit = childslimit;
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
        date = new Date(year - 1900, month, day);
    }

   /**
     * Get children-limit.
     * @return children-limit
     */
    public Long getChildsLimit() {
        return childslimit;
    }

    /**
     * Set registered children-limit.
     * @param children-limit
     * @return
     */
    public void setChildsLimit(Long childslimit) {
        this.childslimit = childslimit;
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
     * Compares two scheduled max childs (compares date).
     * @param obj of type {@link #ScheduledMaxChilds}
     * @return true if equal or false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (!(obj instanceof ScheduledMaxChilds))
            return false;

        final ScheduledMaxChilds other = (ScheduledMaxChilds) obj;
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
        return "ScheduledMaxChilds[ " + date + " ]";
    }

}
