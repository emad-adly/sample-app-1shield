package com.emadadly.sampleapplication1shield;

import android.app.Fragment;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Emad Adly on 9/28/2015.
 */
public class OrientationFragment extends Fragment implements SensorEventListener {
    private SensorManager sensorManager;
    private TextView xTextView, yTextView, zTextView;
    private Sensor mOrientation;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.orientation_fragment_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mOrientation = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        sensorManager.registerListener(this, mOrientation, SensorManager.SENSOR_DELAY_NORMAL);
        xTextView = (TextView) getActivity().findViewById(R.id.or_x);
        yTextView = (TextView) getActivity().findViewById(R.id.or_y);
        zTextView = (TextView) getActivity().findViewById(R.id.or_z);
    }


    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, mOrientation, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() != Sensor.TYPE_ORIENTATION) return;
        float [] orientation = new float[3];
        SensorManager.getOrientation(orientation,event.values);
        xTextView.setText(String.format("%f", orientation[0]));
        yTextView.setText(String.format("%f", orientation[1]));
        zTextView.setText(String.format("%f", orientation[2]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
