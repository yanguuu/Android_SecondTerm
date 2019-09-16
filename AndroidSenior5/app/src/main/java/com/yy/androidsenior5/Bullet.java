package com.yy.androidsenior5;

import android.graphics.Bitmap;

public class Bullet extends Sprite {

    private  int speedX;
    private  int speedY;

    public Bullet(Bitmap bitmap) {
        super(bitmap);
    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    @Override
    public void logic() {
        move(getSpeedX(),getSpeedY());
        outOfBounds();
    }

    @Override
    public void outOfBounds() {
        if (getX() < 0 || getX() > 1080 || getY() < 0 || getY() > 1920){ //出界不做显示
            setVisible(false);
        }
    }
}
