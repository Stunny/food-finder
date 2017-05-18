package edu.salleurl.ls30394.foodfinderapp.repositories.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import edu.salleurl.ls30394.foodfinderapp.R;
import edu.salleurl.ls30394.foodfinderapp.model.Restaurante;
import edu.salleurl.ls30394.foodfinderapp.repositories.RestaurantRepoBD;
import edu.salleurl.ls30394.foodfinderapp.utils.Database;


public class RestaurantDataBase implements RestaurantRepoBD {

    private static final String TABLE_NAME = "restaurant";
    private static final String COLUMN_USERID = "userId";
    private static final String COLUMN_NAME = "restaurantName";

    private Context context;

    public RestaurantDataBase(Context context){
        this.context = context;
    }

    @Override
    public void addRestaurant(String name, int userId) {
        //instancia de la base de datos para poder acceder a la misma
        Database database = Database.getInstance(context);
        //asignamos los valores de las variables a inserir en la base de datos
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_USERID,userId);

        //inserimos los datos a la base de datos. query del INSERT INTO
        database.getWritableDatabase().insert(TABLE_NAME, null, contentValues);
    }

    @Override
    public void removeRestaurant(String name,int userID) {
        Database database  = Database.getInstance(context);

        String whereClause = COLUMN_USERID + "=? and "+ COLUMN_NAME + "=?";
        String [] whereArgs = {String.valueOf(userID),name};

        database.getWritableDatabase().delete(TABLE_NAME,whereClause,whereArgs);

    }

    @Override
    public boolean existRestaurant(String name,int userID) {

        //instancia de la base de datos para poder acceder a la misma
        Database database = Database.getInstance(context);
        //query del WHERE
        String whereClause = COLUMN_USERID + "=? and " + COLUMN_NAME + "=?";
        String[] whereArgs = {String.valueOf(userID),name};
        SQLiteDatabase sqLiteOpenHelper = database.getReadableDatabase();
        long count =
                DatabaseUtils.queryNumEntries(sqLiteOpenHelper, TABLE_NAME, whereClause, whereArgs);


        return count>0;
    }


    @Override
    public List<String> getAllRestaurant() {

        List<String> list = new ArrayList<>();

        Database database = Database.getInstance(context);

        String[] selectColumns = null;

        Cursor cursor = database.getReadableDatabase()
                .query(TABLE_NAME,selectColumns,null,null,null,null,null);

        // La query equivale en SQL a:
        // select * from TABLE_NAME;

        // Comprobamos que se nos haya devuelto un cursor válido.
        if (cursor != null) {
            // Movemos el cursor a la primera instancia. Si el cursor está vacío, devolverá falso.
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                    list.add(name);

                } while (cursor.moveToNext());
            }
            //Cerramos el cursor al terminar.
            cursor.close();
        }
        return list;
    }
}
