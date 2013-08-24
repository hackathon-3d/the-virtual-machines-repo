package net.c0ffee.tailgatr.activities;

import net.c0ffee.tailgatr.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity implements OnClickListener {

	// UI references
	EditText mEmailField;
	EditText mPasswordField;
	Button mLoginButton;
	Button mRegisterButton;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Set the layout
		setContentView(R.layout.activity_login);
		
		// Get references to the ui elements
		mEmailField = (EditText) findViewById(R.id.login_email_field);
		mPasswordField = (EditText) findViewById(R.id.login_password_field);
		mLoginButton = (Button) findViewById(R.id.login_button);
		mRegisterButton = (Button) findViewById(R.id.register_button);
		
		// Set the onClick handlers
		mLoginButton.setOnClickListener(this);
		mRegisterButton.setOnClickListener(this);
	}


	public void onClick(View v) {
		if (v.getId() == mLoginButton.getId()) {
			
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
			
			
			
		} else if (v.getId() == mRegisterButton.getId()) {
			Intent register = new Intent(this, RegisterActivity.class);
			startActivity(register);
		}
	}
	
	public final static boolean isValidEmail(CharSequence target) {
		if (target == null) {
	        return false;
	    } else {
	        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
	    }
	}
}
