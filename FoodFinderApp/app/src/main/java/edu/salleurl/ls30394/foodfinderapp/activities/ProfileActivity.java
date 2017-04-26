package edu.salleurl.ls30394.foodfinderapp.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;

import edu.salleurl.ls30394.foodfinderapp.R;

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
    private Bitmap imageBitmap;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        configWidgets();
    }

    /**
     * Sets initial basic configurations
     */
    private void configWidgets() {
        //setSupportActionBar((Toolbar) findViewById(R.id.profile_activity_toolbar));
        takePicture = (Button) findViewById(R.id.profile_take_picture);
        save = (Button) findViewById(R.id.profile_save);
        profilePicture = (ImageView) findViewById(R.id.profile_user_picture);
        name = (TextInputLayout)findViewById(R.id.profile_nameWrapper);
        surname = (TextInputLayout)findViewById(R.id.profile_surnameWrapper);
        description = (TextInputLayout) findViewById(R.id.profile_descWrapper);
        gender = (RadioGroup) findViewById(R.id.profile_selectGender);

        setVisibilityColour(View.INVISIBLE, false);

        populateData();
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
        gender.setEnabled(b);
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

        name.getEditText().setText("angel");
        surname.getEditText().setText("farre");
        description.getEditText().setText("text test" + System.lineSeparator() + "with line separaTHOR :D");
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
    }

    public void OnImageSelect(View view) {
        //TODO: image select functionality
        dispatchTakePictureIntent();
    }

    public void OnProfileSave(View view){
        //save data to databse
        name.getEditText().clearFocus();
        surname.getEditText().clearFocus();
        description.getEditText().clearFocus();

        setVisibilityColour(View.INVISIBLE, false);
    }

}

