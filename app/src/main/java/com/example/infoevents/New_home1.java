package com.example.infoevents;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.drawable;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class New_home1 extends Activity {

	Button login,reg;
	EditText usernm,userpass;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_home1);
		
		login = (Button) findViewById(R.id.button1);
		reg =(Button) findViewById(R.id.button2);
		usernm=(EditText) findViewById(R.id.usernm);
		userpass=(EditText) findViewById(R.id.userpass);
		usernm.setText("");
		userpass.setText("");
		//new user
		reg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(New_home1.this,UserRegister.class ));
			}
		});
		
		//for login
		
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UrlPaths webaddr=new UrlPaths();
				
				String Uname=usernm.getText().toString();
				String Upass=userpass.getText().toString();
				
				String url = webaddr.urladdr() + "LoginServlet?" +
						"Uname="+Uname+"&Upass="+Upass;
				//Log.e("url:",url);
				if(Uname.equals("admin")&&Upass.equals("Infoevent"))
				{
					Toast.makeText(New_home1.this, "Welcome Admin", Toast.LENGTH_LONG).show();
					startActivity(new Intent(New_home1.this, AdminMainActivity.class));
				}
				else{
				MyTask task = new MyTask();
				url=url.replaceAll(" ", "%20");
				task.execute(url);
				}
				//startActivity(new Intent(New_home1.this, MainActivity.class));
			}
		});
		
	}
	
	class MyTask extends AsyncTask<String, Void, String>
	{

		@Override
		protected String doInBackground(String... params) {
			String url = params[0];
			String data="";
			
			HttpGet get = new HttpGet(url);
			HttpClient client = new DefaultHttpClient();
			
			try {
				HttpResponse resp = client.execute(get);
				HttpEntity myEntity = resp.getEntity();
				InputStream is = myEntity.getContent();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				
				while(true)
				{
					String str = br.readLine();
					if(str==null)
						break;
					data+=str;
				}
				
				br.close();
				isr.close();
				is.close();
				
			} catch (ClientProtocolException e) {
				Log.e("error", e.toString());
				e.printStackTrace();
			} catch (IOException e) {
				Log.e("error", e.toString());
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return data;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			try {
				JSONObject jobj=new JSONObject(result);
				String res=jobj.getString("result");
				String unm=jobj.getString("Uname");
				String Uemail=jobj.getString("uemail");

				if(res.equals("success"))
				{
					
					Toast.makeText(New_home1.this, "welcome: "+unm , Toast.LENGTH_LONG).show();
					
						Intent in=new Intent(New_home1.this,MainActivity.class);
						in.putExtra("usernm", unm);
						in.putExtra("Uemail", Uemail);
						startActivity(in);
					
				}
				else
				
					Toast.makeText(New_home1.this, "wrong user name or password" , Toast.LENGTH_LONG).show();
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
	@Override
	public void onBackPressed()
	{
		Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
		
		/**
	     * Event Handling for Individual menu item selected
	     * Identify single menu item by it's id
	     * */
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item)
	    {
	         
	        switch (item.getItemId())
	        {
	        case R.id.action_about:
	            // Single menu item is selected do something
	            // Ex: launching new activity/screen or show alert message
	        	AlertDialog.Builder alertbox = new AlertDialog.Builder(this);

	        	alertbox.setTitle("About");
	        	
	            // set the message to display
	            alertbox.setMessage("App Showing diffferent events happening in Indore\nDeveloped By:\nYash Khare\nYash Maheshwari\nVirat Govindjiwala\nRanjana Arora");
	            
	            // add a neutral button to the alert box and assign a click listener
	            alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {

	                // click listener on the alert box
	                public void onClick(DialogInterface arg0, int arg1) {
	                    // the button was clicked
	                
	                }
	            });

	            // show it
	            alertbox.show();
	            return true;
	 
	 
	        default:
	            return super.onOptionsItemSelected(item);
	        }
		
	    }

}