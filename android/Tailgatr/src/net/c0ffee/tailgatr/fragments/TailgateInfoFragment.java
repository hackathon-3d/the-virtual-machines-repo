package net.c0ffee.tailgatr.fragments;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import net.c0ffee.tailgatr.R;
import net.c0ffee.tailgatr.data.TailgateDatabase;
import net.c0ffee.tailgatr.data.TailgateProvider;
import net.c0ffee.tailgatr.interfaces.TailgateFragmentCommunicator;
import android.app.Fragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

public class TailgateInfoFragment extends Fragment {

	private TailgateFragmentCommunicator mActivity;
	private Uri mUri;
	
	private EditText mTitleField;
	private EditText mLocationField;
	private DatePicker mPicker;
	
	private Calendar mCal;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCal = Calendar.getInstance();
	}
	
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_tailgate_info_edit, null);
		mTitleField = (EditText) view.findViewById(R.id.tailgate_title_field);
		mLocationField = (EditText) view.findViewById(R.id.tailgate_location_field);
		mPicker = (DatePicker) view.findViewById(R.id.tailgate_edit_date);
		return view;
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		int year = mCal.get(Calendar.YEAR);
		int month = mCal.get(Calendar.MONTH);
		int day = mCal.get(Calendar.DAY_OF_MONTH);
		mPicker.updateDate(year, month, day);
		
		mActivity = (TailgateFragmentCommunicator)getActivity();
		mUri = mActivity.getTailgateUri();
		if (mUri != null) {
			fillData(mUri);
		}
	}
	
	private void fillData(Uri uri) {
		String[] projection = { TailgateDatabase.COLUMN_TAILGATE_TITLE, TailgateDatabase.COLUMN_TAILGATE_LOCATION, TailgateDatabase.COLUMN_TAILGATE_DATE};
	    String id = uri.getLastPathSegment();
	    String selection = TailgateDatabase.COLUMN_ITEM_ID + " = " + id;
	    Cursor cursor = getActivity().getContentResolver().query(uri, projection, selection, null, null);
	    if (cursor != null) {
		      cursor.moveToFirst();
		      
		      String title = cursor.getString(cursor.getColumnIndex(TailgateDatabase.COLUMN_TAILGATE_TITLE));
		      String location = cursor.getString(cursor.getColumnIndexOrThrow(TailgateDatabase.COLUMN_TAILGATE_LOCATION));
		      
		      mTitleField.setText(title);
		      mLocationField.setText(location);
		      
		    try {
		    	Calendar cal = Calendar.getInstance();
				Date date = TailgateDatabase.iso8601Format.parse(cursor.getString(cursor.getColumnIndex(TailgateDatabase.COLUMN_TAILGATE_DATE)));
				cal.setTime(date);
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH);
				int day = cal.get(Calendar.DAY_OF_MONTH);
				mPicker.updateDate(year, month, day);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		    cursor.close();
	    }
	}
	
	public Uri save() {
		String title = mTitleField.getText().toString();
		String location = mLocationField.getText().toString();
		
		boolean error = false;
		if (title.length() < 1) {
			mTitleField.setError("Please enter a title");
			error = true;
		}
		if (location.length() < 1) {
			mLocationField.setError("Please enter a location");
			error = true;
		}
		if (error) {
			return null;
		}
		
		Calendar cal = Calendar.getInstance();
		cal.set(mPicker.getYear(), mPicker.getMonth(), mPicker.getDayOfMonth());
		
		ContentValues values = new ContentValues();
		values.put(TailgateDatabase.COLUMN_TAILGATE_TITLE, title);
		values.put(TailgateDatabase.COLUMN_TAILGATE_LOCATION, location);
		values.put(TailgateDatabase.COLUMN_TAILGATE_DATE, TailgateDatabase.iso8601Format.format(cal.getTime()));
		
		if (mUri == null) {
			mUri = getActivity().getContentResolver().insert(TailgateProvider.CONTENT_TAILGATE_URI, values);
		} else {
			getActivity().getContentResolver().update(mUri, values, null, null);
		}
		
		Log.d("Groppe", mUri.toString());
		return mUri;
	}
}
