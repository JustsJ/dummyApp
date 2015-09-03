package dummy.justs.com.dummyapp.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import dummy.justs.com.dummyapp.R;

/**
 * Created by eptron on 8/21/2015.
 */
public class SensorFragment extends Fragment implements SensorEventListener {

    private static final double PRECISION=0.025;
    private static final int SAMPLING_PERIOD=50000000;

    @Bind(R.id.coordx) TextView mTextCoordx;
    @Bind(R.id.coordy) TextView mTextCoordy;
    @Bind(R.id.coordz) TextView mTextCoordz;

    @Bind(R.id.indicator_top) View mIndicatorTop;
    @Bind(R.id.indicator_bottom) View mIndicatorBottom;
    @Bind(R.id.indicator_left)  View mIndicatorLeft;
    @Bind(R.id.indicator_right)  View mIndicatorRight;

    private SensorManager mSensorManager;
    private Sensor mSensor;

    private double[] linear_acceleration= new double[3];

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.sensor_layout,null);

        ButterKnife.bind(this,view);

        init();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mSensorManager!=null) mSensorManager.registerListener(this, mSensor, SAMPLING_PERIOD);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    private void init(){

        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        mSensor=findSensor(sensorList, Arrays.asList(
                new Integer[]{Sensor.TYPE_GAME_ROTATION_VECTOR,Sensor.TYPE_ROTATION_VECTOR}));

        if (mSensor!=null){

            mSensorManager.registerListener(this, mSensor, SAMPLING_PERIOD);
        }
        else
        {
            Toast.makeText(getActivity(), "No usable sensor found!", Toast.LENGTH_SHORT).show();
        }

        Log.i("Sensor", mSensorManager.getSensorList(Sensor.TYPE_ALL).toString());
    }

    private double applyPrecision(double x){
        return ((int)(x/PRECISION)*PRECISION);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.i("SensorEvent", Arrays.toString(event.values));
        double[] t_event_values=new double[3];

        mTextCoordx.setText(Double.toString(applyPrecision(event.values[0])));
        mTextCoordy.setText(Double.toString(applyPrecision(event.values[1])));
        mTextCoordz.setText(Double.toString(applyPrecision(event.values[2])));
        mIndicatorBottom.setVisibility(View.VISIBLE);

        mIndicatorBottom.setBackgroundColor(Color.rgb((int) (255 * Math.abs(event.values[0])), (int) (255 * Math.abs(event.values[1])), (int) (255 * Math.abs(event.values[2]))));
        //getView().setBackgroundColor(Color.rgb((int) (255 * Math.abs(event.values[0])), (int) (255 * Math.abs(event.values[1])), (int) (255 * Math.abs(event.values[2]))));
        //colorBackground(Color.rgb((int) (255 * event.values[0]), (int) (255 * event.values[1]), (int) (255 * event.values[2])));
    }

    private void colorBackground(int rgb) {
        Canvas canvas=new Canvas();
        Paint paint=new Paint();
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            Activity a = getActivity();
            if(a != null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    private Sensor findSensor(List<Sensor> sensorList, List<Integer> neededSensors){
        Sensor foundSensor=null;
        int sensorType=-1; //needed, because on first check calling .getType() will return error
        for (Sensor i : sensorList){
            if (neededSensors.indexOf(i.getType())>sensorType){
                foundSensor=i;
                sensorType=foundSensor.getType();
            }
        }
        return foundSensor;
    }


}
