package edu.salleurl.ls30394.foodfinderapp.repositories.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import edu.salleurl.ls30394.foodfinderapp.repositories.RecentSearchesRepo;
import edu.salleurl.ls30394.foodfinderapp.utils.Database;

/**
 * Created by avoge on 20/05/2017.
 */

public class RecentSearchesDB implements RecentSearchesRepo {

    private static final String TABLE_NAME = "recent_searches";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_SEARCH_QUERY = "search_query";
    private static final String COLUMN_USER_ID = "userId";

    private Context context;

    public RecentSearchesDB(Context context){
        this.context = context;
    }

    @Override
    public void addRecentSearch(int userId, String searchQuery) {
        Database db = Database.getInstance(context);

        ContentValues values = new ContentValues();
        values.put(COLUMN_SEARCH_QUERY, searchQuery);
        values.put(COLUMN_USER_ID, userId);

        db.getWritableDatabase().insert(TABLE_NAME, null, values);
    }

    @Override
    public String[] getRecentSearches(int userId) {
        List<String> list = new ArrayList<>();

        String auxRow = null;

        Database db = Database.getInstance(context);
        String[] selectColumns = null;
        String orderBy = COLUMN_ID;

        String whereClause = COLUMN_USER_ID + "=?";
        String[] whereArgs = {Integer.toString(userId)};

        Cursor cursor = db.getReadableDatabase()
                .query(TABLE_NAME, selectColumns, whereClause, whereArgs, null, null, orderBy);

        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    list.add(
                            cursor.getString(cursor.getColumnIndex(COLUMN_SEARCH_QUERY))
                    );
                }while(cursor.moveToNext());
            }
            cursor.close();
        }

        return list.toArray(new String[0]);
    }

    @Override
    public void removeRecentSearch(int userId, String searchQuery) {

    }
}
