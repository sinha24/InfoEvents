package com.example.infoevents;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button viewEvent,uploadEvent,logout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		viewEvent=(Button) findViewById(R.id.ViewEvent);
		uploadEvent=(Button) findViewById(R.id.UploadEvent);
		logout=(Button) findViewById(R.id.logout);
		
		viewEvent.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			startActivity(new Intent(MainActivity.this,MainActivity2.class));
			}
		});
		
		logout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainActivity.this,New_home1.class));
		
			}
		});
		uploadEvent.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent= getIntent();
		        Bundle b = intent.getExtras();

		       
		        String usernm =(String) b.get("usernm");
		        String Uemail= (String) b.getString("Uemail");
		        intent=new Intent(MainActivity.this,EUpload.class);
		        intent.putExtra("usernm", usernm);
		        intent.putExtra("Uemail", Uemail);
		        
				startActivity(intent);
			}
		});
	}

	@Override
	public void onBackPressed() {
		Toast.makeText(MainActivity.this, "Press log out", Toast.LENGTH_SHORT).show();
	}
}
