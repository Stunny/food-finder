package edu.salleurl.ls30394.foodfinderapp.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import edu.salleurl.ls30394.foodfinderapp.Adapter.RecentSearchAdapter;
import edu.salleurl.ls30394.foodfinderapp.R;
import edu.salleurl.ls30394.foodfinderapp.repositories.RestaurantsRepo;
import edu.salleurl.ls30394.foodfinderapp.repositories.impl.RestaurantsWebService;
import edu.salleurl.ls30394.foodfinderapp.service.LocationService;

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static final String REQUEST_SUCCESS = "edu.salleurl.ls30394.foodfinderapp.REQUEST_SUCCESS";
    public static final String REQUEST_EMPTY_RESULT = "edu.salleurl.ls30394.foodfinderapp.REQUEST_EMPTY_RESULT";

    private Intent nextActivity;

    private EditText searchField;
    private android.widget.SeekBar seekBar;
    private TextView seekBarValue;
    private Button searchButton;

    private ListView recentSearchesList;
    private RecentSearchAdapter recentSearchAdapter;

    private String userName;

    private LocationService locationService;
    private RestaurantsRepo restaurantsRepo;
    private BroadcastReceiver bReceiver;
    private LocalBroadcastManager bManager;

    private ProgressDialog searchProgressDialog;

    //************************OVERRIDE FUNCTIONS**************************************************//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");

        configWidgets();
        locationService = LocationService.getInstance(getApplicationContext());
        restaurantsRepo = RestaurantsWebService.getInstance(this);

        bReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()) {
                    case REQUEST_SUCCESS:
                        onRequestSuccess();
                        break;
                    case REQUEST_EMPTY_RESULT:
                        onRequestEmptyResult();
                        break;
                }
            }
        };

        bManager = LocalBroadcastManager.getInstance(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(REQUEST_SUCCESS);
        filter.addAction(REQUEST_EMPTY_RESULT);
        bManager.registerReceiver(bReceiver, filter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        locationService = LocationService.getInstance(getApplicationContext());
        locationService.registerListeners(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        recentSearchAdapter.updateList();
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationService = LocationService.getInstance(getApplicationContext());
        locationService.unregisterListeners();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bManager.unregisterReceiver(bReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.activity_search_goProfile:
                nextActivity = new Intent(this, ProfileActivity.class);
                nextActivity.putExtra("userName", userName);
                startActivity(nextActivity);
                return true;

            case R.id.activity_search_goFavorites:
                nextActivity = new Intent(this, RestaurantsListActivity.class);
                nextActivity.putExtra("username", userName);
                nextActivity.putExtra("favorites", true);
                startActivity(nextActivity);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        //Log.i("angel", "onRequestPermissionsResult");
        switch (requestCode) {
            case LocationService.MY_PERMISSIONS_REQUEST_LOCATION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LocationService.getInstance(this).registerListeners(this);
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String searchQuery = ((TextView) view).getText().toString();
        restaurantsRepo.fetchRestaurants(searchQuery);
        recentSearchAdapter.addRecentSearch(searchQuery);
        searchProgressDialog.show();
    }

    //*****************UI FUNCTIONS***************************************************************//

    private void configWidgets() {
        initWidgets();

        recentSearchAdapter = new RecentSearchAdapter(this, userName);
        recentSearchesList.setAdapter(recentSearchAdapter);
        recentSearchesList.setOnItemClickListener(this);
    }

    private void initWidgets() {

        getSupportActionBar().setTitle(R.string.rest_Search);

        searchButton = (Button) findViewById(R.id.search_go_btn);

        seekBar = (android.widget.SeekBar) findViewById(R.id.seek_bar);
        seekBarValue = (TextView) findViewById(R.id.search_slider_kms);
        seekBarValue.setText("0 km");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
        });

        searchField = (EditText) findViewById(R.id.search_field);

        searchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    OnSearchClick(v);
                    handled = true;
                }
                return handled;
            }
        });

        recentSearchesList = (ListView) findViewById(R.id.recent_searches_list);

        searchProgressDialog = new ProgressDialog(this);
        searchProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        searchProgressDialog.setMessage(getString(R.string.search_progress));
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

        if (!(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            checkGPS();
        } else {
            onStart();
        }
    }

    private void checkGPS() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.GPS_error)
                    .setCancelable(false)
                    .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            Intent gps = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(gps, 1);
                        }
                    })
                    .setNegativeButton(R.string.deny, new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            dialog.cancel();
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        } else {
            launchGeoSearch();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, false);
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
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            launchGeoSearch();
        } else {
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            //TODO: ACTUALIZAR EL VALOR DE LOCATION
            //locationManager.requestSingleUpdate(provider, null);
            launchGeoSearch();
        }
    }

    private void launchGeoSearch() {
        String aux = (String) seekBarValue.getText();
        int searchRadius = Integer.parseInt(aux.split(" ")[0]);

        double latitude = locationService.getLocation().getLatitude();
        double longitude = locationService.getLocation().getLongitude();

        searchProgressDialog.show();

        restaurantsRepo.fetchRestaurants(latitude, longitude, searchRadius);
    }

    /**
     *
     * @param view
     */
    public void OnSearchClick(View view){
        String searchQuery = searchField.getText().toString();
        if(!searchQuery.equals("")) {
            recentSearchAdapter.addRecentSearch(searchQuery);
            recentSearchAdapter.notifyDataSetChanged();
            searchField.setText("");

            searchProgressDialog.show();
            restaurantsRepo.fetchRestaurants(searchQuery);
        }else{
            Toast.makeText(this, "Search field can't be empty", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     *
     */
    public void onRequestSuccess(){
        if(searchProgressDialog.isShowing()) searchProgressDialog.dismiss();

        nextActivity = new Intent(SearchActivity.this, RestaurantsListActivity.class);
        nextActivity.putExtra("username", userName);
        nextActivity.putExtra("favorites", false);
        startActivity(nextActivity);
    }

    /**
     *
     */
    public void onRequestEmptyResult(){
        if(searchProgressDialog.isShowing()) searchProgressDialog.dismiss();

        Snackbar.make(findViewById(R.id.search_activity_coordinatorLayout), getString(R.string.no_results_snack), Snackbar.LENGTH_SHORT)
            .show();
    }

}
