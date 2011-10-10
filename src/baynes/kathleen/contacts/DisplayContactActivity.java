package baynes.kathleen.contacts;

import baynes.kathleen.contacts.db.ContactsDB;
import baynes.kathleen.contacts.models.Contact;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * This Activity displays static data related to a contact. Also links to the
 * EditContactActivity and returns to ContactLauncherActivity
 * 
 * @author <A HREF="mailto:ktbaynes@gmail.com">Kathleen Baynes</A>
 **/
public class DisplayContactActivity extends Activity {

	/** The Constant TAG. */
	protected static final String TAG = "baynes.kathleen.contacts.DisplayContactActivity";
	
	/** The Constant EDIT_RESULT. */
	protected static final int EDIT_RESULT = 17;

	/** The contact. */
	private Contact contact;

	private ContactsDB contactsDB;

	/**
	 * Creates the intent.
	 *
	 * @param from the from
	 * @param contact the contact
	 * @param position the position
	 * @return the intent
	 */
	public static Intent createIntent(Context from, long id) {
		Intent i = new Intent(from, DisplayContactActivity.class);
		i.putExtra(ContactLauncherActivity.CONTACT_ID, id);
		return i;
	}

	/**
	 * Draws the screen and sets up the values.
	 *
	 * @param savedInstanceState the saved instance state
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_contact);
		
		Button backToListButton = (Button) findViewById(R.id.back_to_list_button);
		Button editButton = (Button) findViewById(R.id.edit_button);

		backToListButton.setOnClickListener(new OnClickListener() {

			/**
			 * sends contact id to the ContactLauncherActivity
			 * 
			 * @see android.view.View.OnClickListener#onClick(android.view.View)
			 */
			@Override
			public void onClick(View v) {
				getIntent().putExtra(ContactLauncherActivity.CONTACT_ID, contact.getId());
				setResult(RESULT_OK, getIntent());
				finish();
			}
		});

		editButton.setOnClickListener(new OnClickListener() {
			/**
			 * sends the contact id to the EditContactActivity
			 * 
			 * @see android.view.View.OnClickListener#onClick(android.view.View)
			 */
			@Override
			public void onClick(View v) {
				Log.d(TAG, contact.getDisplayName() + " was clicked");
				Intent editIntent = EditContactActivity.createIntent(DisplayContactActivity.this, contact.getId());
				startActivityForResult(editIntent, EDIT_RESULT);
			}
		});
	}

	/**
	 * populates the view when this activity starts
	 * 
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		
		populateContactData();
		
		super.onStart();
  }

	
	/**
	 * Populates contact data into the layout.
	 */
	private void populateContactData() {
		long contact_id = getIntent().getExtras().getLong(ContactLauncherActivity.CONTACT_ID);
		
		contactsDB = new ContactsDB(this);
		
	  contact = contactsDB.retrieveContact(contact_id);
		((TextView) findViewById(R.id.display_name_value)).setText(contact.getDisplayName());
		((TextView) findViewById(R.id.first_name_value)).setText(contact.getFirstName());
		((TextView) findViewById(R.id.last_name_value)).setText(contact.getLastName());
		((TextView) findViewById(R.id.birthdate_value)).setText(contact.getBirthday());
		((TextView) findViewById(R.id.preferred_contact_time_value)).setText(contact.getPreferredCallTimeStart() + " - "
		    + contact.getPreferredCallTimeEnd());
		((TextView) findViewById(R.id.home_phone_value)).setText(contact.getHomePhone());
		((TextView) findViewById(R.id.work_phone_value)).setText(contact.getWorkPhone());
		((TextView) findViewById(R.id.mobile_phone_value)).setText(contact.getMobilePhone());
		((TextView) findViewById(R.id.email_value)).setText(contact.getEmail());
		((TextView) findViewById(R.id.address_value)).setText(contact.getAddress());
		
		contactsDB.close();
	}

	/** 
	 * updates the layout based on updates from the edit screen
	 * 
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case EDIT_RESULT:
			if (resultCode == RESULT_OK) {

				// extract the contact from the incoming data
				populateContactData();
				Log.d(TAG, "edited data: " + contact.getDisplayName());
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
