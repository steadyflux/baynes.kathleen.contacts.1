package baynes.kathleen.contacts;

import java.util.ArrayList;

import baynes.kathleen.contacts.models.Contact;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorDescription;
import android.app.Application;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;
import android.provider.ContactsContract.Data;
import android.util.Log;

/**
 * The Class ContactApplication. This is shared by all Aactivities in the app
 */
public class ContactApplication extends Application {

	/** The Constant TAG. */
	private static final String TAG = "baynes.kathleen.contacts";
	  
  /**
   * 
   * This inserts the contact into the android database, including several custom columns
   * Heavily Relied on source:
   * http://androidcookbook.com/Recipe.seam;?recipeId=334&recipeFrom=ViewTOC
   *
   * @param contact the contact
   * @return the long
   */
	public long createContact(Contact contact) {
		Log.d(TAG, "Creating contact " + contact.getDisplayName());
		
		long contactId = -1;
		ContentValues values = new ContentValues();
		values.put(Data.DISPLAY_NAME, contact.getDisplayName());
		ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();
		AccountManager accountManager = AccountManager.get(getApplicationContext());
		AuthenticatorDescription[] types = accountManager.getAuthenticatorTypes();
		operations.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
		    .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, types[0].type)
		    .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, contact.getDisplayName()).build());

		operations.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
		    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
		    .withValue(ContactsContract.Data.MIMETYPE, ContactsConstants.CONTENT_ITEM_TYPE)
		    .withValue(ContactsConstants.DISPLAY_NAME_DATA, contact.getDisplayName())
		    .withValue(ContactsConstants.BIRTHDAY_DATA, contact.getBirthday())
		    .withValue(ContactsConstants.START_CONTACT_DATA, contact.getPreferredCallTimeStart())
				.withValue(ContactsConstants.END_CONTACT_DATA, contact.getPreferredCallTimeEnd()).build());
		
		operations.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
		    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
		    .withValue(ContactsContract.Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
		    .withValue(StructuredName.GIVEN_NAME, contact.getFirstName())
		    .withValue(StructuredName.FAMILY_NAME, contact.getLastName()).build());
		
		operations.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
		    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
		    .withValue(ContactsContract.Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
		    .withValue(Phone.NUMBER, contact.getHomePhone())
		    .withValue(Phone.TYPE, Phone.TYPE_HOME)
		    .build());
		
		operations.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
		    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
		    .withValue(ContactsContract.Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
		    .withValue(Phone.NUMBER, contact.getWorkPhone())
		    .withValue(Phone.TYPE, Phone.TYPE_WORK)
		    .build());
		
		operations.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
		    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
		    .withValue(ContactsContract.Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
		    .withValue(Phone.NUMBER, contact.getMobilePhone())
		    .withValue(Phone.TYPE, Phone.TYPE_MOBILE)
		    .build());
		
		operations.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
		    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
		    .withValue(ContactsContract.Data.MIMETYPE, Email.CONTENT_ITEM_TYPE)
		    .withValue(Email.DATA, contact.getEmail())
		    .withValue(Email.TYPE, Email.TYPE_HOME)
		    .build());
		
		operations.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
		    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
		    .withValue(ContactsContract.Data.MIMETYPE, StructuredPostal.CONTENT_ITEM_TYPE)
		    .withValue(StructuredPostal.FORMATTED_ADDRESS, contact.getAddress())
		    .build());
		
    try {
    	ContentProviderResult[] contentProviderResults = getContentResolver().applyBatch(ContactsContract.AUTHORITY, operations);
	    contactId = ContentUris.parseId(contentProviderResults[0].uri);
	    Log.d(TAG, "contentProviderResults: " + contentProviderResults[0].uri);
    } catch (Exception e) {
    	Log.e(TAG, "Exception", e);
    	throw new RuntimeException("Error creating contact: ", e);
    }
		
		return contactId;
	}
	
	/**
	 * Retrieve contact from android database
	 *
	 * @param contactId the contact id
	 * @return the contact
	 */
	public Contact retrieveContact(long contactId) {
		Contact contact = new Contact();
		Log.d(TAG, "Retrieving contact " + contactId);

		contact.setId(contactId);
		String where = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
		
		// Load the names.
    String[] whereParameters = new String[]{ Long.toString(contactId), StructuredName.CONTENT_ITEM_TYPE};
    Cursor cursor = getContentResolver().query(ContactsContract.Data.CONTENT_URI, 
    		new String[] { StructuredName.GIVEN_NAME, StructuredName.FAMILY_NAME }, 
    		where, 
    		whereParameters, 
    		StructuredName.GIVEN_NAME);
    
		try {
			//this is setting the names as the last non-null/non-blank value from the list of all raw contact names, this isn't strictly "correct", but its a decent guess
			String first = null;
			String last = null;
			while (cursor.moveToNext()) {
				//ensure that we aren't setting null or blank values for the first and last name
				if (cursor.getString(cursor.getColumnIndex(StructuredName.GIVEN_NAME)) != null
				    && !("").equals(cursor.getString(cursor.getColumnIndex(StructuredName.GIVEN_NAME)))) {
					first = cursor.getString(cursor.getColumnIndex(StructuredName.GIVEN_NAME));
					Log.d(TAG, "got the first name: " + first);
				}
				if (cursor.getString(cursor.getColumnIndex(StructuredName.FAMILY_NAME)) != null
				    && !("").equals(cursor.getString(cursor.getColumnIndex(StructuredName.FAMILY_NAME)))) {
					last = cursor.getString(cursor.getColumnIndex(StructuredName.FAMILY_NAME));
					Log.d(TAG, "got the last name: " + last);
				}
			}
			contact.setFirstName(first);
			contact.setLastName(last);
		} finally {
			cursor.close();
		}

		//load the custom data fields
		whereParameters = new String[]{ Long.toString(contactId), ContactsConstants.CONTENT_ITEM_TYPE};
    cursor = getContentResolver().query(ContactsContract.Data.CONTENT_URI, 
    		new String[] { ContactsConstants.DISPLAY_NAME_DATA, ContactsConstants.BIRTHDAY_DATA, ContactsConstants.START_CONTACT_DATA, ContactsConstants.END_CONTACT_DATA }, 
    		where, 
    		whereParameters, 
    		null);	
		try {
			if (cursor.moveToFirst()) {
				if (cursor.getString(cursor.getColumnIndex(ContactsConstants.DISPLAY_NAME_DATA)) != null
				    && !("").equals(cursor.getString(cursor.getColumnIndex(ContactsConstants.DISPLAY_NAME_DATA)))) {
					contact.setDisplayName(cursor.getString(cursor.getColumnIndex(ContactsConstants.DISPLAY_NAME_DATA)));
				}
				if (cursor.getString(cursor.getColumnIndex(ContactsConstants.BIRTHDAY_DATA)) != null
				    && !("").equals(cursor.getString(cursor.getColumnIndex(ContactsConstants.BIRTHDAY_DATA)))) {
					contact.setBirthday(cursor.getString(cursor.getColumnIndex(ContactsConstants.BIRTHDAY_DATA)));
				}
				if (cursor.getString(cursor.getColumnIndex(ContactsConstants.START_CONTACT_DATA)) != null
				    && !("").equals(cursor.getString(cursor.getColumnIndex(ContactsConstants.START_CONTACT_DATA)))) {
					contact.setPreferredCallTimeStart(cursor.getString(cursor
					    .getColumnIndex(ContactsConstants.START_CONTACT_DATA)));
				}
				if (cursor.getString(cursor.getColumnIndex(ContactsConstants.END_CONTACT_DATA)) != null
				    && !("").equals(cursor.getString(cursor.getColumnIndex(ContactsConstants.END_CONTACT_DATA)))) {
					contact.setPreferredCallTimeEnd(cursor.getString(cursor.getColumnIndex(ContactsConstants.END_CONTACT_DATA)));
				}
			}
				
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
	
	/**
	 * Update contact in the android database
	 *
	 * @param contact the contact
	 */
	public void updateContact(Contact contact) {
		Log.d(TAG, "Updating contact " + contact.getDisplayName());
		Log.d(TAG, "Contact Id: " + contact.getId());
		ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();

		String where = Data.CONTACT_ID + "=? and " + Data.MIMETYPE + "=?";
		operations.add(ContentProviderOperation.newUpdate(Data.CONTENT_URI)
		    .withSelection(where, new String[] { String.valueOf(contact.getId()), StructuredName.CONTENT_ITEM_TYPE })
		    .withValue(StructuredName.FAMILY_NAME, contact.getLastName())
		    .withValue(StructuredName.GIVEN_NAME, contact.getFirstName()).build());
		operations.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
				.withSelection(where, new String[] { String.valueOf(contact.getId()), StructuredPostal.CONTENT_ITEM_TYPE })
		    .withValue(StructuredPostal.FORMATTED_ADDRESS, contact.getAddress())
		    .build());
		operations.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
				.withSelection(where, new String[] { String.valueOf(contact.getId()), ContactsConstants.CONTENT_ITEM_TYPE })
		    .withValue(ContactsConstants.DISPLAY_NAME_DATA, contact.getDisplayName())
		    .withValue(ContactsConstants.BIRTHDAY_DATA, contact.getBirthday())
		    .withValue(ContactsConstants.START_CONTACT_DATA, contact.getPreferredCallTimeStart())
				.withValue(ContactsConstants.END_CONTACT_DATA, contact.getPreferredCallTimeEnd()).build());
		
		where = Data.CONTACT_ID + "=? and " + Data.MIMETYPE + "=? and " + Phone.TYPE + "=?";
		operations.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
				.withSelection(where, new String[] { String.valueOf(contact.getId()), Phone.CONTENT_ITEM_TYPE, Integer.toString(Phone.TYPE_HOME) })
		    .withValue(Phone.NUMBER, contact.getHomePhone())
		    .build());
		operations.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
				.withSelection(where, new String[] { String.valueOf(contact.getId()), Phone.CONTENT_ITEM_TYPE, Integer.toString(Phone.TYPE_WORK) })
		    .withValue(Phone.NUMBER, contact.getWorkPhone())
		    .build());
		operations.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
				.withSelection(where, new String[] { String.valueOf(contact.getId()), Phone.CONTENT_ITEM_TYPE, Integer.toString(Phone.TYPE_MOBILE) })
		    .withValue(Phone.NUMBER, contact.getMobilePhone())
		    .build());
		
		where = Data.CONTACT_ID + "=? and " + Data.MIMETYPE + "=? and " + Email.TYPE + "=?";
		operations.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
				.withSelection(where, new String[] { String.valueOf(contact.getId()), Email.CONTENT_ITEM_TYPE, Integer.toString(Email.TYPE_HOME) })
		    .withValue(Email.DATA, contact.getEmail())
		    .build());
		
		try {
			getContentResolver().applyBatch(ContactsContract.AUTHORITY, operations);
		} catch (Exception e) {
			Log.e(TAG, "Exception", e);
			throw new RuntimeException("Error updating contact", e);
		}

	}
	
}
