package net.c0ffee.tailgatr.fragments;

import net.c0ffee.tailgatr.R;
import net.c0ffee.tailgatr.data.TailgateDatabase;
import net.c0ffee.tailgatr.data.TailgateProvider;
import net.c0ffee.tailgatr.interfaces.TailgateFragmentCommunicator;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class FoodListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

	private SimpleCursorAdapter mAdapter;
	TailgateFragmentCommunicator mActivity;
	
	private View mHeaderView;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View list_root = inflater.inflate(android.R.layout.list_content, null);
		
		mHeaderView = inflater.inflate(R.layout.tailgate_header, null);
		return list_root;
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mActivity = (TailgateFragmentCommunicator)getActivity();
		getListView().setBackgroundColor(Color.parseColor("#3B3C55"));
		this.getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		
		TextView text = (TextView) mHeaderView.findViewById(R.id.header_text);
		text.setText("What to bring?");
		getListView().addHeaderView(mHeaderView);
		
		String[] uiBindFrom = { TailgateDatabase.COLUMN_ITEM_NAME };
		int[] uiBindTo = { android.R.id.text1 };
		
		getLoaderManager().initLoader(0x03, null, this);
		mAdapter = new SimpleCursorAdapter(getActivity().getApplicationContext(), 
											android.R.layout.simple_list_item_checked, 
											null, uiBindFrom, uiBindTo, 
											CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		setListAdapter(mAdapter);
	}

	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projection = { TailgateDatabase.COLUMN_ITEM_ID, TailgateDatabase.COLUMN_ITEM_NAME, TailgateDatabase.COLUMN_ITEM_PRICE };
	    CursorLoader cursorLoader = new CursorLoader(getActivity(),
	            TailgateProvider.CONTENT_ITEM_URI, projection, null, null, null);
	    return cursorLoader;
    }

	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
    	mAdapter.swapCursor(cursor);
    	
    	Uri uri = mActivity.getTailgateUri();
		if (uri != null) {
			String[] projection = { TailgateDatabase.COLUMN_REFERENCE_ITEM_ID };
			Cursor referenceCursor = getActivity().getContentResolver().query(TailgateProvider.CONTENT_REFERENCE_URI, projection, 
													TailgateDatabase.COLUMN_REFERENCE_TAILGATE_ID + " = " + uri.getLastPathSegment(), null, null);
			
			if (referenceCursor != null && referenceCursor.moveToFirst()) {
				
				long id = referenceCursor.getLong(referenceCursor.getColumnIndexOrThrow(TailgateDatabase.COLUMN_REFERENCE_ITEM_ID));
		
				getListView().setItemChecked(getItemPositionByAdapterId(id), true);
				while (referenceCursor.moveToNext()) {
					id = referenceCursor.getLong(referenceCursor.getColumnIndexOrThrow(TailgateDatabase.COLUMN_REFERENCE_ITEM_ID));
					getListView().setItemChecked(getItemPositionByAdapterId(id), true);
				}
			}
			referenceCursor.close();
		}
    }

	public void onLoaderReset(Loader<Cursor> loader) {
    	mAdapter.swapCursor(null);
    }
	
	public void save(Uri uri) {
		String id = uri.getLastPathSegment();
		ContentResolver resolver = getActivity().getContentResolver();
		
		// Remove existing entries
		resolver.delete(TailgateProvider.CONTENT_REFERENCE_URI, 
													TailgateDatabase.COLUMN_REFERENCE_TAILGATE_ID + " = " + id, null);
		
		// Get the checked ids
		long[] ids = this.getListView().getCheckedItemIds();
		
		
		for (int i = 0; i < ids.length; i++) {
			ContentValues values = new ContentValues();
			values.put(TailgateDatabase.COLUMN_REFERENCE_TAILGATE_ID, id);
			values.put(TailgateDatabase.COLUMN_REFERENCE_ITEM_ID, Long.toString(ids[i]));
			resolver.insert(TailgateProvider.CONTENT_REFERENCE_URI, values);
		}
	}
	
	private int getItemPositionByAdapterId(long id)
	{
		ListView list = getListView();
	    for (int i = 0; i < list.getCount(); i++)
	    {
	        if (list.getItemIdAtPosition(i) == id)
	            return i;
	    }
	    return -1;
	}
}
