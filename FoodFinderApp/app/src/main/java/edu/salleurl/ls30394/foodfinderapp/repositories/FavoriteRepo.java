package edu.salleurl.ls30394.foodfinderapp.repositories;

import java.util.List;

import edu.salleurl.ls30394.foodfinderapp.model.Restaurante;

/**
 * Created by David GN on 20/05/2017.
 */

public interface FavoriteRepo {

    void addFavorite(int userId, int restaurantId);

    void removeFavorite(int restaurantId, int userId);

    List<Restaurante> getAllFavorites(int userId);

    boolean exists(int userId, int restaurantId);
}
