package net.c0ffee.tailgatr.async;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import net.c0ffee.tailgatr.activities.TailgatesActivity;
import net.c0ffee.tailgatr.data.Constants;
import net.c0ffee.tailgatr.data.User;
import android.os.AsyncTask;

public class GetTailgatesTask extends AsyncTask<User, Void, Boolean> {

	private TailgatesActivity mActivity;
	
	private String mErrorMessage;
	
	public GetTailgatesTask(TailgatesActivity activity) {
		mActivity = activity;
		mErrorMessage = null;
	}
	
	protected Boolean doInBackground(User... params) {
		URL url = null;
		
		try {
			url = new URL(Constants.SERVER_URL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.connect();
			
		    int responseCode = conn.getResponseCode();
		    
		    if (responseCode == HttpURLConnection.HTTP_OK) {
		    	StringBuilder response = new StringBuilder();
		    	BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            String strLine = null;
	            while ((strLine = input.readLine()) != null) {
	                response.append(strLine);
	            }
	            input.close();
	            
	            JSONObject in = new JSONObject(response.toString());
	            
	            
	            return true;
		    } else {
		    	mErrorMessage = conn.getResponseMessage();
		    }
		    
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	protected void onPostExecute (Boolean response) {
		if (response == false) {
			// Uh oh
		} else {
			
		}
	}
}
