/**
 * 
 */
package baynes.kathleen.contacts.models;

import java.io.Serializable;

/**
 * The Class Contact.
 *
 * @author kbaynes
 */
public class Contact implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
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
	 * Constructor
	 * 
	 * @param displayName
	 * @param firstName
	 * @param lastName
	 * @param homePhone
	 * @param workPhone
	 * @param mobilePhone
	 * @param email
	 * @param address
	 * @param birthday
	 * @param preferredCallTimeStart
	 * @param preferredCallTimeEnd
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
	 * Gets the serialversionuid.
	 *
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
