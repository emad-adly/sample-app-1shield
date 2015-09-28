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
public class RotationVectorFragment extends Fragment implements SensorEventListener {
    private SensorManager sensorManager;
    private TextView xTextView, yTextView, zTextView;
    private Sensor mRotationVector;
    float [] rotationMatrix = new float[9];
    float [] orientationVals = new float[3];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.rotation_vector_fragment_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mRotationVector = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        sensorManager.registerListener(this, mRotationVector, SensorManager.SENSOR_DELAY_NORMAL);
        xTextView = (TextView) getActivity().findViewById(R.id.rot_x);
        yTextView = (TextView) getActivity().findViewById(R.id.rot_y);
        zTextView = (TextView) getActivity().findViewById(R.id.rot_z);
    }


    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, mRotationVector, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() != Sensor.TYPE_ROTATION_VECTOR) return;
        SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);
        SensorManager.remapCoordinateSystem(rotationMatrix,
                SensorManager.AXIS_X, SensorManager.AXIS_Z,
                rotationMatrix);

        SensorManager.getOrientation(rotationMatrix,orientationVals);
        xTextView.setText(String.format("%f", orientationVals[0]));
        yTextView.setText(String.format("%f", orientationVals[1]));
        zTextView.setText(String.format("%f", orientationVals[2]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
