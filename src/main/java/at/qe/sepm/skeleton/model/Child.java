package at.qe.sepm.skeleton.model;

import at.qe.sepm.skeleton.model.Person;
import at.qe.sepm.skeleton.model.Parent;
import at.qe.sepm.skeleton.model.ContactFor;
import at.qe.sepm.skeleton.model.Lunch;
import at.qe.sepm.skeleton.model.ScheduledChild;
import at.qe.sepm.skeleton.utils.TimeUtils;
import at.qe.sepm.skeleton.model.ScheduledAbsentChild;
import at.qe.sepm.skeleton.model.ContactPickup;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.Objects;
import java.util.Set;
import java.util.HashSet;

import java.util.Date;
import java.sql.Time;

/**
 * Child-Entity for storing children.
 * @author Dominik Kuen (csat2284)
 */
@Entity
public class Child implements Comparable<Child> {

    /**
     * Generated unique id.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Date, when the child was registered.
     */
    @Temporal(TemporalType.DATE)
    private Date registrationdate;

    /**
     * Date, when the child has left the childhood.
     */
    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    private Date cancellationdate;

    /**
     * Time, when the child will be earliest put to the childhood.
     */
    private Time puttimebegin;

    /**
     * Time, when the child will be latest put to the childhood.
     */
    private Time puttimeend;

    /**
     * Time, when the child will be earliest picked from the childhood.
     */
    private Time picktimebegin;

    /**
     * Time, when the child will be latest picked from the childhood.
     */
    private Time picktimeend;

    /**
     * Place for special notes from the parents. (plain text)
     */
    private String notes;

    /**
     * Personalities (name, ...).
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person", unique = true)
    private Person person;

    /**
     * Parents (mother, father).
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parents")
    private Parent parents;

    /**
     * Inverse connections.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "child", cascade = CascadeType.REMOVE)
    private Set<ContactFor> cs;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "child", cascade = CascadeType.REMOVE)
    private Set<Lunch> ls;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "child", cascade = CascadeType.REMOVE)
    private Set<ScheduledChild> rs;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "absentchild", cascade = CascadeType.REMOVE)
    private Set<ScheduledAbsentChild> as;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "child", cascade = CascadeType.REMOVE)
    private Set<ContactPickup> cps;

    /**
     * Get id.
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Get registration-date.
     * @return registration-date
     */
    public Date getRegistrationDate() {
        return registrationdate;
    }

    /**
     * Set a registration-date.
     * @param registrationdate
     * @return
     */
    public void setRegistrationDate(Date registrationdate) {
        this.registrationdate = registrationdate;
    }

    /**
     * Set a registration-date.
     * @param year
     * @param month
     * @param day
     * @return
     */
    public void setRegistrationDate(int year, int month, int day) {
        registrationdate = new Date(year, month, day);
    }

    /**
     * Get cancellation-date.
     * @return cancellation-date or null if still registered
     */
    public Date getCancellationDate() {
        return cancellationdate;
    }

    /**
     * Set a cancellation-date.
     * @param cancellation-date
     * @return
     */
    public void setCancellationDate(Date cancellationdate) {
        this.cancellationdate = cancellationdate;
    }

    /**
     * Set a cancellation-date.
     * @param year
     * @param month
     * @param day
     * @return
     */
    public void setCancellationDate(int year, int month, int day) {
        cancellationdate = new Date(year, month, day);
    }

    /**
     * Get earliest put-time.
     * @return puttimebegin
     */
    public String getPutTimeBegin() {
        return puttimebegin.toString().substring(0,	5);
    }

    /**
     * Set an earliest put-time.
     * @param hour
     * @param minute
     * @return
     */
    public void setPutTimeBegin(int hour, int minute) {
        puttimebegin = new Time(hour, minute, 0);
    }

    public void setPutTimeBegin(String time) {
        puttimebegin = TimeUtils.toTime(time + ":00");
    }
    
    /**
     * Get latest put-time.
     * @return puttimeend
     */
    public String getPutTimeEnd() {
        return puttimeend.toString().substring(0, 5);
    }
    
    /**
     * Get puttime interval as string
     * @return puttime-interval
     */
    public String getPutTime(){
    	return this.getPutTimeBegin() + " - " + this.getPutTimeEnd();
    }

    /**
     * Set a latest put-time.
     * @param hour
     * @param minute
     * @return
     */
    public void setPutTimeEnd(int hour, int minute) {
        puttimeend = new Time(hour, minute, 0);
    }
    
    public void setPutTimeEnd(String time) {
        puttimeend = TimeUtils.toTime(time + ":00");
    }

    /**
     * Get earliest pick-time.
     * @return picktimebegin
     */
    public String getPickTimeBegin() {
        return picktimebegin.toString().substring(0, 5);
    }

    /**
     * Set an earliest pick-time.
     * @param hour
     * @param minute
     * @return
     */
    public void setPickTimeBegin(int hour, int minute) {
        picktimebegin = new Time(hour, minute, 0);
    }
    
    public void setPickTimeBegin(String time) {
        picktimebegin = TimeUtils.toTime(time + ":00");
    }

    /**
     * Get latest pick-time.
     * @return picktimeend
     */
    public String getPickTimeEnd() {
        return picktimeend.toString().substring(0, 5);
    }
    
    /**
     * Get picktime interval as string
     * @return picktime-interval
     */
    public String getPickTime(){
    	return this.getPickTimeBegin() + " - " + this.getPickTimeEnd();
    }

    /**
     * Set a latest pick-time.
     * @param hour
     * @param minute
     * @return
     */
    public void setPickTimeEnd(int hour, int minute) {
        picktimeend = new Time(hour, minute, 0);
    }
    
    public void setPickTimeEnd(String time) {
        picktimeend = TimeUtils.toTime(time + ":00");
    }

    /**
     * Get notes.
     * @return notes (= plain- or html-formatted text)
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Set notes.
     * @param notes (= plain- or html-formatted text)
     * @return
     */
    public void setNotes(String notes) {
        this.notes = notes;
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
     * Get parents.
     * @return parents (= mother and father)
     */
    public Parent getParents() {
        return parents;
    }

    /**
     * Set parents.
     * @param parents
     * @return
     */
    public void setParents(Parent parents) {
        this.parents = parents;
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
     * Equality check for two children (compares personalities)
     * @param obj of type {@link #Child}
     * @return true if equal or false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (!(obj instanceof Child))
            return false;

        final Child other = (Child) obj;
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
            return "Child[ " + id + " ]";
    }

	@Override
	public int compareTo(Child o) {
		return this.person.getLastName().compareTo(o.getPerson().getLastName());
	}

}
