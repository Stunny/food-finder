package edu.salleurl.ls30394.foodfinderapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import edu.salleurl.ls30394.foodfinderapp.R;

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

    public void OnImageSelect(View view) {

        //TODO: image select functionality

    }

    public void OnRegisterComplete(View view) {

        //TODO: comprobacion de campos de registro
        OnRegisterSuccess();
    }

    public void OnRegisterSuccess(){
        //TODO: almacenar datos de usuario

        //TODO: change MainActivity by first app activity
        nextActivity = new Intent(RegisterActivity.this, SearchActivity.class);
        startActivity(nextActivity);
        finish();
    }
}
