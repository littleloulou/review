// OnNewModeAddListener.aidl
package com.lph.ipc;
  import com.lph.ipc.model.ParcelMode;
// Declare any non-default types here with import statements

interface OnNewModeAddListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);


     void OnNewsModeAdd(in ParcelMode newMode);

}
