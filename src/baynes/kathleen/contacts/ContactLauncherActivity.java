package baynes.kathleen.contacts;

import java.util.ArrayList;
import java.util.List;

import baynes.kathleen.contacts.models.Contact;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * This is the main Activity to be launched upon starting this application. It
 * displays a statically generated list of contacts
 * EditContactActivity
 * 
 * @author <A HREF="mailto:ktbaynes@gmail.com">Kathleen Baynes</A>
 **/
public class ContactLauncherActivity extends Activity {
	
	/** Based off in-class example. */
	protected static final String TAG = "baynes.kathleen.contacts.ContactLauncherActivity";
	
	/** The Constant DISPLAY_RESULT. */
	protected static final int DISPLAY_RESULT = 16;

	/** The Constant CONTACT, used for populating extras */
	public static final String CONTACT = "contact";
	
	/** The Constant LIST_POSITION, used for populating extras; included to help determine which contact to change upon return from the display*/
	public static final String LIST_POSITION = "listPosition";
	
	/** The contacts. */
	private ArrayList<Contact> contacts = new ArrayList<Contact>();
	
	/** The contact list adapter. */
	private ContactListAdapter contactListAdapter = null;
	
	/** The inflater. */
	private LayoutInflater inflater;
	
	/**
	 * This class handles display of the contacts and populated the rows of the ListView.
	 */
	private final class ContactListAdapter extends ArrayAdapter<Contact> {

		/**
		 * Instantiates a new contact list adapter.
		 *
		 * @param context the context
		 * @param textViewResourceId the text view resource id
		 * @param contacts the contacts
		 */
		public ContactListAdapter(Context context, int textViewResourceId, List<Contact> contacts) {
	    super(context, textViewResourceId, contacts);
    }

	  /* Populated the contact view
  	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
  	 */
  	@Override
	  public View getView(int position, View availableView, ViewGroup group) {
	  	
	  	View row;
	  	 
			if (null == availableView) {
				row = inflater.inflate(R.layout.contact_entry, null);
			} else {
				row = availableView;
			}
	  	TextView displayName = (TextView) row.findViewById(R.id.display_name);
	  	Contact contact = contacts.get(position);
	  	displayName.setText(contact.getDisplayName());
	  	TextView phoneNumber = (TextView) row.findViewById(R.id.home_phone);
	  	phoneNumber.setText("(" + contact.getHomePhone() + ")");
			return row;
	  }

  }

	/**
	 * Called when the activity is first created.
	 * 
	 * @param savedInstanceState
	 *          the saved instance state
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		contacts.add(new Contact("Caden Cotard", "Caden", "Cotard", "410-867-5309", "111-222-3334", "111-222-3335",
		    "ccotard@example.com", "2057 Guilderland Ave\nSchenectady, NY", "04/25/1959", "03:00 AM", "08:00 AM"));
		contacts.add(new Contact("Adele Lack", "Adele", "Lack", "615-872-4556", "111-222-3337", "111-222-3338",
		    "alack@example.com", "128 Front Street\nSchenectady, NY", "02/12/1968", "05:00 AM", "10:00 AM"));
		contacts.add(new Contact("Olive Cotard", "Olive", "Cotard", "410-867-3000", "111-222-3340", "111-222-3341",
		    "ocotard@example.com", "2109 Colgate Place\nSchenectady, NY", "01/24/1997", "05:00 AM", "10:00 AM"));
		contacts.add(new Contact("Claire Keen", "Claire", "Keen", "443-223-9466", "111-222-3343", "111-222-3344",
		    "ckeen@example.com", "1062 Dean Street\nSchenectady, NY", "11/01/1977", "05:00 PM", "10:00 PM"));
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_list_layout);
		
		inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		ListView list = (ListView) findViewById(R.id.contact_list);

		contactListAdapter = new ContactListAdapter(this, R.layout.contact_entry, contacts);
		list.setAdapter(contactListAdapter);
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				Log.d(TAG, contacts.get(position).getDisplayName() + " was clicked");
				Intent displayIntent = DisplayContactActivity.createIntent(ContactLauncherActivity.this, contacts.get(position), position);
				startActivityForResult(displayIntent, DISPLAY_RESULT);
			}
		});

	}

	/* Handles returned contacts and refreshes the contact list with updated (if any) data
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case DISPLAY_RESULT:
			if (resultCode == RESULT_OK) {
				Log.d(TAG, "data: " + data.toString());
				
				//extract the contact from the incoming data
				Contact contact = (Contact) data.getExtras().getSerializable(ContactLauncherActivity.CONTACT);
				int listPosition = data.getExtras().getInt(LIST_POSITION);
				//replace the original contact with the new version
				contacts.set(listPosition, contact);
				
				//notify the adapter of the change
				//couldn't figure out how to do this "correctly" so I am just regenerating the entire list
				ListView list = (ListView) findViewById(R.id.contact_list);
				contactListAdapter = new ContactListAdapter(this, R.layout.contact_entry, contacts);
				list.setAdapter(contactListAdapter);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}