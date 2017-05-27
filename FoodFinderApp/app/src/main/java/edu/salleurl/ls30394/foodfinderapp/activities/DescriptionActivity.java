package edu.salleurl.ls30394.foodfinderapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import edu.salleurl.ls30394.foodfinderapp.Adapter.CommentsAdapter;
import edu.salleurl.ls30394.foodfinderapp.R;
import edu.salleurl.ls30394.foodfinderapp.model.Restaurante;
import edu.salleurl.ls30394.foodfinderapp.repositories.FavoriteRepo;
import edu.salleurl.ls30394.foodfinderapp.repositories.RestaurantsRepo;
import edu.salleurl.ls30394.foodfinderapp.repositories.UserRepo;
import edu.salleurl.ls30394.foodfinderapp.repositories.impl.FavoriteDB;
import edu.salleurl.ls30394.foodfinderapp.repositories.impl.RestaurantDataBase;
import edu.salleurl.ls30394.foodfinderapp.repositories.impl.UserDatabase;

public class DescriptionActivity extends AppCompatActivity {

    private static final String COMMENT_KEY_USER = "user";
    private static final String COMMENT_KEY_TEXT = "comment";

    private boolean favIsClicked;

    private Restaurante restaurant;
    private String userName;
    private Intent nextActivity;

    private TextView textView,textDescription;
    private RatingBar ratingBar;
    private FloatingActionButton fab;
    private ImageView actionBarImage;

    private RecyclerView commentsList;
    private CommentsAdapter commentsAdapter;
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

        initFAB();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!favIsClicked){
                    fab.setImageResource(R.drawable.ic_love);
                    favIsClicked = true;
                    Snackbar.make(findViewById(R.id.description_layout_parent), R.string.added_to_favs,
                            Snackbar.LENGTH_SHORT).show();
                }else{
                    fab.setImageResource(R.drawable.ic_not_love);
                    favIsClicked = false;
                    Snackbar.make(findViewById(R.id.description_layout_parent), R.string.removed_from_favs,
                            Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        commentsInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if(actionId == EditorInfo.IME_ACTION_SEND){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    onSendCommentClicked(v);
                    handled = true;
                }

                return handled;
            }
        });

        commentsList.setAdapter(commentsAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setAutoMeasureEnabled(true);

        commentsList.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(commentsList.getContext(),DividerItemDecoration.VERTICAL);
        commentsList.addItemDecoration(dividerItemDecoration);
        commentsList.setNestedScrollingEnabled(false);
        commentsList.setHasFixedSize(false);

    }

    /**
     * Initializes the Floating Action Button state
     */
    private void initFAB() {
        RestaurantsRepo rdb = new RestaurantDataBase(this);
        FavoriteRepo fdb = new FavoriteDB(this);
        UserRepo udb = new UserDatabase(this);

        if(!rdb.exists(restaurant.getName())){
            fab.setImageResource(R.drawable.ic_not_love);
            favIsClicked = false;
        } else if(fdb.exists(udb.getUserId(userName), rdb.getRestaurantId(restaurant.getName()))){
            fab.setImageResource(R.drawable.ic_love);
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

        commentsList = (RecyclerView) findViewById(R.id.comments);
        commentsAdapter = new CommentsAdapter(this, userName);

        commentsInput = (TextInputEditText)findViewById(R.id.input_comment);

        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    //********************************************************************************************//
    //---------->MAIN BEHAVIOR FUNCTIONS

    /**
     * @param view
     */
    public void onMapButtonClicked(View view){
        nextActivity = new Intent(this, MapsActivity.class);
        nextActivity.putExtra("lat",restaurant.getLatitude());
        nextActivity.putExtra("lng",restaurant.getLongitude());
        nextActivity.putExtra("userName", userName);
        startActivity(nextActivity);

    }

    /**
     * @param view
     */
    public void onSendCommentClicked(View view){
        String commentText = commentsInput.getText().toString();
        commentsInput.setText("");

        if(commentText.equals(""))return;

        commentsAdapter.addComment(commentText);
        commentsAdapter.notifyDataSetChanged();

    }
}
