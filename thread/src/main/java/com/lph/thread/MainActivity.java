package com.lph.thread;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    /**
     * 资源
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Integer> resource = new ArrayList<>();
        //初始化资源
        new Thread(new Provider(resource)).start();
        new Thread(new Consumer(resource)).start();
    }

    /**
     * 生产者
     */
    class Provider implements Runnable {

        List<Integer> mResource;

        public Provider(List<Integer> resource) {
            mResource = resource;
        }

        @Override
        public void run() {
            synchronized (mResource) {
                while (true) {
                    SystemClock.sleep(500);
                    //当资源大于0的时候，不再生产资源
                    if (mResource.size() > 0) {
                        try {
                            mResource.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    int e = new Random().nextInt(100);
                    System.out.println("创建一个资源Runnable---Provider:" + e);
                    mResource.add(e);
                    //生产了一个资源，告诉消费者，让消费者消费
                    mResource.notify();
                }
            }
        }
    }

    /**
     * 消费之
     */
    class Consumer implements Runnable {

        List<Integer> mResource;

        public Consumer(List<Integer> resource) {
            this.mResource = resource;
        }

        @Override
        public void run() {
            synchronized (mResource) {
                while (true) {
                    SystemClock.sleep(500);
                    //当资源不足的时候,释放cpu执行权,释放资源,让生产者生产
                    if (mResource.size() <= 0) {
                        try {

                            mResource.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    Integer remove = mResource.remove(0);
                    System.out.println("消费一个资源Runnable---Consumer:" + remove);
                    //让生产者解除等待，生产资源
                    mResource.notify();
                }
            }
        }
    }
}
