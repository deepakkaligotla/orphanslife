package com.kaligotla.oms.SponsorView.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class SponsorBackgroundService extends Service {

    private Handler mHandler;
    private Runnable mRunnable;
    private int mInterval = 10000; // 10 seconds by default

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                // Do something in the background
                Log.e("MyBackgroundService", "Service is running");
                mHandler.postDelayed(this, mInterval);
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mHandler.post(mRunnable);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRunnable);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
