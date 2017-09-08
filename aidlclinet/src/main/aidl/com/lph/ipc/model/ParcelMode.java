package com.lph.ipc.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lph on 2017/9/3.
 */

public class ParcelMode implements Parcelable {

    private String name;
    private int age;

    protected ParcelMode(Parcel in) {
        name = in.readString();
        age = in.readInt();
    }

    public static final Creator<ParcelMode> CREATOR = new Creator<ParcelMode>() {
        @Override
        public ParcelMode createFromParcel(Parcel in) {
            return new ParcelMode(in);
        }

        @Override
        public ParcelMode[] newArray(int size) {
            return new ParcelMode[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeInt(this.age);
    }

    public ParcelMode(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "ParcelMode{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
