package edu.salleurl.ls30394.foodfinderapp.repositories;

import java.util.List;

import edu.salleurl.ls30394.foodfinderapp.model.Restaurante;



public interface RestaurantRepoBD {
    void addRestaurant(Restaurante restaurante);
    void removeRestaurant(Restaurante restaurante);
    void updateRestaurant(Restaurante restaurante);
    boolean existRestaurant(String name,String address);
    List<Restaurante> getRestaurant(String name);
    List<Restaurante> getAllRestaurant();

}
