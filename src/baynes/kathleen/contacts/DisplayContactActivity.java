package baynes.kathleen.contacts;

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
	
	/** The list position. */
	private int listPosition = -1;

	/**
	 * Creates the intent.
	 *
	 * @param from the from
	 * @param contact the contact
	 * @param position the position
	 * @return the intent
	 */
	public static Intent createIntent(Context from, Contact contact, int position) {
		Intent i = new Intent(from, DisplayContactActivity.class);
		i.putExtra(ContactLauncherActivity.CONTACT, contact);
		i.putExtra(ContactLauncherActivity.LIST_POSITION, position);
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

		// if the position from the original list has been set, don't set it again
		if (listPosition == -1) {
			listPosition = getIntent().getExtras().getInt(ContactLauncherActivity.LIST_POSITION);
		}
		contact = (Contact) getIntent().getExtras().getSerializable(ContactLauncherActivity.CONTACT);
		populateContactData();

		Button backToListButton = (Button) findViewById(R.id.back_to_list_button);
		Button editButton = (Button) findViewById(R.id.edit_button);

		backToListButton.setOnClickListener(new OnClickListener() {

			/*
			 * sends updated contact to the ContactLauncherActivity
			 * 
			 * @see android.view.View.OnClickListener#onClick(android.view.View)
			 */
			@Override
			public void onClick(View v) {
				contact.setDisplayName(((TextView) findViewById(R.id.display_name_value)).getText().toString());
				contact.setFirstName(((TextView) findViewById(R.id.first_name_value)).getText().toString());
				contact.setLastName(((TextView) findViewById(R.id.last_name_value)).getText().toString());
				contact.setBirthday(((TextView) findViewById(R.id.birthdate_value)).getText().toString());
				contact.setPreferredCallTimeStart(((TextView) findViewById(R.id.preferred_contact_time_value)).toString());
				contact.setPreferredCallTimeEnd(((TextView) findViewById(R.id.preferred_contact_time_value)).toString());
				contact.setPreferredCallTimeStart(Contact
				    .parsePreferredStartTimeFromString(((TextView) findViewById(R.id.preferred_contact_time_value)).getText()
				        .toString()));
				contact.setPreferredCallTimeEnd(Contact
				    .parsePreferredEndTimeFromString(((TextView) findViewById(R.id.preferred_contact_time_value)).getText()
				        .toString()));
				contact.setHomePhone(((TextView) findViewById(R.id.home_phone_value)).getText().toString());
				contact.setWorkPhone(((TextView) findViewById(R.id.work_phone_value)).getText().toString());
				contact.setMobilePhone(((TextView) findViewById(R.id.mobile_phone_value)).getText().toString());
				contact.setEmail(((TextView) findViewById(R.id.email_value)).getText().toString());
				contact.setAddress(((TextView) findViewById(R.id.address_value)).getText().toString());
				getIntent().putExtra(ContactLauncherActivity.CONTACT, contact);
				getIntent().putExtra(ContactLauncherActivity.LIST_POSITION, listPosition);
				setResult(RESULT_OK, getIntent());
				finish();
			}
		});

		editButton.setOnClickListener(new OnClickListener() {
			/*
			 * sends updated contact to the EditContactActivity
			 * 
			 * @see android.view.View.OnClickListener#onClick(android.view.View)
			 */
			@Override
			public void onClick(View v) {
				Log.d(TAG, contact.getDisplayName() + " was clicked");
				Intent editIntent = EditContactActivity.createIntent(DisplayContactActivity.this, contact);
				startActivityForResult(editIntent, EDIT_RESULT);
			}
		});
	}

	
	/**
	 * Populates contact data into the layout.
	 */
	private void populateContactData() {
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
	}

	/* 
	 * updates the layout based on updates from the edit screen
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case EDIT_RESULT:
			if (resultCode == RESULT_OK) {

				// extract the contact from the incoming data
				contact = (Contact) data.getExtras().getSerializable(ContactLauncherActivity.CONTACT);
				Log.e(TAG, "edited data: " + contact.getDisplayName());
				populateContactData();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
