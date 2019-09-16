package com.yy.androidsenior4;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Block {
    private int x;
    private int y;
    private int dir;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;
    public static final int WIDTH = 10;

    public Block(int x, int y, int dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
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

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public void logic(){
        switch (dir){
            case LEFT:
                x -= WIDTH;
                break;
            case RIGHT:
                x += WIDTH;
                break;
            case UP:
                y -= WIDTH;
                break;
            case DOWN:
                y += WIDTH;
                break;
        }
    }

    public void draw(Canvas canvas, Paint paint){
        paint.setColor(Color.BLACK);
        canvas.drawRect(x,y,x+WIDTH,y+WIDTH,paint);
    }



}
