package com.lph.netlib.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.lph.netlib.R;
import com.lph.netlib.retrofit.api.RetrofitApi;

public class RetrofitTestActivity extends AppCompatActivity {

    public final static String BASE_URL_JIN_3 = "http://fy.iciba.com/ajax.php/";
    public final static String BASE_URL_YOU_DAO = "http://fanyi.youdao.com/translate/";
    private RetrofitApi mApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retroft_test);
        mApi = new RetrofitApi();
    }

    public void get(View view) {
        mApi.getTranslation();
    }
}
