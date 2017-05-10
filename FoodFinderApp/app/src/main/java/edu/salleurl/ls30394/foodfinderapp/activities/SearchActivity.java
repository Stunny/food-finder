package edu.salleurl.ls30394.foodfinderapp.activities;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import edu.salleurl.ls30394.foodfinderapp.R;
import edu.salleurl.ls30394.foodfinderapp.repositories.impl.RestaurantsWebService;

public class SearchActivity extends AppCompatActivity {
    private Intent nextActivity;
    private EditText searchField;
    private android.widget.SeekBar seekBar;
    private TextView seekBarValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchField = (EditText) findViewById(R.id.search_field);
        configWidgets();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_search_menu, menu);
        return true;
    }

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

    public void clearSearch(View view) {
        searchField.setText("");
    }

    public void locationSearch(View view) {
        String aux = (String) seekBarValue.getText();
        String [] radius = aux.split(" ");
        Log.i("angel", radius[0]);
        //comprueva si tienes los permisos de localizacion en el manifest
        LocationManager lm = (LocationManager) getSystemService(this.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);

                // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Log.i("angel", "estas viu?");
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();

        RestaurantsWebService r = new RestaurantsWebService(getApplicationContext());
        r.getRestaurants(latitude, longitude, Integer.parseInt(radius[0]));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.activity_search_goProfile:
                nextActivity = new Intent(this, ProfileActivity.class);
                startActivity(nextActivity);
                finish();
                return true;

            case R.id.activity_search_goFavorites:
                return true;

            default:
            return super.onOptionsItemSelected(item);
        }
    }

}
