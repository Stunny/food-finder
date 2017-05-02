package edu.salleurl.ls30394.foodfinderapp.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import edu.salleurl.ls30394.foodfinderapp.model.User;
import edu.salleurl.ls30394.foodfinderapp.utils.Database;

/**
 * Created by Admin on 28/04/2017.
 */

public class UserDatabase {
    // Contantes con los nombres de la tabla y de las columnas de la tabla.
    private static final String TABLE_NAME = "userInfo";
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
        //instancia de la base de datos para poder acceder a la misma
        Database database = Database.getInstance(context);
        //asignamos los valores de las variables a inserir en la base de datos
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, u.getUserName());
        contentValues.put(COLUMN_SURNAME, u.getUserSurname());
        contentValues.put(COLUMN_EMAIL, u.getUserMail());
        contentValues.put(COLUMN_PASSWORD, u.getUserPassword());
        contentValues.put(COLUMN_GENDER, u.getGenderIndex());
        contentValues.put(COLUMN_DESCRIPTION, u.getUserDescription());
        //inserimos los datos a la base de datos. query del INSERT INTO
        database.getWritableDatabase().insert(TABLE_NAME, null, contentValues);
    }

    public void removeUser(User u){
        //se necesita esta funcionalidad?
    }

    public void updateUser(User u){
        //TODO: buscar con el existUser y getUser y modificar los datos del usuario (profile changes)
    }

    public boolean existsUser(String name, String surname, String email, String password){
        //TODO: buscar en la base de datos si existe el usuario
        //instancia de la base de datos para poder acceder a la misma
        Database database = Database.getInstance(context);
        //query del WHERE
        String whereClause = COLUMN_NAME + "=? and " + COLUMN_SURNAME + "=? and " + COLUMN_EMAIL + "=? and " + COLUMN_PASSWORD + "=?";
        String[] whereArgs = {name, surname, email, password};

        SQLiteDatabase sqLiteOpenHelper = database.getReadableDatabase();

        long count = DatabaseUtils.queryNumEntries(sqLiteOpenHelper, TABLE_NAME, whereClause, whereArgs);

        Log.i("angelTest", String.valueOf(count));

        if (count > 0){
            return true;
        }else{
            return false;
        }

    }

    public void getUser(User u){
        //TODO: retornar el usuario
    }

}
