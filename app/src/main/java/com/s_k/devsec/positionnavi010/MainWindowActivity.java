package com.s_k.devsec.positionnavi010;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainWindowActivity extends AppCompatActivity {

    static int monitorWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);

//        DrawArea drawArea = new DrawArea(MainWindowActivity.this);
//        setContentView(drawArea);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        monitorWidth = findViewById(R.id.customView).getWidth();

        Log.i("PositionNavi010", "画面幅:"+ monitorWidth);
    }

//    class DrawArea extends View{
//        private Paint paint;
//
//        private float StrokeWidth = 20.0f;
//
//        public DrawArea(Context context){
//            super(context);
//            paint = new Paint();
//        }
//
//        protected void onDraw (Canvas canvas){
//            paint.setColor(Color.argb(255,255,0,255));
//            paint.setStrokeWidth(StrokeWidth);
//
//            canvas.drawRect(300,300,600, 600, paint);
//        }
//    }
}