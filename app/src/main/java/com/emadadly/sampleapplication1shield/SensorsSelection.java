package com.emadadly.sampleapplication1shield;

import android.app.Fragment;
import android.content.Intent;
import android.hardware.Sensor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by Emad Adly on 9/27/2015.
 */
public class SensorsSelection extends Fragment implements View.OnClickListener {

    private static Button addSensors;
    private static SensorProvider sensorProvider; //for sensor views
    private static MainActivityFragmentManager mainActivityFramentManager; // for fragment management
    private static int lastViewId = -1;

    public static ArrayList<Integer> lastResult = new ArrayList<Integer>();//for store already selected sensors to avoid duplication

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sensors_selection_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //setting add sensor button
        addSensors = (Button) getActivity().findViewById(R.id.addSensors);
        addSensors.setOnClickListener(this);

        //initilizing fragment manager
        mainActivityFramentManager = new MainActivityFragmentManager(this.getActivity());
        //initializing sensorProvider
        sensorProvider = new SensorProvider(this.getActivity());


        //ceating current selected sensors if orientation change happened
        if (savedInstanceState != null) {
            lastViewId = savedInstanceState.getInt("LAST_VIEW_ID");
            lastResult = savedInstanceState.getIntegerArrayList("CURRENT_SENSORS");
            //clear all existed views from the container
            ((LinearLayout) getActivity().findViewById(R.id.sensors_container)).removeAllViews();
            for (int id : lastResult) {
                sensorProvider.createSensorButton(id, this, false);
            }
        }
    }


    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        if (viewId == addSensors.getId()) {
            Intent selectionList = new Intent(SensorsSelection.this.getActivity(), SensorsList.class);
            startActivityForResult(selectionList, 1);
        } else if (viewId == lastViewId) { //test if the same sensor selected
            return;
        }  else if (viewId == Sensor.TYPE_ACCELEROMETER) {
            //replace fragment with accelerometer fragment
            AccelerometerFragment accelerometerFragment = new AccelerometerFragment();
            mainActivityFramentManager.replaceFragment(accelerometerFragment);
        } else if (viewId == Sensor.TYPE_MAGNETIC_FIELD) {
            MagneticFieldFragment magneticFieldFragment = new MagneticFieldFragment();
            mainActivityFramentManager.replaceFragment(magneticFieldFragment);
        } else if (viewId == Sensor.TYPE_ORIENTATION) {
            OrientationFragment orientationFragment = new OrientationFragment();
            mainActivityFramentManager.replaceFragment(orientationFragment);
        } else if (viewId == Sensor.TYPE_ROTATION_VECTOR) {
            RotationVectorFragment rotationVectorFragment = new RotationVectorFragment();
            mainActivityFramentManager.replaceFragment(rotationVectorFragment);
        } else if (viewId == Sensor.TYPE_PROXIMITY) {
            ProximityFragment proximityFragment = new ProximityFragment();
            mainActivityFramentManager.replaceFragment(proximityFragment);
        }

        lastViewId = viewId;
    }


    //Wait for sensor list activity result that initialized by addSensors button
    //getting the selected button from the bundle data loaded with the result intent
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent intent) {

        if (resultCode == resultCode) {
            ArrayList<Integer> selectedSensors = intent.getIntegerArrayListExtra("SELECTED");
            if (selectedSensors.isEmpty()) return;
            for (int id : selectedSensors) {
                sensorProvider.createSensorButton(id, this, true);
            }
            lastResult = selectedSensors;
        }
    }

    //saving current selected sensors for activity recreation
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntegerArrayList("CURRENT_SENSORS", lastResult);
        outState.putInt("LAST_VIEW_ID", lastViewId);
    }

}


