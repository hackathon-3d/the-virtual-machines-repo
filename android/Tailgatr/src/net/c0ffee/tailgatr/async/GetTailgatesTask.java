package net.c0ffee.tailgatr.async;

import net.c0ffee.tailgatr.activities.TailgateActivity;
import net.c0ffee.tailgatr.data.User;
import android.os.AsyncTask;

public class GetTailgatesTask extends AsyncTask<User, Void, Boolean> {

	private TailgateActivity mActivity;
	
	private String mErrorMessage;
	
	public GetTailgatesTask(TailgateActivity activity) {
		mActivity = activity;
		mErrorMessage = null;
	}
	
	protected Boolean doInBackground(User... params) {
		// TODO Auto-generated method stub
		return null;
	}

	protected void onPostExecute (String token) {
		if (token == null) {
			// Uh oh
		} else {
			
		}
	}
}
