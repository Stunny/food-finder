package edu.salleurl.ls30394.foodfinderapp.repositories;

import java.util.LinkedList;

/**
 * Created by David GN on 20/05/2017.
 */

public interface FavoriteRepo {
    void addRestaurant(int user_id,int restaurant_id);
    void removeRestaurant(int restaurant_id,int user_id);
    int[] getAllRestaurants(int user_id);
}
