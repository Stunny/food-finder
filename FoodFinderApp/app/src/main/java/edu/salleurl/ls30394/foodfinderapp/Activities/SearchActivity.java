package edu.salleurl.ls30394.foodfinderapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import edu.salleurl.ls30394.foodfinderapp.R;

public class SearchActivity extends AppCompatActivity {
    private Intent nextActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        configWidgets();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_search_menu, menu);
        return true;
    }

    private void configWidgets() {
        initWidgets();

        getSupportActionBar().setTitle("");
    }

    private void initWidgets() {
        setSupportActionBar((Toolbar) findViewById(R.id.searchactivity_toolbar));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.activity_search_goProfile:
                Log.i("angel: ", "profile selected");
                nextActivity = new Intent(this, ProfileActivity.class);
                startActivity(nextActivity);
                finish();
                return true;

            case R.id.activity_search_goFavorites:
                Log.i("angel: ", "Favourite selected");
                return true;

            default:
            return super.onOptionsItemSelected(item);
        }
    }

}
