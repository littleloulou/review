package review.lph.com.socketclient;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Socket mClient;
    private PrintWriter mPrintWriter;
    private static final String TAG = "MainActivity";
    private BufferedReader mReader;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText = ((EditText) findViewById(R.id.et_msg));
        initClient();
    }

    private void initClient() {
        new Thread() {
            @Override
            public void run() {
                while (mClient == null) {
                    try {
                        //创建socket客户端，并指明主机地址和端口号，这个端口号必须和服务端的创建的serverSocket端口号相同
                        //该方法会自动执行connect方法
                        mClient = new Socket("localhost", 8888);
                        mPrintWriter = new PrintWriter(new OutputStreamWriter(mClient.getOutputStream()), true);
                        //可以通过mClient获取输出流，和输入流，分别像服务端写入数据和从服务端读取数据
                        mReader = new BufferedReader(new InputStreamReader(mClient.getInputStream()));
                        while (!MainActivity.this.isFinishing()) {
                            String line = null;
                            while ((line = mReader.readLine()) != null) {
                                Log.d(TAG, line);
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(TAG, "connect server failure after 2 seconds retry....");
                        SystemClock.sleep(2000);
                    }
                }
            }
        }.start();
    }


    public void send(View view) {
        mPrintWriter.println("wechat__client :" + getCurrentDate() + mEditText.getText().toString());
    }


    public String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        return sdf.format(new Date());
    }

    @Override
    protected void onDestroy() {
        if (mClient != null && mClient.isConnected()) {
            try {
                mClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mClient = null;
        }
        super.onDestroy();
    }

    public void initUDPSocket() {
        try {
            InetSocketAddress address = InetSocketAddress.createUnresolved("localhost", 8888);
            DatagramSocket client = new DatagramSocket(address);
            String data = "hello";
            DatagramPacket datagramPacket = new DatagramPacket(data.getBytes(), data.getBytes().length);
            client.send(datagramPacket);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initUDPServer() {
        try {
            //端口需要和客户端的端口保持一致
            DatagramSocket server = new DatagramSocket(8888);
            byte[] data = new byte[1024 * 1024];
            DatagramPacket datagramPacket = new DatagramPacket(data, data.length);
            server.receive(datagramPacket);
            System.out.println("获取到客户端数据：" + String.valueOf(data));
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
