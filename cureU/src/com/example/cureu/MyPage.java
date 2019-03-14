package com.example.cureu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MyPage extends Activity {
	private Intent intent;
	private TextView username;
	private String str_username;
	private String str_name;
	private String str_phone;
	private int int_age;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_page);
		username = (TextView) this.findViewById(R.id.mypage_username);
		intent= getIntent();
		str_username = intent.getStringExtra("username");
		str_name = intent.getStringExtra("name");
		int_age = intent.getIntExtra("age", 0);
		str_phone = intent.getStringExtra("phone");
		
		username.setText(str_username);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_page, menu);
		return true;
	}
	public void showInfo(View v){
		Intent intent1 = new Intent(this,MyInfo.class);
		 try {
	            intent1.putExtra("username", str_username);
	            intent1.putExtra("name", str_name);
	            intent1.putExtra("age", int_age);
	            intent1.putExtra("phone", str_phone);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		startActivity(intent1);
	}
	public void cancel(View v){
		Intent intent = new Intent(this,Login.class);
		startActivity(intent);
	}

}
