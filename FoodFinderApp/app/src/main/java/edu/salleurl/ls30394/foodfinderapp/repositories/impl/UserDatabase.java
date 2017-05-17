package edu.salleurl.ls30394.foodfinderapp.repositories.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.salleurl.ls30394.foodfinderapp.model.User;
import edu.salleurl.ls30394.foodfinderapp.repositories.UserRepo;
import edu.salleurl.ls30394.foodfinderapp.utils.Database;

/**
 * Created by Admin on 28/04/2017.
 */

public class UserDatabase implements UserRepo {
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


    @Override
    public void addUser(User u){

        //instancia de la base de datos para poder acceder a la misma
        Database database = Database.getInstance(context);
        //asignamos los valores de las variables a inserir en la base de datos
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, u.getUserName());
        contentValues.put(COLUMN_SURNAME, u.getUserSurname());
        contentValues.put(COLUMN_EMAIL, u.getUserMail());
        contentValues.put(COLUMN_PASSWORD, u.getUserPassword());
        contentValues.put(COLUMN_DESCRIPTION, u.getUserDescription());
        contentValues.put(COLUMN_GENDER, u.getGenderIndex());

        //inserimos los datos a la base de datos. query del INSERT INTO
        database.getWritableDatabase().insert(TABLE_NAME, null, contentValues);
    }
    @Override
    public void removeUser(User u){
        //se necesita esta funcionalidad?
    }
    @Override
    public void updateUser(User u){
        //solo hace update del user, comprobación de que si existe o no el usuario se hace en la
        //actividad

        Database database = Database.getInstance(context);

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, u.getUserName());
        contentValues.put(COLUMN_SURNAME, u.getUserSurname());
        contentValues.put(COLUMN_EMAIL, u.getUserMail());
        contentValues.put(COLUMN_PASSWORD, u.getUserPassword());
        contentValues.put(COLUMN_DESCRIPTION, u.getUserDescription());
        contentValues.put(COLUMN_GENDER, u.getGenderIndex());



        // Preparamos la cláusula del where. Su formato es: "<nombre columna> = ?" donde ? se
        // sustituirá por el valor añadido en los argumentos.
        String whereClause = COLUMN_NAME + "=? and "  + COLUMN_PASSWORD + "=?";
        String[] whereArgs = {u.getUserName(),u.getUserPassword()};

        database.getWritableDatabase().update(TABLE_NAME,contentValues,whereClause,whereArgs);
        // El update anterior equivale a la query SQL:
        // update TABLE_NAME set COLUMN_NAME=p.getName(), COLUMN_SURNAME=p.getSurname(),
        // COLUMN_AGE=p.getAge() where COLUMN_NAME=p.getName() and COLUMN_SURNAME=p.getSurname();



    }
    @Override
    public boolean existsUser(String name, String surname, String email, String password){
        //instancia de la base de datos para poder acceder a la misma
        Database database = Database.getInstance(context);
        //query del WHERE
        String whereClause = COLUMN_NAME + "=? and " + COLUMN_SURNAME + "=? and " +
                COLUMN_EMAIL + "=? and " + COLUMN_PASSWORD + "=?";
        String[] whereArgs = {name, surname, email, password};

        SQLiteDatabase sqLiteOpenHelper = database.getReadableDatabase();

        long count = DatabaseUtils.queryNumEntries(sqLiteOpenHelper, TABLE_NAME, whereClause, whereArgs);

        Log.i("angelTest", String.valueOf(count));

        return count>0;//es lo mismo que el if que habia antes

    }
    @Override
    public List<User> getUser(String name, String password,boolean email){
        List<User> users = new ArrayList<>();
        //Retorna el usuario con el nombre y apellido especificado, se ha echo en caso de que no se
        //puede dar el caso de dos usuarios con el mismo nombre

        Database database = Database.getInstance(context);
        User user = null;
        // Preparamos las columnas que queremos seleccionar. En este caso usaremos NULL para
        // indicar que queremos recuperar la fila entera.
        String[] selectColumns = null;

        // Preparamos la cláusula del where. Su formato es: "<nombre columna> = ?" donde ? se
        // sustituirá por el valor añadido en los argumentos.
        String whereClause;
        if(email){
            whereClause = COLUMN_NAME + "=? and " + COLUMN_EMAIL + "=?";

        }else{
            whereClause = COLUMN_NAME + "=? and " + COLUMN_PASSWORD + "=?";
        }

        String[] whereArgs = {name,password};

        Cursor cursor = database.getReadableDatabase().
                query(TABLE_NAME, selectColumns, whereClause, whereArgs, null, null, null);
        // La query equivale en SQL a:
        // select * from TABLE_NAME where COLUMN_NAME=name and COLUMN_SURNAME=surname;
        // Los 3 nulls del final equivalen al GROUPBY, HAVING y ORDERBY.

        // Comprobamos que se nos haya devuelto un cursor válido.
        if (cursor != null) {
            // Movemos el cursor a la primera instancia. Si el cursor está vacío, devolverá falso.
            if (cursor.moveToFirst()) {
                do {
                    String userName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                    String userSurname = cursor.getString(cursor.getColumnIndex(COLUMN_SURNAME));
                    String userMail = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
                    String userPassword = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
                    String userDescription = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                    int userGender = cursor.getInt(cursor.getColumnIndex(COLUMN_GENDER));

                    user = new User(userName,userSurname,userMail,userPassword,userGender,userDescription);
                    users.add(user);

                } while (cursor.moveToNext());
            }
            //Cerramos el cursor al terminar.
            cursor.close();
        }

        return users;


    }

    @Override
    public List<User> getAllUsers() {

        List<User> list = new ArrayList<>();

        Database database = Database.getInstance(context);

        // Preparamos las columnas que queremos seleccionar. En este caso usaremos NULL para
        // indicar que queremos recuperar la fila entera.
        String[] selectColumns = null;

        Cursor cursor = database.getReadableDatabase().
                query(TABLE_NAME, selectColumns, null, null, null, null, null);
        // La query equivale en SQL a:
        // select * from TABLE_NAME;

        // Comprobamos que se nos haya devuelto un cursor válido.
        if (cursor != null) {
            // Movemos el cursor a la primera instancia. Si el cursor está vacío, devolverá falso.
            if (cursor.moveToFirst()) {
                do {
                    String userName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                    String userSurname = cursor.getString(cursor.getColumnIndex(COLUMN_SURNAME));
                    String userMail = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
                    String userPassword = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
                    String userDescription = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                    int userGender = cursor.getInt(cursor.getColumnIndex(COLUMN_GENDER));

                    User user = new User(userName,userSurname,userMail,userPassword,userGender,userDescription);

                    list.add(user);

                } while (cursor.moveToNext());
            }
            //Cerramos el cursor al terminar.
            cursor.close();
        }
        return list;
    }

}
