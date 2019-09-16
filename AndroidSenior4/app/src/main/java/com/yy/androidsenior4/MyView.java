package com.yy.androidsenior4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Build;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.ArrayList;

class MyView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private final SurfaceHolder holder;
    private final GestureDetector gestureDetector;
    private final float scaleX;
    private final float scaleY;
    private ArrayList<Block> blocks;
    private boolean gameOver;
    private Paint paint;
    private boolean isRun;
    private Block head;
    private Food food;

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
        blocks = new ArrayList<>();
        food = new Food();
        init();
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (gameOver) {
                    init();
                }
                return super.onDoubleTap(e);
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float x = e1.getX() - e2.getX();
                float y = e1.getY() - e2.getY();
                if (Math.abs(x) > Math.abs(y)) {
                    if (x > 0) {
                        if (head.getDir() != Block.RIGHT){
                            head.setDir(Block.LEFT);
                        }
                    }else {
                        if (head.getDir() != Block.LEFT){
                            head.setDir(Block.RIGHT);
                        }
                    }
                }else{
                    if (y > 0) {
                        if (head.getDir() != Block.DOWN){
                            head.setDir(Block.UP);
                        }
                    }else {
                        if (head.getDir() != Block.UP){
                            head.setDir(Block.DOWN);
                        }
                    }
                }

                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });

    }

    private void init() {
        gameOver = false;
        blocks.clear();
        for (int i = 0; i < 100; i++) {
            blocks.add(new Block(1000 + Block.WIDTH*i, 500,Block.LEFT));
        }
        head = blocks.get(0);
        setFood();
    }

    private void setFood() {
        food.reset();
        for (Block block : blocks) {
            if (block.getX() == food.getX() && block.getY() == food.getY()) {
                setFood();//判断随机设置的食物是否出现在蛇身中，是的话重新设置
                break;
            }
        }
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
        for (Block block:blocks){
            block.draw(canvas,paint);
        }
        food.draw(canvas,paint);
        if (gameOver){
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(48);
            paint.setColor(Color.BLACK);
            canvas.drawText("游戏结束，请双击屏幕重新开始",540,960,paint);
        }
        canvas.restore();
    }

    private void logic() {
        if (!gameOver) {
            for (Block block : blocks) {
                block.logic();
            }
            for (int i = blocks.size() - 1; i > 0; i--) {
                blocks.get(i).setDir(blocks.get(i - 1).getDir());
            }
            outBounds();
            eatFood();
            eatSelf();
        }
    }

    private void eatSelf() {
        for (int i = 4; i < blocks.size(); i++) {
            if (blocks.get(i).getX() == head.getX() && blocks.get(i).getY() == head.getY()) {
                gameOver = true;
                break;
            }
        }
    }

    private void eatFood() {
        if (head.getX() == food.getX() && head.getY() == food.getY()) {//蛇头坐标与食物坐标，重合说明吃到食物
            Block end = blocks.get(blocks.size() - 1);
            switch (end.getDir()) {
                case Block.LEFT:
                    blocks.add(new Block(end.getX() + Block.WIDTH ,end.getY(), end.getDir()));
                    break;
                case Block.RIGHT:
                    blocks.add(new Block(end.getX() - Block.WIDTH ,end.getY(), end.getDir()));
                    break;
                case Block.UP:
                    blocks.add(new Block(end.getX(), end.getY() + Block.WIDTH ,end.getDir()));
                    break;
                case Block.DOWN:
                    blocks.add(new Block(end.getX(), end.getY() - Block.WIDTH ,end.getDir()));
                    break;
            }
            setFood();
        }
    }

    private void outBounds() {
        if (head.getX() < 0 || head.getX() + Block.WIDTH > 1080 || head.getY() < 0 || head.getY() + Block.WIDTH > 1920) {
            gameOver = true;
        }
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
