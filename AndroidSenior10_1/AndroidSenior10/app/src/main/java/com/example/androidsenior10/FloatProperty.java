package com.example.androidsenior10;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.IOException;
import java.util.ListIterator;

import androidx.annotation.Nullable;

public class FloatProperty extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(this)) {
                final View view = LayoutInflater.from(this).inflate(R.layout.floatplay,null);
                SurfaceView surfaceView = view.findViewById(R.id.surfaceView);
                SurfaceHolder holder = surfaceView.getHolder();
                final MediaPlayer mediaPlayer = new MediaPlayer();
                holder.addCallback(new SurfaceHolder.Callback() {
                    @Override
                    public void surfaceCreated(SurfaceHolder holder) {
                        mediaPlayer.setDisplay(holder);
                    }

                    @Override
                    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                    }

                    @Override
                    public void surfaceDestroyed(SurfaceHolder holder) {

                    }
                });
                Uri uri = Uri.parse("http://vfx.mtime.cn/Video/2018/03/14/mp4/190314223540373995.mp4");
                try {
                    mediaPlayer.setDataSource(this,uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                        mediaPlayer.setLooping(true);
                    }
                });
                final WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
                final WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    layoutParams.type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY;
                }else{
                    layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                }
                layoutParams.x = 150;
                layoutParams.y = 200;
                layoutParams.width = 800;
                layoutParams.height = 450;
                layoutParams.gravity = Gravity.START|Gravity.TOP;
                layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                windowManager.addView(view,layoutParams);
                view.setOnTouchListener(new View.OnTouchListener() {
                    private float x;
                    private float y;

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()){
                            case MotionEvent.ACTION_DOWN:
                                x = event.getRawX();
                                y = event.getRawY();
                                break;
                        }
                        return false;
                    }
                });
            } else {
                Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
