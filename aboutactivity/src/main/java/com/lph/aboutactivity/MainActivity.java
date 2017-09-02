package com.lph.aboutactivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends BaseExampleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //acitiv
        // 正常启动和异常启动ity 启动流程
        //正常启动和异常启动
        findViewById(R.id.tv_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SecondActivity.class));
            }
        });

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //显式启动是指：知道启动对象的类名和包名
                //隐式启动是指：根据匹配规则去查找

                //由于系统在启动新的activity的时候，会自动在intent上添加Intent.CATEGORY_DEFAULT
                //所以在清单文件中必须添加这个Category
                /**
                 *
                 * <intent-filter>
                 <action android:name="com.lph.intent_SEND"/>
                 <action android:name="com.lph.intent_RECEIVER"/>
                 <action android:name="com.lph.intent_SEND_1"/>
                 <action android:name="com.lph.intent_SEND_2"/>
                 <category android:name="android.intent.category.DEFAULT"/>
                 <category android:name="com.lph.intent_Category"/>
                 </intent-filter>
                 *
                 */
                Intent intent = new Intent();
                //隐式启动acitvity
                intent.setAction("com.lph.intent_SEND");
//                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.addCategory("com.lph.intent_Category");
                intent.setDataAndType(Uri.parse("http://www.baidu.com"),"*/jpg");
                startActivity(intent);

                /**
                 * action:intent必须要有一个action和interfilter中一个action匹配，只要一个匹配就行
                 * catedory ：intent中所有的category必须和intentfilter中的所有的category匹配，也就是说intent中的category必须要找到和intentfilter匹配的category
                 * category可以在intent中不出现，系统默认会添加一个Intent.CATEGORY_DEFAULT
                 * data：data 和 action的匹配模式相同
                 */
            }
        });

    }

    {

        //acitvity 生命周期：
        /**
         *1、正常的生命周期：oncreate - 》 onstart -》 onresume -》onpuase -》onstop -》ondestroy
         *  这里应该注意的是在onPuase里面不要做太多过于耗时的操作，因为只有当前的acitvityonPause之后，后面新启动的acitvity才会
         *  开启它的生命周期，可以适当的写在onstop里面
         *   main： oncrate onstart onresume onpause
         *   second ：oncreate onstart onresume
         *   main ： onstop
         *   second：  onpause
         *   main： onrestart onresume
         *   second ：onstop ondestroy
         *
         *
         */

        /**log
         * 09-02 20:05:08.217 10704-10704/com.lph.aboutactivity D/com.lph.aboutactivity.MainActivity: onCreate
         09-02 20:05:08.240 10704-10704/com.lph.aboutactivity D/com.lph.aboutactivity.MainActivity: onStart
         09-02 20:05:08.241 10704-10704/com.lph.aboutactivity D/com.lph.aboutactivity.MainActivity: onResume
         09-02 20:05:08.243 10704-10704/com.lph.aboutactivity D/ViewRootImpl: hardware acceleration is enabled, this = ViewRoot{1024b159 com.lph.aboutactivity/com.lph.aboutactivity.MainActivity,ident = 2}
         09-02 20:05:08.267 10704-10743/com.lph.aboutactivity D/Surface: Surface::connect(this=0x7faa254200,api=1)
         09-02 20:05:35.296 10704-10704/com.lph.aboutactivity D/com.lph.aboutactivity.MainActivity: onPuase
         09-02 20:05:35.304 10704-10704/com.lph.aboutactivity D/com.lph.aboutactivity.SecondActivity: onCreate
         09-02 20:05:35.317 10704-10704/com.lph.aboutactivity D/com.lph.aboutactivity.SecondActivity: onStart
         09-02 20:05:35.319 10704-10704/com.lph.aboutactivity D/com.lph.aboutactivity.SecondActivity: onResume
         09-02 20:05:35.321 10704-10704/com.lph.aboutactivity D/ViewRootImpl: hardware acceleration is enabled, this = ViewRoot{372202fb com.lph.aboutactivity/com.lph.aboutactivity.SecondActivity,ident = 3}
         09-02 20:05:35.349 10704-10743/com.lph.aboutactivity D/Surface: Surface::connect(this=0x7faa255000,api=1)
         09-02 20:05:35.398 10704-10743/com.lph.aboutactivity D/Surface: Surface::disconnect(this=0x7faa254200,api=1)
         09-02 20:05:35.652 10704-10704/com.lph.aboutactivity D/com.lph.aboutactivity.MainActivity: onStop

         以上是首先启动了mainacitvity 然后 在启动 secondacitvity，生命周期，

         * 09-02 20:08:27.405 10704-10704/com.lph.aboutactivity D/com.lph.aboutactivity.SecondActivity: onPuase
         09-02 20:08:27.411 10704-10704/com.lph.aboutactivity D/com.lph.aboutactivity.MainActivity: onRestart
         09-02 20:08:27.412 10704-10704/com.lph.aboutactivity D/com.lph.aboutactivity.MainActivity: onStart
         09-02 20:08:27.412 10704-10704/com.lph.aboutactivity D/com.lph.aboutactivity.MainActivity: onResume
         09-02 20:08:27.422 10704-10743/com.lph.aboutactivity D/Surface: Surface::connect(this=0x7faa254200,api=1)
         09-02 20:08:27.452 10704-10743/com.lph.aboutactivity D/Surface: Surface::disconnect(this=0x7faa255000,api=1)
         09-02 20:08:27.694 10704-10704/com.lph.aboutactivity D/com.lph.aboutactivity.SecondActivity: onStop
         09-02 20:08:27.694 10704-10704/com.lph.aboutactivity D/com.lph.aboutactivity.SecondActivity: onDestroy

         按下返回键之后，secondactivity 失去焦点，mainacitvity重新开始，重新获取焦点

         *屏幕发生旋转之后，如果没有进行参数配置，那么activity将会重启：当前mainacitvity是活动窗口，然后进入onPuase ---》 onSaveInstanceState（在这里记录当前的activtiy的状态信息）---》onStop ---》onDestroy
         * 然后会创建新的acitvity onCreate ----》onStart ---》onRestoreInstanceState（根据之前记录的activity状态信息，读取状态信息）---》onresume 这就是我们看到
         * 新的activty
         *
         * 09-02 20:19:19.693 18480-18510/com.lph.aboutactivity D/Surface: Surface::connect(this=0x7faa254200,api=1)
         09-02 20:20:06.545 18480-18480/com.lph.aboutactivity D/com.lph.aboutactivity.MainActivity: onPuase
         09-02 20:20:06.547 18480-18480/com.lph.aboutactivity D/com.lph.aboutactivity.MainActivity: onSaveInstanceState
         09-02 20:20:06.547 18480-18480/com.lph.aboutactivity D/com.lph.aboutactivity.MainActivity: onStop
         09-02 20:20:06.550 18480-18480/com.lph.aboutactivity D/com.lph.aboutactivity.MainActivity: onDestroy
         09-02 20:20:06.558 18480-18510/com.lph.aboutactivity D/Surface: Surface::disconnect(this=0x7faa254200,api=1)
         09-02 20:20:06.572 18480-18480/com.lph.aboutactivity D/com.lph.aboutactivity.MainActivity: onCreate
         09-02 20:20:06.587 18480-18480/com.lph.aboutactivity D/com.lph.aboutactivity.MainActivity: onStart
         09-02 20:20:06.587 18480-18480/com.lph.aboutactivity D/com.lph.aboutactivity.MainActivity: onRestoreInstanceState
         09-02 20:20:06.589 18480-18480/com.lph.aboutactivity D/com.lph.aboutactivity.MainActivity: onResume
         09-02 20:20:06.591 18480-18480/com.lph.aboutactivity D/ViewRootImpl: hardware acceleration is enabled, this = ViewRoot{35cd514b com.lph.aboutactivity/com.lph.aboutactivity.MainActivity,ident = 1}
         09-02 20:20:06.612 18480-18510/com.lph.aboutactivity D/Surface: Surface::connect(this=0x7faa254200,api=1)

         onSaveInstanceState onRestoreInstanceState 这两个回调只会在activity出现异常终止的情况下才会被框架调用

         配置 android:configChanges="orientation|screenSize" 之后，然后旋转屏幕2次，不再重新创建acitivty，而是调用了onConfigurationChanged这个方法

         log

         *09-02 20:36:53.349 27975-27975/com.lph.aboutactivity W/com.lph.aboutactivity.MainActivity: onCreate
         09-02 20:36:53.367 27975-27975/com.lph.aboutactivity W/com.lph.aboutactivity.MainActivity: onStart
         09-02 20:36:53.368 27975-27975/com.lph.aboutactivity W/com.lph.aboutactivity.MainActivity: onResume
         09-02 20:37:01.329 27975-27975/com.lph.aboutactivity W/com.lph.aboutactivity.MainActivity: onConfigurationChanged
         09-02 20:37:14.885 27975-27975/com.lph.aboutactivity W/com.lph.aboutactivity.MainActivity: onConfigurationChanged

         */
    }

    {
        //acitvity 启动模式化
        // stand singleTop singleTask singleInstance
//        Intent.FLAG_ACTIVITY_NEW_TASK
    }

    {
        //intentFilter 匹配规则：action category data
        //一个acitvity可以设置多个intentFilter，只要intent有一组匹配，就可以启动此activity
        //action: 只要有这个一定要有
    }

}
