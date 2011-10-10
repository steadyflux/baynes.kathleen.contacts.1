package baynes.kathleen.contacts;

import baynes.kathleen.contacts.db.ContactsDB;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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
	protected static final String TAG = "baynes.kathleen.contacts.ContactLauncherActivity";

	/** The Constant DISPLAY_RESULT. */
	protected static final int DISPLAY_RESULT = 16;
	
	/** The Constant CREATE_RESULT. */
	protected static final int CREATE_RESULT = 21;

	/** The Constant CONTACT, used for populating extras */
	public static final String CONTACT_ID = "contact_id";

	/** The contact list adapter. */
	private ContactListAdapter contactListAdapter = null;

  private ContactsDB contactsDB;
	
	/**
	 * This class handles display of the contacts and populated the rows of the
	 * ListView.
	 */
	private final class ContactListAdapter extends SimpleCursorAdapter {

		public ContactListAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
			super(context, layout, c, from, to);
		}

		// @Override
		// public View getView(int position, View availableView, ViewGroup group) {
		//
		// View row;
		//
		// if (null == availableView) {
		// row = inflater.inflate(R.layout.contact_entry, null);
		// } else {
		// row = availableView;
		// }
		// TextView displayName = (TextView) row.findViewById(R.id.display_name);
		// Contact contact = contacts.get(position);
		// displayName.setText(contact.getDisplayName());
		// TextView phoneNumber = (TextView) row.findViewById(R.id.home_phone);
		// phoneNumber.setText("(" + contact.getHomePhone() + ")");
		// return row;
		// }

	}

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

		contactsDB = new ContactsDB(this);
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

	private void populateList() {
	  ListView list = (ListView) findViewById(R.id.contact_list);

		contactListAdapter = new ContactListAdapter(this, R.layout.contact_entry, contactsDB.getAllCursor(), new String[] {
		    ContactsDB.DISPLAY_NAME, ContactsDB.HOME_PHONE }, new int[] { R.id.display_name_value, R.id.home_phone_value });

		list.setAdapter(contactListAdapter);
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				Log.d(TAG, id + " was clicked");
				Intent displayIntent = DisplayContactActivity.createIntent(ContactLauncherActivity.this, id);
				startActivityForResult(displayIntent, DISPLAY_RESULT);
			}
		});
  }

	/*
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

	@Override
	protected void onPause() {
		super.onPause();
		contactsDB.close();
	}
}