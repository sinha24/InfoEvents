package com.example.infoevents;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.DatePicker;
import android.widget.Toast;

public class Event_Upload extends Activity  implements OnClickListener{
	
	EditText evname,evdate,evinfo,evaddr;
	String imgnm,usernm,Uemail;
	Button submit;
	
	private DatePickerDialog fromDatePickerDialog;
	 Date date1 ;
	 Date date2;
    
    private SimpleDateFormat dateFormatter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_upload);
		
		dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
		
		submit=(Button) findViewById(R.id.button1);
		evname=(EditText) findViewById(R.id.usernm);
		evdate=(EditText) findViewById(R.id.userpass);
		evdate.setInputType(InputType.TYPE_NULL);
		
		evinfo=(EditText) findViewById(R.id.editText3);
		evaddr=(EditText) findViewById(R.id.editText4);
		
		//get intent data
		Intent intent= getIntent();
        Bundle b = intent.getExtras();

       usernm=(String) b.get("usernm");
         imgnm =(String) b.get("imgnm");
            Uemail=(String)b.get("Uemail");
        //date
         evdate.setOnClickListener(this);
         
         
         Calendar newCalendar = Calendar.getInstance();
         fromDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {
  
             public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                 Calendar newDate = Calendar.getInstance();
                 newDate.set(year, monthOfYear, dayOfMonth);
                 evdate.setText(dateFormatter.format(newDate.getTime()));
             }
  
         },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
         
        
		
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				UrlPaths u=new UrlPaths();
			//	String url=u.urladdr()+"EventRegisterServlet";
				Eventupload task=new Eventupload();
				String Event_name=evname.getText().toString();
				String E_info=evinfo.getText().toString();
				String E_date=evdate.getText().toString();
				String Address=evaddr.getText().toString();
				
				String current_date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
				
				try {
					date1=dateFormatter.parse(E_date);
					 date2=dateFormatter.parse(current_date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				if(Event_name.equals("") || E_date.equals("") || E_info.equals("") || Address.equals(""))
				{
					Toast.makeText(Event_Upload.this, "Field cannot be blank", 2).show();
				}
				
				else if(Event_name.startsWith(" ") || E_date.startsWith(" ") || E_info.startsWith(" ") || Address.startsWith(" "))
				{
					Toast.makeText(Event_Upload.this, "Field cannot start with blank", 2).show();

				}
				else if(date1.compareTo(date2)<0)
				{
					Toast.makeText(Event_Upload.this, "Event passed!!!\nselect valid date", 2).show();

				}
				else{
				String url = u.urladdr() + "/EventRegisterServlet?" +
						"Uname="+usernm+"&Imgnm="+imgnm+"&Event_name="+Event_name+"&E_info="+E_info+"&E_date="+E_date
						+"&Address="+Address+"&Uemail="+Uemail;
				url=url.replaceAll(" ", "%20");
				task.execute(url);
				}
				
			}
		});
		
	}
		 class Eventupload extends AsyncTask<String, Void, String>
	{
		//Users user;
		
		Events events;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			//Long mobile=Long.parseLong(etmobile.getText().toString());
		//	events=new Events(Event_name, E_info, E_date, Address);	
			
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			//?Uname=67&Upass=priya&Uemail=12300&Uphone=6454132
		/*	String str="";
			HttpPost post=new HttpPost(params[0]);
			HttpClient client=new DefaultHttpClient();
			
			NameValuePair nvpUname= new BasicNameValuePair
							("Uname", usernm);
			NameValuePair nvpImgnm= new BasicNameValuePair
							("Imgnm", imgnm);
			NameValuePair nvpEventnm=new BasicNameValuePair
							("Event_name", events.getEvent_name());
			NameValuePair nvpEinfo=new BasicNameValuePair
							("E_info",events.getE_info());
			NameValuePair nvpEdate=new BasicNameValuePair
							("E_date", events.getE_date());
			NameValuePair nvpAddress=new BasicNameValuePair
							("Address", events.getAddress());
			
			ArrayList<NameValuePair> listParams=
				new ArrayList<NameValuePair>();
			listParams.add(nvpUname);
			listParams.add(nvpImgnm);
			listParams.add(nvpEventnm);
			listParams.add(nvpEinfo);
			listParams.add(nvpEdate);
			listParams.add(nvpAddress);
		
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
			if(result.equals("success"))
			{
				Toast.makeText(Event_Upload.this, "Event upload "+result, Toast.LENGTH_LONG).show();
				
			}
			else
			{
				Toast.makeText(Event_Upload.this, "Try again later..", Toast.LENGTH_LONG).show();
			}
			Intent intent= getIntent();
	        Bundle b = intent.getExtras();

	       
	       
	        intent=new Intent(Event_Upload.this,MainActivity.class);
	        intent.putExtra("usernm", usernm);
	        intent.putExtra("Uemail", Uemail);
			startActivity(intent);
			
		}
		
	}
		 @Override
		 public void onClick(View view) {
				if(view == evdate) {
					fromDatePickerDialog.show();
				}		
			}
		
		
	}
	


