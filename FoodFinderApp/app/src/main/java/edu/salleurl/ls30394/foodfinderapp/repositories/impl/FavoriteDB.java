package edu.salleurl.ls30394.foodfinderapp.repositories.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;

import java.util.ArrayList;
import java.util.List;

import edu.salleurl.ls30394.foodfinderapp.model.Restaurante;
import edu.salleurl.ls30394.foodfinderapp.repositories.FavoriteRepo;
import edu.salleurl.ls30394.foodfinderapp.utils.Database;

/**
 * Created by David GN on 20/05/2017.
 */

public class FavoriteDB implements FavoriteRepo {

    private static final String TABLE_NAME = "favorite_restaurants";
    private static final String COLUMN_USER = "userID";
    private static final String COLUMN_RESTAURANT = "restaurantId";
    private Context context;

    public FavoriteDB (Context context){
        this.context = context;
    }

    @Override
    public void addFavorite(int userId, int restaurantId) {
        Database database = Database.getInstance(context);

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_USER, userId);
        contentValues.put(COLUMN_RESTAURANT, restaurantId);

        database.getWritableDatabase().insert(TABLE_NAME, null, contentValues);

    }

    @Override
    public void removeFavorite(int restaurantId, int userId) {
        Database db = Database.getInstance(context);

        String whereClause = COLUMN_USER + "=? AND "+COLUMN_RESTAURANT +"=?";
        String[] whereArgs = {Integer.toString(restaurantId), Integer.toString(userId)};

        db.getWritableDatabase().delete(TABLE_NAME, whereClause, whereArgs);
    }

    @Override
    public List<Restaurante> getAllFavorites(int userId) {
        Database db = Database.getInstance(context);
        RestaurantDataBase rdb = new RestaurantDataBase(context);

        List<Restaurante> auxList = new ArrayList<>();

        String[] selectColumns = null;

        String whereClause = COLUMN_USER + "=?";
        String[] whereArgs = {Integer.toString(userId)};

        Cursor cursor = db.getReadableDatabase()
                .query(true, TABLE_NAME, selectColumns, whereClause, whereArgs, null, null, null, null);

        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    auxList.add(rdb.getRestaurant(cursor.getInt(cursor.getColumnIndex(COLUMN_RESTAURANT))));
                }while(cursor.moveToNext());
            }
            cursor.close();
        }
        return auxList;
    }

    @Override
    public boolean exists(int userId, int restaurantId) {
        Database db = Database.getInstance(context);

        String whereClause = COLUMN_USER + "=? AND "+COLUMN_RESTAURANT + "=?";
        String[] whereArgs = {Integer.toString(userId), Integer.toString(restaurantId)};

        long count = DatabaseUtils.queryNumEntries(
                db.getReadableDatabase(),
                TABLE_NAME,
                whereClause,
                whereArgs
        );

        return count > 0;
    }
}
