package edu.salleurl.ls30394.foodfinderapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.List;

import edu.salleurl.ls30394.foodfinderapp.Adapter.RestaurantAdapter;
import edu.salleurl.ls30394.foodfinderapp.R;
import edu.salleurl.ls30394.foodfinderapp.model.Restaurante;
import edu.salleurl.ls30394.foodfinderapp.repositories.impl.RestaurantsWebService;

public class SearchResultActivity extends AppCompatActivity {
    private Intent nextActivity;
    private ListView searchResultListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Bundle extras = getIntent().getExtras();
        searchResultListView = (ListView) findViewById(R.id.result_restaurants_list);


        Double lat = (Double) extras.get("lat");
        Double lng = (Double) extras.get("lng");
        String rad = extras.getString("rad");

        RestaurantsWebService r = new RestaurantsWebService(getApplicationContext());
        Log.i("lat", String.valueOf(lat));
        Log.i("lng", String.valueOf(lng));
        Log.i("rad", rad);
        List<Restaurante> restaurantList = r.getRestaurants(lat, lng, Integer.parseInt(rad));

        for (int i = 0; i < restaurantList.size(); i++){
            Log.i("name", restaurantList.get(i).getName());
        }

        RestaurantAdapter rAdapter = new RestaurantAdapter(this, restaurantList);
        //searchResultListView.setAdapter(rAdapter);

    }

    @Override
    public void onBackPressed() {
        nextActivity = new Intent(SearchResultActivity.this, SearchActivity.class);

        startActivity(nextActivity);
        finish();
    }
}
