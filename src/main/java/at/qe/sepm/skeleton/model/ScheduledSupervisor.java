package at.qe.sepm.skeleton.model;

import at.qe.sepm.skeleton.model.Supervisor;

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
 * Schedule-Entity for storing scheduled supervisor.
 * @author Dominik Kuen (csat2284)
 */
@Entity
public class ScheduledSupervisor {

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
     * registered supervisor or null if not used.
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supervisor")
    private Supervisor supervisor;

    /**
     * Default constructor.
     */
    public ScheduledSupervisor() {

    }

    /**
     * Constructor with already given data.
     */
    public ScheduledSupervisor(Date date, Supervisor supervisor) {
        this.date = date;
        this.supervisor = supervisor;
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
     * Get registered supervisor.
     * @return supervisor or null if not set
     */
    public Supervisor getSupervisor() {
        return supervisor;
    }

    /**
     * Set registered supervisor.
     * @param supervisor
     * @return
     */
    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
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
     * Compares two scheduled supervisor (compares date).
     * @param obj of type {@link #ScheduledSupervisor}
     * @return true if equal or false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (!(obj instanceof ScheduledSupervisor))
            return false;

        final ScheduledSupervisor other = (ScheduledSupervisor) obj;
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
        return "ScheduledSupervisor[ " + date + " ]";
    }

}
