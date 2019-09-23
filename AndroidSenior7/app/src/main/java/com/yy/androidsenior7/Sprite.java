package com.yy.androidsenior7;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {
    private Bitmap bitmap;
    private int width;
    private int height;
    private int x;
    private int y;
    private boolean isVisible;
    private int frameNumber;
    private int[] frameX;
    private int[] frameY;
    private int frameIndex;
    private Rect src;
    private Rect dst;

    public Sprite(Bitmap bitmap) {
        this(bitmap,bitmap.getWidth(),bitmap.getHeight());
    }

    public Sprite(Bitmap bitmap, int width, int height) {
        this.bitmap = bitmap;
        this.width = width;
        this.height = height;
        int w = bitmap.getWidth()/width;
        int h = bitmap.getHeight()/height;
        frameNumber = w * h;
        frameX = new int[frameNumber];
        frameY = new int[frameNumber];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                frameX[i * w + j] = width * j;
                frameY[i * w + j] = height * j;
            }
        }
        src = new Rect();
        dst = new Rect();
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

    public int getFrameIndex() {
        return frameIndex;
    }

    public void setFrameIndex(int frameIndex) {
        this.frameIndex = frameIndex;
    }

    public void move(float distanceX , float distanceY){
        x += distanceX;
        y += distanceY;
        outOfBounds();
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

    public boolean collisionWith(Sprite sprite){ //使用排除法 做命中判断
        if (!isVisible || !sprite.isVisible()){ //排除隐藏
            return false;
        }
        if (getX() < sprite.getX() && getX() + getWidth() < sprite.getX()){ //排除右侧
            return false;
        }
        if (sprite.getX() < getX() && sprite.getX() + sprite.getWidth() < getX()){ //排除左侧
            return false;
        }
        if (getY() < sprite.getY() && getY() + getHeight() < sprite.getY()){ //排除下侧
            return false;
        }
        if (sprite.getY() < getY() && sprite.getY() + sprite.getHeight() < getY()){ //排除上侧
            return false;
        }
        return true;
    }

    public void nextFrame(){
        setFrameIndex((getFrameIndex() + 1)%frameNumber);
    }
}
