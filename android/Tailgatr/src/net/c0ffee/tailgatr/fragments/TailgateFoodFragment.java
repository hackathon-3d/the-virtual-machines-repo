package net.c0ffee.tailgatr.fragments;

import java.util.ArrayList;

import net.c0ffee.tailgatr.R;
import net.c0ffee.tailgatr.data.User;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TailgateFoodFragment extends ListFragment implements DialogInterface.OnClickListener {

	ArrayList<String> mFoodItems;
	
	private ArrayAdapter<String> mAdapter;
	
	AlertDialog mCreateDialog;
	
	private View mHeaderView;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View list_root = inflater.inflate(android.R.layout.list_content, null);
		
		mHeaderView = inflater.inflate(R.layout.tailgate_header, null);
		return list_root;
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		TextView text = (TextView) mHeaderView.findViewById(R.id.header_text);
		text.setText(getActivity().getResources().getString(R.string.what_to_eat));
		getListView().addHeaderView(mHeaderView);
		
		mFoodItems = new ArrayList<String>();
		mAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, mFoodItems);
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
				mFoodItems.add(text);
				mAdapter.notifyDataSetChanged();
			}
		}
	}
}
