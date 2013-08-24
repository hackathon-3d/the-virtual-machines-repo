package net.c0ffee.tailgatr.fragments;


import net.c0ffee.tailgatr.R;
import net.c0ffee.tailgatr.data.TailgateDatabase;
import net.c0ffee.tailgatr.data.TailgateProvider;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MasterFoodFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>, DialogInterface.OnClickListener {

	private SimpleCursorAdapter mAdapter;
	
	private AlertDialog mCreateDialog;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		getListView().setBackgroundColor(Color.BLUE);
		
		String[] uiBindFrom = { TailgateDatabase.COLUMN_ITEM_NAME };
		int[] uiBindTo = { android.R.id.text1 };
		
		getLoaderManager().initLoader(0x02, null, this);
		mAdapter = new SimpleCursorAdapter(getActivity().getApplicationContext(), 
											android.R.layout.simple_list_item_1, 
											null, uiBindFrom, uiBindTo, 
											CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		
		setListAdapter(mAdapter);
	}
	
	public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.food_fragment_menu, menu);
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.menu_item_add_food) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity(), AlertDialog.THEME_HOLO_DARK);
			builder.setView(LayoutInflater.from(this.getActivity()).inflate(R.layout.dialog_food, null))
				.setTitle(R.string.new_food)
				.setPositiveButton(R.string.ok, this)
				.setNegativeButton(R.string.cancel, this);
			mCreateDialog = builder.create();
			mCreateDialog.show();
		}
		return true;
	}

	public void onClick(DialogInterface dialog, int which) {
		if (dialog.equals(mCreateDialog) && which == DialogInterface.BUTTON_POSITIVE) {
			String text = ((EditText)mCreateDialog.findViewById(R.id.food_name_field)).getText().toString();
			if (text.length() < 1) {
				Toast.makeText(getActivity(), R.string.invalid_name, Toast.LENGTH_LONG).show();
			} else {
				ContentValues values = new ContentValues();
				values.put(TailgateDatabase.COLUMN_ITEM_NAME, text);
				values.put(TailgateDatabase.COLUMN_ITEM_PRICE, 1);
				getActivity().getContentResolver().insert(TailgateProvider.CONTENT_ITEM_URI, values);
				Toast.makeText(getActivity(), "Item Saved", Toast.LENGTH_SHORT).show();
			}
		}
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
