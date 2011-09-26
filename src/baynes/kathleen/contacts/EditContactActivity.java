package baynes.kathleen.contacts;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.TimePicker;

/**
 * This Activity displays static (at the moment) data related to a contact and
 * enables editing (but not saving) of the contents of each field.
 * 
 * Borrows heavily from: http://developer.android.com/resources/tutorials/views/hello-timepicker.html
 * 
 * @author <A HREF="mailto:ktbaynes@gmail.com">Kathleen Baynes</A>
**/
public class EditContactActivity extends Activity {

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
	
	/** the callback received when the user sets the start time in the TimePicker dialog 
	 *  can most likely be refactored into shared listener for start and end time 
	 */
	private TimePickerDialog.OnTimeSetListener mStartTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mStartHour = hourOfDay;
			mStartMinute = minute;
			updatePreferredTimeDisplay();
		}
	};
	
	/** the callback received when the user sets the end time in the TimePicker dialog 
	 *  can most likely be refactored into shared listener for start and end time 
	 */
	private TimePickerDialog.OnTimeSetListener mEndTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mEndHour = hourOfDay;
			mEndMinute = minute;
			updatePreferredTimeDisplay();
		}
	};
	
	/** the callback received when the user sets the birthday in the DatePicker dialog  */
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDayOfMonth = dayOfMonth;
			updateBirthdate();
		}
	};

	/** Draws the screen and sets up the values */
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_contact);
		String name = null;

		//unpack the bundled extras and set the fields
		name = getIntent().getExtras().getString("displayName");
		((EditText) findViewById(R.id.editDisplayNameValue)).setText(name);

		name = getIntent().getExtras().getString("firstName");
		((EditText) findViewById(R.id.editFirstNameValue)).setText(name);

		name = getIntent().getExtras().getString("lastName");
		((EditText) findViewById(R.id.editLastNameValue)).setText(name);

		Button pickBirthdate = (Button) findViewById(R.id.editBirthdate);
		Button pickStartTime = (Button) findViewById(R.id.editPreferredContactTimeStart);
		Button pickEndTime = (Button) findViewById(R.id.editPreferredContactTimeEnd);

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

		//dummmy initialization.
		mStartHour = 18;
		mStartMinute = 0;
		mEndHour = 21;
		mEndMinute = 0;
		mYear = 2011;
		mDayOfMonth = 28;
		mMonth = 2;
		
		//set initial values based on initialized fields
		updateBirthdate();
		updatePreferredTimeDisplay();

		name = getIntent().getExtras().getString("homeNumber");
		((TextView) findViewById(R.id.editHomePhoneValue)).setText(name);

		name = getIntent().getExtras().getString("workNumber");
		((TextView) findViewById(R.id.editWorkPhoneValue)).setText(name);

		name = getIntent().getExtras().getString("mobileNumber");
		((TextView) findViewById(R.id.editMobilePhoneValue)).setText(name);

		name = getIntent().getExtras().getString("email");
		((TextView) findViewById(R.id.editEmailValue)).setText(name);

		name = getIntent().getExtras().getString("address");
		((TextView) findViewById(R.id.editAddressValue)).setText(name, TextView.BufferType.EDITABLE);

	}

	/** sets up the dialogs and adds their respective listeners*/
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateDialog(int)
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
	    switch (id) {
	    case BIRTHDAY_DIALOG_ID:
	        return new DatePickerDialog(this,
	                mDateSetListener, mYear, mMonth, mDayOfMonth);
	    case START_TIME_DIALOG_ID:
	        return new TimePickerDialog(this,
	                mStartTimeSetListener, mStartHour, mStartMinute, false);
	    case END_TIME_DIALOG_ID:
	        return new TimePickerDialog(this,
	                mEndTimeSetListener, mEndHour, mEndMinute, false);
	    }
	    return null;
	}

	
	/**
	 * Update preferred time display TextView. Format: HH:MM AM|PM - HH:MM AM|PM 
	 */
	private void updatePreferredTimeDisplay() {
		((TextView) findViewById(R.id.preferredContactTimeValue)).setText(
	        new StringBuilder()
	                .append(mStartHour % 12).append(":")
	                .append(pad(mStartMinute))
	                .append((mStartHour < 12) ? " AM" : " PM")
					.append(" - ")
					.append(mEndHour % 12).append(":")
	                .append(pad(mEndMinute))
	                .append((mEndHour < 12) ? " AM" : " PM"));
	}

	/**
	 * Pad single digit fields as needed
	 * From: http://developer.android.com/resources/tutorials/views/hello-timepicker.html
	 * @param valueToPad the value to pad
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
		((TextView) findViewById(R.id.birthdateValue)).setText(
			new StringBuilder()
				.append(pad(mMonth+1)) //zero indexing
				.append("\\")
				.append(pad(mDayOfMonth))
				.append("\\")
				.append(mYear));
	}
}
