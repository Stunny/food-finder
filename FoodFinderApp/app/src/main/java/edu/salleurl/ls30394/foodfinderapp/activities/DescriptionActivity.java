package edu.salleurl.ls30394.foodfinderapp.activities;

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

import edu.salleurl.ls30394.foodfinderapp.R;

public class DescriptionActivity extends AppCompatActivity {
    private static int clicked = 0;
    private String name,description;
    private TextView textView,textDescription;
    private RatingBar ratingBar;
    private float review;
    private Button buttonMap,buttonSend;
    private ListView listView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        name = intent.getStringExtra("nameRestaurant");
        review = intent.getFloatExtra("ratingValue",0f);
        description = intent.getStringExtra("descriptionLatina");



        setContentView(R.layout.activity_description);
        configWitgets();



        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clicked == 0){
                    fab.setImageResource(R.mipmap.ic_love);
                    clicked++;
                }else{
                    fab.setImageResource(R.mipmap.ic_not_love);
                    clicked = 0;
                }
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    private void configWitgets(){
        textView = (TextView) findViewById(R.id.restaurant_name);
        textView.setText(name);
        textDescription = (TextView) findViewById(R.id.description_textView);
        textDescription.setText(description);
        ratingBar = (RatingBar) findViewById(R.id.ratingBarRestaurant);
        ratingBar.setRating(review);
        buttonMap = (Button) findViewById(R.id.button_map);
        listView = (ListView)findViewById(R.id.comments);
        editText = (EditText) findViewById(R.id.input_comment);
        buttonSend = (Button) findViewById(R.id.button_send);
    }
}
