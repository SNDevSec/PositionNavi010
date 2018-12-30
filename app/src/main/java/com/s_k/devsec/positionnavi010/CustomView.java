package com.s_k.devsec.positionnavi010;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import static java.lang.Math.sqrt;

public class CustomView extends View {

    private Paint paint;
    private Path path;
    private boolean viewflg;
    private double angle = 0;

    public CustomView(Context context, AttributeSet attrs){
        super(context, attrs);
        paint = new Paint();
        path = new Path();
        viewflg = true;
    }

    public void showCanvas(boolean flg, double ang){
        viewflg = flg;
        angle = ang;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas){

        float customViewWidth = MainWindowActivity.customViewWidth;
        //正三角形の一辺 = width / (3^(1/2) = 0.578)
//        float yourYPosition = yourXPosition / 2 * 2 / (float)sqrt(3.0);
        float yourXPosition = customViewWidth / 2;
        float yourYPosition = customViewWidth * (float)0.7;
        Log.d("CustomView", "yourYPosition:"+ yourYPosition);
        double radius = yourYPosition - (customViewWidth - yourYPosition);

        float targetXPosition;
        float targetYPosition;

        RectF rectF = null;

        //背景
        canvas.drawColor(Color.argb(255, 255, 255, 255));

        //描画エリアX中心座標マーク
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPoint(yourXPosition,0, paint);

        //自分座標マーク
        canvas.drawCircle(yourXPosition, yourYPosition,10, paint);

        //自分座標文字
//        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        paint.setTextSize(50);
        canvas.drawText("Your Position", yourXPosition, yourYPosition + 100, paint);

        //レンジ線表示
        paint.setStrokeWidth(3);
        path.rewind();
        path.moveTo(yourXPosition, yourYPosition);
        path.lineTo(yourXPosition-((yourYPosition-(customViewWidth - yourYPosition))/2*(float)sqrt(3.0))*(float)1.3,yourYPosition-((yourYPosition-(customViewWidth - yourYPosition))/2)*(float)1.3);
        canvas.drawPath(path, paint);
        path.rewind();
        path.moveTo(yourXPosition, yourYPosition);
        path.lineTo(yourXPosition+((yourYPosition-(customViewWidth - yourYPosition))/2*(float)sqrt(3.0))*(float)1.3,yourYPosition-((yourYPosition-(customViewWidth - yourYPosition))/2)*(float)1.3);
        canvas.drawPath(path, paint);

        //円弧
        paint.setStrokeWidth(1);
        rectF = new RectF(yourXPosition - (float)radius, yourYPosition - (float)radius, yourXPosition + (float)radius, yourYPosition + (float)radius);
        canvas.drawArc(rectF, 210, 120, false, paint);

        if(viewflg){
            targetXPosition = yourXPosition;
            targetYPosition = yourYPosition - (float)radius;
        }else{
            Log.d("CustomView", "radius:"+ radius);
            Log.d("CustomView", "angle:"+ angle);
            float rcos_theta = (float)(radius * Math.cos(Math.toRadians(angle)));
            Log.d("CustomView", "rsin_theta:"+ rcos_theta);
            float rsin_theta = (float)(radius * Math.sin(Math.toRadians(angle)));
            Log.d("CustomView", "rcos_theta:"+ rsin_theta);
            targetXPosition = yourXPosition + rsin_theta;
            targetYPosition = yourYPosition - rcos_theta;
        }

        //ターゲット座標マーク
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(targetXPosition, targetYPosition,10, paint);
        //ターゲット座標文字
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        paint.setTextSize(50);
        canvas.drawText("Target", targetXPosition, targetYPosition - 50, paint);

        //ターゲットまでの線表示
        paint.setStrokeWidth(1);
//        paint.setPathEffect(new DashPathEffect(new float[]{2, 4},0));
        path.rewind();
        path.moveTo(yourXPosition, yourYPosition);
        path.lineTo(targetXPosition,targetYPosition);
        canvas.drawPath(path, paint);
   }
}
