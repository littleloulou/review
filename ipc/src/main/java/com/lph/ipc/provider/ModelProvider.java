package com.lph.ipc.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by lph on 2017/9/8.
 */

public class ModelProvider extends ContentProvider {
    private static final String TAG = "ModelProvider";

    //客户端调用使用
    private static final String AUTHORITIES = "com.lph.ipc.modelProvider";
    //针对不同的数据，定义不同的uri，让客户端调用
    public static final Uri MODEL_CONTENT_URI = Uri.parse("content://" + AUTHORITIES + "/model");
    public static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITIES + "/user");

    //自定义匹配码，根据匹配返回的值，执行相应的操作
    private static final int MODEL_CONTENT_URI_CODE = 1;
    private static final int USER_CONTENT_URI_CODE = 2;

    private static final UriMatcher uriMather = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMather.addURI(AUTHORITIES, "model", MODEL_CONTENT_URI_CODE);
        uriMather.addURI(AUTHORITIES, "user", USER_CONTENT_URI_CODE);
    }

    private ModelSqliteHelper mSqliteHelper;
    private SQLiteDatabase mDb;


    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate: current Tread is " + Thread.currentThread().getName());
        initProviderData();
        return false;
    }

    private void initProviderData() {
        mSqliteHelper = new ModelSqliteHelper(getContext());
        mDb = mSqliteHelper.getWritableDatabase();
        mDb.execSQL("delete from " + ModelSqliteHelper.MODEL_TABLE_NAME);
        mDb.execSQL("delete from " + ModelSqliteHelper.USER_TABLE_NAME);
        mDb.execSQL("insert into " + ModelSqliteHelper.MODEL_TABLE_NAME + " (name ,age) values ('lph',18)");
        mDb.execSQL("insert into " + ModelSqliteHelper.MODEL_TABLE_NAME + " (name ,age) values ('zh',250)");
        mDb.execSQL("insert into " + ModelSqliteHelper.MODEL_TABLE_NAME + " (name ,age) values ('wtx',16)");
        mDb.execSQL("insert into " + ModelSqliteHelper.MODEL_TABLE_NAME + " (name ,age) values ('ypy',16)");


        mDb.execSQL("insert into " + ModelSqliteHelper.USER_TABLE_NAME + " (name ,aliname) values ('ypy','hehehe')");
        mDb.execSQL("insert into " + ModelSqliteHelper.USER_TABLE_NAME + " (name ,aliname) values ('hello','world')");


    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Log.d(TAG, "query:current Tread is " + Thread.currentThread().getName());
        String tableName = getTableName(uri);
        if (TextUtils.isEmpty(tableName)) {
            Log.w(TAG, "tableName is null");
            return null;
        }

        Log.d(TAG, "tableName is " + tableName);
        return mDb.query(tableName, strings, s, strings1, s1, null, s1);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Log.d(TAG, "query:current Tread is " + Thread.currentThread().getName());
        String tableName = getTableName(uri);
        long insert = mDb.insert(tableName, null, contentValues);
        if (insert > 0) {
            //通知数据发生了改变
            getContext().getContentResolver().notifyChange(uri, null);
        }
        Log.d(TAG, "insert " + tableName + insert + "rows has affected");
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        Log.d(TAG, "delete:current Tread is " + Thread.currentThread().getName());
        int delete = mDb.delete(getTableName(uri), s, strings);
        Log.d(TAG, "delete " + getTableName(uri) + delete + "rows has affected");
        if (delete > 0) {
            //通知数据发生了改变
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return delete;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        Log.d(TAG, "query:current Tread is " + Thread.currentThread().getName());
        int update = mDb.update(getTableName(uri), contentValues, s, strings);
        Log.d(TAG, "update " + getTableName(uri) + update + "rows has affected");
        if (update > 0) {
            //通知数据发生了改变
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return update;
    }

    private String getTableName(Uri uri) {
        Log.d(TAG, uri.toString());
        Log.d(TAG, "URI_CODE is " + uriMather.match(uri) + "");
        switch (uriMather.match(uri)) {
            case MODEL_CONTENT_URI_CODE:
                return ModelSqliteHelper.MODEL_TABLE_NAME;
            case USER_CONTENT_URI_CODE:
                return ModelSqliteHelper.USER_TABLE_NAME;
            default:
                return ModelSqliteHelper.MODEL_TABLE_NAME;
        }
    }
}
