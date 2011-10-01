package baynes.kathleen.contacts;

import java.util.ArrayList;
import java.util.List;

import baynes.kathleen.contacts.models.Contact;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * This is the main Activity to be launched upon starting this application. It
 * includes buttons linking to both the DisplayContactActivity and the
 * EditContactActivity
 * 
 * @author <A HREF="mailto:ktbaynes@gmail.com">Kathleen Baynes</A>
 **/
public class ContactLauncherActivity extends Activity {

	/** Based off in-class example */
	protected static final String TAG = "baynes.kathleen.contacts.ContactLauncherActivity";
	protected static final int DISPLAY_RESULT = 16;

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
		final List<Contact> contacts = new ArrayList<Contact>();

		contacts.add(new Contact("Caden Cotard", "Caden", "Cotard", "410-867-5309", "111-222-3334", "111-222-3335",
		    "ccotard@example.com", "2057 Guilderland Ave\nSchenectady, NY", "04/25/1959", "03:00 AM", "08:00 AM"));
		contacts.add(new Contact("Adele Lack", "Adele", "Lack", "615-872-4556", "111-222-3337", "111-222-3338",
		    "alack@example.com", "128 Front Street\nSchenectady, NY", "02/12/1968", "05:00 AM", "10:00 AM"));
		contacts.add(new Contact("Olive Cotard", "Olive", "Cotard", "410-867-3000", "111-222-3340", "111-222-3341",
		    "ocotard@example.com", "2109 Colgate Place\nSchenectady, NY", "01/24/1997", "05:00 AM", "10:00 AM"));
		contacts.add(new Contact("Claire Keen", "Claire", "Keen", "443-223-9466", "111-222-3343", "111-222-3344",
		    "ckeen@example.com", "1062 Dean Street\nSchenectady, NY", "11/01/1977", "05:00 PM", "10:00 PM"));

		final LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

		ListView list = (ListView) findViewById(R.id.contact_list);
		if (list == null) {
			Log.d(TAG, "list is null");
		}
		Log.d(TAG, "List: " + list.toString());
		list.setAdapter(new ListAdapter() {

			@Override
			public int getCount() {
				return contacts.size();
			}

			@Override
			public Object getItem(int position) {
				return contacts.get(position);
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public int getItemViewType(int type) {
				return type;
			}

			@Override
			public View getView(int position, View availableView, ViewGroup group) {
				View view = inflater.inflate(R.layout.contact_entry, null);
				TextView displayName = (TextView) view.findViewById(R.id.display_name);
				Contact contact = contacts.get(position);
				displayName.setText(contact.getDisplayName());
				TextView phoneNumber = (TextView) view.findViewById(R.id.home_phone);
				phoneNumber.setText("(" + contact.getHomePhone() + ")");
				return view;
			}

			@Override
			public int getViewTypeCount() {
				return 1;
			}

			@Override
			public boolean hasStableIds() {
				return true;
			}

			@Override
			public boolean isEmpty() {
				return contacts.isEmpty();
			}

			@Override
			public void registerDataSetObserver(DataSetObserver arg0) {
			}

			@Override
			public void unregisterDataSetObserver(DataSetObserver arg0) {
			}

			@Override
			public boolean areAllItemsEnabled() {
				return true;
			}

			@Override
			public boolean isEnabled(int arg0) {
				return true;
			}

		});
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				Log.d(TAG, contacts.get(position).getDisplayName() + " was clicked");
				Intent i = DisplayContactActivity.createIntent(ContactLauncherActivity.this, contacts.get(position));
				startActivityForResult(i, DISPLAY_RESULT);
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case DISPLAY_RESULT:
			if (resultCode == RESULT_OK) {
				Log.d(TAG, "data: " + data.toString());
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}