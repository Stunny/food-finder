package edu.salleurl.ls30394.foodfinderapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import edu.salleurl.ls30394.foodfinderapp.R;
import edu.salleurl.ls30394.foodfinderapp.model.Restaurante;
import edu.salleurl.ls30394.foodfinderapp.repositories.FavoriteRepo;
import edu.salleurl.ls30394.foodfinderapp.repositories.RestaurantsRepo;
import edu.salleurl.ls30394.foodfinderapp.repositories.UserRepo;
import edu.salleurl.ls30394.foodfinderapp.repositories.impl.FavoriteDB;
import edu.salleurl.ls30394.foodfinderapp.repositories.impl.RestaurantDataBase;
import edu.salleurl.ls30394.foodfinderapp.repositories.impl.UserDatabase;

public class DescriptionActivity extends AppCompatActivity {

    private boolean favIsClicked;

    private Restaurante restaurant;
    private String userName;

    private TextView textView,textDescription;
    private RatingBar ratingBar;
    private FloatingActionButton fab;

    private Button buttonMap,buttonSend;
    private ListView listView;
    private EditText editText;

    //********************************************************************************************//
    //---------->OVERRIDE FUNCTIONS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        restaurant = (Restaurante) intent.getSerializableExtra("restaurant");
        userName = intent.getStringExtra("username");

        setContentView(R.layout.activity_description);
        configWidgets();
    }

    @Override
    protected void onStop() {
        super.onStop();

        RestaurantDataBase rdb = new RestaurantDataBase(this);
        UserDatabase udb = new UserDatabase(this);
        FavoriteDB fdb = new FavoriteDB(this);

        if(!rdb.exists(restaurant.getName())){
            rdb.addRestaurant(restaurant);
        }

        int resId = rdb.getRestaurantId(restaurant.getName());
        int userId = udb.getUserId(userName);

        if(favIsClicked){
            if(!fdb.exists(userId, resId)){
                fdb.addFavorite(userId, resId);
            }

        } else {
            if(fdb.exists(userId, resId)){
                fdb.removeFavorite(resId, userId);
            }
        }
    }


    private void onMapClicked(View view){
        Intent intent = new Intent(this,MapsActivity.class);
        intent.putExtra("lat",restaurant.getLatitude());
        intent.putExtra("lng",restaurant.getLongitude());
        this.startActivity(intent);

    }

    //********************************************************************************************//
    //---------->UI FUNCTIONS

    /**
     * Configures all the UI components
     */
    private void configWidgets(){

        initWidgets();

        textView.setText(restaurant.getName());

        textDescription.setText(restaurant.getDescription());

        ratingBar.setRating(restaurant.getReview());

        fab = (FloatingActionButton) findViewById(R.id.fab);

        initFAB();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!favIsClicked){
                    fab.setImageResource(R.mipmap.ic_love);
                    favIsClicked = true;
                    Snackbar.make(findViewById(R.id.description_layout_parent), R.string.added_to_favs,
                            Snackbar.LENGTH_SHORT).show();
                }else{
                    fab.setImageResource(R.mipmap.ic_not_love);
                    favIsClicked = false;
                    Snackbar.make(findViewById(R.id.description_layout_parent), R.string.removed_from_favs,
                            Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        buttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMapClicked(v);
            }
        });



        // Remove focus from edit text at the creation of the activity
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

    }

    /**
     * Initializes the Floating Action Button state
     */
    private void initFAB() {
        RestaurantsRepo rdb = new RestaurantDataBase(this);
        FavoriteRepo fdb = new FavoriteDB(this);
        UserRepo udb = new UserDatabase(this);

        if(!rdb.exists(restaurant.getName())){
            fab.setImageResource(R.mipmap.ic_not_love);
            favIsClicked = false;
        } else if(fdb.exists(udb.getUserId(userName), rdb.getRestaurantId(restaurant.getName()))){
            fab.setImageResource(R.mipmap.ic_love);
            favIsClicked = true;
        }
    }

    /**
     * Initializes all the UI components into the class' attributes
     */
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
