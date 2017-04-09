package com.example.infoevents;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

public class AdminVIewAllMainActivity2 extends Activity {
    
    ListView list;
    LazyAdapter adapter;
    private List<Events> eventList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        UrlPaths webaddr=new UrlPaths();
        list=(ListView)findViewById(R.id.listView1);
       
        
        
        String url=webaddr.urladdr()+"AdminViewJSONEventInfoServlet";
        EventListTask task=new EventListTask();
		task.execute(url);
        
        
        
    }
    
    class EventListTask extends AsyncTask<String, Void, String>
	{

    	@Override
    	protected void onPreExecute() {
    		// TODO Auto-generated method stub
    		super.onPreExecute();
    		eventList= new ArrayList<Events>();
    	}
    	
    	
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String resp="";
			HttpClient client=new DefaultHttpClient();
			HttpGet get=new HttpGet(params[0]);
			
			try {
				HttpResponse hr=client.execute(get);
				
				InputStream is=hr.getEntity().getContent();
				InputStreamReader isr=new InputStreamReader(is);
				BufferedReader br=new BufferedReader(isr);
				while(true)
				{
					String s=br.readLine();
					if(s==null)
						break;
					else
						resp=resp+s;
				}
				
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return resp;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			//list.clear();
			try {
				JSONArray jarray=new JSONArray(result);
				for (int i=0;i<jarray.length();i++)
				{
					JSONObject jobj=jarray.getJSONObject(i);
					
					String imgnm=jobj.getString("imgnm");
					String event_name=jobj.getString("event_name");
					String e_info=jobj.getString("e_info");
					String e_date=jobj.getString("e_date");
					String address=jobj.getString("address");
					
					Events eve=new Events(imgnm, event_name, e_info, e_date, address);					
					eventList.add(eve);
					
				}
				adapter=new LazyAdapter(AdminVIewAllMainActivity2.this,eventList);
				list.setAdapter(adapter);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}