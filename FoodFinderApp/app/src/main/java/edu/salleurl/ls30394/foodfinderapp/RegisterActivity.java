package edu.salleurl.ls30394.foodfinderapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RegisterActivity extends AppCompatActivity {

    private Intent nextActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    public void onBackPressed() {
        nextActivity = new Intent(RegisterActivity.this, MainActivity.class);

        startActivity(nextActivity);
        finish();
    }
}
