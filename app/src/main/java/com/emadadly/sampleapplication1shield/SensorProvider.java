package com.emadadly.sampleapplication1shield;

import android.app.Activity;
import android.hardware.Sensor;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Random;

/**
 * Created by Emad Adly on 9/28/2015.
 */
class SensorProvider {
    private View inflatedView;
    private LinearLayout sensorsButtonsContainer;
    private Random r = new Random(); //for random color
    private LayoutInflater layoutInflater;

    public SensorProvider(Activity contextActivity) {
        sensorsButtonsContainer = (LinearLayout) contextActivity.findViewById(R.id.sensors_container);
        layoutInflater = contextActivity.getLayoutInflater();
    }

    /**
     * create sensor button with id and title
     *
     * @param id
     * @param test
     */
    public void createSensorButton(int id, View.OnClickListener listener, boolean test) {
        String buttonTitle = null;
        if (id == Sensor.TYPE_ACCELEROMETER) {
            buttonTitle = "A";
        } else if (id == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            buttonTitle = "AT";
        } else if (id == Sensor.TYPE_GRAVITY) {
            buttonTitle = "G";
        } else if (id == Sensor.TYPE_GYROSCOPE) {
            buttonTitle = "Gyr";
        } else if (id == Sensor.TYPE_LIGHT) {
            buttonTitle = "L";
        } else if (id == Sensor.TYPE_LINEAR_ACCELERATION) {
            buttonTitle = "LiA";
        } else if (id == Sensor.TYPE_MAGNETIC_FIELD) {
            buttonTitle = "M";
        } else if (id == Sensor.TYPE_ORIENTATION) {
            buttonTitle = "O";
        } else if (id == Sensor.TYPE_PRESSURE) {
            buttonTitle = "P";
        } else if (id == Sensor.TYPE_PROXIMITY) {
            buttonTitle = "Prox";
        } else if (id == Sensor.TYPE_RELATIVE_HUMIDITY) {
            buttonTitle = "Hu";
        } else if (id == Sensor.TYPE_GAME_ROTATION_VECTOR) {
            buttonTitle = "R";
        } else if (id == Sensor.TYPE_TEMPERATURE) {
            buttonTitle = "T";
        }
        if (buttonTitle != null)
            createInflatedButton(buttonTitle, id, listener, test);
    }

    /**
     * inflating sensor button
     *
     * @param title
     * @param id
     */
    private void createInflatedButton(String title, int id, View.OnClickListener listener, boolean test) {
        if (test == false || !sensorExists(id)) {
            inflatedView = layoutInflater.inflate(R.layout.sensor_button, null, false);
            Button inflatedButton = (Button) inflatedView;
            inflatedButton.setText(title);
            inflatedButton.setBackgroundColor(r.nextInt());
            inflatedButton.setOnClickListener(listener);
            inflatedButton.setId(id);
            sensorsButtonsContainer.addView(inflatedView);
        }
    }

    private boolean sensorExists(int id) {
        for (Integer i : SensorsSelection.lastResult) {
            if (i == id)
                return true;
        }
        return false;
    }

}
