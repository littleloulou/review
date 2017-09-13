package com.lph.providerclient;

import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";

    private static final Uri MODEL_URL = Uri.parse("content://com.lph.ipc.modelProvider/model");
    private static final Uri USER_URL = Uri.parse("content://com.lph.ipc.modelProvider/user");

    private ContentObserver mObserver = new ContentObserver(new Handler()) {
        @Override
        public boolean deliverSelfNotifications() {
            return super.deliverSelfNotifications();
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            Log.d(TAG, "current change ");
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            Log.d(TAG, "current change is " + uri.toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getContentResolver().registerContentObserver(MODEL_URL, false, mObserver);
        getContentResolver().registerContentObserver(USER_URL, false, mObserver);
    }


    public void query(View view) {
        Cursor cursor = getContentResolver().query(MODEL_URL, null, null, null, null);
        if (cursor == null) {
            Log.w(TAG, "cursor is null");
        } else {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int age = cursor.getInt(cursor.getColumnIndex("age"));
                Log.d(TAG, String.format("currentData: name is --->%s,age is ------>%d", name, age));
            }
            cursor.close();
        }

        Cursor cursor2 = getContentResolver().query(USER_URL, null, null, null, null);
        if (cursor2 == null) {
            Log.w(TAG, "cursor2 is null");

        } else {
            while (cursor2.moveToNext()) {
                String name = cursor2.getString(cursor2.getColumnIndex("name"));
                String aliname = cursor2.getString(cursor2.getColumnIndex("aliname"));
                Log.d(TAG, String.format("currentData: name is --->%s,aliname is ------>%s", name, aliname));
            }
            cursor2.close();
        }
    }


    public void insert(View view) {
        ContentValues values = new ContentValues();
        values.put("name", "insert" + SystemClock.currentThreadTimeMillis());
        values.put("age", new Random().nextInt() * 100 % 100);
        insert2(MODEL_URL, values);
        values.put("name", "insert2" + SystemClock.currentThreadTimeMillis());
        values.put("aliname", "a" + new Random().nextInt() * 100 % 100);
        ContentValues values2 = new ContentValues();
        insert2(USER_URL, values);
    }

    private void insert2(Uri userUrl, ContentValues values) {
        getContentResolver().insert(userUrl, values);
    }


    public void delete(View view) {
        getContentResolver().delete(MODEL_URL, "name = ?", new String[]{"lph"});
        getContentResolver().delete(USER_URL, "name = ?", new String[]{"hello"});
    }


    public void update(View view) {
        ContentValues values = new ContentValues();
        values.put("name", "大傻逼");
        getContentResolver().update(MODEL_URL, values, "name = ?", new String[]{"zh"});
        getContentResolver().update(USER_URL, values, "name = ?", new String[]{"ypy"});
    }

    @Override
    protected void onDestroy() {
        getContentResolver().unregisterContentObserver(mObserver);
        super.onDestroy();
    }
}
