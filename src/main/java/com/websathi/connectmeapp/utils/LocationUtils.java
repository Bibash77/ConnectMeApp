package com.websathi.connectmeapp.utils;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationUtils {
    private static FusedLocationProviderClient fusedLocationClient;

    public static void initialize(final Context context) {
        LocationUtils.fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    public static void getLastKnownLocation(final Context context, final OnSuccessListener<Location> successListener, final OnFailureListener failureListener) {
        LocationUtils.fusedLocationClient.getLastLocation().addOnSuccessListener(successListener).addOnFailureListener(e -> {
            System.out.println(e.fillInStackTrace());
        });
    }

    // You can add methods for requesting location updates, handling permissions, etc.
}
