package com.lph.algorithm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    int[] array1 = {49, 38, 65, 97, 76, 13, 27};
    int[] array2 = {49, 38, 65, 97, 76, 13, 27};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void selectSort(View view) {
        SortUtil.selectSort(array1);
    }

    public void bubbleSort(View view) {
        SortUtil.bubbleSort(array2);
    }

    public void binarySearch(View view) {
        SortUtil.selectSort(array1);
        for (int i = 0; i < array1.length; i++) {
            int position = SortUtil.binarySearch(array1[i], array1);
            System.out.println("当前元素:" + array1[i] + "所在的位置是:" + position);
        }
        int position = SortUtil.binarySearch(100, array1);
        System.out.println("当前元素:" + 100 + "所在的位置是:" + position);
    }
}
