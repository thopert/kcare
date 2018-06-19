package at.qe.sepm.skeleton.model;

import at.qe.sepm.skeleton.model.Person;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

import java.util.Objects;

import java.sql.Timestamp;

/**
 * Picture-Entity for storing picture-data.
 * @author Dominik Kuen (csat2284)
 */
@Entity
public class Picture {

    /**
     * Generated unique id.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Name.
     */
    private String name;

    /**
     * Filepath.
     */
    private String path;

    /**
     * Uploadtime.
     */
    private Timestamp uploadtime;

    /**
     * Associated person.
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person")
    private Person person;

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
     * Get path.
     * @return path
     */
    public String getPath() {
        return path;
    }

    /**
     * Set path.
     * @param path
     * @return 
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Get upload-time.
     * @return upload-time
     */
    public Timestamp getUploadTime() {
        return uploadtime;
    }

    /**
     * Set upload-time.
     * @param upload-time
     * @return 
     */
    public void setUploadTime(Timestamp uploadtime) {
        this.uploadtime = uploadtime;
    }

    /**
     * Set upload-time.
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @return 
     */
    public void setUploadTime(int year, int month, int day, int hour, int minute) {
        uploadtime = new Timestamp(year, month, day, hour, minute, 0, 0);
    }

    /**
     * Get person.
     * @return person
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Set person.
     * @param person
     * @return 
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * Get hash-code (taken from path).
     * @return hash-code
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(path);
        return hash;
    }

    /**
     * Compares two pictures (compares path).
     * @param obj of type {@link #Picture}
     * @return true if equal or false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (!(obj instanceof Picture))
            return false;

        final Picture other = (Picture) obj;
        if (!Objects.equals(this.path, other.path))
            return false;

        return true;
    }

    /**
     * Get string-version.
     * @return string-version
     */
    @Override
    public String toString() {
        return "Picture[ " + path + " ]";
    }

}
