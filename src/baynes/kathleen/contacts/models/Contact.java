/**
 * 
 */
package baynes.kathleen.contacts.models;

import java.io.Serializable;

/**
 * The Class Contact used by other activities.
 *
 * @author kbaynes
 */
public class Contact implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The id for the contact. */
	private long id;
	
	/** The display name. */
	private String displayName;
	
	/** The first name. */
	private String firstName;
	
	/** The last name. */
	private String lastName;
	
	/** The home phone. */
	private String homePhone;
	
	/** The work phone. */
	private String workPhone;
	
	/** The mobile phone. */
	private String mobilePhone;
	
	/** The email. */
	private String email;
	
	/** The address. */
	private String address;
	
	/** The birthday. */
	private String birthday;
	
	/** The preferred call time start. */
	private String preferredCallTimeStart;
	
	/** The preferred call time end. */
	private String preferredCallTimeEnd;

	/**
	 * Constructor.
	 *
	 * @param displayName the display name
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param homePhone the home phone
	 * @param workPhone the work phone
	 * @param mobilePhone the mobile phone
	 * @param email the email
	 * @param address the address
	 * @param birthday the birthday
	 * @param preferredCallTimeStart the preferred call time start
	 * @param preferredCallTimeEnd the preferred call time end
	 */
	public Contact(String displayName, String firstName, String lastName,
			String homePhone, String workPhone, String mobilePhone,
			String email, String address, String birthday,
			String preferredCallTimeStart, String preferredCallTimeEnd) {
		super();
		this.displayName = displayName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.homePhone = homePhone;
		this.workPhone = workPhone;
		this.mobilePhone = mobilePhone;
		this.email = email;
		this.address = address;
		this.birthday = birthday;
		this.preferredCallTimeStart = preferredCallTimeStart;
		this.preferredCallTimeEnd = preferredCallTimeEnd;
	}

	/**
	 * Instantiates a new contact.
	 */
	public Contact() {
  }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Contact [displayName=" + displayName + ", firstName="
				+ firstName + ", lastName=" + lastName + ", homePhone="
				+ homePhone + ", workPhone=" + workPhone + ", mobilePhone="
				+ mobilePhone + ", email=" + email + ", address=" + address
				+ ", birthday=" + birthday + ", preferredCallTimeStart="
				+ preferredCallTimeStart + ", preferredCallTimeEnd="
				+ preferredCallTimeEnd + "]";
	}

	/**
	 * Gets the display name.
	 *
	 * @return the display name
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Sets the display name.
	 *
	 * @param displayName the new display name
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the home phone.
	 *
	 * @return the home phone
	 */
	public String getHomePhone() {
		return homePhone;
	}

	/**
	 * Sets the home phone.
	 *
	 * @param homePhone the new home phone
	 */
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	/**
	 * Gets the work phone.
	 *
	 * @return the work phone
	 */
	public String getWorkPhone() {
		return workPhone;
	}

	/**
	 * Sets the work phone.
	 *
	 * @param workPhone the new work phone
	 */
	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	/**
	 * Gets the mobile phone.
	 *
	 * @return the mobile phone
	 */
	public String getMobilePhone() {
		return mobilePhone;
	}

	/**
	 * Sets the mobile phone.
	 *
	 * @param mobilePhone the new mobile phone
	 */
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the address.
	 *
	 * @param address the new address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Gets the birthday.
	 *
	 * @return the birthday
	 */
	public String getBirthday() {
		return birthday;
	}

	/**
	 * Sets the birthday.
	 *
	 * @param birthday the new birthday
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	/**
	 * Gets the preferred call time start.
	 *
	 * @return the preferred call time start
	 */
	public String getPreferredCallTimeStart() {
		return preferredCallTimeStart;
	}

	/**
	 * Sets the preferred call time start.
	 *
	 * @param preferredCallTimeStart the new preferred call time start
	 */
	public void setPreferredCallTimeStart(String preferredCallTimeStart) {
		this.preferredCallTimeStart = preferredCallTimeStart;
	}

	/**
	 * Gets the preferred call time end.
	 *
	 * @return the preferred call time end
	 */
	public String getPreferredCallTimeEnd() {
		return preferredCallTimeEnd;
	}

	/**
	 * Sets the preferred call time end.
	 *
	 * @param preferredCallTimeEnd the new preferred call time end
	 */
	public void setPreferredCallTimeEnd(String preferredCallTimeEnd) {
		this.preferredCallTimeEnd = preferredCallTimeEnd;
	}

	

	/**
	 * Parses the preferred start time from string of format: HH:mm AM|PM - HH:mm AM|PM.
	 *
	 * @param preferredContactTimeString the preferred contact time string
	 * @return the string
	 */
	public static String parsePreferredStartTimeFromString(String preferredContactTimeString) {
		return preferredContactTimeString.substring(0, 8).trim();
  }
	
	/**
	 * Parses the preferred end time from string of format: HH:mm AM|PM - HH:mm AM|PM.
	 *
	 * @param preferredContactTimeString the preferred contact time string
	 * @return the string
	 */
	public static String parsePreferredEndTimeFromString(String preferredContactTimeString) {
		return preferredContactTimeString.substring(10).trim();
  }

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId() {
	  return id;
  }

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(long id) {
	  this.id = id;
  }
	
	
	/**
	 * Checks if this is a new contact (has an id of 0)
	 *
	 * @return true, if is new
	 */
	public boolean isNew() {
	  return (this.id == 0L);
  }
}
