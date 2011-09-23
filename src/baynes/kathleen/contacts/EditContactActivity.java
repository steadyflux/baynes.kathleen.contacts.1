package baynes.kathleen.contacts;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class EditContactActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_contact);

        String name = null;
        
        name = getIntent().getExtras().getString("displayName");
        ((TextView) findViewById(R.id.editDisplayNameValue)).setText(name);
        
        name = getIntent().getExtras().getString("firstName");
        ((TextView) findViewById(R.id.editFirstNameValue)).setText(name);
        
        name = getIntent().getExtras().getString("lastName");
        ((TextView) findViewById(R.id.editLastNameValue)).setText(name);
    }
}
