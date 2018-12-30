package com.s_k.devsec.positionnavi010;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import static java.lang.Math.sqrt;

public class CustomView extends View {

    Paint paint;

    public CustomView(Context context, AttributeSet attrs){
        super(context, attrs);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas){

        float centerXPosition = MainWindowActivity.customViewWidth;
        //正三角形の一辺 = width / (3^(1/2) = 0.578)
//        float yourYPosition = centerXPosition / 2 * 2 / (float)sqrt(3.0);
        float yourYPosition = centerXPosition * (float)0.8;

        //背景
        canvas.drawColor(Color.argb(255, 255, 255, 255));

        //原点位置
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPoint(centerXPosition/2,0, paint);
//        canvas.drawPoint(centerXPosition/2,centerXPosition * (2 / (int)Math.sqrt(3.0)) * (2 / 5), paint);
//        canvas.drawPoint(centerXPosition/2,centerXPosition /2, paint);
        canvas.drawCircle(centerXPosition/2, yourYPosition,10, paint);
        Log.i("CustomView", "yourYPosition:"+ yourYPosition);

//        //円
//        paint.setColor(Color.argb(255,68,255,255));
//        paint.setStrokeWidth(30);
//        paint.setAntiAlias(true);
//        paint.setStyle(Paint.Style.STROKE);
//        canvas.drawCircle(450,450,100, paint);
//
//        //矩形
//        paint.setColor(Color.argb(255,255,190,0));
//        paint.setStrokeWidth(10);
//        paint.setStyle(Paint.Style.STROKE);
//        canvas.drawRect(480,480,850,880,paint);
//
//        //線
//        paint.setStrokeWidth(15);
//        paint.setColor(Color.argb(255,0,255,120));
//        canvas.drawLine(350,850,750,630, paint);

    }
}
