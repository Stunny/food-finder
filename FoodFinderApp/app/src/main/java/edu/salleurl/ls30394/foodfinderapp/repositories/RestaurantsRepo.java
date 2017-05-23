package edu.salleurl.ls30394.foodfinderapp.repositories;

import java.util.List;

import edu.salleurl.ls30394.foodfinderapp.model.Restaurante;

/**
 * Created by avoge on 29/04/2017.
 */

public interface RestaurantsRepo {

    void addRestaurant(Restaurante restaurante);

    void removeRestaurant(String name);

    boolean exists(String restaurantName);

    int getRestaurantId(String name);

    Restaurante getRestaurant(String name);

    Restaurante getRestaurant(int id);

    void fetchRestaurants(String search);

    void fetchRestaurants(double lat, double lng, int radius);

    List<Restaurante> getResult();

}
