package at.qe.sepm.skeleton.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.Objects;
import java.util.Date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Holiday-Entity for storing holidays.
 * @author Dominik Kuen (csat2284)
 */
@Entity
public class Holiday {

    /**
     * Generated unique id.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * name.
     */
    private String name;

    /**
     * Date when holiday(s) begin(s).
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date fromdate;

    /**
     * Date when holiday(s) end(s).
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date todate;
    
    /**
     * default constructor 
     */
    public Holiday(){
    	
    }
    
    /**
     * custom constructor
     */
    public Holiday(String name, Date start, Date end){
    	this.name = name;
    	this.fromdate = start;
    	this.todate = end;
    }

    /**
     * Get id.
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Get name.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set name.
     * @param name
     * @return 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get date when holiday(s) begin(s).
     * @return date
     */
    public Date getFromDate() {
        return fromdate;
    }

    /**
     * Set date when holiday(s) begin(s).
     * @param date
     * @return
     */
    public void setFromDate(Date fromdate) {
        this.fromdate = fromdate;
    }

    /**
     * Set date when holiday(s) begin(s).
     * @param year
     * @param month
     * @param day
     * @return
     */
    public void setFromDate(int year, int month, int day) {
        fromdate = new Date(year-1900, month, day);
    }

    /**
     * Set date when holiday(s) begin(s).
     * @param date as a string in format of yyyy-mm-dd
     * @return 
     */
    public void setFromDate(String date) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            fromdate = df.parse(date);
        } catch (Exception e) {
            System.out.println("Unable to parse given date: " + date);
        }
    }

    /**
     * Get date when holiday(s) end(s).
     * @return date
     */
    public Date getToDate() {
        return todate;
    }

    /**
     * Set date when holiday(s) end(s).
     * @param date
     * @return
     */
    public void setToDate(Date todate) {
        this.todate = todate;
    }

    /**
     * Set date when holiday(s) end(s).
     * @param year
     * @param month
     * @param day
     * @return
     */
    public void setToDate(int year, int month, int day) {
        todate = new Date(year-1900, month, day);
    }

    /**
     * Set date when holiday(s) end(s).
     * @param date as a string in format of yyyy-mm-dd
     * @return 
     */
    public void setToDate(String date) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            todate = df.parse(date);
        } catch (Exception e) {
            System.out.println("Unable to parse given date: " + date);
        }
    }

    /**
     * Get hash-code (taken from fromdate).
     * @return hash-code
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(fromdate);
        return hash;
    }

    /**
     * Compares two holidays. (compares name and fromdate)
     * @param obj of type {@link #Holiday}
     * @return true if equal or false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (!(obj instanceof Holiday))
            return false;

        final Holiday other = (Holiday) obj;
        if (!Objects.equals(name, other.name) || !Objects.equals(fromdate, other.fromdate))
            return false;

        return true;
    }

    /**
     * Get a string-version.
     * @return string-version
     */
    @Override
    public String toString() {
        return "Holiday[ " + name + " ]";
    }

}
