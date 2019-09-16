package com.yy.androidsenior5;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

import java.io.IOException;
import java.util.ArrayList;

class MyView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private final SurfaceHolder holder;
    private final GestureDetector gestureDetector;
    private final float scaleX;
    private final float scaleY;
    private boolean gameOver;
    private Paint paint;
    private boolean isRun;
    private Background background;
    private Player player;

    public MyView(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Point screenSize = new Point();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                windowManager.getDefaultDisplay().getRealSize(screenSize);
            } else {
                windowManager.getDefaultDisplay().getSize(screenSize);
            }
        }
        scaleX = (float) screenSize.x / 1080;
        scaleY = (float) screenSize.y / 1920;
        init();
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (gameOver) {//双击重新开始
                    init();
                }
                return super.onDoubleTap(e);
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                player.move(-distanceX,-distanceY);
                return super.onScroll(e1, e2, distanceX, distanceY);
            }
        });

    }

    private void init() {
        gameOver = false;
        Bitmap bitmap_background1 = getBitmap("background1.jpg");
        Bitmap bitmap_player1 = getBitmap("player1.png");
        Bitmap bitmap_bullet1 = getBitmap("bullet1.png");
        background = new Background(bitmap_background1);
        player = new Player(bitmap_player1);
        player.setPosition((1080-player.getWidth())/2,1920-player.getHeight());
        player.setVisible(true);
        ArrayList<Bullet> bullets = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Bullet bullet = new Bullet(bitmap_bullet1);
            bullet.setSpeedY(-20);
            bullets.add(bullet);
        }
        player.setBullets(bullets);
    }

    private Bitmap getBitmap(String path) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContext().getAssets().open(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isRun = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRun = false;
    }

    @Override
    public void run() {
        while (isRun) {
            long startTime = System.currentTimeMillis();
            logic();
            redraw();
            long endTime = System.currentTimeMillis();
            long diffTime = endTime - startTime;
            if (diffTime < 1000 / 60) {
                try {
                    Thread.sleep(1000 / 60 - diffTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void logic() {
        if (!gameOver) {
           background.logic();
           player.logic();
        }
    }

    private void redraw() {
        Canvas lockCanvas = holder.lockCanvas();
        try {
            synchronized (holder){
                myDraw(lockCanvas);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (lockCanvas != null){
                holder.unlockCanvasAndPost(lockCanvas);
            }
        }
    }

    private void myDraw(Canvas canvas) {
        canvas.save();
        canvas.scale(scaleX,scaleY);
        canvas.drawColor(Color.WHITE);
        paint.reset();
        background.draw(canvas);
        player.draw(canvas);
        if (gameOver){
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(48);
            paint.setColor(Color.BLACK);
            canvas.drawText("游戏结束，请双击屏幕重新开始",540,960,paint);
        }
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        performClick();
        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }
}
