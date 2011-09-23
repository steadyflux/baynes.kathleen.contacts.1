package baynes.kathleen.contacts;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayContactActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_contact);
        String name = null;
        
        name = getIntent().getExtras().getString("displayName");
        ((TextView) findViewById(R.id.displayNameValue)).setText(name);
        
        name = getIntent().getExtras().getString("firstName");
        ((TextView) findViewById(R.id.firstNameValue)).setText(name);
        
        name = getIntent().getExtras().getString("lastName");
        ((TextView) findViewById(R.id.lastNameValue)).setText(name);
        
        name = getIntent().getExtras().getString("birthdate");
        ((TextView) findViewById(R.id.birthdateValue)).setText(name);
    }
}
