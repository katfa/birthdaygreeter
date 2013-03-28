package hioa.mappe2.s171183;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class DBAdapter {
	Context context;
	static final String TAG="DbHelper";
	static final String DB_CONTACT = "contact.db";
	static final String TABLE = "contact";
	static final String ID = BaseColumns._ID;
	static final String FIRST_NAME = "firstname";
	static final String LAST_NAME = "lastname";
	static final String PHONENUMBER = "phonenumber";
	static final String BIRTHDAY = "birthday";
	
	static final int DB_VERSION = 2;
	
	private DBHelper dbHelper;
	
	public DBAdapter(Context context){
		this.context = context;
		dbHelper = new DBHelper(context);
	}
	
	public DBAdapter open() throws SQLException
	{
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		return this;
	}
	
	public void insert(Contact contact){
		dbHelper.addContact(contact);
	}
	
	public int getTotalContacts(){
		return dbHelper.getCount();
	}
	
	public ArrayList<Contact> selectAll(){
		return dbHelper.getAllContacts();
	}
	
	public Contact getContact(int id){
		return dbHelper.getContact(id);
	}
	
	public void deleteContact(Contact c){
		dbHelper.deleteContact(c.getId());
	}
	
	private static class DBHelper extends SQLiteOpenHelper{
		private SQLiteDatabase db;

		private DBHelper(Context context, String databaseName, CursorFactory factory,
				int databaseVersion) {
			super(context, databaseName, factory, databaseVersion);
		
		}
		
		private DBHelper(Context context){
			super(context, DB_CONTACT,null, DB_VERSION);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			String createTable = "create table " + TABLE + "(" + ID + " integer primary key autoincrement, " 
								+ FIRST_NAME + " text, " + LAST_NAME + " text, " + PHONENUMBER + " text, " + BIRTHDAY + " text);";
			Log.d(TAG, "sql onCreate " + createTable);
			db.execSQL(createTable);
		}


		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("drop table if exists " + TABLE);
			Log.d(TAG, "updated");
			onCreate(db);
		}

		
		private void addContact(Contact contact){
		    db = this.getWritableDatabase();
		    ContentValues values = new ContentValues(3);

		    values.put(FIRST_NAME, contact.getFirstName());
		    values.put(LAST_NAME, contact.getLastName());
		    values.put(PHONENUMBER, contact.getPhoneNumber());
		    values.put(BIRTHDAY, contact.getBirthday());

		    db.insert(TABLE, null, values);
		  }
		
		private ArrayList<Contact> getAllContacts(){
			ArrayList<Contact> allContacts = new ArrayList<Contact>();
			String selectQuery = "select * from " + TABLE;
			
			db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			
			if(cursor.moveToFirst()){
				while(cursor.isAfterLast() == false){
					int id = cursor.getInt(cursor.getColumnIndex(ID));
					String firstname = cursor.getString(cursor.getColumnIndex(FIRST_NAME));
					String lastname = cursor.getString(cursor.getColumnIndex(LAST_NAME));
					String phoneNumber = cursor.getString(cursor.getColumnIndex(PHONENUMBER));
					String birthday = cursor.getString(cursor.getColumnIndex(BIRTHDAY));
					
					Contact c = new Contact(id, firstname, lastname, phoneNumber, birthday);
					allContacts.add(c);
					cursor.moveToNext();
				}
			}
			cursor.close();
			return allContacts;
		}
		private Contact getContact(int id){
			db = this.getReadableDatabase();
			Cursor cursor = db.query(TABLE, new String[] { ID, FIRST_NAME, LAST_NAME, PHONENUMBER, BIRTHDAY }, 
									ID + "=?", 
									new String[] { String.valueOf(id)}, 
									null, null, null, null);
			if(cursor != null){
				cursor.moveToFirst();
			}
			
			String firstname = cursor.getString(cursor.getColumnIndex(FIRST_NAME));
			String lastname = cursor.getString(cursor.getColumnIndex(LAST_NAME));
			String phonenumber = cursor.getString(cursor.getColumnIndex(PHONENUMBER));
			String birthday = cursor.getString(cursor.getColumnIndex(BIRTHDAY));
			
			return new Contact(id, firstname, lastname, phonenumber, birthday);
		}
		
		private int getCount(){
			String countQuery = "select * from " + TABLE + ";";
			db = this.getReadableDatabase();
			
			Cursor cursor = db.rawQuery(countQuery, null);
			return cursor.getCount();
		}
		
		private void deleteContact(int id){
			db = this.getWritableDatabase();
			db.delete(TABLE, ID + " =?", new String[] {String.valueOf(id)});
		}

	}

}

