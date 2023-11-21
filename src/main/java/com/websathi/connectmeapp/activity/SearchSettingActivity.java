package com.websathi.connectmeapp.activity;

import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.websathi.connectmeapp.BL.SearchConfig;
import com.websathi.connectmeapp.R;
import com.websathi.connectmeapp.helper.db.BusinessBookMarkDBHelper;
import com.websathi.connectmeapp.helper.db.SearchSettingDBHelper;

import java.util.ArrayList;
import java.util.Collections;

public class SearchSettingActivity extends AppCompatActivity implements OnMapReadyCallback {

    boolean[] selectedLanguage;
    ArrayList<Integer> categoriesList = new ArrayList<>();
    private FusedLocationProviderClient fusedLocationClient;
    private Location userCurrentLocation;
    private SeekBar locationRadiusSeekBar;
    private TextView textLocationRadiusValue;
    private SeekBar ratingSeekBar;
    private TextView ratingValue;
    private final String[] categories = {"COLLEGE", "RESTAURANT", "HOSPITAL", "GOVERNMENT-OFFICE", "IT-COMPANY"};
    private TextView categoryOptions;
    private SearchSettingDBHelper searchSettingDBHelper;

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        this.fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(final Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            SearchSettingActivity.this.userCurrentLocation = location;
                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull final Exception e) {
                        SearchSettingActivity.this.userCurrentLocation = new Location("");
                        SearchSettingActivity.this.userCurrentLocation.setLatitude(27.707627944721672);
                        SearchSettingActivity.this.userCurrentLocation.setLongitude(85.32192786686072);
                    }
                });


        this.setContentView(R.layout.fragment_serach_setting);
        searchSettingDBHelper = new SearchSettingDBHelper(this.getApplicationContext());

        this.textLocationRadiusValue = this.findViewById(R.id.text_location_radius_value);
        this.locationRadiusSeekBar = this.findViewById(R.id.seekbar_location_radius);

        this.categoryOptions = this.findViewById(R.id.categoryOptions);
        this.selectedLanguage = new boolean[this.categories.length];


        ratingSeekBar = findViewById(R.id.seekbar_rating);
        this.ratingValue = this.findViewById(R.id.text_rating_value);

        configLocationRadiusSeekBar();
        configRatingSeekBar();
        configureCategoryDropDown();


        final SupportMapFragment mapFragment = (SupportMapFragment) this.getSupportFragmentManager()
                .findFragmentById(R.id.googleMapFragment);
        final ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        mapFragment.getMapAsync(this);
    }




    private void configLocationRadiusSeekBar() {
        this.locationRadiusSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(final SeekBar seekBar, final int i, final boolean b) {
                SearchSettingActivity.this.textLocationRadiusValue.setText(i + " KM");
                searchSettingDBHelper.updateValue("radius", String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(final SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(final SeekBar seekBar) {

            }
        });
    }

    private void configRatingSeekBar() {
        this.ratingSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(final SeekBar seekBar, final int i, final boolean b) {
                SearchSettingActivity.this.ratingValue.setText(i + " Star");
                searchSettingDBHelper.updateValue("radius", String.valueOf(i));

            }

            @Override
            public void onStartTrackingTouch(final SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(final SeekBar seekBar) {

            }
        });
    }

    private void configureCategoryDropDown() {
        this.categoryOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                // Initialize alert dialog
                final AlertDialog.Builder builder = new AlertDialog.Builder(SearchSettingActivity.this);

                // set title
                builder.setTitle("Select Categories");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(categories, selectedLanguage, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, final int i, final boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position  in lang list
                            categoriesList.add(i);
                            // Sort array list
                            Collections.sort(categoriesList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            categoriesList.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, final int i) {
                        // Initialize string builder
                        final StringBuilder stringBuilder = new StringBuilder();
                        // use for loop
                        for (int j = 0; j < categoriesList.size(); j++) {
                            // concat array value
                            stringBuilder.append(categories[SearchSettingActivity.this.categoriesList.get(j)]);
                            // check condition
                            if (j != categoriesList.size() - 1) {
                                // When j value  not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        SearchSettingActivity.this.categoryOptions.setText(stringBuilder.toString());
                        searchSettingDBHelper.updateValue("categories", stringBuilder.toString());

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, final int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, final int i) {
                        // use for loop
                        for (int j = 0; j < selectedLanguage.length; j++) {
                            // remove all selection
                            selectedLanguage[j] = false;
                            // clear language list
                            categoriesList.clear();
                            // clear text view value
                            categoryOptions.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
            }
        });
    }


    @Override
    public void onMapReady(@NonNull final GoogleMap googleMap) {
        final LatLng latLng = new LatLng(27.707627944721672, 85.32192786686072);
        googleMap.addMarker(new MarkerOptions()
                .position(latLng));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.0f));

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                this.finish();
//                return true;
//        }
        finish();
        return true;
    }
}
