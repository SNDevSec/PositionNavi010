package com.s_k.devsec.positionnavi010;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

        Button button1 = findViewById(R.id.btDemo1);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                CustomView customView = findViewById(R.id.customView);
                customView.showCanvas(false, 30);
                Log.d("MainWindowActivity", "bt_number=1");
                tvDistance.setText("10");
                tvAngle.setText("30");
            }
        });
        Button button2 = findViewById(R.id.btDemo2);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                CustomView customView = findViewById(R.id.customView);
                customView.showCanvas(false, 60);
                Log.d("MainWindowActivity", "bt_number=2");
                tvDistance.setText("20");
                tvAngle.setText("60");
            }
        });
        Button button3 = findViewById(R.id.btDemo3);
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                CustomView customView = findViewById(R.id.customView);
                customView.showCanvas(false, -30);
                Log.d("MainWindowActivity", "bt_number=3");
                tvDistance.setText("5");
                tvAngle.setText("-30");
            }
        });
        Button button4 = findViewById(R.id.btReset);
        button4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                CustomView customView = findViewById(R.id.customView);
                customView.showCanvas(true, 0);
                Log.d("MainWindowActivity", "bt_number=4");
                tvDistance.setText("0");
                tvAngle.setText("0");
            }
        });
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
