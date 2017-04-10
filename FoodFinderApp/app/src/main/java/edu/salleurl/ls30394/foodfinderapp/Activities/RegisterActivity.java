package edu.salleurl.ls30394.foodfinderapp.Activities;

import android.content.Intent;
import android.os.StrictMode;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import edu.salleurl.ls30394.foodfinderapp.R;

public class RegisterActivity extends AppCompatActivity {

    private Intent nextActivity;
    private TextInputLayout name;
    private TextInputLayout surname;
    private TextInputLayout email;
    private TextInputLayout password;
    private TextInputLayout confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (TextInputLayout)findViewById(R.id.register_nameWrapper);
        surname = (TextInputLayout)findViewById(R.id.register_surnameWrapper);
        email = (TextInputLayout)findViewById(R.id.register_emailWrapper);
        password = (TextInputLayout)findViewById(R.id.register_passwdWrapper);
        confirmPassword = (TextInputLayout)findViewById(R.id.register_passwdConfWrapper);
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
        if (String.valueOf(name.getEditText().getText()).equals("")){
            mostraError(getString(R.string.error_name), getString(R.string.error_title));
        }else if (String.valueOf(surname.getEditText().getText()).equals("")) {
            mostraError(getString(R.string.error_surname), getString(R.string.error_title));
        }else if (String.valueOf(email.getEditText().getText()).equals("")) {
            mostraError(getString(R.string.error_email), getString(R.string.error_title));
            //}else if (apacheEmailValidator){
        }else if(String.valueOf(password.getEditText().getText()).equals("")) {
            mostraError(getString(R.string.error_password), getString(R.string.error_title));
        }else if(String.valueOf(confirmPassword.getEditText().getText()).equals("")) {
            mostraError(getString(R.string.error_confirmPassword), getString(R.string.error_title));
        }else if(!String.valueOf(password.getEditText().getText()).equals(String.valueOf(confirmPassword.getEditText().getText()))){
            mostraError(getString(R.string.error_passwordValidation), getString(R.string.error_title));
        }else{
            OnRegisterSuccess();
        }
    }

    public void OnRegisterSuccess(){
        //TODO: almacenar datos de usuario

        //TODO: change MainActivity by first app activity
        nextActivity = new Intent(RegisterActivity.this, SearchActivity.class);
        startActivity(nextActivity);
        finish();
    }

    private void mostraError (String message, String titol){
        Log.i("angel", "entro a mostrar el nom");
        //ventana emergente de error
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage(message);
        dlgAlert.setTitle(titol);
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }
}
