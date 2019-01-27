package com.s_k.devsec.positionnavi010;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView tvDistance;
    TextView tvAngle;
    TextView tvPortNumber;

    static int customViewWidth;
    static int customViewHeight;

    Globals globals;
    UDPReceiverThread mUDPReceiver= null;
    int commPort;

    Handler mHandler;

//    Button btRcvStart;
//    Button btRcvStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity", "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);

        globals = (Globals) this.getApplication();

        mUDPReceiver= new UDPReceiverThread();
        mUDPReceiver.start();

        mHandler = new Handler();

        tvDistance = findViewById(R.id.tvDistance);
        tvDistance.setText("0");
        tvAngle = findViewById(R.id.tvAngle);
        tvAngle.setText("0");
        TextView tvSSID = findViewById(R.id.tvSSID);
        tvSSID.setText(getWifiSSID(MainActivity.this));
        TextView tvIpAddress = findViewById(R.id.tvIpAddress);
        tvIpAddress.setText(getWifiIPAddress(MainActivity.this));
        tvPortNumber = findViewById(R.id.tvPortNumber);
        tvPortNumber.setText(globals.getPortNumber());

        final CustomView customView = findViewById(R.id.customView);

        Button button1 = findViewById(R.id.btDemo1);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                double angle = 30;
                customView.showCanvas(false, angle);
                Log.d("MainActivity", "bt_number=1");
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
                Log.d("MainActivity", "bt_number=2");
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
                Log.d("MainActivity", "bt_number=3");
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
                Log.d("MainActivity", "bt_number=4");
                tvDistance.setText("0");
                tvAngle.setText("" + angle);
            }
        });
