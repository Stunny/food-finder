package edu.salleurl.ls30394.foodfinderapp.repositories.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import edu.salleurl.ls30394.foodfinderapp.model.Restaurante;
import edu.salleurl.ls30394.foodfinderapp.repositories.RestaurantDBRepo;
import edu.salleurl.ls30394.foodfinderapp.utils.Database;

/**
 * Created by David GN on 20/05/2017.
 */

public class RestaurantDataBase implements RestaurantDBRepo {


    private static final String TABLE_NAME  = "restaurant";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_LAT  = "lat";
    private static final String COLUMN_LNG = "lng";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_OPENING = "opening";
    private static final String COLUMN_CLOSING = "closing";
    private static final String COLUMN_REVIEW = "review";
    private static final String COLUMN_DESCRIPTION = "description";

    private Context context;

    public RestaurantDataBase (Context context){
        this.context = context;
    }
    @Override
    public int getRestaurantId(String name){
        int restaurant_id = -1;

        Database database = Database.getInstance(context);

        String[] selectColumns = null;

        String whereClause = COLUMN_ID + "=?";

        String [] whereArgs = {name};

        Cursor cursor = database.getReadableDatabase()
                .query(TABLE_NAME,selectColumns,whereClause,whereArgs,null,null,null);

        if(cursor != null){
            if(cursor.moveToFirst()){
                restaurant_id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            }
            cursor.close();
        }
        return restaurant_id;
    }

    @Override
    public void addRestaurant(Restaurante restaurante) {
        Database database = Database.getInstance(context);

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME,restaurante.getName());
        contentValues.put(COLUMN_TYPE,restaurante.getType());
        contentValues.put(COLUMN_LAT,restaurante.getLatitude());
        contentValues.put(COLUMN_LNG,restaurante.getLongitude());
        contentValues.put(COLUMN_ADDRESS,restaurante.getAddress());
        contentValues.put(COLUMN_OPENING,restaurante.getOpeningTime());
        contentValues.put(COLUMN_CLOSING,restaurante.getClosingTime());
        contentValues.put(COLUMN_REVIEW,restaurante.getReview());
        contentValues.put(COLUMN_DESCRIPTION,restaurante.getDescription());

        database.getWritableDatabase().insert(TABLE_NAME,null,contentValues);

    }

    @Override
    public void removeRestaurant(String name) {
        Database database = Database.getInstance(context);

        String whereClause = COLUMN_NAME + "=?";
        String[] whereArgs = {name};

        database.getWritableDatabase().delete(TABLE_NAME,whereClause,whereArgs);

    }

    @Override
    public boolean existRestaurant(String name) {
        Database database = Database.getInstance(context);

        String whereClause = COLUMN_NAME + "=?";
        String [] whereArgs = {name};

        SQLiteDatabase db = database.getReadableDatabase();

        long count = DatabaseUtils.queryNumEntries(db,TABLE_NAME,whereClause,whereArgs);

        return count>0;
    }

    @Override
    public Restaurante getRestaurant(String name){

        Restaurante restaurante = null;

        Database database = Database.getInstance(context);

        String[] selectColumns = null;

        String whereClause = COLUMN_ID + "=?";

        String[] whereArgs = {name};

        Cursor cursor = database.getReadableDatabase()
                .query(TABLE_NAME,selectColumns,whereClause,whereArgs,null,null,null);

        if(cursor != null){
            if(cursor.moveToFirst()){
                String nameRest = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE));
                float lat = cursor.getFloat(cursor.getColumnIndex(COLUMN_LAT));
                float lng = cursor.getFloat(cursor.getColumnIndex(COLUMN_LNG));
                String address = cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS));
                String opening = cursor.getString(cursor.getColumnIndex(COLUMN_OPENING));
                String closing = cursor.getString(cursor.getColumnIndex(COLUMN_CLOSING));
                float review = cursor.getFloat(cursor.getColumnIndex(COLUMN_REVIEW));
                String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                restaurante = new Restaurante(nameRest,type,lat,lng,address,opening,closing,review,description);
            }
            cursor.close();
        }
        return restaurante;
    }
}
