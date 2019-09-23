package com.yy.androidsenior8;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.customview.widget.ViewDragHelper;

import java.io.IOException;
import java.util.Random;

class PuzzleLayout extends RelativeLayout {

    private ViewDragHelper viewDragHelper;
    private DataHelper dataHelper;

    public PuzzleLayout(Context context) {
        super(context);
        init();
    }

    private void init() {
        createChild();
        viewDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(@NonNull View child, int pointerId) {
                int indexOfChild = indexOfChild(child);
                return dataHelper.getMoveDir(indexOfChild) != DataHelper.N;
            }

            @Override
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                int indexOfChild = indexOfChild(releasedChild);
                int position = dataHelper.getPosition(indexOfChild);
                viewDragHelper.settleCapturedViewAt(position % 3 * 360, position / 3 * 640);
                View invisibleView = getChildAt(8);
                ViewGroup.LayoutParams layoutParams = invisibleView.getLayoutParams();
                invisibleView.setLayoutParams(releasedChild.getLayoutParams());
                releasedChild.setLayoutParams(layoutParams);
                dataHelper.swap(position, dataHelper.getPosition(8));
                if (dataHelper.isCompleted()){
                    invisibleView.setVisibility(VISIBLE);
                    Toast.makeText(getContext(), "恭喜你胜利了！",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
                int indexOfChild = indexOfChild(child);
                int position = dataHelper.getPosition(indexOfChild);
                int selfLeft = (position % 3) * 360;
                int leftEdge = selfLeft - 360;
                int rightEdge = selfLeft + 360;
                int dir = dataHelper.getMoveDir(indexOfChild);
                switch (dir){
                    case DataHelper.L:
                        if (left <= leftEdge)
                            return leftEdge;
                        else if (left >= selfLeft)
                            return selfLeft;
                        else
                            return left;
                    case DataHelper.R:
                        if (left >= rightEdge)
                            return rightEdge;
                        else if (left <= selfLeft)
                            return selfLeft;
                        else
                            return left;
                    default:
                        return selfLeft;
                }
            }

            @Override
            public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
                int indexOfChild = indexOfChild(child);
                int position = dataHelper.getPosition(indexOfChild);
                int selfTop = (position / 3) * 640;
                int topEdge = selfTop - 640;
                int bottomEdge = selfTop + 640;
                int dir = dataHelper.getMoveDir(indexOfChild);
                switch (dir){
                    case DataHelper.T:
                        if (top <= topEdge)
                            return topEdge;
                        else if (top >= selfTop)
                            return selfTop;
                        else
                            return top;
                    case DataHelper.B:
                        if (top >= bottomEdge)
                            return bottomEdge;
                        else if (top <= selfTop)
                            return selfTop;
                        else
                            return top;
                    default:
                        return selfTop;
                }
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        performClick();
        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    private void createChild() {
        Bitmap bitmap = getBitmap("p1.jpg");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                ImageView imageView = new ImageView(getContext());
                Bitmap bitmap1 = Bitmap.createBitmap(bitmap, 360 * j,640 * i,360,640);
                imageView.setImageBitmap(bitmap1);
                ViewGroup.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                ((LayoutParams) layoutParams).leftMargin = 360 * j;
                ((LayoutParams) layoutParams).topMargin = 640 * i;
                imageView.setLayoutParams(layoutParams);
                addView(imageView);
            }
        }
        dataHelper = new DataHelper();
        randomOrder();
    }

    private void randomOrder() {
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            int r = random.nextInt(8);
            View child1 = getChildAt(dataHelper.data.get(i));
            View child2 = getChildAt(dataHelper.data.get(r));
            ViewGroup.LayoutParams layoutParams = child1.getLayoutParams();
            child1.setLayoutParams(child2.getLayoutParams());
            child2.setLayoutParams(layoutParams);
            dataHelper.swap(i, r);
        }
        getChildAt(8).setVisibility(View.INVISIBLE);
    }

    private Bitmap getBitmap(String path) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContext().getAssets().open(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
