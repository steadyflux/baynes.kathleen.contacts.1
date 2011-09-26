package baynes.kathleen.contacts;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 *  This class displays a contact's information
 */
public class DisplayContactActivity extends Activity {

	/** Draws the screen and sets up the values 
	 * @param savedInstanceState the saved instance state
	 */
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_contact);
        String name = null;
        
        //unpack the bundled extras and set the fields
        name = getIntent().getExtras().getString("displayName");
        ((TextView) findViewById(R.id.displayNameValue)).setText(name);
        
        name = getIntent().getExtras().getString("firstName");
        ((TextView) findViewById(R.id.firstNameValue)).setText(name);
        
        name = getIntent().getExtras().getString("lastName");
        ((TextView) findViewById(R.id.lastNameValue)).setText(name);
        
        name = getIntent().getExtras().getString("birthdate");
        ((TextView) findViewById(R.id.birthdateValue)).setText(name);
        
        //dummy initialization
        ((TextView) findViewById(R.id.preferredContactTimeValue)).setText("6:00 PM - 9:00 PM");
        
        name = getIntent().getExtras().getString("homeNumber");
        ((TextView) findViewById(R.id.homePhoneValue)).setText(name);
        
        name = getIntent().getExtras().getString("workNumber");
        ((TextView) findViewById(R.id.workPhoneValue)).setText(name);
        
        name = getIntent().getExtras().getString("mobileNumber");
        ((TextView) findViewById(R.id.mobilePhoneValue)).setText(name);
        
        name = getIntent().getExtras().getString("email");
        ((TextView) findViewById(R.id.emailValue)).setText(name);
        
        name = getIntent().getExtras().getString("address");
        ((TextView) findViewById(R.id.addressValue)).setText(name);
               
    }
}
