package edu.salleurl.ls30394.foodfinderapp.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import edu.salleurl.ls30394.foodfinderapp.Adapter.RestaurantAdapter;
import edu.salleurl.ls30394.foodfinderapp.R;
import edu.salleurl.ls30394.foodfinderapp.model.Restaurante;
import edu.salleurl.ls30394.foodfinderapp.repositories.RestaurantsRepo;
import edu.salleurl.ls30394.foodfinderapp.repositories.impl.RestaurantsWebService;

public class SearchResultActivity extends AppCompatActivity {

    private Spinner actionbarSpinner;

    private ListView searchResultListView;
    private RestaurantAdapter listAdapter;

    private RestaurantsRepo restaurantsRepo;
    private List<Restaurante> list;

    //***************************OVERRIDE FUNTIONS************************************************//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        restaurantsRepo = RestaurantsWebService.getInstance(this);
        list = restaurantsRepo.getResult();

        configWidgets();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_search_menu, menu);

        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    //*********************************UI FUNTIONS************************************************//

    public void configWidgets(){
        initActionBar();
        initWidgets();

        searchResultListView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
    }

    private void initWidgets() {

        searchResultListView = (ListView) findViewById(R.id.result_restaurants_list);
        listAdapter = new RestaurantAdapter(this, list);

        actionbarSpinner.setAdapter(
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_dropdown_item_1line,
                        listAdapter.getRestaurantTypes()
                )
        );

        actionbarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    listAdapter.filterRestaurants(true, null);
                } else {
                    listAdapter.filterRestaurants(false, ((TextView)view).getText().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void initActionBar(){
        ActionBar actionBar = getSupportActionBar();
        View mActionBarView = getLayoutInflater().inflate(R.layout.actionbar_search_result, null);
        actionBar.setCustomView(mActionBarView);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        actionbarSpinner = (Spinner) findViewById(R.id.actionbar_results_spinner);

    }

    //**********************MAIN BEHAVIOR FUNTIONS************************************************//
}
