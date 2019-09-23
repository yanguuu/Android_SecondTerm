package com.yy.androidsenior7;

import android.graphics.Bitmap;

public class Ball extends Sprite {

    public Ball(Bitmap bitmap) {
        super(bitmap);
    }

    @Override
    public void outOfBounds() {
        if (getX() < 85){
            setX(85);
        }else if (getX() > 1920 - 85 - getWidth()){
            setX(1920 - 85 - getWidth());
        }
        if (getY() < 70){
            setX(70);
        }else if (getY() > 1080 - 70 - getHeight()){
            setX(1080 - 70 - getHeight());
        }
    }
}
