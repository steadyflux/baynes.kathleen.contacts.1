package baynes.kathleen.contacts;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * This is the main Activity to be launched upon starting this application. It
 * displays a statically generated list of contacts EditContactActivity
 * 
 * @author <A HREF="mailto:ktbaynes@gmail.com">Kathleen Baynes</A>
 **/
public class ContactLauncherActivity extends Activity {

	/** Based off in-class example. */
	protected static final String TAG = "baynes.kathleen.contacts";

	/** The Constant DISPLAY_RESULT. */
	protected static final int DISPLAY_RESULT = 16;

	/** The Constant CREATE_RESULT. */
	protected static final int CREATE_RESULT = 21;

	/** The Constant CONTACT, used for populating extras */
	public static final String CONTACT_ID = "contact_id";

	/**
	 * Called when the activity is first created.
	 * 
	 * @param savedInstanceState
	 *          the saved instance state
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_list_layout);
	}

	
	/**
	 * 
	 * sets up the event listener each time the activity regain focus
	 * 
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {

		populateList();

		Button createContactButton = (Button) findViewById(R.id.create_contact_button);

		createContactButton.setOnClickListener(new OnClickListener() {

			/*
			 * sends to the edit contact activity to create a new contact
			 * 
			 * @see android.view.View.OnClickListener#onClick(android.view.View)
			 */
			@Override
			public void onClick(View v) {
				Log.d(TAG, "Create contact button was clicked");
				Intent editIntent = EditContactActivity.createIntent(ContactLauncherActivity.this, -1);
				startActivityForResult(editIntent, CREATE_RESULT);
			}
		});
		super.onStart();
	}

	/**
	 * Populates the contact list view from the database
	 */
	private void populateList() {

    Cursor cursor = getAllContacts();
    String[] fields = new String[] {
    		ContactsContract.Data._ID,
        ContactsContract.Data.DISPLAY_NAME
    };

    Log.d(TAG, "contact list length: " + cursor.getCount());
    
		ListView list = (ListView) findViewById(R.id.contact_list);

		list.setAdapter(new SimpleCursorAdapter(this, R.layout.contact_entry, cursor, fields, 
				new int[] { R.id.home_phone, R.id.display_name }));
		
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				Log.d(TAG, "[Position: " + position + "], [id:" + id + "] was clicked");
				Intent displayIntent = DisplayContactActivity.createIntent(ContactLauncherActivity.this, id);
				startActivityForResult(displayIntent, DISPLAY_RESULT);
			}
		});
	}

	/**
	 * Handles returned contacts and refreshes the contact list with updated (if
	 * any) data
	 * 
	 * @see android.app.Activity#onActivityResult(int, int,
	 * android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case DISPLAY_RESULT:
			if (resultCode == RESULT_OK) {
				Log.d(TAG, "data: " + data.toString());
			}
		}
		populateList();
		super.onActivityResult(requestCode, resultCode, data);
	}
	
  /**
   * Obtains the contact list for the currently selected account.
   *
   * @return A cursor for for accessing the contact list.
   */
  private Cursor getAllContacts()
  {   
			Uri contactUri = ContactsContract.Contacts.CONTENT_URI;
	
			String[] projection = new String[] {
					ContactsContract.Contacts._ID,
					ContactsContract.Contacts.DISPLAY_NAME,
					ContactsContract.Contacts.HAS_PHONE_NUMBER
			};
	
			String selection = ContactsContract.Contacts.HAS_PHONE_NUMBER + "='1'";
			String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
			Cursor contacts = managedQuery(contactUri, projection, selection, null, sortOrder);
      
      return contacts;
  }
}