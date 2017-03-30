package edu.salleurl.ls30394.foodfinderapp;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout tilUsername;

    private TextInputLayout tilPassword;

    private Button btnLogin;

    private Intent nextActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configWidgets();
    }

    /**
     * Configures the activitie's widgets
     */
    private void configWidgets() {

        initWidgets();

        tilUsername.setHint(getString(R.string.username));
        tilPassword.setHint(getString(R.string.password));

        tilPassword.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    btnLogin.performClick();
                }
                return false;
            }
        });
    }

    /**
     * Initializes the class' fields to their corresponding widgets
     */
    private void initWidgets() {

        tilUsername = (TextInputLayout)findViewById(R.id.tilUsername);
        tilPassword = (TextInputLayout)findViewById(R.id.tilPassword);

        btnLogin = (Button) findViewById(R.id.btnLogin);

    }

    /**
     * Method executed when the login button on the activity is pressed.
     * It executes all the functionalities that define a proper user login
     * @param view Button view
     */
    public void OnLogin(View view) {
        Toast.makeText(this, "LOGIN", Toast.LENGTH_SHORT).show();
    }

    /**
     * Method executed when the register button on the activity is pressed.
     * Starts the register activity and finishes this one.
     * @param view Button view
     */
    public void OnRegister(View view) {

        nextActivity = new Intent(MainActivity.this, RegisterActivity.class);

        startActivity(nextActivity);
        finish();
    }
}
