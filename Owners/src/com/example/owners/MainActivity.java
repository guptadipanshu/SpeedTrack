package com.example.owners;

import java.util.List;


import com.example.owners.CustomPinpoint;
import com.example.owners.R;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends MapActivity  {

	MapView map;
	String lat_ref;
	String long_ref;
	IntentFilter intentFilter;
	Drawable d;
	Button loc;
	List<Overlay> overlaylist;
	MapController controller;
	String et,et2;
	AlertDialog alert;
	Button b1;
	private BroadcastReceiver intentReceiver = new BroadcastReceiver() {

	@Override
	public void onReceive(Context context, Intent intent) {
	//---display the SMS received in the TextView---
	
	 lat_ref= (intent.getExtras().getString("sms1"));
	 long_ref= (intent.getExtras().getString("sms2"));
	String latitude= lat_ref;
	//SMSes.setText(lat_ref+long_ref);
	Toast.makeText(context, "LAT: "+lat_ref+"LONG: "+long_ref, Toast.LENGTH_SHORT).show();
	try
	{
		int lat1= Integer.parseInt(lat_ref);
		int longi1= Integer.parseInt(long_ref);
		GeoPoint ourlocation =new GeoPoint(lat1 ,longi1); 
		if((lat1 !=0)&&(longi1 !=0)){
	    OverlayItem overlayItem=new OverlayItem(ourlocation,"Str1 ","Str2");				
	  	CustomPinpoint custom =	new CustomPinpoint(d,MainActivity.this);				
	  	custom.insertPinpoint(overlayItem)	;
	  	overlaylist.add(custom); 
	  	controller= map.getController();
	  	controller.animateTo(ourlocation);
	  	controller.setZoom(6);
		}
	}
	catch (Exception e)
	{
		
	}
	}
	};
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	map =(MapView)findViewById(R.id.mvMain);
    map.setBuiltInZoomControls(true);
    d=getResources().getDrawable(R.drawable.ic_launcher1);
    overlaylist= map.getOverlays();
    b1= (Button)findViewById(R.id.button1);
	
	//---intent to filter for SMS messages received---
	intentFilter = new IntentFilter();
	intentFilter.addAction("SMS_RECEIVED_ACTION");
	loc= (Button)findViewById(R.id.bLoc);
	Toast.makeText(MainActivity.this, "PRESS MENU TO CHANGE DETAILS", Toast.LENGTH_SHORT).show();
	loc.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			try
			{
				SharedPreferences getPrefs=PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		        et =getPrefs.getString("name","sads");
		        et2 =getPrefs.getString("uid","sads"); 
		        Toast.makeText(MainActivity.this, "ASKING"+ et+" FOR LOCATION", Toast.LENGTH_SHORT).show();
		        sendSMS(et, et2);
			}
			catch (Exception e)
			{
				
			}
					
		}
	});
b1.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			try
			{
				if(map.isSatellite()){
					map.setSatellite(false);
					map.setStreetView(true);
				}else{
					map.setSatellite(true);
					map.setStreetView(false);
					
				}
			}
			catch (Exception e)
			{
				
			}
					
		}
	});
	}
	
	public void sendSMS(String num, String msg)
    {
    	SmsManager sms= SmsManager.getDefault ();
    	sms.sendTextMessage(num, null, msg, null, null);
    }

	@Override
	protected void onResume() {
	//---register the receiver---
	registerReceiver(intentReceiver, intentFilter);
	super.onResume();
	}
	@Override
	protected void onPause() {
	//---unregister the receiver---
	unregisterReceiver(intentReceiver);
	super.onPause();
	}
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		Intent i= new Intent("com.example.owners.Prefs");
		startActivity(i);
		return false;
	}
	
	}