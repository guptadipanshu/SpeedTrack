

package ydtracker.driver;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;

import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.LocationService;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.android.map.event.OnStatusChangedListener.STATUS;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.LinearUnit;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.geometry.Unit;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.esri.core.symbol.SimpleFillSymbol.STYLE;





public class YDTrackerActivity extends Activity implements OnClickListener {
	MapView mMapView = null;
	ArcGISTiledMapServiceLayer tileLayer;
	
	Button b1;
    boolean fls=true;
    String et,et2;
    double locy ;
    double locx;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mMapView = (MapView)findViewById(R.id.map);
		SharedPreferences getPrefs=PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        et =getPrefs.getString("name","sads");
        et2 =getPrefs.getString("uid","uniquekey");
        Toast toast2 = Toast.makeText(YDTrackerActivity.this, "PRESS MENU FOR OPTIONS" , Toast.LENGTH_SHORT);
        toast2.show();
b1=(Button)findViewById(R.id.button1);
		tileLayer = new ArcGISTiledMapServiceLayer(
				"http://services.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer");
		b1.setOnClickListener(this);
		mMapView.addLayer(tileLayer);
		mMapView.setOnStatusChangedListener(new OnStatusChangedListener() {

  private static final long serialVersionUID = 1L;

  public void onStatusChanged(Object source, STATUS status) {
  if (source == mMapView && status == STATUS.INITIALIZED) {
  LocationService ls = mMapView.getLocationService();
  ls.setAutoPan(false);
  ls.setLocationListener(new LocationListener() {

  

 
  public void onLocationChanged(Location loc) {
	 
  
   locy = loc.getLatitude();
   locx = loc.getLongitude();
  b1.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			int locy2=(int) (locy*1000000);
			int locx2=(int) (locx*1000000);
			
				String fmsg2 ="Lat:"+locy2+"Long:"+locx2;
				   Toast.makeText(YDTrackerActivity.this, fmsg2+" SEND TO" +et, Toast.LENGTH_SHORT).show();
				   
				   
				   
				  sendSMS(et.toString(), fmsg2);
	            }
	});
  
 
  Point wgspoint = new Point(locx, locy);
  Point mapPoint = (Point) GeometryEngine.project(wgspoint, SpatialReference.create(4326),mMapView.getSpatialReference());
  Unit mapUnit = mMapView.getSpatialReference().getUnit();
  double zoomWidth = Unit.convertUnits(

  2000000, Unit.create(LinearUnit.Code.MILE_US), mapUnit);
  Envelope zoomExtent = new Envelope(mapPoint, zoomWidth, zoomWidth);

  mMapView.setExtent(zoomExtent);

  GraphicsLayer gLayer = new GraphicsLayer();
  SimpleMarkerSymbol symbol = new SimpleMarkerSymbol(Color.RED, 20, SimpleMarkerSymbol.STYLE.CIRCLE);
  Graphic graphic = new Graphic(mapPoint, symbol);
  //Graphic point=new Graphic(new Point(51,78),new SimpleMarkerSymbol(Color.BLACK,23,SimpleMarkerSymbol.STYLE.CIRCLE));   
  gLayer.addGraphic(graphic);
  mMapView .addLayer(gLayer);
 
 // Toast toast2 = Toast.makeText(HelloWorld.this, "LAT"+locy+"  LONG "+locx, Toast.LENGTH_LONG);
 // toast2.show();
  
  Uri uriSMSURI = Uri.parse("content://sms/inbox");
  Cursor cur = getContentResolver().query(uriSMSURI, null, null, null,null);
  String sms = "";
  while (cur.moveToNext()) {
	 String str= cur.getString(11);
	sms += "From :" + cur.getString(2) + " : " + str+"\n";
	 String fmsg ="Lat:"+locy+"Long:"+locx;
	
	if(cur.getString(2).equals(et) && str.equals(et2))
	{
	
		  
	        
		int locy1=(int) (locy*1000000);
		int locx1=(int) (locx*1000000);
		
			 fmsg ="Lat:"+locy1+"Long:"+locx1;
			   Toast.makeText(YDTrackerActivity.this, fmsg+" SEND TO" +et, Toast.LENGTH_SHORT).show();
			   
			   if(fls==true){
			   sendSMS(et.toString(), fmsg);
               fls=false;
			   }	   
	    	  
  }
  }
  
     }
  

  public void onProviderDisabled(String arg0) {

        }
  public void onProviderEnabled(String arg0) {
        }

  public void onStatusChanged(String arg0, int arg1,
  Bundle arg2) {

     }
    });
  ls.start();

 }
} });

	
  }
	  public void sendSMS(String num, String msg)
	    {
	    	SmsManager sms= SmsManager.getDefault ();
	    	sms.sendTextMessage(num, null, msg, null, null);
	    }

	
	

	protected void onPause() {
		super.onPause();
		mMapView.pause();
 }

	
	protected void onResume() {
		super.onResume(); 
		mMapView.unpause();
	}	
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activiry_main, menu);
		Intent i= new Intent("ydtracker.driver.Password");
		startActivity(i);
		return false;
	}
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	
}