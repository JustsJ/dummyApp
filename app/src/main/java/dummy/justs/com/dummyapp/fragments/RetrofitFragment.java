package dummy.justs.com.dummyapp.fragments;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import de.greenrobot.event.EventBus;
import dummy.justs.com.dummyapp.BenchmarkHandler;
import dummy.justs.com.dummyapp.R;
import dummy.justs.com.dummyapp.library.model.MainEvent;
import dummy.justs.com.dummyapp.library.model.NewsEvent;
import dummy.justs.com.dummyapp.retrofit.API.TestAPI;
import dummy.justs.com.dummyapp.retrofit.model.TestModel;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by eptron on 7/2/2015.
 */
public class RetrofitFragment extends Fragment{

    private static final String API_URL = "http://freemusicarchive.org/api";
    private static final String API_KEY = "BT9YCJ8XEKWRMN4J";
    private long mStartTime;
    private long mEndTime;
    TextView mTextView;
    private BenchmarkHandler handler1;
    private BenchmarkHandler handler2;
    private BenchmarkHandler handler3;


    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    public void onEventMainThread(NewsEvent event){
        Log.i("RetrofitFragment","event received");
        event.parseArray();
        handler1.handleCall(BenchmarkHandler.CALL_TYPE_NORM);
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.retrofit_fragment,null);

        LinearLayout benchmarkList1=(LinearLayout) inflater.inflate(R.layout.benchmark_list,null);
        handler1=new BenchmarkHandler(getActivity(),benchmarkList1,BenchmarkHandler.CALL_TYPE_NORM);

        LinearLayout benchmarkList2=(LinearLayout) inflater.inflate(R.layout.benchmark_list,null);
        handler2=new BenchmarkHandler(getActivity(),benchmarkList2,BenchmarkHandler.CALL_TYPE_RETRO);

        LinearLayout benchmarkList3=(LinearLayout) inflater.inflate(R.layout.benchmark_list,null);
        handler3=new BenchmarkHandler(getActivity(),benchmarkList3,BenchmarkHandler.CALL_TYPE_RETRO_CB);

        ((FrameLayout) view.findViewById(R.id.frame1)).addView(benchmarkList1);
        ((FrameLayout) view.findViewById(R.id.frame2)).addView(benchmarkList2);
        ((FrameLayout) view.findViewById(R.id.frame3)).addView(benchmarkList3);


       // mTextView = (TextView) view.findViewById(R.id.text_view);
     //   BackgroundTask task = new BackgroundTask();
      //  task.execute();
 /*
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .build();
        TestAPI methods = restAdapter.create(TestAPI.class);

      mStartTime=System.currentTimeMillis();
        methods.getCurators(API_KEY, new Callback<TestModel>() {
            @Override
            public void success(TestModel testModel, Response response) {
                mEndTime=System.currentTimeMillis();
                Toast.makeText(getActivity(), "Time: " + Float.toString((float) (mEndTime - mStartTime) / 1000), Toast.LENGTH_LONG).show();
                TestModel curators = testModel;
                mTextView.setText(curators.title + "\n\n");
                for (TestModel.Dataset dataset : curators.dataset) {
                    mTextView.setText(mTextView.getText() + dataset.curator_title +
                            " - " + dataset.curator_tagline + "\n");
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
*/
        return view;
    }


}
