package com.yy.androidsenior6;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

public class Enemy extends Sprite {

    private ArrayList<Bullet> bullets;
    private int count;
    private int speedX;
    private int speedY;
    private Blast blast;

    public Enemy(Bitmap bitmap) {
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

    public Blast getBlast() {
        return blast;
    }

    public void setBlast(Blast blast) {
        this.blast = blast;
    }

    @Override
    public void logic() {
        if (isVisible()){
            move(getSpeedX(),getSpeedY());
            fire();
        }
        outOfBounds();
        bulletsLogic();
        blastLogic();
    }

    private void blastLogic() {
        if (blast != null){
            blast.logic();
        }
    }

    private void bulletsLogic() {
        if (bullets != null){
            for (Bullet bullet :
                    bullets) {
                bullet.logic();
            }
        }
    }

    private void fire() {
        if (count++%20 == 0){
            if (bullets != null){
                for (Bullet bullet:bullets
                ) {
                    if (!bullet.isVisible()){
                        bullet.setPosition(getX() + getWidth()/2-bullet.getWidth()/2,getY()+getHeight());
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
        bulletsDraw(canvas);
        blastDraw(canvas);
    }

    private void blastDraw(Canvas canvas) {
        if (blast != null){
            blast.draw(canvas);
        }
    }

    private void bulletsDraw(Canvas canvas) {
        if (bullets != null){
            for (Bullet bullet :
                    bullets) {
                bullet.draw(canvas);
            }
        }
    }

    @Override
    public void outOfBounds() {
        if (getX() < 0 || getX() > 1080 || getY() > 1920){
            setVisible(false);
        }
    }

    public boolean isReuse(){
        if (isVisible()){ //自身显示 不可服用
            return  false;
        }
        if (bullets != null){
            for (Bullet bullet :
                    bullets) {
                if (bullet.isVisible()){
                    return  false;
                }
            }
        }
        if (blast != null && blast.isVisible()){// 还有爆炸效果显示时，不可复用
            return  false;
        }
        return true;
    }


}
