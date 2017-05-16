package edu.salleurl.ls30394.foodfinderapp.repositories.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.salleurl.ls30394.foodfinderapp.R;
import edu.salleurl.ls30394.foodfinderapp.model.Restaurante;
import edu.salleurl.ls30394.foodfinderapp.repositories.RestaurantRepoBD;
import edu.salleurl.ls30394.foodfinderapp.utils.Database;


public class RestaurantDataBase implements RestaurantRepoBD {

    private static final String TABLE_NAME = "restaurant";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_LAT = "lat";
    private static final String COLUMN_LNG = "lng";
    private static final String COLUMN_OP = "openingTime";
    private static final String COLUMN_CLOSE = "closingTime";
    private static final String COLUMN_RATING = "rating";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_TYPE = "type";

    private Context context;

    public RestaurantDataBase(Context context){
        this.context = context;
    }

    @Override
    public void addRestaurant(Restaurante restaurante) {
        //instancia de la base de datos para poder acceder a la misma
        Database database = Database.getInstance(context);
        //asignamos los valores de las variables a inserir en la base de datos
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, restaurante.getName());
        contentValues.put(COLUMN_ADDRESS, restaurante.getAddress());
        contentValues.put(COLUMN_LAT, restaurante.getLatitude());
        contentValues.put(COLUMN_LNG, restaurante.getLongitude());
        contentValues.put(COLUMN_OP,restaurante.getOpeningTime());
        contentValues.put(COLUMN_CLOSE,restaurante.getClosingTime());
        contentValues.put(COLUMN_RATING,restaurante.getReview());
        contentValues.put(COLUMN_DESCRIPTION,restaurante.getDescription());
        contentValues.put(COLUMN_TYPE,restaurante.getType());


        //inserimos los datos a la base de datos. query del INSERT INTO
        database.getWritableDatabase().insert(TABLE_NAME, null, contentValues);
    }

    @Override
    public void removeRestaurant(Restaurante restaurante) {

    }

    @Override
    public void updateRestaurant(Restaurante restaurante) {
        //instancia de la base de datos para poder acceder a la misma
        Database database = Database.getInstance(context);
        //asignamos los valores de las variables a inserir en la base de datos
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, restaurante.getName());
        contentValues.put(COLUMN_ADDRESS, restaurante.getAddress());
        contentValues.put(COLUMN_LAT, restaurante.getLatitude());
        contentValues.put(COLUMN_LNG, restaurante.getLongitude());
        contentValues.put(COLUMN_OP,restaurante.getOpeningTime());
        contentValues.put(COLUMN_CLOSE,restaurante.getClosingTime());
        contentValues.put(COLUMN_RATING,restaurante.getReview());
        contentValues.put(COLUMN_DESCRIPTION,restaurante.getDescription());
        contentValues.put(COLUMN_TYPE,restaurante.getType());

        // Preparamos la cláusula del where. Su formato es: "<nombre columna> = ?" donde ? se
        // sustituirá por el valor añadido en los argumentos.
        String whereClause = COLUMN_NAME + "=? and " + COLUMN_ADDRESS + "=? and " + COLUMN_LAT + "=? and " +
                COLUMN_LNG + "=? and " +  COLUMN_OP + "=? and " +COLUMN_CLOSE+ "=? and " + COLUMN_RATING
                + "=? and "+ COLUMN_DESCRIPTION + "=? and " +COLUMN_TYPE+ "=?";
        String[] whereArgs = {restaurante.getName(),restaurante.getAddress(),
                String.valueOf(restaurante.getLatitude()),String.valueOf(restaurante.getLongitude()),
        restaurante.getOpeningTime(),restaurante.getClosingTime(),String.valueOf(restaurante.getReview()),
                restaurante.getDescription()};

        database.getWritableDatabase().update(TABLE_NAME,contentValues,whereClause,whereArgs);
        // El update anterior equivale a la query SQL:
        // update TABLE_NAME set COLUMN_NAME=p.getName(), COLUMN_SURNAME=p.getSurname(),
        // COLUMN_AGE=p.getAge() where COLUMN_NAME=p.getName() and COLUMN_SURNAME=p.getSurname();

    }

    @Override
    public boolean existRestaurant(String name,String address) {

        //instancia de la base de datos para poder acceder a la misma
        Database database = Database.getInstance(context);
        //query del WHERE
        String whereClause = COLUMN_NAME + "=? and " + COLUMN_ADDRESS + "=?";
        String[] whereArgs = {name,address};
        SQLiteDatabase sqLiteOpenHelper = database.getReadableDatabase();
        long count =
                DatabaseUtils.queryNumEntries(sqLiteOpenHelper, TABLE_NAME, whereClause, whereArgs);


        return count>0;
    }

    @Override
    public List<Restaurante> getRestaurant(String name) {
        List<Restaurante> list = new ArrayList<>();

        Database database =  Database.getInstance(context);



        String[] selectColumns = null;

        String whereClause = COLUMN_NAME + "=?";

        String[] whereArgs = {name};

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
                    String nameRestaurant = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                    String address = cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS));
                    float lat = cursor.getFloat(cursor.getColumnIndex(COLUMN_LAT));
                    float lng = cursor.getFloat(cursor.getColumnIndex(COLUMN_LNG));
                    String openingTime = cursor.getString(cursor.getColumnIndex(COLUMN_OP));
                    String closingTime = cursor.getString(cursor.getColumnIndex(COLUMN_CLOSE));
                    float rating = cursor.getFloat(cursor.getColumnIndex(COLUMN_RATING));
                    String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                    String type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE));

                    Restaurante restaurante =
                            new Restaurante(nameRestaurant,address,lat,lng,openingTime,closingTime,type,rating,description);

                    list.add(restaurante);

                } while (cursor.moveToNext());
            }
            //Cerramos el cursor al terminar.
            cursor.close();
        }

        return list;
    }

    @Override
    public List<Restaurante> getAllRestaurant() {

        List<Restaurante> list = new ArrayList<>();

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
                    String address = cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS));
                    float lat = cursor.getFloat(cursor.getColumnIndex(COLUMN_LAT));
                    float lng = cursor.getFloat(cursor.getColumnIndex(COLUMN_LNG));
                    String openingTime = cursor.getString(cursor.getColumnIndex(COLUMN_OP));
                    String closingTime = cursor.getString(cursor.getColumnIndex(COLUMN_CLOSE));
                    float rating = cursor.getFloat(cursor.getColumnIndex(COLUMN_RATING));
                    String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                    String type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE));

                    Restaurante restaurante =
                            new Restaurante(name,address,lat,lng,openingTime,closingTime,type,rating,description);

                    list.add(restaurante);

                } while (cursor.moveToNext());
            }
            //Cerramos el cursor al terminar.
            cursor.close();
        }
        return list;
    }
}
