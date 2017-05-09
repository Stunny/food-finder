package edu.salleurl.ls30394.foodfinderapp.repositories.impl;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import edu.salleurl.ls30394.foodfinderapp.app.AppController;
import edu.salleurl.ls30394.foodfinderapp.model.Restaurante;
import edu.salleurl.ls30394.foodfinderapp.repositories.RestaurantsRepo;

/**
 * Created by avoge on 29/04/2017.
 */

public class RestaurantsWebService implements RestaurantsRepo {

    public static final String WS_BASE_URL = "http://testapi-pprog2.azurewebsites.net/api/locations.php?method=getLocations";
    public static final String REQ_TAG = "restaurant_search";

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
        String requestURL = "";
        try {
            requestURL = WS_BASE_URL+"&s="+ URLEncoder.encode(search.trim(), "utf-8");
        } catch (UnsupportedEncodingException e) {}

        searchRestaurants(requestURL);
        return null;
    }

    @Override
    public List<Restaurante> getRestaurants(double lat, double lng, int radius) {
        String requestURL = WS_BASE_URL+"&lat="+Double.toString(lat)+"&lon="+Double.toString(lng)
                +"&dist="+Integer.toString(radius);

        searchRestaurants(requestURL);
        return null;
    }

    private void searchRestaurants(String url){

        JsonArrayRequest request = new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //TODO: Notify data is changed on serach result activity
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //TODO: Dont show anything
                    }
                }
        );
        AppController.getInstance().addToRequestQueue(request, REQ_TAG);
    }
}
