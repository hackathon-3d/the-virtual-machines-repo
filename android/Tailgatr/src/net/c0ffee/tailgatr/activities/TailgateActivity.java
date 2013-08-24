package net.c0ffee.tailgatr.activities;

import java.util.ArrayList;

import net.c0ffee.tailgatr.R;
import net.c0ffee.tailgatr.adapters.TailgateAdapter;
import net.c0ffee.tailgatr.data.Constants;
import net.c0ffee.tailgatr.data.Tailgate;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class TailgateActivity extends ListActivity {

	private ArrayList<Tailgate> mTailgates;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Set the list content view
		setContentView(R.layout.activity_tailgate);
		
		// Check to see if there is a user logged in
		String token = getSharedPreferences(Constants.APP_PREFIX, MODE_PRIVATE).getString("AUTH", null);
		
		//if (token == null) {
		//	Intent authenticate = new Intent(this, LoginActivity.class);
		//	startActivity(authenticate);
		//}
		
		mTailgates = new ArrayList<Tailgate>();
		TailgateAdapter adapter = new TailgateAdapter(this, mTailgates);
		setListAdapter(adapter);
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
}
