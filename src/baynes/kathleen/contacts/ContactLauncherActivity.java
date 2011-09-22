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
				//intent.putExtras(getExtrasForActivity(activity));
				startActivity(intent);
			}
		});
	}

	protected Intent getExtrasForActivity(String activity) {
		return null;
	}
}