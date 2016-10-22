package com.vodovozov.umorilifreshanecdotes.ui;

import android.support.v7.app.AppCompatActivity;

import com.vodovozov.umorilifreshanecdotes.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.api.BackgroundExecutor;


@EActivity(R.layout.splash_activity)
public class SplashScreenActivity extends AppCompatActivity {

    @AfterViews
    void ready() {
        doInBackground();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        BackgroundExecutor.cancelAll("start", true);
    }

    @Background(id = "start", delay = 3000)
    void doInBackground() {
        MainActivity_.intent(this).start();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}


