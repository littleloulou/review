package com.lph.binderpoolserver;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lph on 2017/9/14.
 */

public class BinderRequestCode implements Parcelable {
    public static final int REQUEST_CODE_SECURYTIY = 0;
    public static final int REQUEST_CODE_COMPUTE = 1;

    private int mValue = 0;


    public BinderRequestCode(int mValue) {
        this.mValue = mValue;
    }

    protected BinderRequestCode(Parcel in) {
        mValue = in.readInt();
    }

    public static final Creator<BinderRequestCode> CREATOR = new Creator<BinderRequestCode>() {
        @Override
        public BinderRequestCode createFromParcel(Parcel in) {
            return new BinderRequestCode(in);
        }

        @Override
        public BinderRequestCode[] newArray(int size) {
            return new BinderRequestCode[size];
        }
    };


    public int getValue() {
        return mValue;
    }

    public void setValue(int value) {
        mValue = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(mValue);
    }
}
