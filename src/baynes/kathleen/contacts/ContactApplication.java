package baynes.kathleen.contacts;

import baynes.kathleen.contacts.db.ContactsDB;
import baynes.kathleen.contacts.models.Contact;
import android.app.Application;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;
import android.provider.ContactsContract.Contacts;
import android.util.Log;

public class ContactApplication extends Application {

	private static final String TAG = "baynes.kathleen.contacts";
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

	public Contact retrieveContact(long contactId) {
		Contact contact = new Contact();

		Cursor cursor = getContentResolver().query(Contacts.CONTENT_URI,
		    new String[] { Contacts._ID, Contacts.DISPLAY_NAME }, Contacts._ID + " = " + contactId, null, null);
		try {
			if (cursor.moveToFirst()) {
				contact.setId(cursor.getLong(cursor.getColumnIndex(Contacts._ID)));
				contact.setDisplayName(cursor.getString(cursor.getColumnIndex(Contacts.DISPLAY_NAME)));
				Log.d(TAG, "Display Name for " + contact.getId() + ": " + contact.getDisplayName());
			}
		} finally {
			cursor.close();
		}

		// Load the names.
		
		String where = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?"; 
    String[] whereParameters = new String[]{ Long.toString(contactId), ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE};

    cursor = getContentResolver().query(ContactsContract.Data.CONTENT_URI, 
    		new String[] { StructuredName.GIVEN_NAME, StructuredName.FAMILY_NAME }, 
    		where, 
    		whereParameters, 
    		ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME);
    
		try {
			//this is setting the names as the last non-null value from the list of all raw contact names, this isn't strictly "correct", but its a decent guess
			String first = null;
			String last = null;
			while (cursor.moveToNext()) {
				first = cursor.getString(cursor
				    .getColumnIndex(StructuredName.GIVEN_NAME));
				last = cursor.getString(cursor
				    .getColumnIndex(StructuredName.FAMILY_NAME));
			}
			contact.setFirstName(first);
			contact.setLastName(last);
		} finally {
			cursor.close();
		}

		// Load the phone numbers.
		cursor = getContentResolver().query(Phone.CONTENT_URI, new String[] { Phone.NUMBER, Phone.TYPE },
		    Phone.CONTACT_ID + "=" + contactId, null, Phone.IS_SUPER_PRIMARY + " DESC");
		try {
			while (cursor.moveToNext()) {
				// determine type of number and assign it to the proper contact field
				int type = cursor.getInt(cursor.getColumnIndex(Phone.TYPE));
				String phoneNumber = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
				switch (type) {
				case Phone.TYPE_HOME:
					contact.setHomePhone(phoneNumber);
					break;
				case Phone.TYPE_WORK:
					contact.setWorkPhone(phoneNumber);
					break;
				case Phone.TYPE_OTHER: //just catching some edge cases imported from map data
					contact.setWorkPhone(phoneNumber);
					break;
				case Phone.TYPE_MOBILE:
					contact.setMobilePhone(phoneNumber);
					break;
				default:
					break;
				}

			}
		} finally {
			cursor.close();
		}
		
		cursor = getContentResolver().query(Email.CONTENT_URI, new String[] { Email.DATA1 }, //couldnt find the Email.ADDRESS in eclipse
		    Email.CONTACT_ID + "=" + contactId, null, null);
		try {
			//in the case of multiples, we are just loading the first email, no sort order used
			if (cursor.moveToFirst()) {
				contact.setEmail(cursor.getString(cursor.getColumnIndex(Email.DATA1)));
			}
		} finally {
			cursor.close();
		}

		cursor = getContentResolver().query(StructuredPostal.CONTENT_URI, new String[] { StructuredPostal.FORMATTED_ADDRESS },
		    StructuredPostal.CONTACT_ID + "=" + contactId, null, null);
		try {
			//in the case of multiples, we are just loading the first structured postal address
			if (cursor.moveToFirst()) {
				contact.setAddress(cursor.getString(cursor.getColumnIndex(StructuredPostal.FORMATTED_ADDRESS)));
			}
		} finally {
			cursor.close();
		}
		
		return contact;
	}
}
