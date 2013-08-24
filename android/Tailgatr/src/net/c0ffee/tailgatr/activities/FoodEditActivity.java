package net.c0ffee.tailgatr.activities;

import net.c0ffee.tailgatr.R;
import net.c0ffee.tailgatr.data.TailgateDatabase;
import net.c0ffee.tailgatr.data.TailgateProvider;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class FoodEditActivity extends Activity {
	
	private Uri mUri;
	
	private EditText mTitleField;
	private NumberPicker mPicker;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food_edit);
		
		mTitleField = (EditText)findViewById(R.id.item_title_text);
		
		mPicker = (NumberPicker)findViewById(R.id.price_picker);
		String[] nums = new String[50];
	    for(int i=0; i<nums.length; i++)
	           nums[i] = Integer.toString(i);
	    mPicker.setMinValue(1);
	    mPicker.setMaxValue(50);
	    mPicker.setDisplayedValues(nums);
	    mPicker.setValue(5);
	    
	    mUri = null;
	    Bundle extras = getIntent().getExtras();
	    if (extras != null) {
	        mUri = extras.getParcelable(TailgateProvider.CONTENT_ITEM_TYPE);
	        fillData(mUri);
	    }
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.edit_tailgate_menu, menu);
	    
	    if (getIntent().getExtras() == null) {
	    	menu.findItem(R.id.menu_item_delete).setVisible(false);
	    }
	    
	    return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.menu_item_save:
	        	String title = mTitleField.getText().toString();
				int price = mPicker.getValue() - 1;
				
				if (title.length() < 1) {
					mTitleField.setError("Please Enter a Title");
					return true;
				}
				
				ContentValues values = new ContentValues(); 
				values.put(TailgateDatabase.COLUMN_ITEM_NAME, title);
				values.put(TailgateDatabase.COLUMN_ITEM_PRICE, price);
				if (mUri == null) {
					getContentResolver().insert(TailgateProvider.CONTENT_ITEM_URI, values);
				} else {
					getContentResolver().update(mUri, values, null, null);
				}
				
				Toast.makeText(getApplicationContext(), "Item Saved", Toast.LENGTH_SHORT).show();
		
				finish();
	            return true;
	        case R.id.menu_item_delete:
	        	getContentResolver().delete(mUri, null, null);
	        	Toast.makeText(getApplicationContext(), "Item deleted", Toast.LENGTH_SHORT).show();
	        	finish();
	        	return true;
	        case R.id.menu_item_cancel:
	        	Toast.makeText(getApplicationContext(), "Changes Not Saved", Toast.LENGTH_SHORT).show();
				finish();
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	private void fillData(Uri uri) {
	    String[] projection = { TailgateDatabase.COLUMN_ITEM_NAME, TailgateDatabase.COLUMN_ITEM_PRICE};
	    String id = uri.getLastPathSegment();
	    String selection = TailgateDatabase.COLUMN_ITEM_ID + " = " + id;
	    Cursor cursor = getContentResolver().query(uri, projection, selection, null, null);
	    if (cursor != null) {
	      cursor.moveToFirst();
	      
	      String title = cursor.getString(cursor.getColumnIndexOrThrow(TailgateDatabase.COLUMN_ITEM_NAME));
	      int price = cursor.getInt(cursor.getColumnIndex(TailgateDatabase.COLUMN_ITEM_PRICE));
	      
	      mPicker.setValue(price);
	      mTitleField.setText(title);
	      
	      cursor.close();
	    }
	}
}
