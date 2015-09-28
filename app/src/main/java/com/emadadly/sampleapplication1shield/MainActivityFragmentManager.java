package com.emadadly.sampleapplication1shield;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

/**
 * Created by Emad Adly on 9/28/2015.
 */
class MainActivityFragmentManager {
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    MainActivityFragmentManager(Activity contextActivity) {
        //getting fragment manager for main activity
        fragmentManager = contextActivity.getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
    }

    /**
     * Replace data fragment with new fragment due to selected sensor
     *
     * @param fragment
     */
    public void replaceFragment(Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.dataFragmentView, fragment, "SENSORS_DATA");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack("LAST");
        fragmentTransaction.commit();
    }

}
