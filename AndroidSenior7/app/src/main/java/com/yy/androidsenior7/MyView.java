package com.yy.androidsenior7;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Build;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

class MyView extends SurfaceView implements SurfaceHolder.Callback, Runnable, SensorEventListener {

    private final SurfaceHolder holder;
    private final GestureDetector gestureDetector;
    private final float scaleX;
    private final float scaleY;
    private Paint paint;
    private boolean isRun;
    private Ball ball;
    private Bitmap bitmap_floor;
    private boolean win;
    private boolean drawWin;
    private Bitmap bitmap_win;
    private Sprite finish;
    private ArrayList<Sprite> holes;
    private Goal goal;


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
        scaleX = (float) screenSize.x / 1920; //横屏显示
        scaleY = (float) screenSize.y / 1080;
        init();
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                win = false;
                drawWin = false;
                ball.setPosition(300, 500);
                ball.setVisible(true);
                return super.onSingleTapConfirmed(e);
            }
        });

    }

    private void init() {
        Bitmap bitmap_ball = getBitmap("ball.png");
        Bitmap bitmap_finish = getBitmap("finish.png");
        bitmap_floor = getBitmap("floor.png");
        Bitmap bitmap_goal = getBitmap("goal.png");
        Bitmap bitmap_hole = getBitmap("hole.png");
        bitmap_win = getBitmap("win.png");
        ball = new Ball(bitmap_ball);
        ball.setPosition(300, 500);
        ball.setVisible(true);
        finish = new Sprite(bitmap_finish);
        finish.setPosition(1600, 500);
        finish.setVisible(true);
        holes = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Sprite hole = new Sprite(bitmap_hole);
            hole.setPosition(600 + 300 * (i / 10), 200 + 80 * (i % 10));
            hole.setVisible(true);
            holes.add(hole);
        }
        for (int i = 0; i < 30; i++) {
            Sprite hole = new Sprite(bitmap_hole);
            hole.setPosition(750 + 300 * (i / 10), 100 + 80 * (i % 10));
            hole.setVisible(true);
            holes.add(hole);
        }
        goal = new Goal(bitmap_goal, 50, 50);
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
        if (holes != null) {
            for (Sprite hole :
                    holes) {
                if (ball.collisionWith(hole)) {
                    goal.setPosition(hole.getX() + 5, hole.getY() + 5);
                    goal.setVisible(true);
                    ball.setVisible(false);
                    break;
                }
            }
        }
        if (ball.collisionWith(finish)) {
            goal.setPosition(finish.getX() + 5, finish.getY() + 5);
            goal.setVisible(true);
            ball.setVisible(false);
            win = true;
        }
        if (goal.isVisible()) {
            goal.nextFrame();
            if (goal.getFrameIndex() == 0) {
                goal.setVisible(false);
            }
            if (win) {
                drawWin = true;
            } else {
                ball.setPosition(300, 500);
                ball.setVisible(true);
            }
        }
    }

    private void redraw() {
        Canvas lockCanvas = holder.lockCanvas();
        try {
            synchronized (holder) {
                myDraw(lockCanvas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (lockCanvas != null) {
                holder.unlockCanvasAndPost(lockCanvas);
            }
        }
    }

    private void myDraw(Canvas canvas) {
        canvas.save();
        canvas.scale(scaleX, scaleY);
        paint.reset();
        if (drawWin) {
            canvas.drawColor(Color.BLACK);
            canvas.drawBitmap(bitmap_win, 500, 250, null);
        } else {
            canvas.drawBitmap(bitmap_floor, 0, 0, null);
            ball.draw(canvas);
            finish.draw(canvas);
            if (holes != null) {
                for (Sprite hole :
                        holes) {
                    hole.draw(canvas);
                }
            }
            goal.draw(canvas);
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

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        ball.move(2 * y, 2 * x);
        if (y > 0) {
            goal.setLeft(true);
        } else {
            goal.setLeft(false);
        }
        if (x > 0) {
            goal.setUp(true);
        } else {
            goal.setUp(false);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

