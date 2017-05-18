package edu.salleurl.ls30394.foodfinderapp.activities;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.List;

import edu.salleurl.ls30394.foodfinderapp.Adapter.RestaurantAdapter;
import edu.salleurl.ls30394.foodfinderapp.R;
import edu.salleurl.ls30394.foodfinderapp.model.Restaurante;
import edu.salleurl.ls30394.foodfinderapp.repositories.RestaurantsRepo;
import edu.salleurl.ls30394.foodfinderapp.repositories.impl.RestaurantsWebService;

public class SearchResultActivity extends AppCompatActivity {

    private ListView searchResultListView;
    private RestaurantAdapter adapter;

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

        searchResultListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void initWidgets() {

        searchResultListView = (ListView) findViewById(R.id.result_restaurants_list);
        adapter = new RestaurantAdapter(this, list);

    }

    private void initActionBar(){
        ActionBar actionBar = getSupportActionBar();
        View mActionBarView = getLayoutInflater().inflate(R.layout.actionbar_search_result, null);
        actionBar.setCustomView(mActionBarView);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        Spinner actionbarSpinner = (Spinner) findViewById(R.id.actionbar_results_spinner);

        //TODO: implementar el adaptador del spinner y asociarlo con el adaptador de restaurantes
        //      para filtrar el tipo de restaurantes a mostrar

        String[] data = {"lel", "wah"};
        actionbarSpinner.setAdapter(
                new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, data)
        );

    }

    //**********************MAIN BEHAVIOR FUNTIONS************************************************//
}
