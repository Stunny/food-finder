package edu.salleurl.ls30394.foodfinderapp.activities;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import edu.salleurl.ls30394.foodfinderapp.R;
import edu.salleurl.ls30394.foodfinderapp.model.User;
import edu.salleurl.ls30394.foodfinderapp.repositories.UserRepo;
import edu.salleurl.ls30394.foodfinderapp.repositories.impl.UserDatabase;

/**
 * Created by Admin on 11/04/2017.
 */

public class ProfileActivity extends AppCompatActivity {
    private Button takePicture;
    private Button save;
    private TextInputLayout name;
    private TextInputLayout surname;
    private TextInputLayout description;
    private ImageView profilePicture;
    private RadioGroup gender;
    private RadioButton male,female,other;
    private Intent nextActivity;
    private Bitmap imageBitmap;
    private String userName;


    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");

        configWidgets();
    }

    /**
     * Sets initial basic configurations
     */
    private void configWidgets() {
        getSupportActionBar().setTitle(getString(R.string.profile));

        //setSupportActionBar((Toolbar) findViewById(R.id.profile_activity_toolbar));
        takePicture = (Button) findViewById(R.id.profile_take_picture);
        save = (Button) findViewById(R.id.profile_save);
        profilePicture = (ImageView) findViewById(R.id.profile_user_picture);
        name = (TextInputLayout)findViewById(R.id.profile_nameWrapper);
        surname = (TextInputLayout)findViewById(R.id.profile_surnameWrapper);
        description = (TextInputLayout) findViewById(R.id.profile_descWrapper);
        gender = (RadioGroup) findViewById(R.id.profile_selectGender);
        male = (RadioButton) findViewById(R.id.button_male);
        female = (RadioButton)findViewById(R.id.button_female);
        other = (RadioButton)findViewById(R.id.button_other);

        imageBitmap = getBitmap(userName + "_profile.png");
        if(imageBitmap != null){
            profilePicture.setImageBitmap(imageBitmap);
        }else{
            profilePicture.setImageResource(R.mipmap.ic_profile_picture_default);
        }

        populateData();
        setVisibilityColour(View.INVISIBLE, false);
    }

    /**
     * Sets widgets visibility, EditText editability and EditText colour
     * @param visibility set visibility of the widgets
     * @param b sets EditText to editable/not editable
     */
    private void setVisibilityColour(int visibility, boolean b){
        takePicture.setVisibility(visibility);
        save.setVisibility(visibility);

        name.getEditText().setEnabled(b);
        surname.getEditText().setEnabled(b);
        description.getEditText().setEnabled(b);
        male.setClickable(b);
        female.setClickable(b);
        other.setClickable(b);
        //gender.getCameraDistance();
        profilePicture.setClickable(b);
        name.getEditText().setTextColor(Color.BLACK);
        surname.getEditText().setTextColor(Color.BLACK);
        description.getEditText().setTextColor(Color.BLACK);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_profile_menu, menu);
        return true;
    }

    private void populateData() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //load data from database
        UserRepo userDB = new UserDatabase(this);
        List<User> users = userDB.getUser(userName,false);
        name.getEditText().setText(userName);
        surname.getEditText().setText(users.get(0).getUserSurname());
        description.getEditText().setText(users.get(0).getUserDescription());
        RadioButton radioButton;
        switch (users.get(0).getGenderIndex()){
            case 0:
                radioButton = (RadioButton)findViewById(R.id.button_male);
                radioButton.setChecked(true);
                break;
            case 1:
                radioButton = (RadioButton) findViewById(R.id.button_female);
                radioButton.setChecked(true);
                break;
            case 2:
                radioButton = (RadioButton) findViewById(R.id.button_other);
                radioButton.setChecked(true);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.activity_profile_edit){
            editContent();
        }
        return super.onOptionsItemSelected(item);
    }

    private void editContent() {
        setVisibilityColour(View.VISIBLE, true);
    }


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
        saveImageToInternalStorage(imageBitmap,userName);
    }

    public void OnImageSelect(View view) {
        //TODO: image select functionality
        dispatchTakePictureIntent();
    }

    public void OnProfileSave(View view){
        //save data to databse
        name.getEditText().clearFocus();
        userName = name.getEditText().getText().toString();
        surname.getEditText().clearFocus();
        String userSurname = surname.getEditText().getText().toString();
        description.getEditText().clearFocus();
        String descriptionUser = description.getEditText().getText().toString();
        int genderUser;
        if(male.isChecked()){
            genderUser = 0;
        }else{
            if(female.isChecked()){
                genderUser = 1;
            }else{
                genderUser = 2;
            }
        }
        setVisibilityColour(View.INVISIBLE, false);
        UserRepo userDataBase = new UserDatabase(this);
        List<User> user = userDataBase.getUser(userName,false);
        User user1 = user.get(0);
        user1.setUserSurname(userSurname);
        user1.setUserDescription(descriptionUser);
        user1.setGenderIndex(genderUser);
        userDataBase.updateUser(user1);
    }

    @Override
    public void onBackPressed() {
        nextActivity = new Intent(ProfileActivity.this, SearchActivity.class);
        nextActivity.putExtra("userName",userName);
        finish();
    }


    private Bitmap getBitmap(String filename) {

        Bitmap thumbnail = null;

        //look in internal storage

        try {
            File filePath = this.getFileStreamPath(filename);
            FileInputStream fi = new FileInputStream(filePath);
            thumbnail = BitmapFactory.decodeStream(fi);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return thumbnail;
    }

    private boolean saveImageToInternalStorage(Bitmap image,String name) {

        try {
            // Use the compress method on the Bitmap object to write image to
            // the OutputStream
            FileOutputStream fos =
                    this.openFileOutput( name + "_profile.png", Context.MODE_PRIVATE);

            // Writing the bitmap to the output stream
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();

            return true;
        } catch (Exception e) {
            Log.e("saveToInternalStorage()", e.getMessage());
            return false;
        }
    }

}

