package com.example.smartphonesensor;

import com.example.smartphonesensor.WebServiceSensor;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class Sensoren extends Activity implements SensorEventListener {
String host;
	@Override
	public void onAccuracyChanged(Sensor sensor, int arg1) {
		// TODO Auto-generated method stub
	
	}
public Sensoren(String host){
	this.host=host;
}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		float x=event.values[0];
		float y=event.values[1];
		float z=event.values[2];
		String [] url=new String[]{"http://"+host+":8088/Middleware/Schicht1/Smartphone/rotation/nummer1/"+x+"/"+y+"/"+z};
	       WebServiceSensor w=new WebServiceSensor();
	    		   w.execute(url);	
	}

}
