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
import android.widget.TextView;

import edu.salleurl.ls30394.foodfinderapp.R;

public class DescriptionActivity extends AppCompatActivity {
    private static int clicked = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String name = intent.getStringExtra("nameRestaurant");

        TextView textView = (TextView) findViewById(R.id.restaurant_name);

        textView.setText(name);

        setContentView(R.layout.activity_description);


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
}
