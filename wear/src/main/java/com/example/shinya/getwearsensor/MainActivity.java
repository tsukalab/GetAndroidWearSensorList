package com.example.shinya.getwearsensor;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import android.hardware.Sensor;
import android.hardware.SensorManager;

public class MainActivity extends WearableActivity {

    private static final SimpleDateFormat AMBIENT_DATE_FORMAT =
            new SimpleDateFormat("HH:mm", Locale.US);

    private BoxInsetLayout mContainerView;
    private TextView mTextView;
    private TextView mClockView;

    private SensorManager sensorManager;

    private int sensorList[] = {
            Sensor.TYPE_ACCELEROMETER, Sensor.TYPE_AMBIENT_TEMPERATURE,
            Sensor.TYPE_DEVICE_PRIVATE_BASE, Sensor.TYPE_GAME_ROTATION_VECTOR,
            Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR, Sensor.TYPE_GRAVITY,
            Sensor.TYPE_GYROSCOPE, Sensor.TYPE_GYROSCOPE_UNCALIBRATED,
            Sensor.TYPE_HEART_BEAT, Sensor.TYPE_HEART_RATE,
            Sensor.TYPE_LIGHT, Sensor.TYPE_LINEAR_ACCELERATION,
            Sensor.TYPE_MAGNETIC_FIELD, Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED,
            Sensor.TYPE_MOTION_DETECT, Sensor.TYPE_ORIENTATION, Sensor.TYPE_POSE_6DOF,
            Sensor.TYPE_PRESSURE, Sensor.TYPE_PROXIMITY,
            Sensor.TYPE_RELATIVE_HUMIDITY, Sensor.TYPE_ROTATION_VECTOR,
            Sensor.TYPE_SIGNIFICANT_MOTION, Sensor.TYPE_STEP_COUNTER,
            Sensor.TYPE_STEP_DETECTOR, Sensor.TYPE_TEMPERATURE
    };

    private String sensorNameList[] = {
            "ACCELEROMETER", "AMBIENT_TEMPERATURE", "DEVICE_PRIVATE_BASE",
            "GAME_ROTATION_VECTOR", "GEOMAGNETIC_ROTATION_VECTOR", "GRAVITY",
            "GYROSCOPE", "GYROSCOPE_UNCALIBRATED", "HEART_BEAT",
            "HEART_RATE", "LIGHT", "LINEAR_ACCELERATION",
            "MAGNETIC_FIELD", "MAGNETIC_FIELD_UNCALIBRATED", "MOTION_DETECT",
            "ORIENTATION", "POSE_6DOF", "PRESSURE",
            "PROXIMITY", "RELATIVE_HUMIDITY", "ROTATION_VECTOR",
            "SIGNIFICANT_MOTION", "STEP_COUNTER", "STEP_DETECTOR",
            "TEMPERATURE"
    };

    private boolean flg = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAmbientEnabled();

        mContainerView = (BoxInsetLayout) findViewById(R.id.container);
        mTextView = (TextView) findViewById(R.id.text);
        mClockView = (TextView) findViewById(R.id.clock);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 表示の切り替え
        if(flg){
            // 端末で使用できるセンサーを表示
            checkSensors();
        }
        else{
            // センサーリストから使用可能かどうかの表示
            checkSensorsEach();
        }
    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
        updateDisplay();
    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
        updateDisplay();
    }

    @Override
    public void onExitAmbient() {
        updateDisplay();
        super.onExitAmbient();
    }

    private void updateDisplay() {
        if (isAmbient()) {
            mContainerView.setBackgroundColor(getResources().getColor(android.R.color.black));
            mTextView.setTextColor(getResources().getColor(android.R.color.white));
            mClockView.setVisibility(View.VISIBLE);

            mClockView.setText(AMBIENT_DATE_FORMAT.format(new Date()));
        } else {
            mContainerView.setBackground(null);
            mTextView.setTextColor(getResources().getColor(android.R.color.black));
            mClockView.setVisibility(View.GONE);
        }
    }

    private void checkSensors(){
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        String strTmp = "Sensor List:\n\n";
        int count = 0;
        for(Sensor sensor : sensors) {
            count++;
            strTmp += String.valueOf(count)+": "+ sensor.getName() + "\n";
        }

        Log.d("hoge", strTmp);
    }

    private void checkSensorsEach(){

        String strTmp = "Sensor List:\n\n";
        for(int i=0; i < sensorList.length; i++){
            Sensor sensor = sensorManager.getDefaultSensor(sensorList[i]);

            if(sensor !=null){
                strTmp += String.valueOf(i+1)+": "+ sensorNameList[i] + ": 使用可能\n";
            }
            else{
                strTmp += String.valueOf(i+1)+": "+ sensorNameList[i] + ": X\n";
            }
        }
        Log.d("hoge", strTmp);
    }
}
