package com.fangjian.words.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.fangjian.words.ui.WordsPrompt;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by fangjian on 13-7-6.
 */
public class WordsService extends Service {

    private static boolean started = false;
    private Timer timer;
    private TimerTask task;

    public static boolean isStarted() {
        return started;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("test", "WordsService Start");
        started = true;
        startTimer();
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d("test", "WordsService Stop");
        started = false;
        stopTimer();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createTimer();
    }

    private void createTimer() {
        timer = new Timer();
        task = new TimerTask() {
            public void run() {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), WordsPrompt.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        };
    }

    private void startTimer() {
        timer.schedule(task, 0, 10000);
    }

    private void stopTimer() {
        timer.cancel();
    }
}
