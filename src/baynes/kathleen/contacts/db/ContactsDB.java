package baynes.kathleen.contacts.db;

import baynes.kathleen.contacts.models.Contact;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

/**
 * The Class ContactsDB.
 * 
 * Borrows from
 * http://www.vogella.de/articles/AndroidSQLite/article.html#overview_sqlite
 * (isn't this guy awesome?)
 * 
 * @author kbaynes
 */
public class ContactsDB {
	private static final String DATABASE_NAME = "contacts.db";

	private static final int DATABASE_VERSION = 1;
	
	private static final String CONTACT_ROWID = "_id";
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
	private static final String CREATE_CONTACT_TABLE_SQL = "create table contact (_id integer primary key autoincrement, " +
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
			PREFERRED_CALL_TIME_END + " text not null)";
	
	private static final String INSERT_CONTACT_TABLE_SQL = "insert into contact (" +
			DISPLAY_NAME + ", " + 
			FIRST_NAME + ", " + 
			LAST_NAME + ", " +
			HOME_PHONE + ", " + 
			WORK_PHONE + ", " + 
			MOBILE_PHONE + ", " +
			BIRTHDATE + ", " + 
			EMAIL + ", " + 
			ADDRESS + ", " +
			PREFERRED_CALL_TIME_START + ", " + 
			PREFERRED_CALL_TIME_END +
			") values(?,?,?,?,?,?,?,?,?,?,?)";
	
	private static final String DROP_CONTACT_TABLE_SQL = "drop table contact";
	
	private static final String[] SELECT_COLUMNS = {
		CONTACT_ROWID, DISPLAY_NAME, FIRST_NAME, LAST_NAME, HOME_PHONE, WORK_PHONE, MOBILE_PHONE,
		BIRTHDATE, EMAIL, ADDRESS, PREFERRED_CALL_TIME_START, PREFERRED_CALL_TIME_END
	};

	private static final String TAG = "baynes.kathleen.contacts.ContactsDB";
	
	private SQLiteDatabase db;
	private SQLiteStatement insertStatement;

	/**
	 * @param db
	 */
	public ContactsDB(Context context) {
		Log.d(TAG, CREATE_CONTACT_TABLE_SQL);
		ContactsDBHelper helper = new ContactsDBHelper(context);
		db = helper.getWritableDatabase();
		insertStatement = db.compileStatement(INSERT_CONTACT_TABLE_SQL);
		
	}

	public void close() {
		if (db != null)
			db.close();
	}

	public Contact createContact(Cursor cursor) {
		if (cursor.moveToFirst()) {
			Contact contact = new Contact();
			contact.setId(cursor.getLong(cursor.getColumnIndex(CONTACT_ROWID)));
			contact.setDisplayName(cursor.getString(cursor.getColumnIndex(DISPLAY_NAME)));
			contact.setFirstName(cursor.getString(cursor.getColumnIndex(FIRST_NAME)));
			contact.setLastName(cursor.getString(cursor.getColumnIndex(LAST_NAME)));
			contact.setHomePhone(cursor.getString(cursor.getColumnIndex(HOME_PHONE)));
			contact.setWorkPhone(cursor.getString(cursor.getColumnIndex(WORK_PHONE)));
			contact.setMobilePhone(cursor.getString(cursor.getColumnIndex(MOBILE_PHONE)));
			contact.setBirthday(cursor.getString(cursor.getColumnIndex(BIRTHDATE)));
			contact.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL)));
			contact.setAddress(cursor.getString(cursor.getColumnIndex(ADDRESS)));
			contact.setPreferredCallTimeStart(cursor.getString(cursor.getColumnIndex(PREFERRED_CALL_TIME_START)));
			contact.setPreferredCallTimeEnd(cursor.getString(cursor.getColumnIndex(PREFERRED_CALL_TIME_END)));
			return contact;
		}
		return null;
	}
	
	public long insert(Contact contact) {
		insertStatement.bindString(1, contact.getDisplayName());
		insertStatement.bindString(2, contact.getFirstName());
		insertStatement.bindString(3, contact.getLastName());
		insertStatement.bindString(4, contact.getHomePhone());
		insertStatement.bindString(5, contact.getWorkPhone());
		insertStatement.bindString(6, contact.getMobilePhone());
		insertStatement.bindString(7, contact.getBirthday());
		insertStatement.bindString(8, contact.getEmail());
		insertStatement.bindString(9, contact.getAddress());
		insertStatement.bindString(10, contact.getPreferredCallTimeStart());
		insertStatement.bindString(11, contact.getPreferredCallTimeEnd());
		return insertStatement.executeInsert();
	}
	
	public Contact retrieveContact(long id) {
		Cursor cursor = db.query("contact", SELECT_COLUMNS, "_id = ?", new String[] {String.valueOf(id)}, null, null, null);
		try {
			return createContact(cursor);
		} finally {
			if (cursor != null)
				cursor.close();
		}
	}
	
	public void update(Contact contact) {
		ContentValues values = new ContentValues();
		values.put(DISPLAY_NAME, contact.getDisplayName());
		values.put(FIRST_NAME, contact.getFirstName());
		values.put(LAST_NAME, contact.getLastName());
		values.put(HOME_PHONE, contact.getHomePhone());
		values.put(WORK_PHONE, contact.getWorkPhone());
		values.put(MOBILE_PHONE, contact.getMobilePhone());
		values.put(BIRTHDATE, contact.getBirthday());
		values.put(EMAIL, contact.getEmail());
		values.put(ADDRESS, contact.getAddress());
		values.put(PREFERRED_CALL_TIME_START, contact.getPreferredCallTimeStart());
		values.put(PREFERRED_CALL_TIME_END, contact.getPreferredCallTimeEnd());
		db.update("contact", values , "_id = ?", new String[] {String.valueOf(contact.getId())});
	}

	public boolean deleteContact(long id) {
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	public Cursor getAllCursor() {
		return db.query("contact", SELECT_COLUMNS, null, null, null, null, DISPLAY_NAME + " asc");
	}
	
	public class ContactsDBHelper extends SQLiteOpenHelper  {

		
		public ContactsDBHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		@Override
		public void onCreate(SQLiteDatabase database) {
			Log.w(ContactsDB.class.getName(), CREATE_CONTACT_TABLE_SQL);
			database.execSQL(CREATE_CONTACT_TABLE_SQL);
		}

		// Method is called during an upgrade of the database, e.g. if you increase
		// the database version
		@Override
		public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
			Log.w(ContactsDB.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion
			    + ", which will destroy all old data");
			database.execSQL(DROP_CONTACT_TABLE_SQL);
			onCreate(database);
		}
		
	}
}
