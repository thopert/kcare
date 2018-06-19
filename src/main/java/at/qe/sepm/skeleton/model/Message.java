package at.qe.sepm.skeleton.model;

import at.qe.sepm.skeleton.model.Person;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.Objects;

import java.sql.Timestamp;

/**
 * Message-Entity for storing messages.
 * @author Dominik Kuen (csat2284)
 */
@Entity
public class Message {

    /**
     * Generated unique id.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Title (plain text).
     */
    private String title;

    /**
     * Content (plain text or html-formatted).
     */
    private String content;

    /**
     * Datetime, when the message was posted.
     */
    private Timestamp datetime;

    /**
     * Author (as Person).
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author")
    private Person author;

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
    public String getContent() {
        return content;
    }

    /**
     * Set content.
     * @param content
     * @return
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Get posted-date-time.
     * @return datetime
     */
    public Timestamp getDateTime() {
        return datetime;
    }

    /**
     * Set posted-date-time.
     * @param datetime
     * @return
     */
    public void setDateTime(Timestamp datetime) {
        this.datetime = datetime;
    }

    /**
     * Set posted-date-time.
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public void setDateTime(int year, int month, int day, int hour, int minute, int second) {
        datetime = new Timestamp(year, month, day, hour, minute, second, 0);
    }

    /**
     * Get author.
     * @return author
     */
    public Person getAuthor() {
        return author;
    }

    /**
     * Set author.
     * @param author
     * @return
     */
    public void setAuthor(Person author) {
        this.author = author;
    }

    /**
     * Get hash-code (taken from posted-date-time).
     * @return hash-code
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(datetime);
        return hash;
    }

    /**
     * Compares two messages (compares title and posted-date-time).
     * @param obj of type {@link #Message}
     * @return true if equal or false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (!(obj instanceof Message))
            return false;

        final Message other = (Message) obj;
        if (!Objects.equals(title, other.title) || !Objects.equals(datetime, other.datetime))
            return false;

        return true;
    }

    /**
     * Get a string-version.
     * @return string-version
     */
    @Override
    public String toString() {
        return "Message[ " + id + " ]";
    }

}
