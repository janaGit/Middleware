package com.example.smartphone;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;


import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;
//http://developer.android.com/training/basics/firstapp/building-ui.html
//http://developer.android.com/training/basics/firstapp/starting-activity.html
public class MainActivity extends Activity {

	private WebService w;
	private String host="192.168.137.1";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		w=new WebService();
		String [] url=new String[]{"http://"+host+":8088/Middleware/Controller/start"};
		w.execute(url); 
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	
	
	}
	/** Called when the user clicks the Send button */
	public void sendMessageOben(View view) {
	    // Do something in response to button
		//mSensorManager.= (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		//mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
	
		String [] url=new String[]{"http://"+host+":8088/Middleware/Schicht1/Smartphone/obenKlick/nummer1"};
		w=new WebService();
	    		   w.execute(url);		
}
	public void sendMessageUnten(View view) {
	    // Do something in response to button
		//mSensorManager.= (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		//mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
		String [] url=new String[]{"http://"+host+":8088/Middleware/Schicht1/Smartphone/untenKlick/nummer1"};
		w=new WebService();
		w.execute(url);		
}	public void sendMessageRechts(View view) {
    // Do something in response to button
	//mSensorManager.= (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	//mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
	String [] url=new String[]{"http://"+host+":8088/Middleware/Schicht1/Smartphone/rechtsKlick/nummer1"};
	w=new WebService();
		   w.execute(url);		
}	public void sendMessageLinks(View view) {
    // Do something in response to button
	//mSensorManager.= (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	//mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
	System.out.println("aufgerufen");
	String [] url=new String[]{"http://"+host+":8088/Middleware/Schicht1/Smartphone/linksKlick/nummer1"};
	w=new WebService();
	w.execute(url);		
}	public void sendMessageOK(View view) {
    // Do something in response to button
	//mSensorManager.= (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	//mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
	System.out.println("aufgerufen");
	String [] url=new String[]{"http://"+host+":8088/Middleware/Schicht1/Smartphone/okKlick/nummer1"};
	w=new WebService();
	w.execute(url);		
}	
//http://developer.android.com/guide/topics/ui/controls/radiobutton.html
public void getRadioButton(View view){
	   // Is the button now checked?
    boolean checked = ((RadioButton) view).isChecked();
    
    // Check which radio button was clicked
    switch(view.getId()) {
        case R.id.radioButton1:
            if (checked){
                host="192.168.137.1";
           
            break;}
        case R.id.radioButton2:
            if (checked){
            	host="192.168.178.22";
            break;}
    }
   
}
public void anmelden(View view){
	w=new WebService();
	String [] url=new String[]{"http://"+host+":8088/Middleware/Controller/start"};
	w.execute(url); 
	String [] url2=new String[]{"http://"+host+":8088/Middleware/Controller/anmelden/1/Smartphone/nummer1"};
	w=new WebService();
	w.execute(url2);	
}

}
