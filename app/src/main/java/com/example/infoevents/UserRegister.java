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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserRegister extends Activity{
	
	Button signup;
	EditText etname,etpass,etcon,etemail,etmobile;
	Users user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_reg);
		signup=(Button) findViewById(R.id.select1);
		etname=(EditText) findViewById(R.id.usernm);
		etpass=(EditText) findViewById(R.id.userpass);
		etemail=(EditText) findViewById(R.id.editText3);
		etcon=(EditText) findViewById(R.id.conf);
		etmobile=(EditText) findViewById(R.id.editText4);
		TelephonyManager manager =(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		String phoneNumber = manager.getLine1Number();
		etmobile.setText(phoneNumber);
		//etmobile.setEnabled(false);
		signup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				UrlPaths u=new UrlPaths();
				//String url=u.urladdr()+"RegisterServlet";
				UserReg task=new UserReg();
				String uname=etname.getText().toString().trim();
				String upass=etpass.getText().toString();
				String eemail=etemail.getText().toString().trim();
				String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
				
				if(uname.equals("") || upass.equals(" "))
				{
					Toast.makeText(getApplicationContext(),"Fields cannot be blanks",Toast.LENGTH_SHORT).show();

				}
				else if(uname.startsWith(" ") || upass.startsWith(" "))
				{
					Toast.makeText(getApplicationContext(),"Fields cannot start with blanks",Toast.LENGTH_SHORT).show();

				}
				else if(upass.length()<4)
				{
					Toast.makeText(UserRegister.this,"passoword length should be atleat 4",Toast.LENGTH_SHORT).show();

				}
				else if(etmobile.getText().toString().equals(""))
				{
					Toast.makeText(UserRegister.this,"cannot be blank",Toast.LENGTH_SHORT).show();

				}
				else if (!upass.equals(etcon.getText().toString())) {
					Toast.makeText(UserRegister.this,"passoword does not match",Toast.LENGTH_SHORT).show();
					etpass.setText("");
					etcon.setText("");
				}
				else if (eemail.matches(emailPattern))
				{ 
					
					Long mobile=Long.parseLong(etmobile.getText().toString());
					//replaceAll(" ", "%20");
					
					//uname=uname.replaceAll(" ","%20");
					String url = u.urladdr() + "/RegisterServlet?" +
							"Uname="+uname+"&Upass="+upass+"&Uemail="+eemail+"&Uphone="+mobile;
					//user=new Users(uname,upass,eemail,mobile);
					url=url.replaceAll(" ", "%20");
					task.execute(url);
				}
				else 
				{
				Toast.makeText(UserRegister.this,"Invalid email address", Toast.LENGTH_SHORT).show();
				etemail.setText("");
				}
				
				
				
			}
		});
	}
	
	class UserReg extends AsyncTask<String, Void, String>
	{
		

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
			
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			//?Uname=67&Upass=priya&Uemail=12300&Uphone=6454132
			/*String str="";
			HttpPost post=new HttpPost(params[0]);
			HttpClient client=new DefaultHttpClient();
			
			NameValuePair nvpUname= new BasicNameValuePair
							("Uname", user.getUname());
			NameValuePair nvpUpass=new BasicNameValuePair
							("Upass", user.getUpass());
			NameValuePair nvpUemail=new BasicNameValuePair
							("Uemail",user.getUemail());
			NameValuePair nvpUphone=new BasicNameValuePair("Uphone", user.getUphone()+"");
			
			ArrayList<NameValuePair> listParams=
				new ArrayList<NameValuePair>();
			listParams.add(nvpUname);
			listParams.add(nvpUpass);
			listParams.add(nvpUemail);
			listParams.add(nvpUphone);
		
			try {
				UrlEncodedFormEntity entity=new UrlEncodedFormEntity(listParams);
				post.setEntity(entity);
				
				HttpResponse resp=client.execute(post);
				InputStream is=resp.getEntity().getContent();
				BufferedReader br=new BufferedReader(
						new InputStreamReader(is));
				
				 str=br.readLine();
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				return str;
		
			*/
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
			Log.d("msg", result);
			Toast.makeText(UserRegister.this, result, 6).show();
			if(result.equals("user name  already exist"))
			{
				etname.setText("");
				etpass.setText("");
			}
			else{
			startActivity(new Intent(UserRegister.this,New_home1.class));
			}
		}
		
	}

}
