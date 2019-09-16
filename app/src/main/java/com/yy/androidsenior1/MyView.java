package com.yy.androidsenior1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;

class MyView extends View {

    private Paint paint;
    private Path path;
    private RectF rectF;
    private int[] x;
    private int[] y;

    public MyView(Context context) {
        super(context);
        paint = new Paint();
        paint.setAntiAlias(true);
        path = new Path();
        rectF = new RectF();
        x = new int[10];
        y = new int[10];
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        paint.reset();

        //画点
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(10);
        canvas.drawPoint(100,100,paint);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(1);
        float[] pts = {100,100,105,105,110,110};
        canvas.drawPoints(pts,paint);

        //画线
        paint.setStrokeWidth(5);
        paint.setColor(Color.YELLOW);
        canvas.drawLine(100,200,300,400,paint);
        paint.setColor(Color.argb(255,0,200,255));
        float[] pts1 = {0,111,222,444,333,555,666,777,888,999};
        canvas.drawLines(pts1,paint);
        paint.setColor(Color.argb(100,0,200,255));
        canvas.drawLines(pts1,1,8,paint);

        //画路径
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        path.reset();
        path.moveTo(50,50);
        path.lineTo(100,300);
        path.lineTo(300,400);
        path.lineTo(600,800);
        path.lineTo(800,430);
        path.close();
        canvas.drawPath(path,paint);

        //贝塞尔曲线
        paint.setColor(Color.GRAY);
        path.reset();
        path.moveTo(400,400);
        path.quadTo(800,200,1000,400);
        canvas.drawPath(path,paint);

        //二阶白塞尔曲线
        paint.setColor(Color.MAGENTA);
        path.reset();
        path.moveTo(600,600);
        path.cubicTo(800,200,900,900,1000,600);
        canvas.drawPath(path,paint);

        //相对坐标
        paint.setColor(Color.GREEN);
        path.reset();
        path.rMoveTo(200,200);
        path.rLineTo(200,200);
        path.rQuadTo(100,-200,200,0);
        path.rCubicTo(100,-100,200,100,300,0);
        canvas.drawPath(path,paint);

        //圆
        paint.setColor(Color.WHITE);
        canvas.drawCircle(getWidth()/2,1100,200,paint);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(getWidth()/2,1100,150,paint);

        //矩形
        paint.setColor(Color.BLUE);
        canvas.drawRect(100,800,300,1000,paint);
        rectF.set(800,800,1000,1000);
        canvas.drawRect(rectF,paint);

        //圆角矩形
        paint.setColor(Color.CYAN);
        rectF.set(100,1100,300,1500);
        canvas.drawRoundRect(rectF,20,20,paint);

        //椭圆
        paint.setColor(Color.MAGENTA);
        canvas.drawOval(rectF,paint);

        //扇形
        paint.setColor(Color.YELLOW);
        canvas.drawArc(rectF,0,90,true,paint);

        //楔形
        paint.setColor(Color.DKGRAY);
        canvas.drawArc(rectF,180,90,false,paint);

        //弧
        rectF.set(800,1100,1000,1500);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.LTGRAY);
        canvas.drawArc(rectF,0,90,true,paint);
        paint.setColor(Color.GREEN);
        canvas.drawArc(rectF,180,90,false,paint);

        //五角星
        int r_out = 150;
        for (int i = 0; i < 5; i++) {
            x[2*i] = getWidth()/2 + (int)(r_out*Math.cos(Math.toRadians((18+72*i))));
            y[2*i] = 1100 - (int)(r_out*Math.sin(Math.toRadians(18+72*i)));
        }
        int r_in = (int)(r_out*Math.sin(Math.toRadians(18))/Math.sin(Math.toRadians(126)));
        for (int i = 0; i < 5; i++) {
            x[2*i+1] = getWidth()/2 + (int)(r_in*Math.cos(Math.toRadians((54+72*i))));
            y[2*i+1] = 1100 - (int)(r_in*Math.sin(Math.toRadians(54+72*i)));
        }
        path.reset();
        path.moveTo(x[0],y[0]);
        for (int i = 1; i < x.length; i++) {
            path.lineTo(x[i],y[i]);
        }
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.RED);
        canvas.drawPath(path,paint);
    }
}
