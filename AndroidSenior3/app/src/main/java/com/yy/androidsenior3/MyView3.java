package com.yy.androidsenior3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class MyView3 extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private final SurfaceHolder holder;
    private Paint paint;
    private boolean isRun;
    private RectF rectF;
    private Path path;

    public MyView3(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        path = new Path();
        rectF = new RectF(100,1500,980,1570);
    }

    @Override
    public void run() {
        while (isRun) {
            long startTime = System.currentTimeMillis();
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
        canvas.drawColor(Color.BLACK);
        paint.reset();//重置
        //绘制清屏按钮
        paint.setColor(Color.GRAY);
        canvas.drawRect(rectF,paint);
        paint.setColor(Color.BLUE);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(48);
        canvas.drawText("清      屏",canvas.getWidth()/2,1550,paint);
        //利用path实现画线
        canvas.save();
        canvas.clipRect(0,0,canvas.getWidth(),1500);//设置可绘制区域
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(15);
        canvas.drawPath(path,paint);
        canvas.restore();

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
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(event.getX(),event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(event.getX(),event.getY());
                break;
            case MotionEvent.ACTION_UP:
                if(rectF.contains(event.getX(),event.getY())){//判断点是否在矩形内
                    path.reset();
                }
                //重写onTouchEven后，系统默认的performClick给屏蔽了，手动调用是为了对默认点击事件进行支持
                performClick();
                break;
        }
        return true;//拦截系统默认的处理
    }

    @Override
    public boolean performClick() {
        //完成点击，performClick()的作用是调用你在setOnClickListener时设置的onClick()方法
        return super.performClick();
    }
}
