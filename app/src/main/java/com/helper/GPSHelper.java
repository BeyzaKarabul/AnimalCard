package com.helper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

public class GPSHelper implements LocationListener {

    private final Context context;

    private final String[] permissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private Location currentLocation = null;
    private boolean canGetLocation = true;
    private boolean canSetSettings = true;

    private final LocationManager mLocationManager;

    private boolean isGPSProviderEnabled;
    private boolean isNetworkProviderEnabled;

    @SuppressLint("StaticFieldLeak")
    private static GPSHelper gpsHelper;

    public static GPSHelper getInstance(Context context) {
        if (gpsHelper == null) {
            gpsHelper = new GPSHelper(context);
        }
        return gpsHelper;
    }

    private GPSHelper(Context context) {
        this.context = context;
        mLocationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
    }

    public void setSettings() {
        if (!canSetSettings) {
            return;
        }

        boolean permissionCheck = true;
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(context, perm) != PackageManager.PERMISSION_GRANTED) {
                permissionCheck = false;
            }
        }

        if (!permissionCheck) {
            return;
        }

        isGPSProviderEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkProviderEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSProviderEnabled && !isNetworkProviderEnabled) {
            canGetLocation = false;
            return;
        }
        canSetSettings = false;

        if (isGPSProviderEnabled) {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    1000 * 60 * 2, 100, this
            );
        }

        if (isNetworkProviderEnabled) {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    1000 * 60 * 2, 100,
                    this
            );
        }

        List<String> providers = mLocationManager.getProviders(true);
        for (String provider : providers) {
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (currentLocation == null || l.getAccuracy() < currentLocation.getAccuracy()) {
                currentLocation = l;
            }
        }
    }

    public boolean isCanGetLocation() {
        isGPSProviderEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkProviderEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        canGetLocation = isGPSProviderEnabled || isNetworkProviderEnabled;

        return canGetLocation;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if (currentLocation == null || location.getAccuracy() < currentLocation.getAccuracy()) {
            currentLocation = location;
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}
