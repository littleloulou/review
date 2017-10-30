package com.lph.tools.git;

/**
 * Created by lph on 2017/9/18.
 */

public interface Summary {
    //添加远程仓库
   /* 第1步：创建SSH Key。在用户主目录下，看看有没有.ssh目录，如果有，再看看这个目录下有没有id_rsa和id_rsa.pub这两个文件，如果已经有了，
    可直接跳到下一步。如果没有，打开Shell（Windows下打开Git Bash），创建SSH Key：*/
   //代码 $ ssh-keygen -t rsa -C "youremail@example.com"

    //第2步，
    /**
     * 如果一切顺利的话，可以在用户主目录里找到.ssh目录，里面有id_rsa和id_rsa.pub两个文件，
     * 这两个就是SSH Key的秘钥对，id_rsa是私钥，不能泄露出去，id_rsa.pub是公钥，可以放心地告诉任何人。
     */
    //第3步，
    /**
     * 在github设置界面中添加自己的公钥，添加成功后，认证阶段配置完成
     *
     */

    //第4步，建立联系
//    $ git remote add origin git@github.com:michaelliao/learngit.git（你的项目地址）

    //第5步，推送本地库内容

    /**
     * 把本地库的所有内容推送到远程库上：
     $ git push -u origin master
     */

    /**
     * 由于远程库是空的，我们第一次推送master分支时，加上了-u参数，Git不但会把本地的master分支内容推送的远程新的master分支，
     * 还会把本地的master分支和远程的master分支关联起来，在以后的推送或者拉取时就可以简化命令。
     *
     */

    //git 常用命令：



}
