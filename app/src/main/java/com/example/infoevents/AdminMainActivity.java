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

public class AdminMainActivity extends Activity {

	Button viewEvent,validate,logout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_activity_main);
		
		viewEvent=(Button) findViewById(R.id.ViewAll);
		validate=(Button) findViewById(R.id.Validate);
		logout=(Button) findViewById(R.id.logout);
		
		viewEvent.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			startActivity(new Intent(AdminMainActivity.this,AdminVIewAllMainActivity2.class));
			}
		});
		
		logout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(AdminMainActivity.this,New_home1.class));
		
			}
		});
		validate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				

		       
		       Intent intent=new Intent(AdminMainActivity.this,AdminMainActivity2.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public void onBackPressed() {
		Toast.makeText(AdminMainActivity.this, "Press log out", Toast.LENGTH_SHORT).show();
	}
}
