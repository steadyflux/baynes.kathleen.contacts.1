package baynes.kathleen.contacts;

import android.app.Activity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.TextView;

public class EditContactActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_contact);

        String name = null;
        
        name = getIntent().getExtras().getString("displayName");
        ((EditText) findViewById(R.id.editDisplayNameValue)).setText(name);
        
        name = getIntent().getExtras().getString("firstName");
        ((EditText) findViewById(R.id.editFirstNameValue)).setText(name);
        
        name = getIntent().getExtras().getString("lastName");
        ((EditText) findViewById(R.id.editLastNameValue)).setText(name);
        
        ((DatePicker) findViewById(R.id.editBirthdateValue)).init(2011, 02, 28, null);
    }
}
