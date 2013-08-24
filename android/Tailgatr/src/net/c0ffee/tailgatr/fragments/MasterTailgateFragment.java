package net.c0ffee.tailgatr.fragments;

import net.c0ffee.tailgatr.R;
import net.c0ffee.tailgatr.activities.TailgateActivity;
import net.c0ffee.tailgatr.activities.TailgatesActivity;
import net.c0ffee.tailgatr.data.TailgateDatabase;
import net.c0ffee.tailgatr.data.TailgateProvider;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MasterTailgateFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

	private SimpleCursorAdapter mAdapter;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		getListView().setBackgroundColor(Color.parseColor("#3B3C55"));
		
		// Data to display
		String[] uiBindFrom = { TailgateDatabase.COLUMN_TAILGATE_TITLE, TailgateDatabase.COLUMN_TAILGATE_LOCATION };

		// Where to display them
		int[] uiBindTo = { android.R.id.text1, android.R.id.text2 };

		// The loader does the loading asynchronously
		getLoaderManager().initLoader(0x01, null, this);

		// Initialize the adapter
		mAdapter = new SimpleCursorAdapter(
				getActivity().getApplicationContext(), android.R.layout.simple_list_item_2,
				null, uiBindFrom, uiBindTo,
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

		// Set it for this list
		setListAdapter(mAdapter);
	}
	
	public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
	    inflater.inflate(R.menu.tailgate_list_menu, menu);
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.menu_item_new_tailgate:
				Intent i = new Intent(getActivity(), TailgateActivity.class);
		        startActivity(i);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent i = new Intent(this.getActivity(), TailgateActivity.class);
	    Uri eventUri = Uri.parse(TailgateProvider.CONTENT_TAILGATE_URI + "/" + id);
	    i.putExtra(TailgateProvider.CONTENT_TAILGATE_TYPE, eventUri);
	    startActivity(i);
	}
	
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projection = { TailgateDatabase.COLUMN_TAILGATE_ID, TailgateDatabase.COLUMN_TAILGATE_TITLE, TailgateDatabase.COLUMN_TAILGATE_LOCATION };
	    CursorLoader cursorLoader = new CursorLoader(getActivity(),
	            TailgateProvider.CONTENT_TAILGATE_URI, projection, null, null, null);
	    return cursorLoader;
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
    	mAdapter.swapCursor(cursor);
    }
    
    public void onLoaderReset(Loader<Cursor> loader) {
    	mAdapter.swapCursor(null);
    }
}
