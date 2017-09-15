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
import java.net.Socket;
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
                        mClient = new Socket("localhost", 8888);
                        mPrintWriter = new PrintWriter(new OutputStreamWriter(mClient.getOutputStream()), true);
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
}
