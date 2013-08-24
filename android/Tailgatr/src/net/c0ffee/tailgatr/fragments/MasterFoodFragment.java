package net.c0ffee.tailgatr.fragments;

import net.c0ffee.tailgatr.R;
import net.c0ffee.tailgatr.activities.FoodEditActivity;
import net.c0ffee.tailgatr.data.TailgateDatabase;
import net.c0ffee.tailgatr.data.TailgateProvider;
import android.app.AlertDialog;
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

public class MasterFoodFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

	private SimpleCursorAdapter mAdapter;
	
	private AlertDialog mCreateDialog;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		getListView().setBackgroundColor(Color.parseColor("#3B3C55"));
		
		String[] uiBindFrom = { TailgateDatabase.COLUMN_ITEM_NAME, TailgateDatabase.COLUMN_ITEM_PRICE };
		int[] uiBindTo = { android.R.id.text1, android.R.id.text2 };
		
		getLoaderManager().initLoader(0x02, null, this);
		mAdapter = new SimpleCursorAdapter(getActivity().getApplicationContext(), 
											android.R.layout.simple_list_item_2, 
											null, uiBindFrom, uiBindTo, 
											CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		
		setListAdapter(mAdapter);
	}
	
	public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.food_fragment_menu, menu);
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.menu_item_add_food) {
			Intent i = new Intent(this.getActivity(), FoodEditActivity.class);
	        startActivity(i);
		}
		return true;
	}
	
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent i = new Intent(this.getActivity(), FoodEditActivity.class);
	    Uri todoUri = Uri.parse(TailgateProvider.CONTENT_ITEM_URI + "/" + id);
	    i.putExtra(TailgateProvider.CONTENT_ITEM_TYPE, todoUri);
	    startActivity(i);
	}
	
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projection = { TailgateDatabase.COLUMN_ITEM_ID, TailgateDatabase.COLUMN_ITEM_NAME, TailgateDatabase.COLUMN_ITEM_PRICE };
	    CursorLoader cursorLoader = new CursorLoader(getActivity(),
	            TailgateProvider.CONTENT_ITEM_URI, projection, null, null, null);
	    return cursorLoader;
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
    	mAdapter.swapCursor(cursor);
    }
    
    public void onLoaderReset(Loader<Cursor> loader) {
    	mAdapter.swapCursor(null);
    }
}
