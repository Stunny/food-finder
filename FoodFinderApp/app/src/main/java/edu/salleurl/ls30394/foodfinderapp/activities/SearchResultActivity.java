package edu.salleurl.ls30394.foodfinderapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.salleurl.ls30394.foodfinderapp.R;

public class SearchResultActivity extends AppCompatActivity {
    private Intent nextActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
    }

    @Override
    public void onBackPressed() {
        nextActivity = new Intent(SearchResultActivity.this, SearchActivity.class);

        startActivity(nextActivity);
        finish();
    }
}
