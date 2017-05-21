package edu.salleurl.ls30394.foodfinderapp.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.salleurl.ls30394.foodfinderapp.Adapter.RestaurantAdapter;
import edu.salleurl.ls30394.foodfinderapp.Adapter.TabAdapter;
import edu.salleurl.ls30394.foodfinderapp.R;
import edu.salleurl.ls30394.foodfinderapp.fragments.RestaurantListFragment;
import edu.salleurl.ls30394.foodfinderapp.model.Restaurante;
import edu.salleurl.ls30394.foodfinderapp.repositories.RestaurantsRepo;
import edu.salleurl.ls30394.foodfinderapp.repositories.impl.RestaurantsWebService;

public class SearchResultActivity extends AppCompatActivity {

    private Intent nextActivity;
    private String userName;

    private Spinner actionbarSpinner;

    private RestaurantListFragment allRestaurantsFragment;
    private RestaurantListFragment onlyOpenRestaurantsFragment;

    //***************************OVERRIDE FUNTIONS************************************************//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userName = getIntent().getStringExtra("username");

        setContentView(R.layout.activity_search_result);

        configWidgets();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
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
        actionbarSpinner.setAdapter(
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_dropdown_item_1line,
                        RestaurantsWebService.getInstance(this).getRestaurantTypes()
                )
        );

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
        allRestaurantsFragment.setArguments(fragmentArgs);

        fragmentArgs = new Bundle();
        fragmentArgs.putBoolean("onlyOpen", true);
        onlyOpenRestaurantsFragment.setArguments(fragmentArgs);

        ArrayList<TabAdapter.TabEntry> entries = new ArrayList<>();
        entries.add(new TabAdapter.TabEntry(allRestaurantsFragment, getString(R.string.all_restaurants)));
        entries.add(new TabAdapter.TabEntry(onlyOpenRestaurantsFragment, getString(R.string.only_open)));

        TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), entries);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    //**********************MAIN BEHAVIOR FUNTIONS************************************************//
}
