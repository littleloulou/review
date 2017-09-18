package com.lph.view.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.lph.view.R;

/**
 * Created by lph on 2017/9/17.
 */

public class CircleVIew extends View {

    private Paint mPaint;

    private int mDefaultSize = 240;

    private int mWidth;
    private int mHeight;
    private int mColor;

    public CircleVIew(Context context) {
        this(context, null);
    }

    public CircleVIew(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleVIew(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleVIew);
        mColor = array.getColor(R.styleable.CircleVIew_fill_color, Color.GREEN);
        init();

    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //在自定义view的过程中，view的measure过程中，view的大小受到了父view的测量规则和自己layoutParameter的限制，当自己自wrapconet时，自己的大小就是父view剩余的大小
        //这样的结果显示不是我们所期望的，所以这里可以设置一些默认的宽和高
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = width;
        } else {
            mWidth = mDefaultSize;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = height;
        } else {
            mHeight = mDefaultSize;
        }
        //调用这个方法是很有必要的，因为这个方法就是设置view在测量结束之后view的宽度和高度的
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //把pading计算在内，否则设置padding属性是没有任何的效果的,padding指的是父容器留出的空间，当前view就是父容器，而这个要画的圆的就是子元素
        int circleWidth = Math.max(0, mWidth - getPaddingLeft() - getPaddingRight());
        int circleHeight = Math.max(0, mHeight - getPaddingTop() - getPaddingBottom());

        canvas.drawCircle(circleWidth / 2 + getPaddingLeft(), circleHeight / 2 + getPaddingTop(), Math.min(circleWidth, circleHeight) / 2, mPaint);
    }
}
