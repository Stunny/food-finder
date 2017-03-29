package edu.salleurl.ls30394.foodfinderapp;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout tilUsername;

    private TextInputLayout tilPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        configWidgets();
    }

    private void configWidgets() {

        initWidgets();

        tilUsername.setHint(getString(R.string.username));
        tilPassword.setHint(getString(R.string.password));

    }

    private void initWidgets() {

        tilUsername = (TextInputLayout)findViewById(R.id.tilUsername);
        tilPassword = (TextInputLayout)findViewById(R.id.tilPassword);

    }
}
