package com.lph.view.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by lph on 2017/9/15.
 */

public class FollowHandView extends android.support.v7.widget.AppCompatTextView {
    public FollowHandView(Context context) {
        super(context);
    }

    public FollowHandView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FollowHandView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int mLastX;
    private int mLastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = (int) event.getRawX();
                mLastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int deltX = (int) (event.getRawX() - mLastX);
                int deltY = (int) (event.getRawY() - mLastY);
                //must above android 3.0
                this.setTranslationX(getTranslationX() + deltX);
                this.setTranslationY(getTranslationY() + deltY);
                mLastX = (int) event.getRawX();
                mLastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
