package com.lph.ipc.serial;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.lph.ipc.model.ParcelMode;
import com.lph.ipc.R;

public class ParcelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcel);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            Parcelable model = extras.getParcelable("model");
            if(model!=null){
                String name = ((ParcelMode) model).getName();
                int age = ((ParcelMode) model).getAge();
                Log.i("ParcelActivity",String.format("name is %s,age is %d",name,age));
            }
        }else{
            Log.e("ParcelActivity","get extras failure");
        }
    }
}
