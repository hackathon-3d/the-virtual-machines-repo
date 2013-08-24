package net.c0ffee.tailgatr.activities;

import net.c0ffee.tailgatr.R;
import net.c0ffee.tailgatr.data.Constants;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

public class TailgateActivity extends ListActivity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Set the list content view
		setContentView(R.layout.activity_tailgate);
		
		// Check to see if there is a user logged in
		String token = getSharedPreferences(Constants.APP_PREFIX, MODE_PRIVATE).getString("AUTH", null);
		if (token == null) {
			Intent authenticate = new Intent(this, LoginActivity.class);
			startActivity(authenticate);
		}
	}
}
