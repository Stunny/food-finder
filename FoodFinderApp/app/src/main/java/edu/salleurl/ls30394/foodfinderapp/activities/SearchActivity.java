package edu.salleurl.ls30394.foodfinderapp.activities;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import edu.salleurl.ls30394.foodfinderapp.R;
import edu.salleurl.ls30394.foodfinderapp.model.Restaurante;
import edu.salleurl.ls30394.foodfinderapp.model.User;
import edu.salleurl.ls30394.foodfinderapp.repositories.impl.RestaurantsWebService;
import edu.salleurl.ls30394.foodfinderapp.service.LocationService;

public class SearchActivity extends AppCompatActivity {

    private Intent nextActivity;

    private EditText searchField;
    private android.widget.SeekBar seekBar;
    private TextView seekBarValue;

    private String userName;
    private String userPassword;
    private User user;

    private LocationService locationService;

    //************************OVERRIDE FUNCTIONS**************************************************//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchField = (EditText) findViewById(R.id.search_field);
        configWidgets();
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        userPassword = intent.getStringExtra("userPassword");

        locationService = LocationService.getInstance(getApplicationContext());
    }

    @Override
    protected void onStart() {
        super.onStart();
        locationService = LocationService.getInstance(getApplicationContext());
        locationService.registerListeners(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationService = LocationService.getInstance(getApplicationContext());
        locationService.unregisterListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.activity_search_goProfile:
                nextActivity = new Intent(this, ProfileActivity.class);
                nextActivity.putExtra("userName",userName);
                nextActivity.putExtra("userPassword",userPassword);
                startActivity(nextActivity);
                finish();
                return true;

            case R.id.activity_search_goFavorites:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case LocationService.MY_PERMISSIONS_REQUEST_LOCATION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LocationService.getInstance(this).registerListeners(this);
                }
                break;
        }
    }

    //*****************UI FUNCTIONS***************************************************************//

    private void configWidgets() {
        initWidgets();
    }

    private void initWidgets() {
        //declaro los componentes
        seekBar = (android.widget.SeekBar) findViewById(R.id.seek_bar);
        seekBarValue = (TextView) findViewById(R.id.search_slider_kms);
        seekBarValue.setText("0 km");
        //creo el listener
        SeekBar.OnSeekBarChangeListener seekBarListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarValue.setText(String.valueOf(progress) + " km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
        //asigno el listener al seekBar
        seekBar.setOnSeekBarChangeListener(seekBarListener);
    }

    //***********************MAIN BEHAVIOR FUNCTIONS**********************************************//

    /**
     *
     * @param view
     */
    public void clearSearchText(View view) {
        searchField.setText("");
    }

    /**
     *
     * @param view
     */
    public void OnGeolocationSearch(View view) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    LocationService.MY_PERMISSIONS_REQUEST_LOCATION);

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LocationService.MY_PERMISSIONS_REQUEST_LOCATION);
            return;
        }

        String aux = (String) seekBarValue.getText();

        int searchRadius = Integer.parseInt(aux.split(" ")[0]);

    }

    /**
     *
     * @param view
     */
    public void OnSearchClick(View view){

    }


}
