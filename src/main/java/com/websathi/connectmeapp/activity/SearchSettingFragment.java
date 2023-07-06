package com.websathi.connectmeapp.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.websathi.connectmeapp.R;

public class SearchSettingFragment extends Fragment implements OnMapReadyCallback {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_serach_setting, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.googleMapFragment);
        mapFragment.getMapAsync(this);
        return view;
    }



    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng latLng = new LatLng(27.707627944721672, 85.32192786686072);
        googleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("Your Location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.0f));

    }
}
