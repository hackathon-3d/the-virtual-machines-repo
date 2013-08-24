package net.c0ffee.tailgatr.data;

import java.text.SimpleDateFormat;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TailgateDatabase extends SQLiteOpenHelper {
	
	// Database information
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "Tailgater";
		
	// Tailgate table
	public static final String TABLE_TAILGATE = "tailgate";
	public static final String COLUMN_TAILGATE_ID = "_id";
	public static final String COLUMN_TAILGATE_TITLE = "title";
	public static final String COLUMN_TAILGATE_LOCATION = "location";
	public static final String COLUMN_TAILGATE_DATE = "date";
	
	// Food item table
	public static final String TABLE_ITEM = "item";
	public static final String COLUMN_ITEM_ID = "_id";
	public static final String COLUMN_ITEM_NAME = "name";
	public static final String COLUMN_ITEM_PRICE = "price";
		
	// Tailgate-food table (many-to-many)
	public static final String TABLE_REFERENCE = "reference";
	public static final String COLUMN_REFERENCE_TAILGATE_ID = "tailgate";
	public static final String COLUMN_REFERENCE_ITEM_ID = "item";
			
	// SQL statements for creating the tables
	private static final String DATABASE_CREATE_TAILGATES = "create table "
			+ TABLE_TAILGATE + "(" 
			+ COLUMN_TAILGATE_ID + " integer primary key autoincrement, "
			+ COLUMN_TAILGATE_TITLE + " text not null, "
			+ COLUMN_TAILGATE_LOCATION + " text not null, "
			+ COLUMN_TAILGATE_DATE + " text not null);";
			
	private static final String DATABASE_CREATE_ITEMS = "create table "
				+ TABLE_ITEM + "("
				+ COLUMN_ITEM_ID + " integer primary key autoincrement, "
				+ COLUMN_ITEM_NAME + " text not null, "
				+ COLUMN_ITEM_PRICE + " integer not null);";
			
	private static final String DATABASE_CREATE_REFERENCE = "create table "
				+ TABLE_REFERENCE + "("
				+ COLUMN_REFERENCE_TAILGATE_ID + " integer not null, "
				+ COLUMN_REFERENCE_ITEM_ID + " integer not null, " 
				+ " FOREIGN KEY ("+COLUMN_REFERENCE_TAILGATE_ID+") REFERENCES "+TABLE_TAILGATE+" ("+COLUMN_TAILGATE_ID+"), "
				+ " FOREIGN KEY ("+COLUMN_REFERENCE_ITEM_ID+") REFERENCES "+TABLE_ITEM+" ("+COLUMN_ITEM_ID+"));";
	
	// Used to define the format dates are stored (as strings)
	public final static SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
	// Public constructor
	public TailgateDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	// Called when the database is initialized
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE_TAILGATES);
		db.execSQL(DATABASE_CREATE_ITEMS);
		db.execSQL(DATABASE_CREATE_REFERENCE);
	}

	// Called when the database is upgraded
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TailgateDatabase.class.getName(),
		        "Upgrading database from version " + oldVersion + " to "
		            + newVersion + ", which will destroy all old data");
		    db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAILGATE);
		    db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM);
		    db.execSQL("DROP TABLE IF EXISTS " + TABLE_REFERENCE);
		    onCreate(db);
	}
}
