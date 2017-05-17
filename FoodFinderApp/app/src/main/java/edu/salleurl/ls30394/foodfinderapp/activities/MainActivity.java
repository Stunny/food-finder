package edu.salleurl.ls30394.foodfinderapp.activities;

import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import edu.salleurl.ls30394.foodfinderapp.R;
import edu.salleurl.ls30394.foodfinderapp.model.User;
import edu.salleurl.ls30394.foodfinderapp.repositories.UserRepo;
import edu.salleurl.ls30394.foodfinderapp.repositories.impl.UserDatabase;

public class MainActivity extends AppCompatActivity {


    private TextInputLayout tilUsername;

    private TextInputLayout tilPassword;

    private EditText passwordText;

    private EditText userNameText;

    private Button btnLogin;

    private Intent nextActivity;

    private UserRepo userRepo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        userRepo = new UserDatabase(getApplicationContext());

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

        userNameText = (EditText) findViewById(R.id.userName_text);
        passwordText = (EditText) findViewById(R.id.password_text);

        btnLogin = (Button) findViewById(R.id.btnLogin);

    }

    /**
     * TODO: login check
     * Method executed when the login button on the activity is pressed.
     * It executes all the functionalities that define a proper user login
     * @param view Button view
     */
    public void OnLogin(View view) {
        String userName = userNameText.getText().toString();
        String userPassword = passwordText.getText().toString();
        if(userName.equals("")){
            Toast.makeText(this,R.string.user_field_empty, Toast.LENGTH_SHORT).show();
        } else{
            if(userPassword.equals("")){
                Toast.makeText(this,R.string.password_field_empty, Toast.LENGTH_SHORT).show();

            }else{
                //la aplicación peta en la linia de debajo, no se como solucionarlo
                List<User> user = userRepo.getUser(userName,userPassword,isEmail(userName));

                if(user.size() > 0 ){
                    OnLoginSuccess(user.get(0));
                }else{
                    Toast.makeText(this,R.string.invalid_user,Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private boolean isEmail(String userText){
        char[] charSequence = userText.toCharArray();
        int size = charSequence.length;
        boolean isEmail = false;
        for(int i= 0 ; i<size;i++){
            if(charSequence[i] == '@'){
                isEmail  = true;
                break;
            }
        }
        return  isEmail;
    }
    /**
     * Executed when user login has been successful
     */
    public void OnLoginSuccess(User user){
        nextActivity = new Intent(MainActivity.this, SearchActivity.class);
        nextActivity.putExtra("userName",user.getUserName());
        nextActivity.putExtra("userPassword",user.getUserPassword());
        startActivity(nextActivity);
        finish();
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
