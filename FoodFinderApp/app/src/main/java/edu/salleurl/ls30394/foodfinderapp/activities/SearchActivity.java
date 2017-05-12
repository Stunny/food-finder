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
import android.provider.Settings;
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

import java.util.List;

import edu.salleurl.ls30394.foodfinderapp.R;
import edu.salleurl.ls30394.foodfinderapp.model.Restaurante;
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

    public void geolocationSearch(View view) {
        String aux = (String) seekBarValue.getText();
        final String [] radius = aux.split(" ");

        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new android.content.DialogInterface.OnClickListener() {
                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            while (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                                onPause();
                            }
                            Location l = getLastKnownLocation();
                            double latitude = l.getLatitude();
                            double longitude = l.getLongitude();
                            nextActivity = new Intent(SearchActivity.this, SearchResultActivity.class);
                            nextActivity.putExtra("lat", latitude);
                            nextActivity.putExtra("lng", longitude);
                            nextActivity.putExtra("rad", radius[0]);
                            startActivity(nextActivity);
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            dialog.cancel();
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        }else{
            Location l = getLastKnownLocation();
            double latitude = l.getLatitude();
            double longitude = l.getLongitude();
            nextActivity = new Intent(SearchActivity.this, SearchResultActivity.class);
            nextActivity.putExtra("lat", latitude);
            nextActivity.putExtra("lng", longitude);
            nextActivity.putExtra("rad", radius[0]);
            startActivity(nextActivity);
            finish();
        }

    }

    public void locationSearch(View view){

        nextActivity = new Intent(SearchActivity.this, SearchResultActivity.class);
        startActivity(nextActivity);
        finish();
    }


    private Location getLastKnownLocation() {
        LocationManager mLocationManager;

        mLocationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            //comprueva si tienes los permisos de localizacion en el manifest
            LocationManager lm = (LocationManager) getSystemService(this.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
            }

            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        Log.i("angelLocation", String.valueOf(bestLocation));
        return bestLocation;
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
