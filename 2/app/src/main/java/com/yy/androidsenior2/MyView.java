package com.yy.androidsenior2;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

class MyView extends View {

    private LinearGradient linearGradient1;
    private LinearGradient linearGradient2;
    private LinearGradient linearGradient3;
    private SweepGradient sweepGradient;
    private RadialGradient radialGradient1;
    private RadialGradient radialGradient2;
    private BitmapShader bitmapShader;
    private Paint paint;

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linearGradient1 = new LinearGradient(0, 0, 100, 0,
                new int[]{Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN},
                null, Shader.TileMode.REPEAT);//重复
        linearGradient2 = new LinearGradient(0, 0, 100, 0,
                new int[]{Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN},
                new float[]{0.5f,0.6f,0.7f,0.9f}, Shader.TileMode.MIRROR);//镜像
        linearGradient3 = new LinearGradient(700, 100, 800, 200,
                new int[]{Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN},
                null, Shader.TileMode.CLAMP);
        sweepGradient = new SweepGradient(200,500,
                new int[]{Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN},null);
        radialGradient1 = new RadialGradient(500,500,50,
                new int[]{Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN},
                null, Shader.TileMode.REPEAT);
        radialGradient2 = new RadialGradient(500,500,50,
                new int[]{Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN},
                null, Shader.TileMode.MIRROR);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.p1);
        bitmapShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.MIRROR);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        paint.setTextSize(48);
        paint.setShader(linearGradient1);
        canvas.drawText("重复平均",100,100,paint);
        canvas.drawRect(100,100,300,300,paint);
        paint.setShader(linearGradient2);
        canvas.drawText("镜像比例",400,100,paint);
        canvas.drawRect(400,100,600,300,paint);
        paint.setShader(linearGradient3);
        canvas.drawText("倾斜拉伸",700,100,paint);
        canvas.drawRect(700,100,900,300,paint);
        paint.setShader(sweepGradient);
        canvas.drawText("扫描渐变",100,400,paint);
        canvas.drawCircle(200,500,100,paint);
        paint.setShader(radialGradient1);
        canvas.drawText("径向渐变",400,400,paint);
        canvas.drawCircle(500,500,100,paint);
        paint.setShader(radialGradient2);
        canvas.drawText("中心变化",700,400,paint);
        canvas.drawCircle(800,500,100,paint);
        paint.setShader(bitmapShader);
        canvas.drawText("图片渲染",100,700,paint);
        canvas.drawCircle(200,800,100,paint);
    }
}
