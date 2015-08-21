package dummy.justs.com.dummyapp.graphics;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;

import java.util.Random;

/**
 * Created by eptron on 7/13/2015.
 */
public class DrawnObject {

    private static final int COLOR_WHITE=0xFFFFFF;
    private Paint mPaint;
    private Paint mTextPaint;
    private float mX;
    private float mY;
    private float mRadius;
    private int mCounter=0;
    private Random r=new Random();


    public DrawnObject(){
        mPaint=new Paint();
        mTextPaint=new Paint();
    }

    private float getDistance(float x1, float y1, float x2, float y2){
        float distance;
        distance= (float) Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1 - y2, 2));
        return distance;
    }

    private float boxValue(float val,float minVal, float maxVal) {
        return Math.max(minVal,Math.min(val,maxVal));
    }

    public boolean isTouched(float x, float y){
       return getDistance(mX,mY,x,y)<mRadius;
    }

    public void incrementCounter(){
        mCounter++;
    }

    public void draw(Canvas canvas,float parentWidth,float parentHeight){
        Log.i("DrawingView", "View Drawn");

        mPaint.setColor(getRandomColor());
        mRadius=Math.min(parentWidth,parentHeight)/8;
        mX=r.nextInt((int)parentWidth + 1);
        mY=r.nextInt((int)parentHeight + 1);

        mX = boxValue(mX, mRadius,parentWidth-mRadius);
        mY=boxValue(mY,mRadius,parentHeight-mRadius);

        canvas.drawCircle(mX, mY, mRadius, mPaint);
        String text=Integer.toString(mCounter);
        mTextPaint.setColor(0xFF000000 + (COLOR_WHITE - (mPaint.getColor() - 0xFF000000)));
        mTextPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        mTextPaint.setTextSize(mRadius / 2);


        Rect textBounds=new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), textBounds);

        canvas.drawText(Integer.toString(mCounter), mX - textBounds.exactCenterX(), mY - textBounds.exactCenterY(), mTextPaint);

        Log.i("DrawingView", mX + " " + mY + " " + mRadius + " " + mPaint);
    }

    private int getRandomColor(){
        return 0xFF000000 + new Random().nextInt(COLOR_WHITE);
    }

}
