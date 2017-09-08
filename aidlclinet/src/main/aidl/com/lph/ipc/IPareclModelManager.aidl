// IPareclModelManager.aidl
package com.lph.ipc;

// Declare any non-default types here with import statements

import com.lph.ipc.model.ParcelMode;

import java.util.List;

interface IPareclModelManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    List<ParcelMode> getModes();

    void addModel( in ParcelMode mode);
}
