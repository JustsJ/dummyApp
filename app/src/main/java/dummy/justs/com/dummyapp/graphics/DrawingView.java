package dummy.justs.com.dummyapp.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;
import java.util.TimerTask;

/**
 * Created by eptron on 7/9/2015.
 */
public class DrawingView extends View {

    private Paint mPaint;
    private static final int REDRAW_TIME=5000;
    private static final int COLOR_WHITE=0xFFFFFF;
    private float mX;
    private float mY;
    private float mRadius;

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
        mHandler=new Handler();


        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                    if (getDistance(mX,mY,event.getX(),event.getY())<mRadius){
                        mHandler.removeCallbacksAndMessages(null);
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


    @Override
    protected void onDraw(Canvas canvas) {
        Log.i("DrawingView","View Drawn");
        mPaint.setColor(getRandomColor());
        mRadius=Math.min(getWidth(),getHeight())/8;
        mX=new Random().nextInt(getWidth() + 1);
        mY=new Random().nextInt(getHeight()+1);
        canvas.drawCircle(mX,mY,mRadius,mPaint);
        Log.i("DrawingView",mX+" "+mY+" "+mRadius+" "+mPaint);
        mHandler.postDelayed(mUpdate,REDRAW_TIME);
    }

    private int getRandomColor(){
        return 0xFF000000 + new Random().nextInt(COLOR_WHITE);
    }


}
