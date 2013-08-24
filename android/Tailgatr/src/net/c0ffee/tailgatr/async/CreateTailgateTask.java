package net.c0ffee.tailgatr.async;

import net.c0ffee.tailgatr.R;
import net.c0ffee.tailgatr.activities.TailgateEditActivity;
import net.c0ffee.tailgatr.data.Tailgate;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;

public class CreateTailgateTask extends AsyncTask<Tailgate, Void, Boolean> {

	private TailgateEditActivity mActivity;
	private ProgressDialog mProgressDialog;
	
	public CreateTailgateTask(TailgateEditActivity activity) {
		mActivity = activity;
	}
	
	protected void onPreExecute() {
		mProgressDialog = new ProgressDialog(mActivity, AlertDialog.THEME_HOLO_DARK);
		mProgressDialog.setMessage(mActivity.getResources().getString(R.string.creating_tailgate));
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
	}
	
	@Override
	protected Boolean doInBackground(Tailgate... params) {
		// TODO Auto-generated method stub
		return null;
	}

	protected void onPostExecute (Boolean success) {
		if (success == false) {
			// Uh oh
		} else {
			
		}
	}
}
