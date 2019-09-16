package com.yy.androidsenior5;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

public class Player extends Sprite {

    private ArrayList<Bullet> bullets;
    private  int count;

    public Player(Bitmap bitmap) {
        super(bitmap);
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(ArrayList<Bullet> bullets) {
        this.bullets = bullets;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public void logic() {
        if (isVisible()){
            fire();
        }
        if (bullets != null){
            for (Bullet bullet :bullets
                 ) {
                bullet.logic();
            }
        }
        outOfBounds();
    }

    private void fire() {
        if (count++%10 == 0){
            if (bullets != null){
                for (Bullet bullet:bullets
                     ) {
                    if (!bullet.isVisible()){
                        bullet.setPosition(getX() + getWidth()/2-bullet.getWidth()/2,getY());
                        bullet.setVisible(true);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        bulletsDrwa(canvas);
    }

    private void bulletsDrwa(Canvas canvas){
        if (bullets != null){
            for (Bullet bullet :
                    bullets) {
                bullet.draw(canvas);
            }
        }
    }

    @Override
    public void outOfBounds() {
        if (getX() < 0){
            setX(0);
        }else if (getX() > 1080 - getWidth()){
            setX(1080 - getWidth());
        }
        if (getY() < 0){
            setY(0);
        }else if (getY() > 1920 - getHeight()){
            setY(1920 - getHeight());
        }
    }
}
