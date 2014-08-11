package com.speed_alert;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Password extends Activity implements OnClickListener {
Button b1,b2;
EditText e1;
String e2,et;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		e1=(EditText)findViewById(R.id.etCommands);
		b1=(Button)findViewById(R.id.button1);
		b2=(Button)findViewById(R.id.button2);
		SharedPreferences getData= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		e2 =getData.getString("pass", "nam");
		SharedPreferences getPrefs=PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        et =getPrefs.getString("name","sads");
	if(e2=="nam")
	{
		Intent i= new Intent("com.speed_alert.Prefs");
		startActivity(i);
	}
	
		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		e1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		String check = e1.getText().toString();
		switch(view.getId()){
		case R.id.button1:{
		if(check.contentEquals(e2)){
			Intent i= new Intent("com.speed_alert.Prefs");
			startActivity(i);
		}
		break;
		}
		case R.id.button2:{
		String	number= et.toString();
		String message="Password is"+e2;
		sendSMS(number, message);
		Toast.makeText(Password.this, "THE PASSWORD HAS BEEN SEND TO::"+number, Toast.LENGTH_LONG).show();
		break;
		}
			
		}
		
	}
	
	  public void sendSMS(String num, String msg)
	    {
	    	SmsManager sms= SmsManager.getDefault ();
	    	sms.sendTextMessage(num, null, msg, null, null);
	    }

	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();	
		finish();
	}

}
