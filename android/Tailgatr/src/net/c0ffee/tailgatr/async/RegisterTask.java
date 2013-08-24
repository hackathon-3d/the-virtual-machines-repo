package net.c0ffee.tailgatr.async;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import net.c0ffee.tailgatr.activities.RegisterActivity;
import net.c0ffee.tailgatr.activities.TailgateActivity;
import net.c0ffee.tailgatr.data.Constants;
import net.c0ffee.tailgatr.data.User;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.JsonReader;

public class RegisterTask extends AsyncTask<User, Void, String> {

	private RegisterActivity mActivity;
	
	private String mErrorMessage;
	private User mUser;
	
	public RegisterTask(RegisterActivity activity) {
		mActivity = activity;
		mErrorMessage = null;
	}
	
	protected String doInBackground(User... params) {
		URL url = null;
		mUser = params[0];
		
		try {
			url = new URL(Constants.SERVER_URL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			JSONObject json = new JSONObject();
			json.put("email", params[0].getEmail());
			json.put("password", params[0].getPassword());
			json.put("name", params[0].getNickname());
			
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
		    conn.setRequestProperty("Content-Length", Integer.toString(json.toString().getBytes().length));
		    
		    OutputStream out = conn.getOutputStream();
		    out.write(json.toString().getBytes());
		    out.close();

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
	            return in.get("token").toString();
		    } else {
		    	
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
	
	protected void onPostExecute (String token) {
		if (token == null || token.length() < 1) {
			mActivity.registrationFailedWithError(mErrorMessage);
		} else {
			mActivity.getSharedPreferences(Constants.APP_PREFIX, Context.MODE_PRIVATE).edit().putString("AUTH_TOKEN", token);
			mActivity.getSharedPreferences(Constants.APP_PREFIX, Context.MODE_PRIVATE).edit().putString("EMAIL", mUser.getEmail());
			
			Intent i = new Intent(mActivity, TailgateActivity.class);
			mActivity.startActivity(i);
		}
	}
}
