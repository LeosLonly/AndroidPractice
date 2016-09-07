package edu.bistu.computer.doublebufferingapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/4.
 */
public class CustomView extends View {

    List<Path> pathList = new ArrayList<Path>();
    Path path;
    Bitmap bitmap;
    Canvas canvas;
    Paint paint;
    boolean isOnTouch = false;

    float previouX;
    float previouY;

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path = new Path();
                path.moveTo(x, y);
                previouX = x;
                previouY = y;
                isOnTouch = true;
                pathList.add(path);
                break;
            case MotionEvent.ACTION_MOVE:
                if (isOnTouch) {
                    path.quadTo(previouX, previouY, x, y);
                    previouY = x;
                    previouY = y;
                    drawStrokes();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isOnTouch) {
                    path.quadTo(previouX, previouY, x, y);
                    drawStrokes();
                    isOnTouch = false;
                }
                break;
        }
        return true;
    }

    void drawStrokes() {
        if (canvas == null) {
            bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            canvas = new Canvas();
            canvas.setBitmap(bitmap);
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(5);
        }
        for (Path path : pathList) {
            canvas.drawPath(path, paint);
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, 0, 0, paint);
        }
    }
}
