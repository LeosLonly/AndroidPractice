package edu.bistu.computer.lineargradientapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
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
        Paint paint = new Paint();

        Resources resources = getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher);

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        //canvas.drawBitmap(bitmap,0,0,paint);

        Matrix matrix = new Matrix();
        matrix.setTranslate(0, height / 2);
        matrix.setRotate(180, width / 2, height / 2);
        canvas.drawBitmap(bitmap, matrix, paint);
    }
}
