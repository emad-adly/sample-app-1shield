package com.emadadly.sampleapplication1shield;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private static  FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    Bundle onSaveInstanceState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3a3a3a")));

        SensorsSelection sensorsSelection = new SensorsSelection();

        fragmentManager = getFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.sensorsFragmentView, sensorsSelection, "SENSORS_LIST");
        transaction.commit();

    }

    @Override
    public void onBackPressed() {
        fragmentManager.popBackStack();
        if (fragmentManager.getBackStackEntryCount() == 0) {
            finish();
        }
    }


}
