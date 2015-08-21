package dummy.justs.com.dummyapp.fragments;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dummy.justs.com.dummyapp.R;

/**
 * Created by eptron on 8/21/2015.
 */
public class SensorFragment extends Fragment implements SensorEventListener {

    private static final double PRECISSION=0.25;

    private TextView mTextCoordx, mTextCoordy;
    private View mIndicatorTop,mIndicatorBottom,mIndicatorLeft,mIndicatorRight;

    private SensorManager mSensorManager;
    private Sensor mSensor;

    private double[] linear_acceleration= new double[3];

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.sensor_layout,null);

        mTextCoordx= (TextView) view.findViewById(R.id.coordx);
        mTextCoordy= (TextView) view.findViewById(R.id.coordy);

        mIndicatorTop= view.findViewById(R.id.indicator_top);
        mIndicatorBottom= view.findViewById(R.id.indicator_bottom);
        mIndicatorLeft= view.findViewById(R.id.indicator_left);
        mIndicatorRight= view.findViewById(R.id.indicator_right);

        init();

        return view;
    }

    private void init(){
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this,mSensor,500000);

        Log.i("Sensor",mSensorManager.getSensorList(Sensor.TYPE_ALL).toString());
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        double[] gravity=new double[3];
        double[] t_linear_acceleration=new double[3];

        final double alpha = 0.8;

        // Isolate the force of gravity with the low-pass filter.
        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

        // Remove the gravity contribution with the high-pass filter.
        t_linear_acceleration[0] =(event.values[0] - gravity[0])/PRECISSION;
        t_linear_acceleration[1] =(event.values[1] - gravity[1])/PRECISSION;
        t_linear_acceleration[2] =(event.values[2] - gravity[2])/PRECISSION;

        removeIndicators();

        if (linear_acceleration[0]-t_linear_acceleration[0]>0){
            mIndicatorLeft.setVisibility(View.VISIBLE);
        }
        else if (linear_acceleration[0]-t_linear_acceleration[0]<0){
            mIndicatorRight.setVisibility(View.VISIBLE);
        }

        if (linear_acceleration[1]-t_linear_acceleration[1]>0){
            mIndicatorTop.setVisibility(View.VISIBLE);
        }
        else if (linear_acceleration[1]-t_linear_acceleration[1]<0){
            mIndicatorBottom.setVisibility(View.VISIBLE);
        }

        linear_acceleration=t_linear_acceleration;

        mTextCoordx.setText(Double.toString(linear_acceleration[0]));
        mTextCoordy.setText(Double.toString(linear_acceleration[1]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void removeIndicators(){
        mIndicatorTop.setVisibility(View.INVISIBLE);
        mIndicatorBottom.setVisibility(View.INVISIBLE);
        mIndicatorLeft.setVisibility(View.INVISIBLE);
        mIndicatorRight.setVisibility(View.INVISIBLE);
    }
}
