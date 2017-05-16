package edu.salleurl.ls30394.foodfinderapp.repositories;

import java.util.List;

import edu.salleurl.ls30394.foodfinderapp.model.User;


public interface UserRepo {

    void addUser(User u);
    void removeUser(User u);
    void updateUser(User u);
    boolean existsUser(String name, String surname, String email, String password);
    List<User> getUser(String name, String password,boolean email);
    List<User> getAllUsers();
}
