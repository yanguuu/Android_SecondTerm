package com.yy.androidsenior3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.View;
import android.view.WindowManager;

class MyView1 extends View implements Runnable {

    private final Bitmap bufferBitmap;
    private Paint paint;
    private int radius;
    private boolean isRun;
    private Canvas myCanvas;

    public MyView1(Context context) {
        super(context);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        isRun = true;
        new Thread(this).start();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point screenSize = new Point();
        if (windowManager != null) {
            windowManager.getDefaultDisplay().getSize(screenSize);
        }
        bufferBitmap = Bitmap.createBitmap(screenSize.x, screenSize.y, Bitmap.Config.ARGB_8888);
        myCanvas = new Canvas(bufferBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        myDraw(myCanvas);
        canvas.drawBitmap(bufferBitmap, 0, 0, null);//自制双缓冲
    }

    private void myDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        paint.setColor(Color.RED);
        if (radius > 200) {
            radius = 0;
        }
        canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, radius++, paint);
    }

    @Override
    public void run() {
        while (isRun) {
            try {
                Thread.sleep(50);
                postInvalidate();//在其他线程中使用postInvalidate刷新画布
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
