package edu.salleurl.ls30394.foodfinderapp.utils;

import android.location.Location;
import android.os.Bundle;

/**
 * Created by angel on 27/05/2017.
 */

public class LocationListener implements android.location.LocationListener {

    private LocationListener mListener = new LocationListener() {

    };

    @Override
    public void onLocationChanged(Location location) {
        // Previously mock location is cleared.
        // getLastKnownLocation(LocationManager.GPS_PROVIDER); will not return mock location.
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
