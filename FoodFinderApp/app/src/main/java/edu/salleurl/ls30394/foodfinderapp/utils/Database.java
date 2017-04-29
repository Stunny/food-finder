package edu.salleurl.ls30394.foodfinderapp.utils;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import edu.salleurl.ls30394.foodfinderapp.R;

/**
 * Created by Admin on 28/04/2017.
 */

public class Database extends SQLiteOpenHelper {
    private Context context;
    private static Database instance;
    private static final String name = "users_db";
    private static final int version = 1;
    private static SQLiteDatabase.CursorFactory factory;

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    public static Database getInstance(Context context) {
        if (instance == null) {
            instance = new Database(context, name, factory, version);
        }
        instance.context = context;

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("angel", "DBCreation");
        executeSQLScript(db, R.raw.database_creation);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO: Implementar los cambios cuando el usuario modifica su perfil
        Log.i("angel", "updateDone");
        // Destruir DB.
        executeSQLScript(db, R.raw.database_removal);
        // Crear DB.
        executeSQLScript(db, R.raw.database_creation);
    }

    private void executeSQLScript(SQLiteDatabase database, int scriptFile) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte buf[] = new byte[1024];
        int len;
        InputStream inputStream = null;

        try{
            inputStream = context.getResources().openRawResource(scriptFile);
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();

            String[] createScript = outputStream.toString().split(";");
            for (int i = 0; i < createScript.length; i++) {
                String sqlStatement = createScript[i].trim();
                // TODO You may want to parse out comments here
                if (sqlStatement.length() > 0) {
                    database.execSQL(sqlStatement + ";");
                }
            }
        } catch (IOException e) {
            // TODO Handle Script Failed to Load
        } catch (SQLException e) {
            // TODO Handle Script Failed to Execute
        }
    }

}
