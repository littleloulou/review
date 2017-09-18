package com.lph.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;

import com.lph.view.customview.TestActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    {
        /**
         * 工具类：
         *
         */
        //获取最小的可以人为是滑动的距离，这个由于设备的不同而改变
//        int scaledTouchSlop = ViewConfiguration.get(this).getScaledTouchSlop();

        /**
         * 获取手指滑动速度的工具类
         */
//        VelocityTracker obtain = VelocityTracker.obtain();


    }

    public void customview(View view) {
        startActivity(new Intent(this, TestActivity.class));
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
