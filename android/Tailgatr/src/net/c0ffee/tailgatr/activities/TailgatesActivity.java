package net.c0ffee.tailgatr.activities;

import java.util.ArrayList;

import net.c0ffee.tailgatr.R;
import net.c0ffee.tailgatr.adapters.TailgateAdapter;
import net.c0ffee.tailgatr.data.Constants;
import net.c0ffee.tailgatr.data.Tailgate;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class TailgatesActivity extends ListActivity {

	private ArrayList<Tailgate> mTailgates;
	
	private TailgateAdapter mAdapter;
	
	private View mHeaderView;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Check to see if there is a user logged in
		String token = getSharedPreferences(Constants.APP_PREFIX, MODE_PRIVATE).getString("AUTH_TOKEN", null);
		if (token == null) {
			Intent authenticate = new Intent(this, LoginActivity.class);
			startActivity(authenticate);
		}
		
		mHeaderView = getLayoutInflater().inflate(R.layout.tailgate_list_header, null);
		getListView().addHeaderView(mHeaderView);
		
		mTailgates = new ArrayList<Tailgate>();
		mAdapter = new TailgateAdapter(this, mTailgates);
		setListAdapter(mAdapter);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.tailgate_menu, menu);
	    return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.menu_item_add:
	        	Intent newTailgate = new Intent(this, TailgateEditActivity.class);
	        	startActivity(newTailgate);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private boolean isOnline() {
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}
}
