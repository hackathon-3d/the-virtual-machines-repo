package net.c0ffee.tailgatr.activities;

import net.c0ffee.tailgatr.R;
import net.c0ffee.tailgatr.async.LoginTask;
import net.c0ffee.tailgatr.data.User;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener, DialogInterface.OnClickListener {

	// UI references
	private EditText mEmailField;
	private EditText mPasswordField;
	private Button mLoginButton;
	private Button mRegisterButton;
	private ProgressBar mProgressBar;
	
	private AlertDialog mNetworkDialog;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Set the layout
		setContentView(R.layout.activity_login);
		
		// Get references to the ui elements
		mEmailField = (EditText) findViewById(R.id.login_email_field);
		mPasswordField = (EditText) findViewById(R.id.login_password_field);
		mLoginButton = (Button) findViewById(R.id.login_button);
		mRegisterButton = (Button) findViewById(R.id.register_button);
		mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
		
		// Set the onClick handlers
		mLoginButton.setOnClickListener(this);
		mRegisterButton.setOnClickListener(this);
	}


	public void onClick(View v) {
		if (v.getId() == mLoginButton.getId()) {
			
			// Check the network
			if(!isOnline()) {
				AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_DARK);
				mNetworkDialog = builder.setMessage(R.string.check_internet)
				.setPositiveButton(R.string.ok, this)
				.setNegativeButton(R.string.cancel, this)
				.setCancelable(false)
				.setTitle(R.string.no_internet)
				.create();
				mNetworkDialog.show();
				return;
			}
			
			// Remove any previous errors
			mEmailField.setError(null);
			mPasswordField.setError(null);
			
			// Get the values from fields
			String email = mEmailField.getText().toString();
			String password = mPasswordField.getText().toString();
			
			// Is the email valid?
			if (!isValidEmail(email)) {
				mEmailField.setError(getResources().getString(R.string.invalid_email));
				return;
			}
			
			// is the password valid?
			if (password.length() < 1) {
				mPasswordField.setError(getResources().getString(R.string.invalid_password));
				return;
			}
			
			// Create a dummy user
			User logger = new User(0, email, null, password);
			
			// Create and execute the login task
			LoginTask login = new LoginTask(this);
			this.showProgress();
			login.execute(logger);
			
		} else if (v.getId() == mRegisterButton.getId()) {
			Intent register = new Intent(this, RegisterActivity.class);
			startActivity(register);
		}
	}
	
	/**
	 * Prevents the user from exiting the login activity manually
	 */
	public void onBackPressed() {
	    return;
	}
	
	public void loginFailedWithError(String message) {
		this.showButtons();
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}
	
	public void showProgress() {
		mLoginButton.setVisibility(View.INVISIBLE);
		mRegisterButton.setVisibility(View.INVISIBLE);
		mProgressBar.setVisibility(View.VISIBLE);
	}
	
	public void showButtons() {
		mLoginButton.setVisibility(View.VISIBLE);
		mRegisterButton.setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.INVISIBLE);
	}
	
	private boolean isOnline() {
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}
	
	public final static boolean isValidEmail(CharSequence target) {
		if (target == null) {
	        return false;
	    } else {
	        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
	    }
	}

	public void onClick(DialogInterface dialog, int which) {
		if (dialog.equals(mNetworkDialog) && which == DialogInterface.BUTTON_POSITIVE) {
			// Open wireless settings
			startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
		}
	}
}
