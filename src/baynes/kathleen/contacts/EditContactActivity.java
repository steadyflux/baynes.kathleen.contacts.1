package baynes.kathleen.contacts;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import baynes.kathleen.contacts.db.ContactsDB;
import baynes.kathleen.contacts.models.Contact;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * This Activity displays data related to a contact and
 * enables editing (or canceling) of the contents of each field.
 * It also allows the creation of new contacts when passed a contact id of -1
 * 
 * Borrows heavily from:
 * http://developer.android.com/resources/tutorials/views/hello-timepicker.html
 * 
 * @author <A HREF="mailto:ktbaynes@gmail.com">Kathleen Baynes</A>
 **/
public class EditContactActivity extends Activity {

	protected static final String TAG = "baynes.kathleen.contacts.EditContactActivity";

	/**
	 * helper method
	 *
	 * @param from the from activity
	 * @param id the id of the contact
	 * @return the intent to be used
	 */
	public static Intent createIntent(Context from, long id) {
		Intent i = new Intent(from, EditContactActivity.class);
		i.putExtra(ContactLauncherActivity.CONTACT_ID, id);
		return i;
	}

	/** The starting hour for the preferred contact time */
	protected int mStartHour;

	/** The starting minute for the preferred contact time */
	protected int mStartMinute;

	/** The end hour for the preferred contact time */
	protected int mEndHour;

	/** The end minute for the preferred contact time */
	protected int mEndMinute;

	/** The year for the contact's birthday */
	protected int mYear;

	/** The month for the contact's birthday (remember to use zero index!) */
	protected int mMonth;

	/** The day of the month for the contact's birthday */
	protected int mDayOfMonth;

	/** identifies the birthday DatePicker dialog */
	private static final int BIRTHDAY_DIALOG_ID = 0;

	/** identifies the preferred contact start time TimePicker dialog */
	private static final int START_TIME_DIALOG_ID = 1;

	/** identifies the preferred contact end time TimePicker dialog */
	private static final int END_TIME_DIALOG_ID = 2;

