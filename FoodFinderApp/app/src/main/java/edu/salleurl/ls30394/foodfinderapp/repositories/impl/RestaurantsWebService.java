package edu.salleurl.ls30394.foodfinderapp.repositories.impl;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import edu.salleurl.ls30394.foodfinderapp.R;
import edu.salleurl.ls30394.foodfinderapp.activities.SearchActivity;
import edu.salleurl.ls30394.foodfinderapp.app.AppController;
import edu.salleurl.ls30394.foodfinderapp.model.Restaurante;
import edu.salleurl.ls30394.foodfinderapp.repositories.RestaurantsRepo;

/**
 * Created by avoge on 29/04/2017.
 */

public class RestaurantsWebService implements RestaurantsRepo {

    public static final String WS_BASE_URL = "http://testapi-pprog2.azurewebsites.net/api/locations.php?method=getLocations";
    public static final String REQ_TAG = "restaurant_search";

    private List<Restaurante> result;
    private String[] typesInResult;

    private static RestaurantsWebService instance;
    private Context context;
    private LocalBroadcastManager bManager;

    private int pendingRequests;

    @Override
    /**
     * Adds a restaurant to the webservice's result restaurant list
     */
    public void addRestaurant(Restaurante restaurante) {
        if(result != null && !result.contains(restaurante))
            result.add(restaurante);
    }

    @Override
    /**
     * Removes the first occurance of a restaurant with the specified name in the webservice's result list
     */
    public void removeRestaurant(String name) {
        if(result != null){
            int size = result.size();

            for (int i = 0; i < size; i++){
                if(result.get(i).getName().equals(name)) {
                    result.remove(i);
                    break;
                }
            }
        }
    }

    @Override
    /**
     * Checks if the webservice's result list contains a restaurant with the specified name
     */
    public boolean exists(String restaurantName) {
        if(result == null) return false;

        int size = result.size();
        boolean found = false;

        for(int i = 0; i < size; i++){
            if(result.get(i).getName().equals(restaurantName)){
                found = true;
            }
        }

        return found;
    }

    @Override
    /**
     * Returns the position in the webservice's result list of the first occurance of a restaurant with
     * the specified.
     * If result is null, returns -1
     * If not found, returns -2
     */
    public int getRestaurantId(String name) {
        if(result == null) return -1;

        int size = result.size();
        for(int i = 0; i < size; i ++){
            if(result.get(i).getName().equals(name))
                return i;
        }

        return -2;
    }

    @Override
    /**
     * Returns the first occurance of a restaurant with the specified name in the webservice's result list
     * If result list is null or restaurant is not inside it, returns null
     */
    public Restaurante getRestaurant(String name) {
        if(result == null) return null;

        int size = result.size();
        for(int i = 0; i < size; i++){
            if(result.get(i).getName().equals(name))
                return result.get(i);
        }

        return null;
    }

    @Override
    /**
     * Not yet implemented
     */
    public Restaurante getRestaurant(int id){
        return null;
    }

    @Override
    public void fetchRestaurants(String search) {
        String requestURL = "";
        try {
            requestURL = WS_BASE_URL + "&s=" + URLEncoder.encode(search.trim(), "utf-8");
        } catch (UnsupportedEncodingException e) {}

        searchRestaurants(requestURL);
    }

    @Override
    public void fetchRestaurants(double lat, double lng, int radius) {
        String requestURL = WS_BASE_URL + "&lat=" + Double.toString(lat) + "&lon=" + Double.toString(lng)
                + "&dist=" + Integer.toString(radius);
        searchRestaurants(requestURL);
    }

    @Override
    public List<Restaurante> getResult(){
        return result;
    }

    /**
     *
     * @param context
     */
    private RestaurantsWebService(Context context){
        this.context = context;

        bManager = LocalBroadcastManager.getInstance(context);
    }

    /**
     *
     * @param context
     * @return
     */
    public static synchronized RestaurantsWebService getInstance(Context context){
        if(instance == null){
            instance = new RestaurantsWebService(context);
        }

        return instance;
    }

    //********************************************************************************************//
    /**
     *
     * @param url
     */
    private void searchRestaurants(String url){
        final String NAME = "name";
        final String TYPE = "type";
        final String LOCATION = "location";
        final String ADDRESS = "address";
        final String OPENING = "opening";
        final String CLOSING = "closing";
        final String REVIEW = "review";
        final String DESCRIPTION = "description";
        final List<Restaurante> restaurantList = new ArrayList<>();

        JsonArrayRequest request = new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        result = parseInfo(response);
                        setResultTypes();
                        pendingRequests--;
                        notifyActivityRequestEnded();
                    }

                    private List<Restaurante> parseInfo(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++){
                                JSONObject jObject = (JSONObject) response.get(i);

                                JSONObject location = (JSONObject) jObject.get(LOCATION);

                                Restaurante r = new Restaurante(String.valueOf(jObject.get(NAME)),
                                        String.valueOf(jObject.get(TYPE)),
                                        Double.parseDouble(String.valueOf(location.get("lat"))),
                                        Double.parseDouble(String.valueOf(location.get("lng"))),
                                        String.valueOf(jObject.get(ADDRESS)),
                                        String.valueOf(jObject.get(OPENING)),
                                        String.valueOf(jObject.get(CLOSING)),
                                        Float.parseFloat(String.valueOf(jObject.get(REVIEW))),
                                        String.valueOf(jObject.get(DESCRIPTION)));

                                restaurantList.add(r);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return restaurantList;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        pendingRequests++;
        AppController.getInstance().addToRequestQueue(request, REQ_TAG);
    }

    /**
     *
     */
    private void notifyActivityRequestEnded(){

        Intent endOfRequestIntent;

        if(result.size() == 0){
            endOfRequestIntent = new Intent(SearchActivity.REQUEST_EMPTY_RESULT);
        } else {
            endOfRequestIntent = new Intent(SearchActivity.REQUEST_SUCCESS);
        }

        bManager.sendBroadcast(endOfRequestIntent);
    }

    /**
     * Identifica los diferentes tipos de restaurante en el resultado
     */
    private void setResultTypes(){
        int size = result.size();

        List<String> auxTypes = new ArrayList<>();
        auxTypes.add(0, context.getString(R.string.all));

        int typesSize;

        Restaurante auxRest;
        boolean typeFound = false;

        for(int i = 0; i < size; i++){

            auxRest = result.get(i);

            if (auxTypes.size() == 0){
                auxTypes.add(auxRest.getType());
            } else {
                typesSize = auxTypes.size();

                for(int j = 0; j < typesSize; j++){
                    if(auxRest.getType().equals(auxTypes.get(j))){
                        typeFound = true;
                    }
                }

                if(!typeFound){
                    auxTypes.add(auxRest.getType());
                }
            }
        }
        typesInResult = auxTypes.toArray(new String[0]);
    }

    /**
     * @return Los diferentes tipos de restaurante que hay en el resultado de la request
     */
    public String[] getRestaurantTypes(){
        return typesInResult;
    }
}
