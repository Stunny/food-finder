package edu.salleurl.ls30394.foodfinderapp.utils;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import edu.salleurl.ls30394.foodfinderapp.R;

/**
 * Created by Admin on 10/05/2017.
 */

public class SeekBar extends Activity {
    private android.widget.SeekBar seekBar;
    private TextView seekBarValue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        seekBar = (android.widget.SeekBar) findViewById(R.id.seek_bar);
        seekBarValue = (TextView) findViewById(R.id.search_slider_kms);
        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener());

    }

    private class OnSeekBarChangeListener implements android.widget.SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(android.widget.SeekBar seekBar, int progress, boolean fromUser) {
            seekBarValue.setText(String.valueOf(progress));
        }

        @Override
        public void onStartTrackingTouch(android.widget.SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(android.widget.SeekBar seekBar) {

        }
    }
}
