package edu.salleurl.ls30394.foodfinderapp.repositories;


import java.util.List;
import edu.salleurl.ls30394.foodfinderapp.model.Restaurante;

public interface RestaurantDBRepo {

    int getRestaurantId(String name);

    void addRestaurant(Restaurante restaurante);

    void removeRestaurant(String name);

    boolean existRestaurant(String name);

    Restaurante getRestaurant(String name);

}
