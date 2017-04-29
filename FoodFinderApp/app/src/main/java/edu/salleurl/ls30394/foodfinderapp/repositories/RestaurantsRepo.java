package edu.salleurl.ls30394.foodfinderapp.repositories;

import java.util.List;

import edu.salleurl.ls30394.foodfinderapp.model.Restaurante;

/**
 * Created by avoge on 29/04/2017.
 */

public interface RestaurantsRepo {

    List<Restaurante> getRestaurants(String search);

    List<Restaurante> getRestaurants(double lat, double lng, int radius);

}
