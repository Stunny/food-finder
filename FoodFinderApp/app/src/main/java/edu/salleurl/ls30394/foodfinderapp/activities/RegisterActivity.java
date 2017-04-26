package edu.salleurl.ls30394.foodfinderapp.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import edu.salleurl.ls30394.foodfinderapp.R;

public class RegisterActivity extends AppCompatActivity {

    private Intent nextActivity;
    private TextInputLayout name;
    private TextInputLayout surname;
    private TextInputLayout email;
    private TextInputLayout password;
    private TextInputLayout confirmPassword;
    private ImageView profilePicture;
    private CheckBox terms;
    private Bitmap imageBitmap;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        profilePicture = (ImageView) findViewById(R.id.register_user_picture);
        name = (TextInputLayout)findViewById(R.id.register_nameWrapper);
        surname = (TextInputLayout)findViewById(R.id.register_surnameWrapper);
        email = (TextInputLayout)findViewById(R.id.register_emailWrapper);
        password = (TextInputLayout)findViewById(R.id.register_passwdWrapper);
        confirmPassword = (TextInputLayout)findViewById(R.id.register_passwdConfWrapper);
        terms = (CheckBox) findViewById(R.id.register_terms);
    }

    @Override
    public void onBackPressed() {
        nextActivity = new Intent(RegisterActivity.this, MainActivity.class);

        startActivity(nextActivity);
        finish();
    }

/*
Opcional de seleccionar la imagen de la galeria. Necesita de onActivityresult tambien.
source: http://stackoverflow.com/a/5086706/7060082
    public void OnGallerySelect(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }
*/
    /**
     * Selects default camera and takes picture
     */
    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    /**
     * Transforms the picture taken into a bitmap
     * @param requestCode image code
     * @param resultCode checking if OK
     * @param data bitmap in the extras, corresponding to our image
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            this.imageBitmap = imageBitmap;
            profilePicture.setImageBitmap(imageBitmap);
        }
    }

    public void OnImageSelect(View view) {
        //TODO: image select functionality
        dispatchTakePictureIntent();
    }

    public void OnRegisterComplete(View view) {

        //TODO: comprobacion de campos de registro
        if (String.valueOf(name.getEditText().getText()).equals("")){
            showError(getString(R.string.error_name), getString(R.string.error_title));
        }else if (String.valueOf(surname.getEditText().getText()).equals("")) {
            showError(getString(R.string.error_surname), getString(R.string.error_title));
        }else if (String.valueOf(email.getEditText().getText()).equals("")) {
            showError(getString(R.string.error_email), getString(R.string.error_title));
            //}else if (apacheEmailValidator){
        }else if(String.valueOf(password.getEditText().getText()).equals("")) {
            showError(getString(R.string.error_password), getString(R.string.error_title));
        }else if(String.valueOf(confirmPassword.getEditText().getText()).equals("")) {
            showError(getString(R.string.error_confirmPassword), getString(R.string.error_title));
        }else if(!String.valueOf(password.getEditText().getText()).equals(String.valueOf(confirmPassword.getEditText().getText()))) {
            showError(getString(R.string.error_passwordValidation), getString(R.string.error_title));
        }else if (!terms.isChecked()){
            showError(getString(R.string.error_terms), getString(R.string.error_title));
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

    /**
     * Shows pop-up window with a message error
     * @param message body of the message
     * @param titol header of the message
     */

    private void showError (String message, String titol){
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage(message);
        dlgAlert.setTitle(titol);
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }
}
