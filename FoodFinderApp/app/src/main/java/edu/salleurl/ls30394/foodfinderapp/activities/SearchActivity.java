package edu.salleurl.ls30394.foodfinderapp.activities;

import android.Manifest;
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

public class SearchActivity extends AppCompatActivity {
    private Intent nextActivity;
    private EditText searchField;
    private android.widget.SeekBar seekBar;
    private TextView seekBarValue;
    private SeekBar.OnSeekBarChangeListener s;

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
        seekBar = (android.widget.SeekBar) findViewById(R.id.seek_bar);
        seekBarValue = (TextView) findViewById(R.id.search_slider_kms);

        s = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                displaySeekBarValue(true);
                Log.i("angel", "start");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                displaySeekBarValue(false);
                Log.i("angel", "stop");
            }
/*
            private void displaySeekBarValue(boolean b) {
                while (b){
                    onProgressChanged(seekBar,seekBar.getProgress(), true);
                }
            }*/
        };

    }

    private void displaySeekBarValue(boolean b) {
        while (b){
            s.onProgressChanged(seekBar,seekBar.getProgress(), true);
        }

    }


    public void clearSearch(View view) {
        searchField.setText("");
    }

    public void locationSearch(View view) {



        LocationManager lm = (LocationManager) getSystemService(this.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.activity_search_goProfile:
                Log.i("angel: ", "profile selected");
                nextActivity = new Intent(this, ProfileActivity.class);
                startActivity(nextActivity);
                finish();
                return true;

            case R.id.activity_search_goFavorites:
                Log.i("angel: ", "Favourite selected");
                return true;

            default:
            return super.onOptionsItemSelected(item);
        }
    }

}
