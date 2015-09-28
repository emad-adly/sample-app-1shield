package com.emadadly.sampleapplication1shield;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class SensorsList extends  AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private static ArrayAdapter<String> listAdapter;
    private static ListView listView;
    private static SensorManager sensorManager;
    private static CheckedTextView checkedTextView; //every item list has checkedTextView
    private static String[] sensorsNamesList;
    private static int[] sensorsTypes = { Sensor.TYPE_ACCELEROMETER, Sensor.TYPE_AMBIENT_TEMPERATURE,
            Sensor.TYPE_GRAVITY, Sensor.TYPE_GYROSCOPE, Sensor.TYPE_LIGHT, Sensor.TYPE_LINEAR_ACCELERATION,
            Sensor.TYPE_MAGNETIC_FIELD, Sensor.TYPE_ORIENTATION, Sensor.TYPE_PRESSURE, Sensor.TYPE_PROXIMITY,
            Sensor.TYPE_RELATIVE_HUMIDITY, Sensor.TYPE_ROTATION_VECTOR, Sensor.TYPE_TEMPERATURE};
    boolean [] supported_selected_sensors = new boolean[sensorsTypes.length];

    private static Button okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors_list);

        //initialize sensor manager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        //creating sensors list
        sensorsNamesList = getResources().getStringArray(R.array.sensors_list);

        listView = (ListView) findViewById(R.id.listView);
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, sensorsNamesList);
        listView.setAdapter(listAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(this);

        //setting ok button to complete selection operation
        okButton = (Button) findViewById(R.id.ok);
        okButton.setOnClickListener(this);
    }

    /**
     *
     * @param toast
     * display toast with string message parameter "toast"
     */
    private  void showToast(String toast) {
        Toast.makeText(getBaseContext(), toast, Toast.LENGTH_SHORT).show();
    }

    private boolean sensorIsNotAvailable(int sensorListPosition) {
       return  (sensorManager.getDefaultSensor(sensorsTypes[sensorListPosition]) == null) ? true : false;

    }


    //when sensor list item clicked
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        checkedTextView = (CheckedTextView) view;

        if (sensorIsNotAvailable(position)) {
            showToast(checkedTextView.getText().toString() + " not supported");
            listView.setItemChecked(position, false);
            supported_selected_sensors[position] = false;
        } else {
            if (!checkedTextView.isChecked()) {
                supported_selected_sensors[position] = false;
            }  else {
                supported_selected_sensors[position] = true;
            }
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == okButton.getId()) {
            createResult();
        }
    }

    @Override
    public void onBackPressed() {
        createResult();
    }

    private void createResult() {
        Intent intent = new Intent();
        ArrayList<Integer> selectedSensors = new ArrayList<Integer>();
        for (int i=0; i<supported_selected_sensors.length; i++) {
            if (supported_selected_sensors[i] == true) {
                selectedSensors.add(sensorsTypes[i]);
            }
        }

        intent.putExtra("SELECTED", selectedSensors);
        setResult(1, intent);
        finish();
    }
}

