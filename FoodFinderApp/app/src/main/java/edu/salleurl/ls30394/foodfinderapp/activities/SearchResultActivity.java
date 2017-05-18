package edu.salleurl.ls30394.foodfinderapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

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
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    //*********************************UI FUNTIONS************************************************//

    public void configWidgets(){
        initWidgets();

        searchResultListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void initWidgets() {

        searchResultListView = (ListView) findViewById(R.id.result_restaurants_list);
        adapter = new RestaurantAdapter(this, list);

    }

    //**********************MAIN BEHAVIOR FUNTIONS************************************************//
}
