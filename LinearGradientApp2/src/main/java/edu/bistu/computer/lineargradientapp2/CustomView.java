package edu.bistu.computer.lineargradientapp2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/9/4.
 */
public class CustomView extends View {

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        canvas.drawRect(10, 10, 100, 100, paint);

        Shader shader = new LinearGradient(0, 0, 50, 50, new int[]{Color.RED, Color.GREEN, Color.BLUE}
                , null, Shader.TileMode.REPEAT);
        paint.setShader(shader);
        canvas.drawRect(10, 110, 200, 200, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setShader(shader);
        canvas.drawRect(10, 210, 300, 300, paint);
    }
}
