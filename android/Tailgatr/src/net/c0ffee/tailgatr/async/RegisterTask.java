package net.c0ffee.tailgatr.async;

import net.c0ffee.tailgatr.activities.RegisterActivity;
import net.c0ffee.tailgatr.data.User;
import android.os.AsyncTask;

public class RegisterTask extends AsyncTask<User, Void, String> {

	private RegisterActivity mActivity;
	
	private String mErrorMessage;
	
	public RegisterTask(RegisterActivity activity) {
		mActivity = activity;
		mErrorMessage = null;
	}
	
	protected String doInBackground(User... params) {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected void onPostExecute (String token) {
		if (token == null) {
			mActivity.registrationFailedWithError(mErrorMessage);
		} else {
			
		}
	}
}
