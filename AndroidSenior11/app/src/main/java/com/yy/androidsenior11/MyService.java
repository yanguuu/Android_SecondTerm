package com.yy.androidsenior11;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MyService extends Service {

    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("music","音乐", NotificationManager.IMPORTANCE_LOW);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
            Notification notification = new Notification.Builder(this, "music").build();
            startForeground(0x1001, notification);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(this, R.raw.shbzyqdgq);
        }
        if (!mediaPlayer.isPlaying()){
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
