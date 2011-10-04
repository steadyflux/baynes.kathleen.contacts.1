package baynes.kathleen.contacts.db;

import baynes.kathleen.contacts.models.Contact;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * The Class ContactsDB.
 * 
 * Borrows from http://www.vogella.de/articles/AndroidSQLite/article.html#overview_sqlite (isn't this guy awesome?)
 * 
 * @author kbaynes
 */
public class ContactsDB extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "contacts.db";

	private static final int DATABASE_VERSION = 1;
	
	private SQLiteDatabase database;

	public ContactsDB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(ContactsDBHelper.DATABASE_CREATE);
	}

	// Method is called during an upgrade of the database, e.g. if you increase
	// the database version
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		Log.w(ContactsDB.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion
		    + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS contact");
		onCreate(database);
	}
	
	public long createContact(Contact contact) {
		return database.insert(DATABASE_TABLE, null, initialValues);
		return -1;
	}
	
	public Contact retrieveContact(long id) {
		return null;
	}
	
	public boolean updateContact(Contact contact) {
		return true;
	}
	
	public boolean deleteContact(long id) {
		return true;
	}
}
