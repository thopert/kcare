package at.qe.sepm.skeleton.model;

import at.qe.sepm.skeleton.model.Parent;

import javax.persistence.Entity;
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

import java.util.Date;

/**
 * Job-Entity for storing jobs.
 * @author Dominik Kuen (csat2284)
 */
@Entity
public class Job {

    /**
     * Generated unique id.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Title (plain text)
     */
    private String title;

    /**
     * Description (in plain text or html-formatted)
     */
    private String description;

    /**
     * Date, when the job has to be done (start-date).
     */
    @Temporal(TemporalType.DATE)
    private Date startdate;

    /**
     * Date, when the job has to be done (end-date).
     */
    @Temporal(TemporalType.DATE)
    private Date enddate;

    /**
     * Number of days it is visible before the due date.
     */
    private Integer daysbefore;

    /**
     * Boolean to store if job is done or not.
     */
    private Boolean done;

    /**
     * Parents, which will do the job.
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parents")
    private Parent parents;

    public Job(){
    
    }
    
    public Job (Job job){
    	this.title = job.getTitle();
    	this.description = job.getDescription();
    	this.done = job.getDone();
    	this.daysbefore = job.getDaysBefore();
    	this.startdate = job.getStartDate();
    	this.enddate = job.getEndDate();
    }
    
    /**
     * Get id.
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Get title.
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set title.
     * @param title
     * @return
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get content.
     * @return content
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set description.
     * @param description
     * @return
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get startdate.
     * @return date
     */
    public Date getStartDate() {
        return startdate;
    }
    
    public String getStartDateString() {
    	 DateFormat df = new SimpleDateFormat("dd.MM.yy");
        return df.format(startdate);
    }

    /**
     * Set startdate.
     * @param date
     * @return
     */
    public void setStartDate(Date startdate) {
        this.startdate = startdate;
    }

    /**
     * Set startdate.
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public void setStartDate(int year, int month, int day) {
        startdate = new Date(year, month, day);
    }

    /**
     * Set startdate.
     * @param date
     * @return
     */
    public void setStartDate(String date) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            startdate = df.parse(date);
        } catch (Exception e) {
            System.out.println("Unable to parse given date: " + date);
        }
    }

    /**
     * Get enddate.
     * @return date
     */
    public Date getEndDate() {
        return enddate;
    }
    
    public String getEndDateString() {
    	 DateFormat df = new SimpleDateFormat("dd.MM.yy");
         return df.format(enddate);
    }

    /**
     * Set enddate.
     * @param date
     * @return
     */
    public void setEndDate(Date enddate) {
        this.enddate = enddate;
    }

    /**
     * Set enddate.
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public void setEndDate(int year, int month, int day) {
        enddate = new Date(year, month, day);
    }

    /**
     * Set enddate.
     * @param date
     * @return
     */
    public void setEndDate(String date) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            enddate = df.parse(date);
        } catch (Exception e) {
            System.out.println("Unable to parse given date: " + date);
        }
    }

    /**
     * Set start- and enddate.
     * @param date
     * @return
     */
    public void setDate(String date) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date d = df.parse(date);
            setStartDate(d);
            setEndDate(d);
        } catch (Exception e) {
            System.out.println("Unable to parse given date: " + date);
        }
    }

    /**
     * Get number of days it is viewable before due-date.
     * @return content
     */
    public Integer getDaysBefore() {
        return daysbefore;
    }

    /**
     * Set number of days it is viewable before the due-date.
     * @param daysbefore
     * @return
     */
    public void setDaysBefore(Integer daysbefore) {
        this.daysbefore = daysbefore;
    }

    /**
     * Get parents.
     * @return parents
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
     * Get done.
     * @return done
     */
    public Boolean getDone() {
        return done;
    }

    /**
     * Set done.
     * @param done
     * @return
     */
    public void setDone(Boolean done) {
        this.done = done;
    }

    /**
     * Get hash-code (taken from title).
     * @return hash-code
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(title);
        return hash;
    }

    /**
     * Compares two jobs. (compares title and due-date)
     * @param obj of type {@link #Job}
     * @return true if equal or false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (!(obj instanceof Job))
            return false;

        final Job other = (Job) obj;
        if (!Objects.equals(title, other.title) || !Objects.equals(startdate, other.startdate) || !Objects.equals(enddate, other.enddate) || !Objects.equals(parents, other.parents))
            return false;

        return true;
    }

    /**
     * Get string-version.
     * @return string-version
     */
    @Override
    public String toString() {
        return "Job[ " + title + " ]";
    }

}
