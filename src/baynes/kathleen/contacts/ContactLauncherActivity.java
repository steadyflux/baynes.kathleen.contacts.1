package baynes.kathleen.contacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ContactLauncherActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button displayContact = (Button) findViewById(R.id.displayContact);
        displayContact.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent display = new Intent();
				
			}
		});
        		
    }
}