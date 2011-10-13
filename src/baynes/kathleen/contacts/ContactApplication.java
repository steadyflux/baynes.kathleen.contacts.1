package baynes.kathleen.contacts;

import baynes.kathleen.contacts.db.ContactsDB;
import android.app.Application;

public class ContactApplication extends Application {

	/** The contact database */
	private ContactsDB contactsDB;
	
	/* (non-Javadoc)
   * @see android.app.Application#onCreate()
   */
  @Override
  public void onCreate() {
  	super.onCreate();
  }

	/* (non-Javadoc)
   * @see android.app.Application#onLowMemory()
   */
  @Override
  public void onLowMemory() {
	  super.onLowMemory();
	  if (contactsDB != null) {
	  	contactsDB.close();
	  }
	  contactsDB = null;
  }

	/* (non-Javadoc)
   * @see android.app.Application#onTerminate()
   */
  @Override
  public void onTerminate() {
	  super.onTerminate();
	  if (contactsDB != null) {
	  	contactsDB.close();
	  }
	  contactsDB = null;
  }

	public ContactsDB getContactsDB() {
		if (contactsDB == null) {
			contactsDB = new ContactsDB(this);
			
		}
	  return contactsDB;
  }
}
