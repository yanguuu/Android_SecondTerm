package com.yy.androidsenior9;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class FlowLayout extends ViewGroup {
    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取父容器为他设置的测量模式和大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //当前父容器的Padding值
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        //用于记录当行的动态宽高
        int lineWidth = 0;
        int lineHeight = 0;
        //根据内容决定宽高
        int wrapContentWidth = 0;
        int wrapContentHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE){
                System.out.println("进入-------------------");
                continue;//子view是隐藏的 跳过
            }
            measureChild(child,widthMeasureSpec,heightMeasureSpec);//子view的measure
            MarginLayoutParams childLayoutParams = (MarginLayoutParams) child.getLayoutParams();
            //获得子view + 外边距的宽高
            int childWidth = childLayoutParams.leftMargin + child.getMeasuredWidth() + childLayoutParams.rightMargin;
            int childHeight = childLayoutParams.topMargin + child.getMeasuredWidth() + childLayoutParams.bottomMargin;
            //换行情况
            if (lineWidth + childWidth > widthSize - paddingLeft - paddingRight){
                //更新最终想要的结果
                wrapContentWidth = Math.max(wrapContentWidth, lineWidth);
                wrapContentHeight += lineHeight;
                lineHeight = 0;
                lineWidth = 0;
            }
            //更新本行宽高
            lineWidth += childWidth;
            lineHeight = Math.max(lineHeight,childHeight);
            //最后一个，跟换行情况一样处理
            if (i == getChildCount() - 1){
                wrapContentWidth = Math.max(wrapContentWidth,lineWidth);
                wrapContentHeight += lineHeight;
            }
        }
        //最终结果加上padding值
        wrapContentWidth += wrapContentWidth + (paddingLeft + paddingRight);
        wrapContentHeight += wrapContentHeight + (paddingTop + paddingBottom);
        //最宽和最高不能大于可容纳的宽高
        if (wrapContentWidth > widthSize){
            wrapContentWidth = widthSize;
        }
        if (wrapContentHeight > heightSize){
            wrapContentHeight = heightSize;
        }
        //设置当前的viewGroup的wrap_Contetn的值
        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY?widthSize:wrapContentWidth,heightMode == MeasureSpec.EXACTLY?heightSize:wrapContentHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //当前父容器的Padding值
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        //开始的坐标
        int x = 0;
        int y = 0;
        //用于记录单行的高
        int lineHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE){
                continue;//子view是隐藏的 跳过
            }
            MarginLayoutParams childLayoutParams = (MarginLayoutParams) child.getLayoutParams();
            //获得子view + 外边距的宽高
            int childWidth = childLayoutParams.leftMargin + child.getMeasuredWidth() + childLayoutParams.rightMargin;
            int childHeight = childLayoutParams.topMargin + child.getMeasuredHeight() + childLayoutParams.bottomMargin;
            //换行情况
            if (x + childWidth > getWidth() - paddingLeft - paddingRight){
               x = 0;
               y += lineHeight;
               lineHeight = 0;
            }
            //子view的layout
            int childLeft = paddingLeft + childLayoutParams.leftMargin + x;
            int childTop = paddingTop + childLayoutParams.topMargin + y;
            int childRight = childLeft + child.getMeasuredWidth();
            int childBottom = childTop + child.getMeasuredHeight();
            child.layout(childLeft,childTop,childRight,childBottom);
            //更新开始左坐标
            x += childWidth;
            //更新行高
            lineHeight = Math.max(lineHeight,childHeight);
        }
    }
}
