package com.s_k.devsec.positionnavi010;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainWindowActivity extends AppCompatActivity {

    static int customViewWidth;
    static int customViewHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);

        final TextView tvDistance = findViewById(R.id.tvDistance);
        tvDistance.setText("0");
        final TextView tvAngle = findViewById(R.id.tvAngle);
        tvAngle.setText("0");

        final CustomView customView = findViewById(R.id.customView);

        Button button1 = findViewById(R.id.btDemo1);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                double angle = 30;
                customView.showCanvas(false, angle);
                Log.d("MainWindowActivity", "bt_number=1");
                tvDistance.setText("10");
                tvAngle.setText("" + angle);
            }
        });
        Button button2 = findViewById(R.id.btDemo2);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                double angle = 60;
                customView.showCanvas(false, angle);
                Log.d("MainWindowActivity", "bt_number=2");
                tvDistance.setText("20");
                tvAngle.setText("" + angle);
            }
        });
        Button button3 = findViewById(R.id.btDemo3);
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                double angle = -10;
                customView.showCanvas(false, angle);
                Log.d("MainWindowActivity", "bt_number=3");
                tvDistance.setText("5");
                tvAngle.setText("" + angle);
            }
        });
        Button button4 = findViewById(R.id.btReset);
        button4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                double angle = 0;
                CustomView customView = findViewById(R.id.customView);
                customView.showCanvas(true, angle);
                Log.d("MainWindowActivity", "bt_number=4");
                tvDistance.setText("0");
                tvAngle.setText("" + angle);
            }
        });
        Button buttonGetIp = findViewById(R.id.btGetIp);
        buttonGetIp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String ip = getWifiIPAddress(MainWindowActivity.this);
                Toast.makeText(MainWindowActivity.this, ip, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private static final String LOCAL_LOOPBACK_ADDR = "127.0.0.1";
    private static final String INVALID_ADDR = "0.0.0.0";

    private static String getWifiIPAddress(Context context) {
        WifiManager manager = (WifiManager)context.getSystemService(WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        int ipAddr = info.getIpAddress();
        String ipString = String.format("%02d.%02d.%02d.%02d",
                (ipAddr>>0)&0xff, (ipAddr>>8)&0xff, (ipAddr>>16)&0xff, (ipAddr>>24)&0xff);
        return ipString;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        customViewWidth = findViewById(R.id.customView).getWidth();
        customViewHeight = findViewById(R.id.customView).getHeight();
        Log.d("MainWindowActivity", "CustomView幅:"+ customViewWidth);
        Log.d("MainWindowActivity", "CustomView高:"+ customViewHeight);

        CustomView customView = (CustomView) findViewById(R.id.customView);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)customView.getLayoutParams();
        marginLayoutParams.height = customViewWidth;
        customView.setLayoutParams(marginLayoutParams);
    }
}
