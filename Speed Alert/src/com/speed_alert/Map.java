package com.speed_alert;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.telephony.SmsManager;
import android.text.InputType;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Map extends MapActivity implements LocationListener{
	AlertDialog alert;
	TextView tv1,tv2;
	int a,b,c;
	boolean flag=true;
	float check;
    MapView map;
	long start,stop;
	MyLocationOverlay compass;
	MapController controller;
	int x,y;
	GeoPoint touchedPoint;
	Drawable d;List<Overlay> overlaylist;
	LocationManager lm;
	LocationManager lm1,lm2;
	String flocation="";
	String towers;
	int lat=0,lat1=0;
    int longi=0,longi1=0;
    float speed=0;
    Location location,location1,location2;
    String number;
    String final_msg="";
    String et,et2;
	String message= "The Person has OverSpeeded";
	boolean fls=true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.maps1);
        map =(MapView)findViewById(R.id.mvMain);
        map.setBuiltInZoomControls(true);
         lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                               0,
                                               0,
                                               this);
         
        SharedPreferences getData= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
    	SharedPreferences getPrefs=PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        et =getPrefs.getString("name","sads");
        et2 =getPrefs.getString("uid","sads");
        
    	String values = getData.getString("list", "4");
    	tv1=(TextView)findViewById(R.id.speed1);
    	tv2=(TextView)findViewById(R.id.speed2);
    	
    	if(values.contentEquals("1"))
    	{
    		check=2;
    	}else if(values.contentEquals("2"))
    	{
    		check=60;
    	}if(values.contentEquals("3"))
    	{
    		check=120;
    	}if(values.contentEquals("4"))
    	{
    		check=180;
    	}
    	tv2.setText("\n" +"  MAX  SPEED "+check+"KM.HR");
    		
        
        
        
      Touchy t= new Touchy();
        overlaylist= map.getOverlays();
        overlaylist.add(t);
        controller=map.getController();
       d=getResources().getDrawable(R.drawable.ic_launcher1);
       alert=new AlertDialog.Builder(Map.this).create();
    //placing pinpoint for current location
      lm1= (LocationManager)getSystemService(Context.LOCATION_SERVICE);
      Criteria crit=new Criteria();
      towers=lm1.getBestProvider(crit, false);
       location1=lm1.getLastKnownLocation(towers);
      try{ 
       onLocationChanged(location1);
      }catch (Exception e)
		{
			e.printStackTrace();
		}
	}     
     	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		/*compass.disableCompass();*/
		super.onPause();
		
				
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		/*compass.enableCompass();*/
		super.onResume();
		lm.requestLocationUpdates(lm.GPS_PROVIDER, 500, 1, this);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		Intent i= new Intent("com.speed_alert.Password");
		startActivity(i);
		return false;
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	  class Touchy extends Overlay{

		@Override
		public boolean onTouchEvent(MotionEvent e, MapView m) {
			// TODO Auto-generated method stub
			if(e.getAction()==MotionEvent.ACTION_DOWN){
				start=e.getEventTime();
				x= (int)e.getX();
				y= (int)e.getY();
				 touchedPoint =  map.getProjection().fromPixels(x, y);
				
			}
			if(e.getAction()==MotionEvent.ACTION_UP){
				stop=e.getEventTime();
			}
			
			if(stop-start > 1500){
				//perform action
				
				alert.setTitle("Pick an Option");
				alert.setMessage("Select An Option");
				
		
           alert.setButton2("Toogle View ", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					if(map.isSatellite()){
						map.setSatellite(false);
						map.setStreetView(true);
					}else{
						map.setSatellite(true);
						map.setStreetView(false);
						
					}
				}
			});
           alert.show();
           return true;
			
			}
			return false;
		}
	
}
	@Override
	public void onLocationChanged(Location location)
	  {
		
		
	    if (location != null)
	    {
	      CLocation myLocation = new CLocation(location, false);
	      
	        
	        Geocoder geocoder= new Geocoder(getBaseContext(), Locale.getDefault() );
	        lat=(int)(location.getLatitude()*1E6);
		     longi=(int)(location.getLongitude()*1E6);
		     alert.setButton("SOS", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
	                    
						number= et.toString();
						try{ 
							
							 String fmsg ="Lat:"+lat+"Long:"+longi;
							 Toast.makeText(Map.this, "CURRENT LOCATION:"+fmsg+ " SEND TO "+number, Toast.LENGTH_LONG).show();
						        sendSMS(number, fmsg);
							}		
					catch (Exception e)
					{
						e.printStackTrace();
					}
					finally{
					}}});
		    	  GeoPoint ourlocation =new GeoPoint(lat ,longi); 
		    	  OverlayItem overlayItem=new OverlayItem(ourlocation,"S1 ","S2");				
		    	  CustomPinPoint custom =	new CustomPinPoint(d,Map.this);				
		    	  custom.insertpinpoint(overlayItem)	;
		    	  overlaylist.add(custom);int ctr=0;
					Uri uriSMSURI = Uri.parse("content://sms/inbox");
				      Cursor cur = getContentResolver().query(uriSMSURI, null, null, null,null);
				      String sms = "";
				      while (cur.moveToNext()) {
				    	 String str= cur.getString(11);
				    	sms += "From :" + cur.getString(2) + " : " + str+"\n";
				    	
				    	if(cur.getString(2).equals(et.toString()) && str.equals(et2))
				    	{
				    	
				    		  
				    	        
				    	      
				    	    	   lat=(int)(location.getLatitude()*1E6);
				    			   longi=(int)(location.getLongitude()*1E6);
				    			   String fmsg ="Lat:"+lat+"Long:"+longi;
				    			  
				    			   
				    			   if(fls==true){
				    			   sendSMS(et.toString(), fmsg);
				    			   Toast.makeText(Map.this, fmsg+" SEND TO" +et, Toast.LENGTH_LONG).show();
				                   fls=false;
				    			   }	   
				    	    	  
				      }
				      }
				    	      
				    		 
		    	  	
		    	  	
		    	  	
			try{ 
				
				List <Address> address= geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);						
				if(address.size() > 0)
				{
					String display= "";
					for(int i=0; i<address.get(0).getMaxAddressLineIndex(); i++)
					{
						
						display+= address.get(0).getAddressLine(i)+ "\n";
					}
					final_msg= display;
					
				}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally{
		}
	    }
	    
	    else
	    {
	    	Toast.makeText(Map.this, "Couldn't get provider :-(", Toast.LENGTH_SHORT).show();
	    }
	    
	    float nCurrentSpeed = 0;
	    nCurrentSpeed = location.getSpeed();
        tv1.setText("CURRENT SPEED"+nCurrentSpeed+"KM.HR");
	   if(nCurrentSpeed>=check)
		{
			
	    	number= et.toString();
	    	message=message+"At a Speed :"+nCurrentSpeed+"At Location:"+final_msg;
	    	Toast.makeText(Map.this, "AN ALERT HAS BEEN SEND TO "+number+"FOR OVERSPEEDING AT"+final_msg, Toast.LENGTH_LONG).show();
	    	if(flag==true){
	    	sendSMS(number, message);
	    	flag=false;
	    	}
	    	} 
	   
	   
			 
		}
	  

	  public void sendSMS(String num, String msg)
	    {
	    	SmsManager sms= SmsManager.getDefault ();
	    	sms.sendTextMessage(num, null, msg, null, null);
	    }

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}

}
