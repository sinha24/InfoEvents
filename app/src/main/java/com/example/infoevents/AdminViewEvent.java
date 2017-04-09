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
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AdminViewEvent extends Activity{
	
	ImageView imgview;
	Button add,del;
	TextView t1;
	String addr,imgnm,evnm,re,Uemail;
	public ImageLoader imageLoader; 
	
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.admin_list_events);
	
	add=(Button) findViewById(R.id.add);
	imgview=(ImageView) findViewById(R.id.imgview111);
	t1=(TextView) findViewById(R.id.textinfo111);
	del=(Button) findViewById(R.id.del);
	imageLoader=new ImageLoader(getApplicationContext());
	
	Intent intent= getIntent();
    Bundle b = intent.getExtras();

    Uemail	 =(String) b.get("Uemail");

     evnm =(String) b.get("Eventnm");
    String evdate =(String) b.get("Evdate");
    String evinfo =(String) b.get("Evinfo");
    addr =(String) b.get("Addr");
     imgnm =(String) b.get("Eventinm");
    UrlPaths u=new UrlPaths();
    imageLoader.DisplayImage(u.urladdr()+"/upload/"+ imgnm, imgview);
    
    
   
    t1.setText("Event: "+evnm+ "\nInfo: "+evinfo+ "\nDate: " +evdate+ 
    		"\nAddress: "+addr);
    add.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			UrlPaths u=new UrlPaths();
			String f="1";
			String url = u.urladdr() + "/AdminAdd?Imgnm="+imgnm+"&Event_name="+evnm+"&Flag="+f+"&Uemail="+Uemail;
			//user=new Users(uname,upass,eemail,mobile);
			url=url.replaceAll(" ", "%20");
			re="Added";
			ADD task=new ADD();
			task.execute(url);
			
		}
	});
    
    
    del.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			UrlPaths u=new UrlPaths();
			String f="-1";
			String url = u.urladdr() + "/AdminAdd?Imgnm="+imgnm+"&Event_name="+evnm+"&Flag="+f+"&Uemail="+Uemail;;
			//user=new Users(uname,upass,eemail,mobile);
			url=url.replaceAll(" ", "%20");
			re="Deleted";
			ADD task=new ADD();
			task.execute(url);
		}
	});
 	}

class ADD extends AsyncTask<String, Void, String>
{
	

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		
		
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		
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
		//Log.d("msg", result);
		if(result.equals("successsend"))
		{
			Toast.makeText(AdminViewEvent.this, "Event Successfully "+re, 6).show();
			

		}
		else
		{
			Toast.makeText(AdminViewEvent.this, "something went wrong..", 6).show();

		}
		
		startActivity(new Intent(AdminViewEvent.this,AdminMainActivity2.class));
		
		
	}
	
}




}
