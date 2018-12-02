package com.example.pavel.monero.serves;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;
import com.example.pavel.monero.R;
import com.example.pavel.monero.ui.MainActivity;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ForegroundService extends IntentService implements CoinHive.Callback {

    public ForegroundService() {
        super("VPN");
    }
    static private Activity activity;
    private CoinHive.Miner wvCoinHive;


    public static Intent START(Activity activity, Context context) {
        ForegroundService.activity = activity;
        return new Intent(context, ForegroundService.class);
    }


    @SuppressLint("CheckResult")
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    @SuppressLint("CheckResult")
    @Override
    public void onCreate() {
        startForeground(1337, showNotification());
        //wvCoinHive = new CoinHive.Miner( activity, this);
        //wvCoinHive.startMining();//todo
        new Thread(()-> {
            while (true) {
                go().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe((e)-> {
                    Toast.makeText(this, e, Toast.LENGTH_SHORT).show();
                });
                try {
                    Thread.sleep(2000 );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_NOT_STICKY;
    }

    @Override
    public boolean isShowMining() {
        return false;
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

    private Notification showNotification() {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        return new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.button_start)
                .setContentTitle("VPN")
                .setContentText("Doing some work...")
                .setContentIntent(pendingIntent).build();
    }

    private Observable<String> go() {
        return Observable.create((e)-> {
            e.onNext("Hello");
        });
    }
}
