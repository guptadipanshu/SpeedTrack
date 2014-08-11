package com.speed_alert;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Menu extends ListActivity {
String choices[]={"Map"};

protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setListAdapter(new ArrayAdapter<String>(Menu.this, android.R.layout.simple_list_item_1, choices));
}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		
		super.onListItemClick(l, v, position, id);
		String abc=choices[position];
		try{
		Class oclass = Class.forName("com.speed_alert."+abc);
		Intent ourIntent= new Intent(Menu.this,oclass);
		startActivity(ourIntent);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		// TODO Auto-generated method stub
		 super.onCreateOptionsMenu(menu);
		 MenuInflater blowup= getMenuInflater();
		 blowup.inflate(R.menu.cool_menu, menu);
		 return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.aboutUs:
			Intent i= new Intent("com.speed_alert.Aboutus");
			startActivity(i);
			break;
		case R.id.prefrences:
			Intent p= new Intent("com.speed_alert.Password");
			startActivity(p);
			break;
		case R.id.exit:	
			finish();
			break;
			
		}
		return false;
		
	}


	

}
