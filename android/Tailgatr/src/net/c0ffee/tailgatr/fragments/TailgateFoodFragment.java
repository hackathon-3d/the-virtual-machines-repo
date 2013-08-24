package net.c0ffee.tailgatr.fragments;

import java.util.ArrayList;

import net.c0ffee.tailgatr.R;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

public class TailgateFoodFragment extends ListFragment implements DialogInterface.OnClickListener {

	ArrayList<String> mFoodItems;
	
	private ArrayAdapter<String> mAdapter;
	
	AlertDialog mCreateDialog;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		
		mFoodItems = new ArrayList<String>();
		mAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, mFoodItems);
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
				mFoodItems.add(text);
				mAdapter.notifyDataSetChanged();
			}
		}
	}
}
