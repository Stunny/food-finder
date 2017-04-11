package edu.salleurl.ls30394.foodfinderapp.Activities;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        configWidgets();
    }

    private void configWidgets() {
        setSupportActionBar((Toolbar) findViewById(R.id.searchactivity_toolbar));
        takePicture = (Button) findViewById(R.id.profile_take_picture);
        save = (Button) findViewById(R.id.profile_save);
        profilePicture = (ImageView) findViewById(R.id.profile_user_picture);
        name = (TextInputLayout)findViewById(R.id.profile_nameWrapper);
        surname = (TextInputLayout)findViewById(R.id.profile_surnameWrapper);
        description = (TextInputLayout) findViewById(R.id.profile_descWrapper);
        gender = (RadioGroup) findViewById(R.id.profile_selectGender);

        takePicture.setVisibility(View.INVISIBLE);
        save.setVisibility(View.INVISIBLE);

        name.getEditText().setEnabled(false);
        surname.getEditText().setEnabled(false);
        description.getEditText().setEnabled(false);
        gender.setEnabled(false);

        populateData();
    }

    private void populateData() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        name.getEditText().setText("angel");
        surname.getEditText().setText("farre");
        description.getEditText().setText("test text" + System.lineSeparator() + "with line separaTHOR :D");
    }


}

