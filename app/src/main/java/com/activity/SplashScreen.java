package com.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.R;

public class SplashScreen extends BaseActivity implements View.OnTouchListener {

    private int count = 0;

    private ImageView imageLogo;
    private RelativeLayout background;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        imageLogo = findViewById(R.id.imageLogo);
        background = findViewById(R.id.background);

        imageLogo.setOnTouchListener(this);
        background.setOnTouchListener(this);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int total = 14;
            if (count % total == 0) {
                background.setBackgroundColor(getResources().getColor(R.color.yellow));
                imageLogo.setImageResource(R.drawable.arctic_fox);
            } else if (count % total == 1) {
                background.setBackgroundColor(getResources().getColor(R.color.red));
                imageLogo.setImageResource(R.drawable.bird);
            } else if (count % total == 2) {
                background.setBackgroundColor(getResources().getColor(R.color.pink));
                imageLogo.setImageResource(R.drawable.cat);
            } else if (count % total == 3) {
                background.setBackgroundColor(getResources().getColor(R.color.blue));
                imageLogo.setImageResource(R.drawable.clown_fish);
            } else if (count % total == 4) {
                background.setBackgroundColor(getResources().getColor(R.color.green));
                imageLogo.setImageResource(R.drawable.cow);
            } else if (count % total == 5) {
                background.setBackgroundColor(getResources().getColor(R.color.purple));
                imageLogo.setImageResource(R.drawable.crane);
            } else if (count % total == 6) {
                background.setBackgroundColor(getResources().getColor(R.color.light_blue));
                imageLogo.setImageResource(R.drawable.deer);
            } else if (count % total == 7) {
                background.setBackgroundColor(getResources().getColor(R.color.orange));
                imageLogo.setImageResource(R.drawable.elephant);
            } else if (count % total == 8) {
                background.setBackgroundColor(getResources().getColor(R.color.yellow));
                imageLogo.setImageResource(R.drawable.hen);
            } else if (count % total == 9) {
                background.setBackgroundColor(getResources().getColor(R.color.red));
                imageLogo.setImageResource(R.drawable.koala);
            } else if (count % total == 10) {
                background.setBackgroundColor(getResources().getColor(R.color.green));
                imageLogo.setImageResource(R.drawable.lion);
            } else if (count % total == 11) {
                background.setBackgroundColor(getResources().getColor(R.color.light_blue));
                imageLogo.setImageResource(R.drawable.polar_bear);
            } else if (count % total == 12) {
                background.setBackgroundColor(getResources().getColor(R.color.blue));
                imageLogo.setImageResource(R.drawable.turtle);
            } else if (count % total == 13) {
                background.setBackgroundColor(getResources().getColor(R.color.pink));
                imageLogo.setImageResource(R.drawable.dog);
            }
            if (count == total) {
                count = 0;
            } else {
                count++;
            }
            return true;
        }
        return false;
    }
}