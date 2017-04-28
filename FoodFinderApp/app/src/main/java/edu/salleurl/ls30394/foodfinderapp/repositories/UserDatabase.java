package edu.salleurl.ls30394.foodfinderapp.repositories;

import android.content.Context;

import edu.salleurl.ls30394.foodfinderapp.model.User;

/**
 * Created by Admin on 28/04/2017.
 */

public class UserDatabase {
    // Contantes con los nombres de la tabla y de las columnas de la tabla.
    private static final String TABLE_NAME = "user";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_SURNAME = "surname";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_DESCRIPTION = "description";

    private Context context;


    public UserDatabase(Context context){
        this.context = context;
    }

    public void addUser(User u){
        //TODO: meter al usuario en la base de datos (registro)
    }

    public void removeUser(User u){
        //se necesita esta funcionalidad?
    }

    public void updateUser(User u){
        //TODO: buscar con el existUser y getUser y modificar los datos del usuario (profile changes)
    }

    public void existsUser(User u){
        //TODO: buscar en la base de datos si existe el usuario
    }

    public void getUser(User u){
        //TODO: retornar el usuario
    }

}
