package at.qe.sepm.skeleton.model;

import java.io.ByteArrayInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 * Person-Entity for storing personalities.
 * @author Dominik Kuen (csat2284)
 */
@Entity
public class Person implements Comparable<Person> {

    /**
     * Generated unique id.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Firstname.
     */
    private String firstname;

    /**
     * Lastname.
     */
    private String lastname;

    /**
     * Gender. (false = man, true = woman)
     */
    private Boolean sex;

    /**
     * Mail.
     */
    @Column(unique = true)
    private String mail;

    /**
     * Phone-number.
     */
    @Column(unique = true)
    private String phone;

    /**
     * Business phone-number.
     */
    @Column(unique = true)
    private String businessphone;

    /**
     * Date of birth.
     */
    @Temporal(TemporalType.DATE)
    private Date birthdate;
    
    /**
     * Profile-picture.
     */  
    @Lob
    private byte picture[];

    /**
     * Inverse connections.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person", cascade = CascadeType.REMOVE)
    private Set<Supervisor> supervisors;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mother", cascade = CascadeType.REMOVE)
    private Set<Parent> mothers;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "father", cascade = CascadeType.REMOVE)
    private Set<Parent> fathers;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person", cascade = CascadeType.REMOVE)
    private Set<Child> childs;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person", cascade = CascadeType.REMOVE)
    private Set<Contact> contacts;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person", cascade = CascadeType.REMOVE)
    private Set<Picture> pictures;

    /**
     * Get id.
     * @return id
     */
    public Long getId() {
    	return id;
    }

    /**
     * Get firstname.
     * @return firstname
     */
    public String getFirstName() {
        return firstname;
    }

    /**
     * Set firstname.
     * @param firstname
     * @return
     */
    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Get the lastname.
     * @return lastname
     */
    public String getLastName() {
        return lastname;
    }

    /**
     * Set a new lastname.
     * @param lastname
     * @return
     */
    public void setLastName(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Get the gender.
     * @return gender
     */
    public Boolean getSex() {
    	return sex;
    }

    /**
     * Set a new gender.
     * @param sex (false = men, true = women)
     * @return
     */
    public void setSex(Boolean sex) {
    	this.sex = sex;
    }

    /**
     * Get the mail-adress.
     * @return mail-adress
     */
    public String getMail() {
        return mail;
    }

    /**
     * Set a new mail-adress.
     * @param mail-adress
     * @return
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Get the phone-number.
     * @return phone-number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Set a new phone-number.
     * @param phone-number
     * @return
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Get the business-phone-number.
     * @return business-phone-number
     */
    public String getBusinessPhone() {
        return businessphone;
    }

    /**
     * Set a new business-phone-number.
     * @param business-phone-number
     * @return
     */
    public void setBusinessPhone(String phone) {
        this.businessphone = phone;
    }

    /**
     * Get the date-of-birth.
     * @return date-of-birth
     */
    public Date getBirthDate() {
        return birthdate;
    }

    /**
     * Set birthdate.
     * @param birthdate
     * @return
     */
    public void setBirthDate(Date birthdate) {
        this.birthdate = birthdate;
    }

    /**
     * Set birthdate.
     * @param year Year of birth
     * @param month Month of birth
     * @param day Day of birth
     * @return
     */
    public void setBirthDate(int year, int month, int day) {
        birthdate = new Date(year - 1900, month, day);
    }

    /**
     * Set birthdate from string.
     * @param string of format yyyy-mm-dd
     * @return 
     */
    public void setBirthDate(String birthdate) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            this.birthdate = df.parse(birthdate);
        } catch (Exception e) {
            System.out.println("Unable to parse given date: " + birthdate);
        }
    }

    /**
     * Get pictures.
     * @return pictures
     */
    public Set<Picture> setPictures() {
        return pictures;
    }
    
    /**
     * Get profile-picture.
     * @return profile-picture
     */
    public byte[] getPicture() {
 		return picture;
 	}

    /**
     * Get profile-picture for displaying.
     * @return profile-picture 
     */
    public StreamedContent getDisplayPicture() {
    	byte picture[] = this.getPicture();
		if (picture != null){
			StreamedContent display = new DefaultStreamedContent(new ByteArrayInputStream(picture));
			return display;
		}
		return null;
    }
    
    /**
     * Set profile-picture.
     * @param profil-picture
     * @return
     */
	public void setPicture(byte[] picture) {
		this.picture = picture;
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
     * Compares two people. (compares firstname and lastname)
     * @param obj of type {@link #Person}
     * @return true if equal or false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof Person)) {
            return false;
        }

        final Person other = (Person) obj;
        if (firstname == null || other.firstname == null || lastname == null || other.lastname == null)
            return false;

        if (!Objects.equals(firstname, other.firstname) || !Objects.equals(lastname, other.lastname))
            return false;

        return true;
    }

    /**
     * Get string-version.
     * @return string-version
     */
    @Override
    public String toString() {
        return this.firstname + " " + this.lastname;
    }

	@Override
	public int compareTo(Person p) {
		return this.lastname.compareToIgnoreCase(p.getLastName());
	}

}
