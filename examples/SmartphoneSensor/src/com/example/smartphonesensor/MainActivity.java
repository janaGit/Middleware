package com.example.smartphonesensor;

import com.example.smartphonesensor.Sensoren;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;

public class MainActivity extends Activity {
    private SensorManager mSensorManager;

    private Sensor mSensor;

    String host = "192.168.137.1";

    Sensoren eins;

    Sensoren zwei;

    private WebServiceSensor w;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eins = new Sensoren("192.168.137.1");
        zwei = new Sensoren("192.168.178.22");
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        w = new WebServiceSensor();
        String[] url = new String[] { "http://" + host + ":8088/Middleware/Controller/start" };
        w.execute(url);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // http://developer.android.com/guide/topics/ui/controls/radiobutton.html
    public void getRadioButton(View view)
    {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
        case R.id.radioButton1:
            if (checked) {
                host = "192.168.137.1";
                mSensorManager.unregisterListener(zwei);
                mSensorManager.registerListener(eins, mSensor, 100000);
                System.out.println("button1");
                break;
            }
        case R.id.radioButton2:
            if (checked) {
                System.out.println("Button2");
                host = "192.168.178.22";
                mSensorManager.unregisterListener(eins);
                // http://developer.android.com/reference/android/hardware/SensorManager.html#SENSOR_DELAY_NORMAL
                mSensorManager.registerListener(zwei, mSensor, SensorManager.SENSOR_DELAY_UI);
                break;
            }
        case R.id.noConnection:
            if (checked) {
                System.out.println("Button3");
                host = "192.168.178.22";
                mSensorManager.unregisterListener(eins);
                mSensorManager.unregisterListener(zwei);
                break;
            }
        }

    }

    public void anmelden(View view)
    {
        w = new WebServiceSensor();
        String[] url = new String[] { "http://" + host + ":8088/Middleware/Controller/start" };
        w.execute(url);
        String[] url2 = new String[] { "http://" + host + ":8088/Middleware/Controller/anmelden/1/Smartphone/nummer1" };
        w = new WebServiceSensor();
        w.execute(url2);
    }
}
