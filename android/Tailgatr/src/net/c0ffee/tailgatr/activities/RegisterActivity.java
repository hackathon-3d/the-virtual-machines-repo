package net.c0ffee.tailgatr.activities;

import net.c0ffee.tailgatr.R;
import net.c0ffee.tailgatr.async.RegisterTask;
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

public class RegisterActivity extends Activity implements OnClickListener, DialogInterface.OnClickListener {

	// UI references
	private EditText mEmailField;
	private EditText mNicknameField;
	private EditText mPasswordField;
	private EditText mPasswordConfirmField;
	private Button mRegisterButton;
	private ProgressBar mProgressBar;
	
	private AlertDialog mNetworkDialog;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// set the content
		setContentView(R.layout.activity_register);
		
		// get references to the ui elements
		mEmailField = (EditText) findViewById(R.id.email_field);
		mNicknameField = (EditText) findViewById(R.id.nickname_field);
		mPasswordField = (EditText) findViewById(R.id.register_password_field);
		mPasswordConfirmField = (EditText) findViewById(R.id.register_password_repeat_field);
		mRegisterButton = (Button) findViewById(R.id.register_button);
		mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
		
		mRegisterButton.setOnClickListener(this);
	}

	public void onClick(View v) {
		if (v.getId() == mRegisterButton.getId()) {
			
			// Clear the errors
			mEmailField.setError(null);
			
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
			
			// Get the values from the fields
			String email = mEmailField.getText().toString();
			String nickname = mNicknameField.getText().toString();
			String password = mPasswordField.getText().toString();
			String passwordConfirm = mPasswordConfirmField.getText().toString();
			
			// Validate the email
			if (!LoginActivity.isValidEmail(email)) {
				mEmailField.setError(getResources().getString(R.string.invalid_email));
				return;
			}
			
			// Validate the nickname
			if (nickname.length() < 1) {
				mNicknameField.setError(getResources().getString(R.string.invalid_nickname));
				return;
			}
			
			// Validate the password
			if (password.length() < 1) {
				mPasswordField.setError(getResources().getString(R.string.invalid_password));
				return;
			}
			
			// Validate the second password
			if (passwordConfirm.length() < 1) {
				mPasswordConfirmField.setError(getResources().getString(R.string.invalid_password));
				return;
			}
			
			// Make sure they match
			if (!passwordConfirm.contentEquals(password)) {
				mPasswordField.setError(getResources().getString(R.string.do_not_match));
				mPasswordConfirmField.setError(getResources().getString(R.string.do_not_match));
				return;
			}
			
			// Create the user object
			User newUser = new User(0, email, nickname, password);
			
			// Create and execute the registration task
			RegisterTask register = new RegisterTask(this);
			this.showProgress();
			register.execute(newUser);
		}
	}
	
	public void registrationFailedWithError(String message) {
		this.showButtons();
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}
	
	public void showProgress() {
		mRegisterButton.setVisibility(View.INVISIBLE);
		mProgressBar.setVisibility(View.VISIBLE);
	}
	
	public void showButtons() {
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

	public void onClick(DialogInterface dialog, int which) {
		if (dialog.equals(mNetworkDialog) && which == DialogInterface.BUTTON_POSITIVE) {
			// Open wireless settings
			startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
		}
	}
}
