package com.example.owners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsMessage;
import android.widget.Toast;
public class ReceiveText extends BroadcastReceiver
{
	String sender, msg;
	String et= "+971555244246";
	String key= "Lat:";
	int a, b, c;
	int ctr;
	int lat, longi;
	Context x;
	boolean flg= false;

@Override
public void onReceive(Context context, Intent intent)
{
//---get the SMS message passed in---
Bundle bundle = intent.getExtras();
SmsMessage[] msgs = null;
String str = "";
if (bundle != null)
{
//---retrieve the SMS message received---
Object[] pdus = (Object[]) bundle.get("pdus");
msgs = new SmsMessage[pdus.length];
for (int i=0; i<msgs.length; i++){
	msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
	//str += "SMS from " + msgs[i].getOriginatingAddress();
	sender= msgs[i].getOriginatingAddress();
	
	//str += " :";
	//str += msgs[i].getMessageBody().toString();
	String str1= msgs[i].getMessageBody().toString();
	SharedPreferences getPrefs=PreferenceManager.getDefaultSharedPreferences(context);
    et =getPrefs.getString("name","sads");
	if(sender.equals(et) && str1.substring(0,4).equals(key))
	{
		//Toast.makeText(context, "PARTY BRO!!!", Toast.LENGTH_SHORT).show();
		//PART FROM BLUEJ
		flg= true;
		for(int i1=0; i1<str1.length(); i1++)
	    {
	        if(str1.charAt(i1)== ':' && ctr== 0)
	        {
	            ctr++;
	            a= i1;
	            System.out.println("First ':' "+a);
	        }
	        else if(str1.charAt(i1)== ':' && ctr== 1)
	        {
	            b= i1;
	            System.out.println("Second ':' "+b);
	        }
	        if(str1.charAt(i1)== 'L' && ctr==1)
	        {
	            c= i1;
	            System.out.println("Second 'L' "+c);
	        }
	    }
	        //System.out.println(str.substring(3, 10));
	        lat= Integer.parseInt(str1.substring(a+1, c));
	        longi= Integer.parseInt(str1.substring(b+1));
	        //System.out.println("LAT: "+lat+" LONG: "+longi);
	      // int w=  getLat();
	       Toast.makeText(context,"PLOTTING DRIVER CO-ORDINATES", Toast.LENGTH_LONG).show();
	        //BLUEJ ENDS HERE
	       int z= 0;
	       int w= getLat(z);
	       Toast.makeText(context,"LAT: "+w, Toast.LENGTH_LONG).show();
	}
	else
	{
		Toast.makeText(context, "DRIVER NUMBER DOES NOT MATCH", Toast.LENGTH_SHORT).show();
	}
}
//---display the new SMS message---
//Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
//---display the new SMS message---
//---send a broadcast intent to update the SMS received in the activity---
Intent broadcastIntent = new Intent();
broadcastIntent.setAction("SMS_RECEIVED_ACTION");
broadcastIntent.putExtra("sms1", Integer.toString(lat));
broadcastIntent.putExtra("sms2", Integer.toString(longi));
context.sendBroadcast(broadcastIntent);}
}

public int getLat(int q)
{
q= lat;
return q;
}
public boolean hasRcvd()
{

return flg;
}


}

