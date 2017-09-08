package com.lph.ipc.aidl;

/**
 * Created by lph on 2017/9/5.
 */

public interface Summary {
    //这是服务端的代码
    //adilclient 这个是客户端，你在那里看看，那个项目
    //aidl 使用的一般流程：创建服务端
    //在android视图下，在项目右键，选择新建aidl文件，创建aidl文件，如果有实体的情况下，请看demo，如何处理
    //ide会在/build/generated/source/aidl/debug 下自动生成.java文件
    //创建service 并创建一个内部类实现 自动生成类的。stub的抽象方法

    //应该注意的是无论是在服务端，还是在客户端都应该在gradle文件中配置：
    //如果不配置，会出现类无法找到的问题
    /*sourceSets {
        main
            java.srcDirs = ['src/main/java', 'src/main/aidl']
        }
    }*/
}
