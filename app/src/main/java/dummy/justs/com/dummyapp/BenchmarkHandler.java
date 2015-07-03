package dummy.justs.com.dummyapp;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import dummy.justs.com.dummyapp.library.DummyController;
import dummy.justs.com.dummyapp.library.ParamsBuilder;
import dummy.justs.com.dummyapp.library.model.MainEvent;
import dummy.justs.com.dummyapp.library.model.NewsModel;
import dummy.justs.com.dummyapp.retrofit.API.TestAPI;
import dummy.justs.com.dummyapp.retrofit.model.TestModel;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by eptron on 7/2/2015.
 */
public class BenchmarkHandler {
    public static final String API_URL="https://fitnesscz-test.azurewebsites.net";
    public static final String CALL_TYPE_NORM="norm";
    public static final String CALL_TYPE_RETRO="retro";
    public static final String CALL_TYPE_RETRO_CB="retrocb";
    private double mCallCount=100.0;
    private TextView mName;
    private TextView mTotal;
    private TextView mAverage;
    private ListView mListView;
    private ArrayAdapter mAdapter;
    private Button mButton;
    private ArrayList<Double> mList;
    private double mTimeStart;
    private double mTimeEnd;


    public BenchmarkHandler(Context context,View view, String callType) {


        mName= (TextView) view.findViewById(R.id.title);
        mTotal= (TextView) view.findViewById(R.id.total);
        mAverage= (TextView) view.findViewById(R.id.average);
        mListView=(ListView) view.findViewById(R.id.list);
        mList=new ArrayList<>();
        mAdapter = new ArrayAdapter(context, R.layout.list_item_forecast, R.id.list_item_forecast_textview, mList);
        mListView.setAdapter(mAdapter);
        mButton=(Button) view.findViewById(R.id.button);

        switch(callType){
            case CALL_TYPE_NORM:
                mName.setText("Normal Call");
                mButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mList.clear();
                        if (mCallCount!=100) return;
                        makeCall(CALL_TYPE_NORM);
                    }
                });
                break;
            case CALL_TYPE_RETRO:
                mName.setText("Retrofit Call");
                mButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mList.clear();
                        if (mCallCount != 100) return;
                        makeCall(CALL_TYPE_RETRO);
                    }
                });
                break;
            case CALL_TYPE_RETRO_CB:
                mName.setText("Retrofit callback Call");
                mButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mList.clear();
                        if (mCallCount!=100) return;
                        makeCall(CALL_TYPE_RETRO_CB);
                    }
                });
                break;
            default:

        };
    }

    public void makeCall(String callType){
        mCallCount--;
        if (mCallCount<=-1) {
            mName.setBackgroundColor(Color.GREEN);
            Double total=0.0;
            for (Double i: mList){
                total+=i;
            }
            mTotal.setText("Sum: "+Double.toString(total));
            mAverage.setText("Avg: "+Double.toString(total/100.00));
            mCallCount=100.0;
            return;
        }
        mTimeStart=System.currentTimeMillis();

        switch(callType){
            case CALL_TYPE_NORM:
                mTimeStart=System.currentTimeMillis();
                DummyController.getController().getNews();
                break;
            case CALL_TYPE_RETRO:
                BackgroundTask task = new BackgroundTask();
                task.execute();
                break;
            case CALL_TYPE_RETRO_CB:
                callRetroCallback();
        }

    }

    public void handleCall(String callType){
        mName.setBackgroundColor(Color.RED);
        mTimeEnd=System.currentTimeMillis();
        mList.add((mTimeEnd - mTimeStart) / 1000);
        mAdapter.notifyDataSetChanged();
        makeCall(callType);
    };


    private void callRetroCallback(){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .build();
        TestAPI methods = restAdapter.create(TestAPI.class);

        mTimeStart=System.currentTimeMillis();
        methods.getNews("93bf6f85bd82791a", 23, 0, 100, new Callback<ArrayList<NewsModel>>() {

            @Override
            public void success(ArrayList<NewsModel> event, Response response) {
                handleCall(CALL_TYPE_RETRO_CB);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }



    private class BackgroundTask extends AsyncTask<Void, Void,
            ArrayList<NewsModel>> {
        RestAdapter restAdapter;

        @Override
        protected void onPreExecute() {
            mTimeStart=System.currentTimeMillis();
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(API_URL)
                    .build();
        }

        @Override
        protected ArrayList<NewsModel> doInBackground(Void... params) {
            TestAPI testAPI = restAdapter.create(TestAPI.class);
           ArrayList<NewsModel> event = testAPI.getNews("93bf6f85bd82791a",23,0,100);

            return event;
        }

        @Override
        protected void onPostExecute(ArrayList<NewsModel> event) {
            mTimeEnd=System.currentTimeMillis();
            handleCall(CALL_TYPE_RETRO);

        }
    }

}
