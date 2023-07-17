package com.websathi.connectmeapp.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.websathi.connectmeapp.R;

public class SearchSettingActivity extends AppCompatActivity implements OnMapReadyCallback {

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view =  inflater.inflate(R.layout.fragment_serach_setting, container, false);
//        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.googleMapFragment);
//        mapFragment.getMapAsync(this);
//        setHasOptionsMenu(true);
//
//        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//
//        return view;
//    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_serach_setting);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.googleMapFragment);
        ActionBar actionBar =   getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng latLng = new LatLng(27.707627944721672, 85.32192786686072);
        googleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("Your Location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.0f));

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                this.finish();
//                return true;
//        }
        this.finish();
        return true;
    }
}
