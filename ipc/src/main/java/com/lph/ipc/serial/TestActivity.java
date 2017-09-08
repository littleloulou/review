package com.lph.ipc.serial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.lph.ipc.model.ParcelMode;
import com.lph.ipc.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TestActivity extends AppCompatActivity {

    private static File objDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        objDir = new File(getFilesDir(),"serial");
        boolean res = objDir.mkdir();
        System.out.println(String.format("create dir is success ? %b,objDir is dir %b,objDir Path is %s",res,objDir.isDirectory(),objDir.getAbsolutePath()));
    }

    public void serial(View view) {
        SerialMode mode = new SerialMode();
        mode.setName("lph");
        mode.setAge(25);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(objDir, "a.obj")));
            oos.writeObject(mode);
            oos.close();
            Toast.makeText(this, "写入成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void reserial(View view) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(objDir, "a.obj")));
            SerialMode serialMode = (SerialMode) ois.readObject();
            Toast.makeText(this, String.format("name is %s,age is %d", serialMode.getName(), serialMode.getAge()), Toast.LENGTH_SHORT).show();
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void parcel(View view){
        ParcelMode model = new ParcelMode("lph", 20);
        Intent intent = new Intent(this,ParcelActivity.class);
        intent.putExtra("model",model);
        startActivity(intent);
    }
    {

        /**发现在mx5手机中，在／data/data/packageName/files/目录下创建新的文件夹是失败的，可以创建新的文件 android version 5.1
         *
         *
         * /System.out: create dir is success ? false,objDir is dir false,objDir Path is /data/data/com.lph.ipc/files/serial
         *
         * 09-03 16:27:08.562 24660-24660/? E/System: stat file error, path is /data/app/com.lph.ipc-1/lib/arm64, exception is android.system.ErrnoException: stat failed: ENOENT (No such file or directory)
         09-03 16:27:08.564 24660-24660/? W/linker: /system/lib64/libfilterUtils.so: unused DT entry: type 0x6ffffffe arg 0x808
         09-03 16:27:08.564 24660-24660/? W/linker: /system/lib64/libfilterUtils.so: unused DT entry: type 0x6fffffff arg 0x2
         09-03 16:27:11.002 24660-24660/com.lph.ipc I/System.out: create dir is success ? false,objDir is dir false,objDir Path is /data/data/com.lph.ipc/files/serial
         09-03 16:27:12.082 24660-24660/com.lph.ipc W/System.err: java.io.FileNotFoundException: /data/data/com.lph.ipc/files/serial/a.obj: open failed: ENOTDIR (Not a directory)
         09-03 16:27:12.082 24660-24660/com.lph.ipc W/System.err:     at libcore.io.IoBridge.open(IoBridge.java:512)
         09-03 16:27:12.082 24660-24660/com.lph.ipc W/System.err:     at java.io.FileOutputStream.<init>(FileOutputStream.java:87)
         09-03 16:27:12.082 24660-24660/com.lph.ipc W/System.err:     at java.io.FileOutputStream.<init>(FileOutputStream.java:72)
         09-03 16:27:12.082 24660-24660/com.lph.ipc W/System.err:     at com.lph.ipc.serial.TestActivity.serial(TestActivity.java:38)
         09-03 16:27:12.082 24660-24660/com.lph.ipc W/System.err:     at java.lang.reflect.Method.invoke(Native Method)
         09-03 16:27:12.082 24660-24660/com.lph.ipc W/System.err:     at java.lang.reflect.Method.invoke(Method.java:372)
         09-03 16:27:12.082 24660-24660/com.lph.ipc W/System.err:     at android.support.v7.app.AppCompatViewInflater$DeclaredOnClickListener.onClick(AppCompatViewInflater.java:288)
         09-03 16:27:12.082 24660-24660/com.lph.ipc W/System.err:     at android.view.View.performClick(View.java:4918)
         09-03 16:27:12.082 24660-24660/com.lph.ipc W/System.err:     at android.view.View$PerformClick.run(View.java:20399)
         09-03 16:27:12.082 24660-24660/com.lph.ipc W/System.err:     at android.os.Handler.handleCallback(Handler.java:815)
         09-03 16:27:12.082 24660-24660/com.lph.ipc W/System.err:     at android.os.Handler.dispatchMessage(Handler.java:104)
         09-03 16:27:12.082 24660-24660/com.lph.ipc W/System.err:     at android.os.Looper.loop(Looper.java:194)
         09-03 16:27:12.082 24660-24660/com.lph.ipc W/System.err:     at android.app.ActivityThread.main(ActivityThread.java:5877)
         09-03 16:27:12.082 24660-24660/com.lph.ipc W/System.err:     at java.lang.reflect.Method.invoke(Native Method)
         09-03 16:27:12.082 24660-24660/com.lph.ipc W/System.err:     at java.lang.reflect.Method.invoke(Method.java:372)
         09-03 16:27:12.082 24660-24660/com.lph.ipc W/System.err:     at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:1019)
         09-03 16:27:12.082 24660-24660/com.lph.ipc W/System.err:     at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:814)
         09-03 16:27:12.082 24660-24660/com.lph.ipc W/System.err: Caused by: android.system.ErrnoException: open failed: ENOTDIR (Not a directory)
         09-03 16:27:12.083 24660-24660/com.lph.ipc W/System.err:     at libcore.io.Posix.open(Native Method)
         09-03 16:27:12.083 24660-24660/com.lph.ipc W/System.err:     at libcore.io.BlockGuardOs.open(BlockGuardOs.java:186)
         09-03 16:27:12.083 24660-24660/com.lph.ipc W/System.err:     at libcore.io.IoBridge.open(IoBridge.java:493)
         09-03 16:27:12.083 24660-24660/com.lph.ipc W/System.err: 	... 16 more

         如果使用的是sdcard是没有问题的，可以成功创建

         华为荣耀手机 android 4.4.2 两种都可以成功

         接下来，使用模拟器测试

         我发现在 /data/data/packagename/files/  目录下创建文件夹 mx5失败 创建文件没有问题 基于andorid5.1
         华为荣耀6 都没有问题
         模拟器5.1 也都没有问题，可以成功创建文件和文件夹

         //然后莫名其妙的又好了
         *
         */
    }
    {
        /**
         * 那么什么时候用Serializable 什么时候用Parcelable呢？
         * Serializable 是java自带的序列化方式，开销比较大，适合保存文件，网络传输
         * Parcelable 是andorid特有的，开销比较小，适合内存中对象序列化，如果是文件，网络传输数据比较麻烦
         */



    }
}
