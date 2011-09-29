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
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * This is the main Activity to be launched upon starting this application. It includes buttons linking to
 * both the DisplayContactActivity and the EditContactActivity
 * 
 * @author <A HREF="mailto:ktbaynes@gmail.com">Kathleen Baynes</A>
**/
public class ContactLauncherActivity extends Activity {
    
    /** Based off in-class example */
    protected static final String TAG = "baynes.kathleen.contacts.ContactLauncherActivity";

	/**
	 * Called when the activity is first created.
	 *
	 * @param savedInstanceState the saved instance state
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list_layout);
        final List<Contact> contacts = new ArrayList<Contact>();
        
        contacts.add(new Contact("ccotard", "Caden", "Cotard", "111-222-3333", "111-222-3334", "111-222-3335", 
        		"ccotard@example.com", "", "04/25/1955", "03:00 AM", "08:00 AM"));
        contacts.add(new Contact("alack", "Adele", "Lack", "111-222-3336", "111-222-3337", "111-222-3338", 
        		"alack@example.com", "", "02/12/1966", "05:00 AM", "10:00 AM"));
        contacts.add(new Contact("ocotard", "Olive", "Cotard", "111-222-3339", "111-222-3340", "111-222-3341", 
        		"ocotard@example.com", "", "01/24/1997", "05:00 AM", "10:00 AM"));
        contacts.add(new Contact("ckeen", "Claire", "Keen", "111-222-3342", "111-222-3343", "111-222-3344", 
        		"ckeen@example.com", "", "11/01/1977", "05:00 PM", "10:00 PM"));
        
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
				displayName.setText(contact.toString());
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
        
//        setButtonListener(R.id.editContact, "edit");
//        setButtonListener(R.id.displayContact, "display");
    }

	/**
	 * Sets the button listener to direct to the edit and display activities
	 *
	 * @param button_id the button_id
	 * @param activity the activity
	 */
	private void setButtonListener(int button_id, final String activity) {
		Button button = (Button) findViewById(button_id);
        button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d(TAG, "Click occurred for " + activity);
				Intent intent = new Intent("baynes.kathleen.contacts." + activity);
				startActivity(setExtrasForActivity(intent, activity));
			}
		});
	}

	/**
	 * Sets most of the initial contact information.
	 *
	 * @param intent the intent we are adding the data to
	 * @param activity the activity we are directing to (display vs. edit)
	 * @return the intent modified with extras
	 */
	protected Intent setExtrasForActivity(Intent intent, String activity) {
		Log.d(TAG, "Setting Extras for " + activity);

		intent.putExtra("displayName", "Ellie C.");
		intent.putExtra("firstName", "Eleanor");
		intent.putExtra("lastName", "Caltagirone");
		
		intent.putExtra("birthdate", "03/28/2011" );
		
		intent.putExtra("homeNumber", "111-222-3333" );
		intent.putExtra("workNumber", "111-222-3334" );
		intent.putExtra("mobileNumber", "111-222-3335" );
		
		intent.putExtra("email", "ebc0328@example.com" );
		
		intent.putExtra("address", "5786 Flagflower Place\r\nColumbia, MD 21045" );
		
		return intent;
	}
}