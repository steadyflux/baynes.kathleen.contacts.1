package baynes.kathleen.contacts;

import baynes.kathleen.contacts.db.ContactsDB;
import android.app.Application;

public class ContactsApplication extends Application {

	private ContactsDB contactDB;

	public ContactsDB getContactDB() {
	  return contactDB;
  }

	public void setContactDB(ContactsDB contactDB) {
	  this.contactDB = contactDB;
  }

}
