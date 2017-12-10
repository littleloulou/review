package review.lph.com.socketserver.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.support.annotation.IntDef;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class TcpServerService extends Service {

    private static final String TAG = "TcpServerService";
    private boolean mServiceIsAlive = false;

    private String[] mResponseStrings = new String[]{
            "hello client ",
            "what's your ",
            "da sa bi  ",
            "我 知道 了 ",
            "哈哈哈哈哈哈哈"

    };

    public TcpServerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        mServiceIsAlive = true;
        //开启socket任务
        new Thread(new ServerTask()).start();
        Log.d(TAG, "start watch client connected");
        super.onCreate();
    }


    @Override
    public void onDestroy() {
        mServiceIsAlive = false;
        super.onDestroy();
    }

    private class ServerTask implements Runnable {
        ServerSocket serverSocket = null;
        Socket client = null;

        @Override
        public void run() {
            try {
                //创建了一个服务端对象，用来和客户端交互，端口号必须保证和客户端的一致
                serverSocket = new ServerSocket(8888);
                //开启一个线程不停的接收客户端
                new Thread() {
                    @Override
                    public void run() {
                        while (mServiceIsAlive) {
                            try {
                                //这是一个阻塞方法，会一直等待客户端的请求，当接收到客户端的请求的时候，继续执行，循环接收客户端请求
                                client = serverSocket.accept();
                                Log.d(TAG, "client connected");
                                //再开启一个线程，处理每个客户端连接
                                new Thread() {
                                    @Override
                                    public void run() {
                                        responseClient(client);
                                    }
                                }.start();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }.start();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void responseClient(Socket client) {
        BufferedReader reader = null;
        PrintWriter pw = null;
        try {
            //读取客户端信息
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String line = null;
            //像客户端写入信息
            pw = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
            pw.println("wechat__welcome to chat room  ------ msg from server");
            while (mServiceIsAlive) {
                while ((line = reader.readLine()) != null) {
                    Log.d(TAG, line);
                    int index = new Random().nextInt(mResponseStrings.length);
                    pw.println(String.format("wechat__server %s:%s", getCurrentDate(), mResponseStrings[index]));
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (pw != null) {
                pw.close();
            }
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        return sdf.format(new Date());
    }
}
