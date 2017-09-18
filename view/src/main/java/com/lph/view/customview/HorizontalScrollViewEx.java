package com.lph.view.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * 一个在水平方向可以滑动的viewgroup，子view水平方向排列
 * 不支持子view的margin的属性
 * Created by lph on 2017/9/18.
 */

public class HorizontalScrollViewEx extends ViewGroup {

    private static final String TAG = "HorizontalScrollViewEx";
    private Scroller mScroller;

    public HorizontalScrollViewEx(Context context) {
        this(context, null);
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        mScroller = new Scroller(getContext());
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //在这里用来确定viewGroup的大小，即是ViewGroup的高度和宽度
        int measureWidth = 0;
        int measureHeight = 0;
        int childCount = getChildCount();
        //测量所有的子view，确定其大小
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        //如果当前child的数目为0，那么就使用viewgroup的自己系统赋给它的大小
        if (childCount == 0) {
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
        } else {
            int widthMode = MeasureSpec.getMode(widthMeasureSpec);
            int heightMode = MeasureSpec.getMode(heightMeasureSpec);
            int widthSize = MeasureSpec.getSize(widthMeasureSpec);
            int heightSize = MeasureSpec.getSize(heightMeasureSpec);
            //如果当前的控件给定了确切的值，那么直接使用确切的值，
            if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
                setMeasuredDimension(widthSize, heightSize);
            } else if (widthMode != MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
                setMeasuredDimension(getChildAt(0).getMeasuredWidth() * getChildCount(), heightSize);
            } else if (widthMode == MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY) {
                setMeasuredDimension(widthSize, getChildAt(0).getMeasuredHeight());
            } else {
                setMeasuredDimension(getChildAt(0).getMeasuredWidth() * getChildCount(), getChildAt(0).getMeasuredHeight());
            }

        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.layout(childLeft, 0, childLeft + child.getMeasuredWidth(), child.getMeasuredHeight());
            childLeft += child.getMeasuredWidth();
        }
    }


    //拦截事件的处理,当用户是水平方向滑动屏幕的时候，拦截事件，并且交个自己的也就是当前viewgroup的onTouchEvent进行处理

    int mInterceptX;
    int mInterceptY;
    boolean isIntecepted = false;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mInterceptX = (int) event.getRawX();
                mInterceptY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int deltX = (int) (event.getRawX() - mInterceptX);
                int deltY = (int) (event.getRawY() - mInterceptY);
                Log.d(TAG, "onInterceptTouchEvent deltX: " + Math.abs(deltX) + " deltY: " + Math.abs(deltY));
                isIntecepted = Math.abs(deltX) > Math.abs(deltY);
                mInterceptX = (int) event.getRawX();
                mInterceptY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        Log.d(TAG, "onInterceptTouchEvent : isIntecepted is " + isIntecepted);
        return isIntecepted;
    }


    int mLastX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                mLastX = (int) event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                smoothScroll(getScrollX(), (int) (mLastX - event.getRawX()),0);
                mLastX = (int) event.getRawX();
                break;
            case MotionEvent.ACTION_UP:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;

        }
        return true;
    }

    //这个方法需要配合Scroller使用
    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), 0);
            postInvalidate();
        }
    }

    /**
     * 借助scroller实现平滑的横向滚动
     *
     * @param startX 开始位置
     * @param dx     结束位置
     */
    private void smoothScroll(int startX, int dx,int duration) {
        Log.d(TAG, "smoothScroll: startX---> " + startX + " dx----> " + dx);
        mScroller.startScroll(startX, 0, dx, 0,duration);
        invalidate();
    }
}
