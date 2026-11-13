package com.example.lock;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

public class GuardService extends Service {

    private Handler handler;
    private Runnable loop;
    private static final String CHANNEL_ID = "lock_channel";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();

        Notification notification = new Notification.Builder(this, CHANNEL_ID)
                .setContentTitle("Lock Active")
                .setContentText("Lock screen is running")
                .setSmallIcon(android.R.drawable.ic_lock_lock)
                .build();
        startForeground(1, notification);

        handler = new Handler(Looper.getMainLooper());
        loop = new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(GuardService.this, LockActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
                handler.postDelayed(this, 2000);
            }
        };
        handler.post(loop);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (handler != null && loop != null) {
            handler.removeCallbacks(loop);
        }
        startService(new Intent(this, GuardService.class));
        super.onDestroy();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Lock Service",
                    NotificationManager.IMPORTANCE_MIN
            );
            channel.setDescription("Keeps the lock screen active");
            channel.setSound(null, null);
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
