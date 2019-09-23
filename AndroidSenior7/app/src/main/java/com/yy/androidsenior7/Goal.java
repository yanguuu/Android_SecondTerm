package com.yy.androidsenior7;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Goal extends Sprite {

    private boolean left;
    private boolean up;

    public Goal(Bitmap bitmap, int width, int height) {
        super(bitmap, width, height);
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    @Override
    public void draw(Canvas canvas) {
        int scaleX = left?1:-1;
        int scaleY = up?1:-1;
        canvas.save();
        canvas.scale(scaleX,scaleY,getX() + getWidth() / 2,getY() + getHeight() / 2);
        super.draw(canvas);
        canvas.restore();
    }
}
