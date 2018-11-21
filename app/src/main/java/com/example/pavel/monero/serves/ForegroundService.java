package com.example.pavel.monero.serves;


public class ForegroundService /*extends Service*/ {
    /*private static final String LOG_TAG = "ForegroundService";
    public static final int DEFAULT_NOTIFICATION_ID = 101;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @SuppressLint("CheckResult")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sendNotification("Ticker","Title","Text");

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
        return START_STICKY;
    }

    public void sendNotification(String Ticker,String Title,String Text) {

        //These three lines makes Notification to open main activity after clicking on it
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setAction(Intent.ACTION_MAIN);
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentIntent(contentIntent)
                .setOngoing(true)   //Can't be swiped out
                .setSmallIcon(R.mipmap.ic_launcher)
                //.setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.large))   // большая картинка
                .setTicker(Ticker)
                .setContentTitle(Title) //Заголовок
                .setContentText(Text) // Текст уведомления
                .setWhen(System.currentTimeMillis());

        Notification notification;
        notification = builder.build();

        startForeground(DEFAULT_NOTIFICATION_ID, notification);
    }

    private Observable<String> go() {
        return Observable.create((e)-> {
            e.onNext("Hello");
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "In onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Used only in case of bound services.
        return null;
    }*/
}
