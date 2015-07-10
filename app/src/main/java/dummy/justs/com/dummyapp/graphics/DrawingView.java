package dummy.justs.com.dummyapp.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;
import java.util.TimerTask;

/**
 * Created by eptron on 7/9/2015.
 */
public class DrawingView extends View  {

    private Paint mPaint;
    private Paint mTextPaint;
    private static final int REDRAW_TIME=5000;
    private static final int COLOR_WHITE=0xFFFFFF;
    private float mX;
    private float mY;
    private float mRadius;
    private int mCounter=0;
    private Random r=new Random();


   private Handler mHandler;
   private Runnable mUpdate=new Runnable() {
       @Override
       public void run() {
           invalidate();
       }
   };


    public DrawingView(Context context) {
        super(context);
        init();
    }

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint=new Paint();
        mTextPaint=new Paint();
        mHandler=new Handler();



        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                    if (getDistance(mX,mY,event.getX(),event.getY())<mRadius){
                        mHandler.removeCallbacksAndMessages(null);
                        mCounter++;
                        invalidate();
                    }
                return true;
            }
        });
    }

    private float getDistance(float x1, float y1, float x2, float y2){
        float distance;
        distance= (float) Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1 - y2, 2));
        return distance;
    }

    private float boxValue(float val,float minVal, float maxVal) {
        return Math.max(minVal,Math.min(val,maxVal));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i("DrawingView", "View Drawn");
        mPaint.setColor(getRandomColor());
        mRadius=Math.min(getWidth(),getHeight())/8;
        mX=r.nextInt(getWidth() + 1);
        mY=r.nextInt(getHeight() + 1);

        mX = boxValue(mX, mRadius,getWidth()-mRadius);
        mY=boxValue(mY,mRadius,getHeight()-mRadius);

        canvas.drawCircle(mX, mY, mRadius, mPaint);
        String text=Integer.toString(mCounter);
        mTextPaint.setColor(0xFF000000 + (COLOR_WHITE - (mPaint.getColor() - 0xFF000000)));
        mTextPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        mTextPaint.setTextSize(mRadius / 2);


        Rect textBounds=new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), textBounds);

        canvas.drawText(Integer.toString(mCounter), mX - textBounds.exactCenterX(), mY - textBounds.exactCenterY(), mTextPaint);

        Log.i("DrawingView", mX + " " + mY + " " + mRadius + " " + mPaint);
        mHandler.postDelayed(mUpdate,REDRAW_TIME);
    }

    private int getRandomColor(){
        return 0xFF000000 + new Random().nextInt(COLOR_WHITE);
    }



}
