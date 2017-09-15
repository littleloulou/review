package review.lph.com.socketserver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import review.lph.com.socketserver.service.TcpServerService;

public class MainActivity extends AppCompatActivity {

    private Intent mSocketService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSocketService = new Intent(this, TcpServerService.class);
        startService(mSocketService);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(mSocketService);
    }
}
