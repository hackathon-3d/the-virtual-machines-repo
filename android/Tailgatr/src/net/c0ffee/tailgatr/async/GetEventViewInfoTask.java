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

import net.c0ffee.tailgatr.activities.EventViewActivity;
import net.c0ffee.tailgatr.data.Constants;
import net.c0ffee.tailgatr.data.Tailgate;
import net.c0ffee.tailgatr.data.User;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class GetEventViewInfoTask extends AsyncTask<String, Void, Tailgate>{
	
	private TextView mTitleBox;
	private TextView mDescriptionBox;
	private Integer mEventId;
	
	public GetEventViewInfoTask(TextView titleBox, TextView descriptionBox, Integer eventId) {
		mTitleBox = titleBox;
		mDescriptionBox = descriptionBox;
		mEventId = eventId;
	}
	
	// takes tailgate/event id
	protected Tailgate doInBackground(String... params) {
		URL url = null;
		
		try {
			//url = new URL(Constants.SERVER_URL);
			url = new URL("http://10.1.10.56/srv/feed/static/test");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			
			JSONObject json = new JSONObject();
			json.put("token", params[0]);
			
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
		    conn.setRequestProperty("Content-Length", Integer.toString(json.toString().getBytes().length));
		    
		    OutputStream out = conn.getOutputStream();
		    out.write(json.toString().getBytes());
		    out.close();

		    int responseCode = conn.getResponseCode();
		    
		    Log.d("blah", "responsecode: " + responseCode);
		    
		    if (responseCode == HttpURLConnection.HTTP_OK) {
		    	StringBuilder response = new StringBuilder();
		    	BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            String strLine = null;
	            while ((strLine = input.readLine()) != null) {
	                response.append(strLine);
	            }
	            
	            input.close();
	            
	            Log.d("blah", "response: " + response.toString());
	            
	            JSONObject in = new JSONObject(response.toString());
	            String title = in.getString("title").toString();
	            String description = in.getString("description").toString();
	            Log.d("blah", "stuff: " + title + description);
	            Tailgate t = new Tailgate(mEventId, title, description, false);
	            return t;
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
	
	protected void onPostExecute (Tailgate t) {
		if (t != null) {
			mTitleBox.setText(t.getTitle());
			mDescriptionBox.setText(t.getDescription());
		}
	}
}