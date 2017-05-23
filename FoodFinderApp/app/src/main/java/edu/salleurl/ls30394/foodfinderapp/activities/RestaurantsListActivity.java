package edu.salleurl.ls30394.foodfinderapp.activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import edu.salleurl.ls30394.foodfinderapp.Adapter.TabAdapter;
import edu.salleurl.ls30394.foodfinderapp.R;
import edu.salleurl.ls30394.foodfinderapp.fragments.RestaurantListFragment;
import edu.salleurl.ls30394.foodfinderapp.repositories.impl.RestaurantsWebService;

public class RestaurantsListActivity extends AppCompatActivity {

    private Intent nextActivity;
    private String userName;
    private boolean isFavsActivity;

    private Spinner actionbarSpinner;

    private RestaurantListFragment allRestaurantsFragment;
    private RestaurantListFragment onlyOpenRestaurantsFragment;

    //***************************OVERRIDE FUNTIONS************************************************//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userName = getIntent().getStringExtra("username");
        isFavsActivity = getIntent().getBooleanExtra("favorites", false);

        setContentView(R.layout.activity_restaurants);

        configWidgets();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if(isFavsActivity)
            getMenuInflater().inflate(R.menu.activity_favs_menu, menu);
        else
            getMenuInflater().inflate(R.menu.activity_search_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.activity_search_goProfile:
                nextActivity = new Intent(this, ProfileActivity.class);
                nextActivity.putExtra("userName",userName);
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
    public void onBackPressed() {
        finish();
    }

    //*********************************UI FUNTIONS************************************************//

    public void configWidgets(){
        initTabs();
        initActionBar();
        initWidgets();
    }

    private void initWidgets() {
        actionbarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    allRestaurantsFragment.getListAdapter().filterRestaurants(true, null);
                    onlyOpenRestaurantsFragment.getListAdapter().filterRestaurants(true, null);
                } else {
                    allRestaurantsFragment.getListAdapter()
                            .filterRestaurants(false, ((TextView)view).getText().toString());
                    onlyOpenRestaurantsFragment.getListAdapter()
                            .filterRestaurants(false, ((TextView)view).getText().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void initActionBar(){

        View mActionBarView = getLayoutInflater().inflate(R.layout.actionbar_search_result, null);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(mActionBarView);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        actionbarSpinner = (Spinner) findViewById(R.id.actionbar_results_spinner);
    }

    private void initTabs(){
        TabLayout tabLayout = (TabLayout) findViewById(R.id.search_result_tabs);
        ViewPager viewPager = (ViewPager) findViewById(R.id.search_result_viewPager);

         allRestaurantsFragment = new RestaurantListFragment();
         onlyOpenRestaurantsFragment = new RestaurantListFragment();

        Bundle fragmentArgs = new Bundle();
        fragmentArgs.putBoolean("onlyOpen", false);
        fragmentArgs.putBoolean("favs", isFavsActivity);
        fragmentArgs.putString("username", userName);
        allRestaurantsFragment.setArguments(fragmentArgs);

        fragmentArgs = new Bundle();
        fragmentArgs.putBoolean("onlyOpen", true);
        fragmentArgs.putBoolean("favs", isFavsActivity);
        fragmentArgs.putString("username", userName);
        onlyOpenRestaurantsFragment.setArguments(fragmentArgs);

        ArrayList<TabAdapter.TabEntry> entries = new ArrayList<>();
        entries.add(new TabAdapter.TabEntry(allRestaurantsFragment, getString(R.string.all_restaurants)));
        entries.add(new TabAdapter.TabEntry(onlyOpenRestaurantsFragment, getString(R.string.only_open)));

        TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), entries);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    public void initSpinnerOptions(){
        actionbarSpinner.setAdapter(
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_dropdown_item_1line,
                        isFavsActivity
                                ? allRestaurantsFragment.getListAdapter().getRestaurantTypes()
                                : RestaurantsWebService.getInstance(this).getRestaurantTypes()
                )
        );
    }

    //**********************MAIN BEHAVIOR FUNTIONS************************************************//
}
