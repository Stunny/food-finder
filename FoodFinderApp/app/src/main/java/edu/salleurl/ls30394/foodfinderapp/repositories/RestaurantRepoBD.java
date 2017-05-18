package edu.salleurl.ls30394.foodfinderapp.repositories;

import java.util.List;

import edu.salleurl.ls30394.foodfinderapp.model.Restaurante;



public interface RestaurantRepoBD {
    void addRestaurant(String name,int userId);
    void removeRestaurant(String name, int userId);
    //void updateRestaurant(Restaurante restaurante);
    boolean existRestaurant(String name,int userID);
    //List<Restaurante> getRestaurant(String name);
    List<String> getAllRestaurant();

}
