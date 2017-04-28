package edu.salleurl.ls30394.foodfinderapp.activities;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import edu.salleurl.ls30394.foodfinderapp.R;

public class SearchActivity extends AppCompatActivity {
    private Intent nextActivity;
    private EditText searchField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchField = (EditText) findViewById(R.id.search_field);
        configWidgets();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_search_menu, menu);
        return true;
    }

    private void configWidgets() {
        initWidgets();
    }

    private void initWidgets() {

    }

    public void clearSearch(View view){
        searchField.setText("");
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
