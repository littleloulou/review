package com.lph.animation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class MainActivity extends AppCompatActivity {

    private View mVTarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVTarget = findViewById(R.id.iv);
    }

    public void start_view_animation(View view) {
        //在res/anim/文件夹下创建一个R.anim.viewanimation，资源文件，通过AnimationUtils加载到代码里面
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.viewanimation);
        mVTarget.startAnimation(animation);
    }
}
