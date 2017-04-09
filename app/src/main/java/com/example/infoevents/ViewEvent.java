package com.example.infoevents;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewEvent extends Activity{
	
	ImageView imgview;
	Button onmap,taxi;
	TextView t1;
	String addr;
	public ImageLoader imageLoader; 
	
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.list_events);
	
	taxi=(Button) findViewById(R.id.taxi);
	imgview=(ImageView) findViewById(R.id.imgview);
	t1=(TextView) findViewById(R.id.textinfo);
	onmap=(Button) findViewById(R.id.bmap);
	imageLoader=new ImageLoader(getApplicationContext());
	
	Intent intent= getIntent();
    Bundle b = intent.getExtras();

   
    String evnm =(String) b.get("Eventnm");
    String evdate =(String) b.get("Evdate");
    String evinfo =(String) b.get("Evinfo");
    addr =(String) b.get("Addr");
    String imgnm =(String) b.get("Eventinm");
    UrlPaths u=new UrlPaths();
    imageLoader.DisplayImage(u.urladdr()+"/upload/"+ imgnm, imgview);
    
   
    t1.setText("Event: "+evnm+ "\nInfo: "+evinfo+ "\nDate: " +evdate+ 
    		"\nAddress: "+addr);
    onmap.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
	
			Uri uri = Uri.parse("https://www.google.com/maps/search/" + addr+ "hotels");
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(intent);
		}
	});
    
    
    taxi.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent callIntent = new Intent(Intent.ACTION_DIAL);
		    callIntent.setData(Uri.parse("tel:0731-3355335"));
		    startActivity(callIntent);
		}
	});
 	}
}
