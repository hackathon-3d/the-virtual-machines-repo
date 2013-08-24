package net.c0ffee.tailgatr.async;

import net.c0ffee.tailgatr.activities.LoginActivity;
import net.c0ffee.tailgatr.data.User;
import android.os.AsyncTask;

public class LoginTask extends AsyncTask<User, Void, String>{

	private LoginActivity mActivity;

	private String mErrorMessage;
	
	public LoginTask(LoginActivity activity) {
		mActivity = activity;
		mErrorMessage = null;
	}
	
	protected String doInBackground(User... params) {
		// TODO Auto-generated method stub
		return null;
	}

	protected void onPostExecute (String token) {
		if (token == null) {
			mActivity.loginFailedWithError(mErrorMessage);
		} else {
			
		}
	}
}
