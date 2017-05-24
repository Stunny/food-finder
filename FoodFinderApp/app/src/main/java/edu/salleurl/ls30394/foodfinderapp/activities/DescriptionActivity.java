package edu.salleurl.ls30394.foodfinderapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    private Intent nextActivity;

    private TextView textView,textDescription;
    private RatingBar ratingBar;
    private FloatingActionButton fab;
    private ImageView actionBarImage;

    private Button buttonMap,buttonSend;

    private ListView commentsList;
    private TextInputEditText commentsInput;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_search_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.activity_search_goProfile:
                nextActivity = new Intent(this, ProfileActivity.class);
                nextActivity.putExtra("userName",userName);
                startActivity(nextActivity);
                return true;

            case R.id.activity_search_goFavorites:
                nextActivity = new Intent(this, RestaurantsListActivity.class);
                nextActivity.putExtra("username", userName);
                nextActivity.putExtra("favorites", true);
                startActivity(nextActivity);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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
                onMapButtonClicked(v);
            }
        });



/*
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );*/

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

        setSupportActionBar((Toolbar)findViewById(R.id.search_result_toolbar));
        getSupportActionBar().setTitle("");

        textView = (TextView) findViewById(R.id.restaurant_name);
        textDescription = (TextView) findViewById(R.id.description_textView);
        ratingBar = (RatingBar) findViewById(R.id.ratingBarRestaurant);

        buttonMap = (Button) findViewById(R.id.button_map);

        commentsList = (ListView)findViewById(R.id.comments);

        commentsInput = (TextInputEditText) findViewById(R.id.input_comment);

        buttonSend = (Button) findViewById(R.id.button_send);
    }

    //********************************************************************************************//
    //---------->MAIN BEHAVIOR FUNCTIONS

    private void onMapButtonClicked(View view){
        nextActivity = new Intent(this, MapsActivity.class);
        nextActivity.putExtra("lat",restaurant.getLatitude());
        nextActivity.putExtra("lng",restaurant.getLongitude());
        startActivity(nextActivity);

    }
}
