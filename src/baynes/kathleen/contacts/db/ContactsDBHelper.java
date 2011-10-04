package baynes.kathleen.contacts.db;

public class ContactsDBHelper {

	public static final String CONTACT_ROWID = "_id";
	public static final String DISPLAY_NAME = "displayName";
	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String HOME_PHONE = "homePhone";
	public static final String WORK_PHONE = "workPhone";
	public static final String MOBILE_PHONE = "mobilePhone";
	
	public static final String BIRTHDATE = "birthdate";
	
	public static final String EMAIL = "email";
	public static final String ADDRESS = "address";
	
	public static final String PREFERRED_CALL_TIME_START = "preferredCallTimeStart";
	public static final String PREFERRED_CALL_TIME_END = "preferredCallTimeEnd";
	
	// Database creation sql statement
	public static final String DATABASE_CREATE = "create table contact (_id integer primary key autoincrement, " +
			DISPLAY_NAME + " text not null, " + 
			FIRST_NAME + " text not null, " + 
			LAST_NAME + " text not null, " +
			HOME_PHONE + " text not null, " + 
			WORK_PHONE + " text not null, " + 
			MOBILE_PHONE + " text not null, " +
			BIRTHDATE + " text not null, " + 
			EMAIL + " text not null, " + 
			ADDRESS + " text not null, " +
			PREFERRED_CALL_TIME_START + " text not null, " + 
			PREFERRED_CALL_TIME_END + " text not null, " +
			");";

	
}
