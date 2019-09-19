package com.yy.androidsenior6;

import android.graphics.Bitmap;

public class Blast extends Sprite {

    public Blast(Bitmap bitmap, int width, int height) {
        super(bitmap, width, height);
    }

    @Override
    public void logic() {
        if (isVisible()){
            nextFrame();
        }
        if (getFrameIndex() == 0){
            setVisible(false);
        }
    }
}
