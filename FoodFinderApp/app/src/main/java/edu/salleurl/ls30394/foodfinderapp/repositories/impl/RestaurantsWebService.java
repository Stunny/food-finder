package edu.salleurl.ls30394.foodfinderapp.repositories.impl;

import android.content.Context;

import java.util.List;

import edu.salleurl.ls30394.foodfinderapp.model.Restaurante;
import edu.salleurl.ls30394.foodfinderapp.repositories.RestaurantsRepo;

/**
 * Created by avoge on 29/04/2017.
 */

public class RestaurantsWebService implements RestaurantsRepo {

    public static final String WS_BASE_URL = "http://testapi-pprog2.azurewebsites.net/api/locations.php?method=getLocations";

    private Context context;

    private static RestaurantsWebService instance;

    private RestaurantsWebService(Context context){
        this.context = context;
    }

    public synchronized RestaurantsWebService getInstance(Context context){
        if(instance == null){
            instance = new RestaurantsWebService(context);
        }

        return instance;
    }

    @Override
    public List<Restaurante> getRestaurants(String search) {

        String requestURL = WS_BASE_URL+"&s="+search.trim();
        String jsonResult;

        return null;
    }

    @Override
    public List<Restaurante> getRestaurants(double lat, double lng, int radius) {
        String requestURL = WS_BASE_URL+"&lat="+Double.toString(lat)+"&lon="+Double.toString(lng)
                +"&dist="+Integer.toString(radius);
        String jsonResult;
        return null;
    }
}
