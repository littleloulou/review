package com.lph.ipc.provider;

/**
 * Created by lph on 2017/9/8.
 */

public interface Summary {
    /**
     * ContentProvider 也是跨进程通信的一种方式，文件共享
     */

    /**
     * 有关ContentProvider的自定义权限
     *
     *1、概述
     在AndroidManifest.xml中provider标签中有三个额外的参数permission、readPermission、writePermission;
     先看下面这段代码：

     [html] view plain copy
     <provider
     android:name=".PeopleContentProvider"
     android:authorities="com.harvic.provider.PeopleContentProvider"
     android:exported="true"
     android:permission="com.harvic.contentProviderBlog"
     android:readPermission="com.harvic.contentProviderBlog.read"
     android:writePermission="com.harvic.cotentProviderBlog.write"/>
     在这段代码中有几个参数要特别注意一下：
     exported:这个属性用于指示该服务是否能被其他程序应用组件调用或跟他交互； 取值为（true | false），如果设置成true，则能够被调用或交互，否则不能；设置为false时，只有同一个应用程序的组件或带有相同用户ID的应用程序才能启动或绑定该服务。具体参见：《Permission Denial: opening provider 隐藏的android:exported属性的含义》
     readPermission:使用Content Provider的查询功能所必需的权限，即使用ContentProvider里的query()函数的权限；
     writePermission：使用ContentProvider的修改功能所必须的权限，即使用ContentProvider的insert()、update()、delete()函数的权限；
     permission：客户端读、写 Content Provider 中的数据所必需的权限名称。 本属性为一次性设置读和写权限提供了快捷途径。 不过，readPermission和writePermission属性优先于本设置。 如果同时设置了readPermission属性，则其将控制对 Content Provider 的读取。 如果设置了writePermission属性，则其也将控制对 Content Provider 数据的修改。也就是说如果只设置permission权限，那么拥有这个权限的应用就可以实现对这里的ContentProvider进行读写；如果同时设置了permission和readPermission那么具有readPermission权限的应用才可以读，拥有permission权限的才能写！也就是说只拥有permission权限是不能读的，因为readPermission的优先级要高于permission；如果同时设置了readPermission、writePermission、permission那么permission就无效了。
     2、实例
     （1）有关自定义权限
     由于在privoder标签中要用到自定义权限，比如：

     [html] view plain copy
     <provider
     android:name=".PeopleContentProvider"
     android:authorities="com.harvic.provider.PeopleContentProvider"
     android:exported="true"
     android:readPermission="com.harvic.contentProviderBlog.read"/>
     比如，在这里我们定义一个readPermission字符串，单纯写一个字符串是毫无意义的，因为根本系统的任何权限都是要申请的，没有申请过的一串String代表的字符串系统根本无法识别，也就是说谁进来都会被挡在外面！
     所以在application标签的同级目录，写上申请permission的代码：
     [html] view plain copy
     <permission
     android:name="com.harvic.contentProviderBlog.read"
     android:label="provider pomission"
     android:protectionLevel="normal" />
     这样，我们的permission才会在系统中注册，在第三方应用中使用<uses-permission来声明使用权限时，才有意义；如果不申请，那么系统中是不存在这个权限的，那么虽然你使用了<uses-permission来申请使用权限，但系统中根本找不到这个权限，所以到provider匹配时，就会出现permission-deny的错误；
     [html] view plain copy
     <uses-permission android:name="com.harvic.contentProviderBlog.read"/>
     有关自定义权限的内容，请参考《声明、使用与自定义权限》
     （2）、带有权限的ContentProvder实例
     首先在ContentProviderBlog中首先向系统申请两个权限：
     分别对应读数据库权限和写数据库权限

     [html] view plain copy
     <permission
     android:name="com.harvic.contentProviderBlog.read"
     android:label="provider read pomission"
     android:protectionLevel="normal" />
     <permission
     android:name="com.harvic.contentProviderBlog.write"
     android:label="provider write pomission"
     android:protectionLevel="normal" />
     情况一：使用读写权限
     然后在provider中，我们这里同时使用读、写权限：
     [html] view plain copy
     <provider
     android:name=".PeopleContentProvider"
     android:authorities="com.harvic.provider.PeopleContentProvider"
     android:exported="true"
     android:readPermission="com.harvic.contentProviderBlog.read"
     android:writePermission="com.harvic.cotentProviderBlog.write" />
     在这种情况下，使用第三方应用同时申请使用这两个权限才可以对数据库读写，即：
     [html] view plain copy
     <uses-permission android:name="com.harvic.contentProviderBlog.read"/>
     <uses-permission android:name="com.harvic.contentProviderBlog.write"/>
     情况二：仅添加读权限
     如果我们在provider中，仅添加读共享数据库的权限，那第三方应该怎么申请权限才能读写数据库呢？
     [html] view plain copy
     <provider
     android:name=".PeopleContentProvider"
     android:authorities="com.harvic.provider.PeopleContentProvider"
     android:exported="true"
     android:readPermission="com.harvic.contentProviderBlog.read"/>
     从上面可以看到，我们只添加了readPermission，那第三方应用在不申请任何权限的情况下是可以写数据库，但当读数据库时就需要权限了；即如果要读数据库需要添加如下使用权限声明：
     [html] view plain copy
     <uses-permission android:name="com.harvic.contentProviderBlog.read"/>
     在上一篇的基础上，代码不需要动，只需要在AndroidManifest.xml中写上权限定义与声明，即可；
     （3）、效果：
     在实例代码中，在provider中添加了读写权限，但在第三方APP：UseProvider中，仅添加了读权限，所以在利用URI来读共享数据库的时候结果是正常的，但在写数据库时，就会抛出permission-denied错误，如果不用try...catch...捕捉，就会造成crash;结果截图如下：

     query()操作


     insert()、delete() 或 update()操作时，就会抛出异常
     *
     */
}