//        Button buttonShowIp = findViewById(R.id.btShowIp);
//        buttonShowIp.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                String ip = globals.getIpAddress();
//                Toast.makeText(MainActivity.this, ip, Toast.LENGTH_SHORT).show();
//            }
//        });
//        Button buttonShowPortNo = findViewById(R.id.btShowPortNo);
//        buttonShowPortNo.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                String portNo = globals.getPortNumber();
//                Toast.makeText(MainActivity.this, portNo, Toast.LENGTH_SHORT).show();
//            }
//        });
//        btRcvStart = findViewById(R.id.btRcvStart);
//        btRcvStart.setEnabled(false);
//        btRcvStart.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                mUDPReceiver.start();
//                Toast.makeText(MainActivity.this, "受信開始", Toast.LENGTH_SHORT).show();
//                btRcvStop.setEnabled(true);
//                btRcvStart.setEnabled(false);
//            }
//        });
//        btRcvStop = findViewById(R.id.btRcvStop);
//        btRcvStop.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                mUDPReceiver.onStop();
//                Toast.makeText(MainActivity.this, "受信停止", Toast.LENGTH_SHORT).show();
//                btRcvStart.setEnabled(true);
//                btRcvStop.setEnabled(false);
//            }
//        });
    }

    private static String getWifiIPAddress(Context context) {
        WifiManager manager = (WifiManager)context.getSystemService(WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        int ipAddr = info.getIpAddress();
        String ipString = String.format("%d.%d.%d.%d",
                (ipAddr>>0)&0xff, (ipAddr>>8)&0xff, (ipAddr>>16)&0xff, (ipAddr>>24)&0xff);
        return ipString;
    }

    private static String getWifiSSID(Context context) {
        WifiManager manager = (WifiManager)context.getSystemService(WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        String  ssid = info.getSSID();
        return ssid;
    }

    class UDPReceiverThread extends Thread {
        private static final String TAG="UDPReceiverThread";

        public static final String COMM_END_STRING="end";

//        Globals globals = null;
        DatagramSocket mDatagramRecvSocket= null;
        boolean mIsArive= false;
        DatagramPacket receivePacket = null;
        byte[] receiveBuffer = null;

        Object obj = null;

        public UDPReceiverThread() {
            super();
            commPort = Integer.parseInt(globals.getPortNumber());
            Log.d(TAG, "Globalsポート番号:"+ commPort);
            // ソケット生成
            try {
                mDatagramRecvSocket= new DatagramSocket(commPort);
            }catch( Exception e ) {
                e.printStackTrace();
            }

        }
        @Override
        public void start() {
            mIsArive= true;
            Log.d(TAG,"mIsArive status:"+ mIsArive);
            super.start();
        }
        public void onStop() {
            Log.d(TAG,"onStop()");
            mIsArive= false;
            mDatagramRecvSocket.close();
            mDatagramRecvSocket= null;
            Log.d(TAG,"mIsArive status:"+ mIsArive);
        }

        // 受信用スレッドのメイン関数
        @Override
        public void run() {
            // UDPパケット生成（最初に一度だけ生成して使いまわし）
            receiveBuffer = new byte[1024];
            receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

            // スレッドループ開始
            Log.d(TAG,"In run(): thread start.");
            try {
                while (mIsArive) {
                    // UDPパケット受信
                    mDatagramRecvSocket.receive(receivePacket);

//                  String packetString = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    try {
                        ByteArrayInputStream bis = new ByteArrayInputStream(receivePacket.getData(), 0, receivePacket.getLength());
                        ObjectInput in = new ObjectInputStream(bis);
                        obj = in.readObject();
                    }catch (Exception ex){
                    }
                    Log.d(TAG,"In run(): packet received :" + obj);

//                    if( packetString.equals(COMM_END_STRING) ) {
//                        // 終了メッセージを受信したらActivity終了
//                        // whileループを抜けてソケットclose＆スレッド終了
//                        mActivity.finish();
//                        break;
//                    }
                         // 受信結果の画面表示
//                    mActivity.printString(packetString);

                    String message = obj.toString();
//                    final String dist = message.substring(1,3);
                    message = message.replaceAll("[^-?0-9]+", " ");
                    List<String> list = Arrays.asList(message.trim().split(" "));
                    final String dist = list.get(0);
                    Log.d(TAG,"dist: " + dist);
//                    final String angle = message.substring(5,7);
                    final String angle = list.get(1);
                    Log.d(TAG,"angle: " + angle);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            tvDistance.setText(dist);
                            tvAngle.setText(angle);
                            CustomView customView = findViewById(R.id.customView);
//                            try {
//                                Thread.sleep(1000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
                            customView.showCanvas(false, Double.parseDouble(angle));
                            Log.d(TAG, "In run(): Canvas refleshed");
                        }
                    });

                }
            }catch( Exception e ) {
                e.printStackTrace();
            }
            Log.d(TAG,"In run(): thread end.");
            // ソケットclose（これをしないと2回目以降の起動ができない）
            if(mDatagramRecvSocket != null){
                mDatagramRecvSocket.close();
                mDatagramRecvSocket= null;
            }
            receivePacket= null;
            receiveBuffer= null;
        }
    }

    @Override
    public void onRestart(){
        tvPortNumber.setText(globals.getPortNumber());
        commPort = Integer.parseInt(globals.getPortNumber());
        super.onRestart();
    }

    @Override
    public void onDestroy() {
        Log.d("MainActivity", "onDestroy()");
        mUDPReceiver.onStop();
        super.onDestroy();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        customViewWidth = findViewById(R.id.customView).getWidth();
        customViewHeight = findViewById(R.id.customView).getHeight();
        Log.d("MainActivity", "CustomView幅:"+ customViewWidth);
        Log.d("MainActivity", "CustomView高:"+ customViewHeight);

        int orientation = getResources().getConfiguration().orientation;
        CustomView customView = findViewById(R.id.customView);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)customView.getLayoutParams();

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // 横向きの場合
            customViewWidth = customViewHeight;
            marginLayoutParams.width = customViewWidth;
            Log.d("MainActivity", "CustomView幅(修正):"+ customViewWidth);
            customView.setLayoutParams(marginLayoutParams);
        }

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            // 縦向きの場合
            customViewHeight = customViewWidth;
            marginLayoutParams.height = customViewHeight;
            Log.d("MainActivity", "CustomView高(修正):"+ customViewHeight);
            customView.setLayoutParams(marginLayoutParams);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d("MainActivity", "onConfigurationChanged()");
        super.onConfigurationChanged(newConfig);
        //処理
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options_menu_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemId = item.getItemId();
        switch (itemId){
            case R.id.menuListOptionSetting:
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
