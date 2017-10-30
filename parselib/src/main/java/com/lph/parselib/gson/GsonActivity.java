package com.lph.parselib.gson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.lph.parselib.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.util.List;

public class GsonActivity extends AppCompatActivity {

    private static final String TAG = "GsonActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gson);
    }

    {
        /**
         * 使用的一般步骤：
         *
         */
    }

    //解析节本类型
    public void baseDataType(View view) {
        Gson gson = new Gson();
        Integer intData = gson.fromJson("100", int.class);
        Double aDouble = gson.fromJson("\"99.99\"", double.class);
        Boolean aTrue = gson.fromJson("true", boolean.class);
        String strdata = gson.fromJson("strdata", String.class);
        Log.w(TAG, String.format("baseDataType: int--->%d double---->%f boolean----->%b,string---->%s", intData, aDouble, aTrue, strdata));
    }

    //解析javabean类型
    public void javaBeanType(View view) {
        Gson gson = new Gson();
        User lph = new User("lph", 24, "719624172@qq.com");
        //javabean序列成为一个字符串
        String jsonObj = gson.toJson(lph, User.class);

        //从字符串中解析一个javabean
        String jsonData = "{\"name\":\"android开发者\",\"age\":18,\"email\":\"629123@lph.com\"}";
        User user = gson.fromJson(jsonData, User.class);
        Log.i(TAG, String.format("javaBeanType:jsonObj----->%s  user------>%s", jsonObj, user));
    }

    //解析array类型到list
    public void parseArray(View view) {
        Gson gson = new Gson();
        String stringArray = "['android','java','php']";
        List<String> strList = gson.fromJson(stringArray, new TypeToken<List<String>>() {
        }.getType());
        Log.i(TAG, String.format("parseArray---->%s", strList.toString()));
    }

    //序列化stream 反序列化stream
    public void jsonStream(View view) {
        //将对象序列化到文件中
        User lph = new User("lph", 24, "719624172@qq.com");
        JsonWriter jsonWriter = null;
        try {
            jsonWriter = new JsonWriter(new BufferedWriter(new FileWriter(new File(getFilesDir(), "lph.obj"))));
            jsonWriter.beginObject()
                    .name("name").value(lph.getName())
                    .name("age").value(lph.getAge())
                    .name("email").value(lph.getEmail())
                    .endObject();
            jsonWriter.flush();
            Log.i(TAG, "write obj success...");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (jsonWriter != null) {
                try {
                    jsonWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        JsonReader reader = null;
        try {
            reader = new JsonReader(new BufferedReader(new FileReader(new File(getFilesDir(), "lph.obj"))));
            User user = new User();
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                switch (name) {
                    case "name":
                        user.setName(reader.nextString());
                        break;
                    case "age":
                        user.setAge(reader.nextInt());
                        break;
                    case "email":
                        user.setEmail(reader.nextString());
                        break;
                }
            }
            reader.endObject();
            Log.i(TAG, "load obj from file " + user);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
