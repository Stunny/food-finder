package edu.salleurl.ls30394.foodfinderapp.repositories.impl;

import android.content.Context;

import edu.salleurl.ls30394.foodfinderapp.repositories.FavoriteRepo;
import edu.salleurl.ls30394.foodfinderapp.utils.Database;

/**
 * Created by David GN on 20/05/2017.
 */

public class FavoriteDB implements FavoriteRepo {

    private static final String TABLE_NAME = "favorite_restaurants";
    private static final String COLUMN_USER = "userID";
    private static final String COLUMN_RESTAURANT = "restaurantId";
    private Context context;

    public FavoriteDB (Context context){
        this.context = context;
    }

    @Override
    public void addRestaurant(int user_id, int restaurant_id) {
        Database database = Database.getInstance(context);



    }

    @Override
    public void removeRestaurant(int restaurant_id, int user_id) {

    }

    @Override
    public int[] getAllRestaurants(int user_id) {
        return new int[0];
    }
}
