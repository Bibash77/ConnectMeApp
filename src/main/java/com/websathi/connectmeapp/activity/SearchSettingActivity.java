package com.websathi.connectmeapp.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.websathi.connectmeapp.helper.db.SearchSettingDBHelper;
import com.websathi.connectmeapp.utils.LocationUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class SearchSettingActivity extends AppCompatActivity implements OnMapReadyCallback {

    private final String[] categories = {"COLLEGE", "RESTAURANT", "HOSPITAL", "GOVERNMENT OFFICE", "IT COMPANY"};
    private boolean[] selectedCategories;
    private FusedLocationProviderClient fusedLocationClient;
    private Location userCurrentLocation;
    private SeekBar locationRadiusSeekBar;
    private TextView textLocationRadiusValue;

    private TextView locationSelectedData;
    private SeekBar ratingSeekBar;
    private TextView ratingValue;
    private TextView categoryOptions;
    private SearchSettingDBHelper searchSettingDBHelper;
    private SearchConfig searchConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocationUtils.initialize(this);
        setContentView(R.layout.fragment_serach_setting);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//        initializeUserLocation();

        searchSettingDBHelper = new SearchSettingDBHelper(getApplicationContext());
        searchConfig = searchSettingDBHelper.getDefaultValues();

        initializeViews();
        configLocationRadiusSeekBar();
        configRatingSeekBar();
        configureCategoryDropDown();

        if (searchConfig != null) {
            updateView(searchConfig);
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMapFragment);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mapFragment.getMapAsync(this);
    }

//    private void initializeUserLocation() {
//        fusedLocationClient.getLastLocation()
//                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location) {
//                        userCurrentLocation = (location != null) ? location : new Location("");
//                        userCurrentLocation.setLatitude(27.707627944721672);
//                        userCurrentLocation.setLongitude(85.32192786686072);
//                    }
//                })
//                .addOnFailureListener(this, new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        userCurrentLocation = new Location("");
//                        userCurrentLocation.setLatitude(27.707627944721672);
//                        userCurrentLocation.setLongitude(85.32192786686072);
//                    }
//                });
//    }

    private void initializeViews() {
        textLocationRadiusValue = findViewById(R.id.text_location_radius_value);
        locationSelectedData = findViewById(R.id.locationSelectedData);
        locationRadiusSeekBar = findViewById(R.id.seekbar_location_radius);
        categoryOptions = findViewById(R.id.categoryOptions);
        selectedCategories = new boolean[categories.length];
        ratingSeekBar = findViewById(R.id.seekbar_rating);
        ratingValue = findViewById(R.id.text_rating_value);
    }

    private void updateView(SearchConfig searchConfig) {
        ratingSeekBar.setProgress(Integer.parseInt(searchConfig.getRating()));
        ratingValue.setText(searchConfig.getRating() + " Star");

        locationRadiusSeekBar.setProgress(Integer.parseInt(searchConfig.getRadius()));
        textLocationRadiusValue.setText(searchConfig.getRadius() + " KM");

        String[] selectedCategoriesArray = searchConfig.getCategories().split(",");
        Arrays.fill(selectedCategories, false);

        for (String selectedCategory : selectedCategoriesArray) {
            int index = Arrays.asList(categories).indexOf(selectedCategory);
            if (index != -1) {
                selectedCategories[index] = true;
            }
        }

        updateSelectedCategoriesText();
    }

    private void configLocationRadiusSeekBar() {
        locationRadiusSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textLocationRadiusValue.setText(i + " KM");
                searchSettingDBHelper.updateValue("radius", String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void configRatingSeekBar() {
        ratingSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ratingValue.setText(i + " Star");
                searchSettingDBHelper.updateValue("rating", String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void configureCategoryDropDown() {
        categoryOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCategoryDialog();
            }
        });
    }

    private void showCategoryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SearchSettingActivity.this);
        builder.setTitle("Select Categories");
        builder.setCancelable(false);

        builder.setMultiChoiceItems(categories, selectedCategories, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                selectedCategories[i] = b;
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                updateSelectedCategoriesText();
                updateSelectedCategoriesInDB();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Arrays.fill(selectedCategories, false);
                updateSelectedCategoriesText();
            }
        });

        builder.show();
    }

    private void updateSelectedCategoriesText() {
        List<String> selectedCategoriesList = new ArrayList<>();
        for (int i = 0; i < selectedCategories.length; i++) {
            if (selectedCategories[i]) {
                selectedCategoriesList.add(categories[i]);
            }
        }

        categoryOptions.setText(TextUtils.join(", ", selectedCategoriesList));
    }

    private void updateSelectedCategoriesInDB() {
        List<String> selectedCategoriesList = new ArrayList<>();
        for (int i = 0; i < selectedCategories.length; i++) {
            if (selectedCategories[i]) {
                selectedCategoriesList.add(categories[i]);
            }
        }

        searchSettingDBHelper.updateValue("categories", TextUtils.join(",", selectedCategoriesList));
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        if(searchConfig.getLatitude() != null && searchConfig.getLongitude() != null) {
            LatLng latLng = new LatLng(Double.valueOf(searchConfig.getLatitude()), Double.valueOf(searchConfig.getLongitude()));
            googleMap.addMarker(new MarkerOptions().position(latLng));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.0f));
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            locationSelectedData.setText(searchConfig.getFormattedAddress());
            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    // Handle the click event, for example, place a marker
                    googleMap.clear(); // Clear existing markers
                    googleMap.addMarker(new MarkerOptions().position(latLng));
                    String address = getCompleteAddressString(Double.valueOf(searchConfig.getLatitude()), Double.valueOf(searchConfig.getLongitude()));
                    locationSelectedData.setText(address);
                    // You can also save the selected location for later use
                    saveSelectedLocation(latLng);
                }
            });
        } else {
            LocationUtils.getLastKnownLocation(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        googleMap.addMarker(new MarkerOptions().position(latLng));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.0f));
                        saveSelectedLocation(latLng);
                    }
                    googleMap.getUiSettings().setZoomControlsEnabled(true);
                    googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng latLng) {
                            // Handle the click event, for example, place a marker
                            googleMap.clear(); // Clear existing markers
                            googleMap.addMarker(new MarkerOptions().position(latLng));

                            String address = getCompleteAddressString(latLng.latitude, latLng.longitude);
                            locationSelectedData.setText(address);
                            // You can also save the selected location for later use
                            saveSelectedLocation(latLng);
                        }
                    });
                }
            }, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SearchSettingActivity.this, "Please Give Location Permission to continue", Toast.LENGTH_SHORT).show();

                }
            });
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        this.finish();
        return true;
    }

    private void saveSelectedLocation(LatLng latLng) {
        searchSettingDBHelper.updateValue("latitude", String.valueOf(latLng.latitude));
        searchSettingDBHelper.updateValue("longitude", String.valueOf(latLng.longitude));
        searchSettingDBHelper.updateValue("formatted_address", locationSelectedData.getText().toString());
    }


    @Override
    public void onBackPressed() {
        // Set a flag to indicate that data should be refreshed
        Intent resultIntent = new Intent();
        resultIntent.putExtra("refreshData", true);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }


    private String getCompleteAddressString(Double LATITUDE, Double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                System.out.println(strReturnedAddress.toString());
            } else {
                System.out.println("no address");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("My Current loction address" + "Canont get Address!");
        }
        return strAdd;
    }
}