	/**
	 * the callback received when the user sets the start time in the TimePicker
	 * dialog can most likely be refactored into shared listener for start and end
	 * time
	 */
	private TimePickerDialog.OnTimeSetListener mStartTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mStartHour = hourOfDay;
			mStartMinute = minute;
			updatePreferredTimeDisplay();
		}
	};

	/**
	 * the callback received when the user sets the end time in the TimePicker
	 * dialog can most likely be refactored into shared listener for start and end
	 * time
	 */
	private TimePickerDialog.OnTimeSetListener mEndTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mEndHour = hourOfDay;
			mEndMinute = minute;
			updatePreferredTimeDisplay();
		}
	};

	/**
	 * the callback received when the user sets the birthday in the DatePicker
	 * dialog
	 */
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDayOfMonth = dayOfMonth;
			updateBirthdate();
		}
	};

	private ContactsDB contactsDB;

	/** 
	 * Draws the screen and sets up the values
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_contact);
		
		contactsDB = new ContactsDB(this);
		
		long contact_id = getIntent().getExtras().getLong(ContactLauncherActivity.CONTACT_ID);
		
		Log.d(TAG, "Contact id: " + contact_id);
		
		final Contact contact =  (contact_id == -1) ? new Contact() : contactsDB.retrieveContact(contact_id);
		
		if (!contact.isNew()) {
			// unpack the bundled extras and set the fields
			((TextView) findViewById(R.id.edit_display_name_value)).setText(contact.getDisplayName());
			((TextView) findViewById(R.id.edit_first_name_value)).setText(contact.getFirstName());
			((TextView) findViewById(R.id.edit_last_name_value)).setText(contact.getLastName());
			((TextView) findViewById(R.id.birthdate_value)).setText(contact.getBirthday());
			((TextView) findViewById(R.id.edit_home_phone_value)).setText(contact.getHomePhone());
			((TextView) findViewById(R.id.edit_work_phone_value)).setText(contact.getWorkPhone());
			((TextView) findViewById(R.id.edit_mobile_phone_value)).setText(contact.getMobilePhone());
			((TextView) findViewById(R.id.edit_email_value)).setText(contact.getEmail());
			((TextView) findViewById(R.id.edit_address_value)).setText(contact.getAddress(), TextView.BufferType.EDITABLE);
		}
		Button pickBirthdate = (Button) findViewById(R.id.edit_birthdate);
		Button pickStartTime = (Button) findViewById(R.id.edit_preferred_contact_time_start);
		Button pickEndTime = (Button) findViewById(R.id.edit_preferred_contact_time_end);

		// add click listeners to the buttons
		pickBirthdate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(BIRTHDAY_DIALOG_ID);
			}
		});
		pickStartTime.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(START_TIME_DIALOG_ID);
			}
		});
		pickEndTime.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(END_TIME_DIALOG_ID);
			}
		});

		Button saveButton = (Button) findViewById(R.id.save_button);
		Button cancelButton = (Button) findViewById(R.id.cancel_button);

		saveButton.setOnClickListener(new OnClickListener() {
			
			/** 
			 * 
			 * adds or update the contact when the save button is clicked
			 * 
			 * @see android.view.View.OnClickListener#onClick(android.view.View)
			 */
			@Override
			public void onClick(View v) {

				Log.d(TAG, "on the click listener: "
				    + ((TextView) findViewById(R.id.edit_display_name_value)).getText().toString());

				contact.setDisplayName(((TextView) findViewById(R.id.edit_display_name_value)).getText().toString());
				contact.setFirstName(((TextView) findViewById(R.id.edit_first_name_value)).getText().toString());
				contact.setLastName(((TextView) findViewById(R.id.edit_last_name_value)).getText().toString());
				contact.setBirthday(((TextView) findViewById(R.id.birthdate_value)).getText().toString());
				contact.setPreferredCallTimeStart(Contact
				    .parsePreferredStartTimeFromString(((TextView) findViewById(R.id.preferred_contact_time_value)).getText()
				        .toString()));
				contact.setPreferredCallTimeEnd(Contact
				    .parsePreferredEndTimeFromString(((TextView) findViewById(R.id.preferred_contact_time_value)).getText()
				        .toString()));
				contact.setHomePhone(((TextView) findViewById(R.id.edit_home_phone_value)).getText().toString());
				contact.setWorkPhone(((TextView) findViewById(R.id.edit_work_phone_value)).getText().toString());
				contact.setMobilePhone(((TextView) findViewById(R.id.edit_mobile_phone_value)).getText().toString());
				contact.setEmail(((TextView) findViewById(R.id.edit_email_value)).getText().toString());
				contact.setAddress(((TextView) findViewById(R.id.edit_address_value)).getText().toString());
				
				if (contact.isNew()) {
					Log.d(TAG, "creating contact: " + contact.getDisplayName());
					contact.setId(contactsDB.insert(contact));
				}
				else {
					Log.d(TAG, "updating contact: " + contact.getDisplayName());
					contactsDB.update(contact);
				}
				
				getIntent().putExtra(ContactLauncherActivity.CONTACT_ID, contact.getId());
				setResult(RESULT_OK, getIntent());
				finish();
			}
		});

		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setResult(RESULT_CANCELED, getIntent());
				finish();
			}
		});

		
		DateFormat formatter;
		Date date;
		
		formatter = new SimpleDateFormat("hh:mm aa");
		try {
			if (contact.isNew()) {
				date = new Date();
			}
			else {
				date = (Date) formatter.parse(contact.getPreferredCallTimeStart());
			}
			mStartHour = date.getHours();
			mStartMinute = date.getMinutes();
			
			if (!contact.isNew()) {
				date = (Date) formatter.parse(contact.getPreferredCallTimeEnd());
			}
			mEndHour = date.getHours();
			mEndMinute = date.getMinutes();			
		} catch (ParseException e) {
    	Log.e(TAG, "Error parsing preferred contact times.", e);
    	//FIXME: need a better way for dealing with errors
    	finish();
    }
		
		formatter = new SimpleDateFormat("MM/dd/yyyy");
		try {
			if (contact.isNew()) {
				date = new Date();
			}
			else {
				date = (Date) formatter.parse(contact.getBirthday());
			}
	    mYear = date.getYear() + 1900;
			mDayOfMonth = date.getDate();
			mMonth = date.getMonth();
    } catch (ParseException e) {
    	Log.e(TAG, "Error parsing birthday", e);
    	//FIXME: need a better way for dealing with errors
    	finish();
    }
		
		// set initial values based on initialized fields
		updateBirthdate();
		updatePreferredTimeDisplay();

	}

	/** 
	 * sets up the dialogs and adds their respective listeners
	 * 
	 * @see android.app.Activity#onCreateDialog(int)
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case BIRTHDAY_DIALOG_ID:
			Log.d(TAG, "month: " + mMonth + "    ------ day: " + mDayOfMonth + "    ------- year: " + mYear);
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDayOfMonth);
		case START_TIME_DIALOG_ID:
			return new TimePickerDialog(this, mStartTimeSetListener, mStartHour, mStartMinute, false);
		case END_TIME_DIALOG_ID:
			return new TimePickerDialog(this, mEndTimeSetListener, mEndHour, mEndMinute, false);
		}
		return null;
	}

	/**
	 * Update preferred time display TextView. Format: HH:mm AM|PM - HH:mm AM|PM
	 */
	private void updatePreferredTimeDisplay() {
		((TextView) findViewById(R.id.preferred_contact_time_value)).setText(new StringBuilder().append(mStartHour % 12)
		    .append(":").append(pad(mStartMinute)).append((mStartHour < 12) ? " AM" : " PM").append(" - ")
		    .append(mEndHour % 12).append(":").append(pad(mEndMinute)).append((mEndHour < 12) ? " AM" : " PM"));
	}

	/**
	 * Pad single digit fields as needed From:
	 * http://developer.android.com/resources
	 * /tutorials/views/hello-timepicker.html
	 * 
	 * @param valueToPad
	 *          the value to pad
	 * @return the string
	 */
	private String pad(int valueToPad) {
		if (valueToPad >= 10)
			return String.valueOf(valueToPad);
		else
			return "0" + String.valueOf(valueToPad);
	}

	/**
	 * Update birthday TextView using set fields.
	 */
	protected void updateBirthdate() {
		((TextView) findViewById(R.id.birthdate_value)).setText(new StringBuilder().append(pad(mMonth + 1))
		    .append("/").append(pad(mDayOfMonth)).append("/").append(mYear));
	}
	
	/**
	 * Ensures that the database is closed when the activity pauses
	 * 
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		Log.d(TAG, "PAUSING and closing the db");
		super.onPause();
		contactsDB.close();
	}
}
