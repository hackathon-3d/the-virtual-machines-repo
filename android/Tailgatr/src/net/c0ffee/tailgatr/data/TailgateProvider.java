package net.c0ffee.tailgatr.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class TailgateProvider extends ContentProvider {

	// Reference to the database
	private TailgateDatabase _database;
	
	/* Symbolic name of this provider. Used in content URI's (Uniform Resource Locator)
	 * so a ContentResolver knows where to get the data
	 */
	private static final String AUTHORITY = "net.c0ffee.tailgatr.data.TailgateProvider";
	
	// Used in the construction of the URI's
	private static final String TAILGATE_BASE_PATH = "tailgate";
	private static final String ITEM_BASE_PATH = "item";
	private static final String REFERENCE_BASE_PATH = "reference";
	
	// Content URI's.  The types of data that this TailgateProvider "provides"
	public static final Uri CONTENT_TAILGATE_URI = Uri.parse("content://" + AUTHORITY + "/" + TAILGATE_BASE_PATH);
	public static final Uri CONTENT_ITEM_URI = Uri.parse("content://" + AUTHORITY + "/" + ITEM_BASE_PATH);
	public static final Uri CONTENT_REFERENCE_URI = Uri.parse("content://" + AUTHORITY + "/" + REFERENCE_BASE_PATH);
	
	// Used to reference types elsewhere
	public static final String CONTENT_TAILGATE_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/c0ffee-tailgate";
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/c0ffee-item";
	public static final String CONTENT_REFERENCE_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/c0ffee-reference";
	
	// Identifiers we can use
	public static final int TAILGATES = 100;
	public static final int TAILGATE_ID = 110;
	public static final int ITEMS = 200;
	public static final int ITEM_ID = 220;
	public static final int REFERENCES = 300;
	public static final int REFERENCE_ID = 330;
	
	// Maps the URI's to ID's we can use
	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static {
		
		// To the item table
		sURIMatcher.addURI(AUTHORITY, ITEM_BASE_PATH, ITEMS);
		
		// To a specific row in the item table
		sURIMatcher.addURI(AUTHORITY, ITEM_BASE_PATH + "/#", ITEM_ID);
		
		// To the tailgate table
		sURIMatcher.addURI(AUTHORITY, TAILGATE_BASE_PATH, TAILGATES);
		
		// To a specific row in the tailgate table
		sURIMatcher.addURI(AUTHORITY, TAILGATE_BASE_PATH + "/#", TAILGATE_ID);
		
		// To the reference table
		sURIMatcher.addURI(AUTHORITY, REFERENCE_BASE_PATH, REFERENCES);
		
		// To a specific reference
		sURIMatcher.addURI(AUTHORITY, REFERENCE_BASE_PATH + "/#", REFERENCE_ID);
	}
	
	// Constructor
	public boolean onCreate() {
		_database = new TailgateDatabase(getContext());
		return true;
	}
	
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder  queryBuilder = new SQLiteQueryBuilder();
		
		// Get the ID we can use, from the URI
		int uriType = sURIMatcher.match(uri);
		
		switch (uriType) {
		
			// Uri is for all of the item table
			case ITEMS:
				queryBuilder.setTables(TailgateDatabase.TABLE_ITEM);
				break;
		
			// Uri is for a specific row in the item table
			case ITEM_ID:
				queryBuilder.setTables(TailgateDatabase.TABLE_ITEM);
				queryBuilder.appendWhere(TailgateDatabase.COLUMN_ITEM_ID + "=" + uri.getLastPathSegment());
				break;
			
			// All of the tailgate table
			case TAILGATES:
				queryBuilder.setTables(TailgateDatabase.TABLE_TAILGATE);
				break;
			
			// A specific row in the tailgate table
			case TAILGATE_ID:
				queryBuilder.setTables(TailgateDatabase.TABLE_TAILGATE);
				queryBuilder.appendWhere(TailgateDatabase.COLUMN_TAILGATE_ID + "=" + uri.getLastPathSegment());
				break;
				
			// All of the reference table
			case REFERENCES:
				queryBuilder.setTables(TailgateDatabase.TABLE_REFERENCE);
				break;
				
			default:
				throw new IllegalArgumentException("Unknown URI");
		}
		
		Cursor cursor = queryBuilder.query(_database.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}
	
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int uriType = sURIMatcher.match(uri);
		
		// Get a writable reference to the database
		SQLiteDatabase db = _database.getWritableDatabase();

		// Number of rows affected by the deletion that will be performed.
		// 0 is the default
		int rowsAffected = 0;
		
		// Id of the row to delete, if specified
		String id = null;
		
		switch (uriType) {
		
			// Delete from the tailgate table
			case TAILGATES:
				rowsAffected = db.delete(TailgateDatabase.TABLE_TAILGATE, selection, selectionArgs);
				break;
			
			// Delete a specific row in the tailgate table
			case TAILGATE_ID:
				id = uri.getLastPathSegment();
				if (TextUtils.isEmpty(selection)) {
					rowsAffected = db.delete(TailgateDatabase.TABLE_TAILGATE, TailgateDatabase.COLUMN_TAILGATE_ID + "=" + id, null);
				} else {
					rowsAffected = db.delete(TailgateDatabase.TABLE_TAILGATE, selection + " and " + TailgateDatabase.COLUMN_TAILGATE_ID + "=" + id, selectionArgs);
				}
			
			// Delete from the item table
			case ITEMS:
				rowsAffected = db.delete(TailgateDatabase.TABLE_ITEM, selection, selectionArgs);
				break;	
			
			// Delete a specific row from the item table
			case ITEM_ID:
				id = uri.getLastPathSegment();
		        if (TextUtils.isEmpty(selection)) {
		            rowsAffected = db.delete(TailgateDatabase.TABLE_ITEM, TailgateDatabase.COLUMN_ITEM_ID + "=" + id, null);
		        } else {
		            rowsAffected = db.delete(TailgateDatabase.TABLE_ITEM, selection + " and " + TailgateDatabase.COLUMN_ITEM_ID + "=" + id, selectionArgs);
		        }
				break;
				
			// Delete from the item table
			case REFERENCES:
				rowsAffected = db.delete(TailgateDatabase.TABLE_REFERENCE, selection, selectionArgs);
				break;

			default:
				throw new IllegalArgumentException("Unknown URI");
		}
		
		// Notify anyone using this data that it has changed
		getContext().getContentResolver().notifyChange(uri, null);
	    return rowsAffected;
	}

	public String getType(Uri uri) {
		return null;
	}

	public Uri insert(Uri uri, ContentValues values) {
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase db = _database.getWritableDatabase();
		
		// The id & Uri of the newly created row
		long rowid = 0;
		Uri newUri = null;
		switch (uriType) {
		
			// Insert into the tailgate table
			case TAILGATES:
				rowid = db.insert(TailgateDatabase.TABLE_TAILGATE, null, values);
				newUri = Uri.parse(CONTENT_TAILGATE_URI + "/" + rowid);
				break;
				
			// Insert into the item table
			case ITEMS:
				rowid = db.insert(TailgateDatabase.TABLE_ITEM, null, values);
				newUri = Uri.parse(CONTENT_ITEM_URI + "/" + rowid);
				break;
				
			// Insert into the reference table
			case REFERENCES:
				rowid = db.insert(TailgateDatabase.TABLE_REFERENCE, null, values);
				newUri = Uri.parse(CONTENT_REFERENCE_URI + "/" + rowid);
				break;
				
			default:
				throw new IllegalArgumentException("Unknown URI");
		}
		
		// Notify anyone using this database that it has changed
		getContext().getContentResolver().notifyChange(uri, null);
		return newUri;
	}

	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase db = _database.getWritableDatabase();
		int rowsAffected = 0;
		String id = uri.getLastPathSegment();
		
		switch (uriType) {
		
			// Update something(s) in the tailgate table
			case TAILGATES:
				rowsAffected = db.update(TailgateDatabase.TABLE_TAILGATE, values, selection, selectionArgs);
				break;
			
			// Update a specific row in the tailgate table
			case TAILGATE_ID:
				if (TextUtils.isEmpty(selection)) {
					rowsAffected = db.update(TailgateDatabase.TABLE_TAILGATE, values, TailgateDatabase.COLUMN_TAILGATE_ID + "=" + id, null);
				} else {
					rowsAffected = db.update(TailgateDatabase.TABLE_ITEM, values, TailgateDatabase.COLUMN_ITEM_ID + "=" + id + " and " + selection, selectionArgs);
				}
				break;
			
			// Update something(s) in the item table
			case ITEMS:
				rowsAffected = db.update(TailgateDatabase.TABLE_ITEM, values, selection, selectionArgs);
				break;
			
			// Update a specific row in the item table
			case ITEM_ID:
				if (TextUtils.isEmpty(selection)) {
			        rowsAffected = db.update(TailgateDatabase.TABLE_ITEM, values, TailgateDatabase.COLUMN_ITEM_ID + "=" + id, null);
			    } else {
			        rowsAffected = db.update(TailgateDatabase.TABLE_ITEM, values, TailgateDatabase.COLUMN_ITEM_ID + "=" + id + " and " + selection, selectionArgs);
			    }
				break;
			
			// Update a row in the reference table
			case REFERENCES:
				rowsAffected = db.update(TailgateDatabase.TABLE_REFERENCE, values, selection, selectionArgs);
				break;
				
			default:
				throw new IllegalArgumentException("Unknown URI");
		}
		
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsAffected;
	}

}
