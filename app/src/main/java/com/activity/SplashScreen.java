package com.activity;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.Window;
import android.view.WindowManager;

import androidx.viewpager.widget.ViewPager;

import com.R;
import com.adapter.AnimalPageAdapter;
import com.helper.AnimalHelper;

public class SplashScreen extends BaseActivity {

    private int backCount = 0;

    private Handler backHandler;
    private Runnable backRunnable;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new AnimalPageAdapter(this, AnimalHelper.getInstance(this).getAnimals()));

        HandlerThread backStateThread = new HandlerThread("BackStateThread", Thread.MAX_PRIORITY);
        backStateThread.start();
        backHandler = new Handler(backStateThread.getLooper());
        backRunnable = () -> backCount = 0;

        mPlayer = MediaPlayer.create(this, R.raw.baby_shark);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPlayer.start();
    }

    @Override
    public void onBackPressed() {
        if (backCount == 5) {
            backCount = 0;
            backHandler.removeCallbacks(backRunnable);
            mPlayer.stop();
            super.onBackPressed();
        } else {
            backHandler.postDelayed(backRunnable, 2000);
            backCount++;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.moveTaskToFront(getTaskId(), 0);
    }
}
