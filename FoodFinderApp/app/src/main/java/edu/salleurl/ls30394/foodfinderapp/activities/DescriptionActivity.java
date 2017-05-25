package edu.salleurl.ls30394.foodfinderapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private Button buttonMap, buttonSend;

    private ListView commentsList;
    private SimpleAdapter commentsAdapter;
    private TextInputEditText commentsInput;
    private List<Map<String, String>> comments;

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

        commentsInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if(actionId == EditorInfo.IME_ACTION_SEND){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    buttonSend.performClick();
                    handled = true;
                }

                return handled;
            }
        });

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
        comments = new ArrayList<>();
        commentsAdapter = new SimpleAdapter(
                this,
                comments,
                android.R.layout.simple_list_item_2,
                new String[]{COMMENT_KEY_USER, COMMENT_KEY_TEXT},
                new int[]{android.R.id.text1, android.R.id.text2}
        );
        commentsInput = (TextInputEditText) findViewById(R.id.input_comment);
        commentsList.setAdapter(commentsAdapter);

        buttonSend = (Button) findViewById(R.id.button_send);

        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    /**
     * Es necesario ir modificando la altura de la lista debido a que está contenida dentro de una
     * NestedScrollView, lo que conlleva que la lista no se muestre correctamente
     *
     * Fuente: https://kennethflynn.wordpress.com/2012/09/12/putting-android-listviews-in-scrollviews/
     */
    private void updateCommentListViewHeight(){

        if(commentsAdapter == null || commentsList == null) return;

        int totalHeight = 0;

        for (int i = 0; i < commentsAdapter.getCount(); i++) {
            View listItem = commentsAdapter.getView(i, null, commentsList);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = commentsList.getLayoutParams();
        params.height = totalHeight + (commentsList.getDividerHeight() * (commentsAdapter.getCount() - 1));
        commentsList.setLayoutParams(params);
        commentsList.requestLayout();
    }

    //********************************************************************************************//
    //---------->MAIN BEHAVIOR FUNCTIONS

    public void onMapButtonClicked(View view){
        nextActivity = new Intent(this, MapsActivity.class);
        nextActivity.putExtra("lat",restaurant.getLatitude());
        nextActivity.putExtra("lng",restaurant.getLongitude());
        nextActivity.putExtra("userName", userName);
        startActivity(nextActivity);

    }

    public void onSendCommentClicked(View view){
        String commentText = commentsInput.getText().toString();

        if(!commentText.equals("")) {
            commentsInput.setText("");

            Map<String, String> comment = new HashMap<>();
            comment.put(COMMENT_KEY_USER, "@"+userName+":");
            comment.put(COMMENT_KEY_TEXT, commentText);

            comments.add(comment);
            commentsAdapter.notifyDataSetChanged();
            updateCommentListViewHeight();
        }
    }
}
