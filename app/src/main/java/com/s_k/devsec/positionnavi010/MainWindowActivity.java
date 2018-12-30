package com.s_k.devsec.positionnavi010;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainWindowActivity extends AppCompatActivity {

    static int customViewWidth;
    static int customViewHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);

        Button button1 = (Button) findViewById(R.id.btDemo1);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

            }
        });
        Button button2 = (Button) findViewById(R.id.btDemo2);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        customViewWidth = findViewById(R.id.customView).getWidth();
        customViewHeight = findViewById(R.id.customView).getHeight();
        Log.i("MainWindowActivity", "CustomView幅:"+ customViewWidth);
        Log.i("MainWindowActivity", "CustomView高:"+ customViewHeight);

        CustomView customView = (CustomView) findViewById(R.id.customView);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)customView.getLayoutParams();
        marginLayoutParams.height = customViewWidth;
        customView.setLayoutParams(marginLayoutParams);
    }
}
