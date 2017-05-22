package edu.salleurl.ls30394.foodfinderapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import edu.salleurl.ls30394.foodfinderapp.R;
import edu.salleurl.ls30394.foodfinderapp.model.Restaurante;

public class DescriptionActivity extends AppCompatActivity {

    private boolean clicked;

    private Restaurante restaurant;
    private String userName;

    private TextView textView,textDescription;
    private RatingBar ratingBar;

    private Button buttonMap,buttonSend;
    private ListView listView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        restaurant = (Restaurante) intent.getSerializableExtra("restaurant");
        userName = intent.getStringExtra("username");

        setContentView(R.layout.activity_description);
        configWitgets();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(clicked){
            //TODO: mirar si ya esta como favorito para este usuario y añadirlo a la db en caso negativo
        } else {
            //TODO: mirar si ya esta como favorito para el usuario, eliminarlo en caso afirmativo
        }
    }

    private void configWitgets(){

        initWidgets();

        textView.setText(restaurant.getName());

        textDescription.setText(restaurant.getDescription());

        ratingBar.setRating(restaurant.getReview());

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!clicked){
                    fab.setImageResource(R.mipmap.ic_love);
                    clicked = true;
                    Snackbar.make(findViewById(R.id.description_layout_parent), R.string.added_to_favs,
                            Snackbar.LENGTH_SHORT).show();
                }else{
                    fab.setImageResource(R.mipmap.ic_not_love);
                    clicked = false;
                    Snackbar.make(findViewById(R.id.description_layout_parent), R.string.removed_from_favs,
                            Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void initWidgets() {

        textView = (TextView) findViewById(R.id.restaurant_name);
        textDescription = (TextView) findViewById(R.id.description_textView);
        ratingBar = (RatingBar) findViewById(R.id.ratingBarRestaurant);

        buttonMap = (Button) findViewById(R.id.button_map);

        listView = (ListView)findViewById(R.id.comments);

        editText = (EditText) findViewById(R.id.input_comment);

        buttonSend = (Button) findViewById(R.id.button_send);
    }
}
