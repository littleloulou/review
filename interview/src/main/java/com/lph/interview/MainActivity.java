package com.lph.interview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        text();
    }

    public void text() {
        Integer f1 = 100, f2 = 100, f3 = 100;
        System.out.println(f1 == f2);
        Integer a = new Integer(3);
        int b = 3;
        Integer c = 3;
        System.out.println(a == b);//true
        System.out.println(a==c);//false
    }
}
