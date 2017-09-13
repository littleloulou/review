package com.lph.ipc.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lph on 2017/9/14.
 * SQLiteOpenHelper 继承系统的数据库操作类
 * 自定义：数据库名称 标名称版本号
 * 创建表的sql语句
 * 在oncreate方法里面创建表
 *
 */

public class ModelSqliteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "model_provider.db";
    public static final String MODEL_TABLE_NAME = "tb_model";
    public static final String USER_TABLE_NAME = "tb_user";

    private static int DB_VERSION = 1;


    private static final String CREATE_TABLE_MODEL = "create table if not exists " + MODEL_TABLE_NAME + " (_id integer primary key,name text,age integer)";
    private static final String CREATE_TABLE_USER = "create table if not exists " + USER_TABLE_NAME + " (_id integer primary key,name text,aliname text,age integer)";


    public ModelSqliteHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
           //如果表不存在，创建表
            sqLiteDatabase.execSQL(CREATE_TABLE_MODEL);
            sqLiteDatabase.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
