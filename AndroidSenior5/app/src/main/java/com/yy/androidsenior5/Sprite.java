package com.yy.androidsenior5;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Sprite {
    private Bitmap bitmap;
    private int width;
    private int height;
    private int x;
    private int y;
    private boolean isVisible;

    public Sprite(Bitmap bitmap) {
        this.bitmap = bitmap;
        width = bitmap.getWidth();
        height = bitmap.getHeight();
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public void setPosition(int x,int y){
        setX(x);
        setY(y);
    }

    public void move(float distanceX ,float distanceY){
        x += distanceX;
        y += distanceY;
    }

    public void draw(Canvas canvas){
        if (isVisible()){
            canvas.drawBitmap(bitmap,x,y,null);
        }
    }

    public void logic(){

    }

    public void outOfBounds(){

    }
}
