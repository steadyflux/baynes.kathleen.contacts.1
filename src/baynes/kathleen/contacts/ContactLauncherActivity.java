package baynes.kathleen.contacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ContactLauncherActivity extends Activity {
    protected static final String TAG = "baynes.kathleen.contacts.ContactLauncherActivity";

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        setButtonListener(R.id.editContact, "edit");
        setButtonListener(R.id.displayContact, "display");
    }

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