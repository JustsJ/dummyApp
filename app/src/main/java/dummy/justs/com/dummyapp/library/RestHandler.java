package dummy.justs.com.dummyapp.library;

import android.os.SystemClock;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;
import dummy.justs.com.dummyapp.library.model.FailedRequest;
import dummy.justs.com.dummyapp.library.model.MainEvent;
import dummy.justs.com.dummyapp.library.model.RestCall;

/**
 * Handler class for JSON type HttpRequest and Response
 * Created by Claudiu on 19/05/2015.
 */
public class RestHandler extends JsonHttpResponseHandler {

    //Things needed for the Builder to execute calls
    private static AsyncHttpClient client = new AsyncHttpClient();

    //API URL -- Needs to be changed to the appropriate URL
    public static String BASE_URL = RestController.BASE_URL;
    public static String getAbsoluteUrl(String url){
        return BASE_URL+url;
    }

    public static final int TYPE_GET = 0;
    public static final int TYPE_POST = 1;
    public static final int TYPE_PUT = 2;

    private RestController mController;
    private MainEvent mSuccessEvent;
    private MainEvent mFailureEvent;
    private String mMethodCall;
    private RequestParams mRequestParams;
    private long mStartTime;
    private boolean isSuccessful;

    /**
     * Default constructor
     * @param controller Controller that started this handler needed in order to post back
     *                   execution data to the controller
     */
    public RestHandler(RestController controller) {
        mController = controller;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
        isSuccessful = true;
        handleResponse(statusCode, null, response);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        isSuccessful = true;
        handleResponse(statusCode, response, null);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        isSuccessful = false;
        handleResponse(statusCode, errorResponse, null);
    }

    /**
     * Handles the response from the HttpCall
     * @param statusCode Status code of the HttpCall response
     * @param object the JSONObject returned by the Call (null if an array was returned)
     * @param array the JSONArray returned by the Call (null if an object was returned)
     */
    private void handleResponse(int statusCode,JSONObject object,JSONArray array){
        long tempTime = SystemClock.currentThreadTimeMillis();
        RestCall temp = new RestCall(statusCode,mMethodCall,(int)tempTime,isSuccessful,mRequestParams);
        mController.handleResponse(temp);
        if(isSuccessful) {
            mSuccessEvent.setJSON(object);
            mSuccessEvent.setJSON(array);
            mSuccessEvent.setRestCall(temp);
            mSuccessEvent.parseJSON();
            Log.i("RestHandler", "Successful request  " + temp.getMethodCall());
            EventBus.getDefault().post(mSuccessEvent);
        } else {
            mFailureEvent.setJSON(object);
            mFailureEvent.setRestCall(temp);
            mFailureEvent.parseJSON();
            Log.i("RestHandler", "Failed request  " + temp.getMethodCall());
            EventBus.getDefault().post(mFailureEvent);
        }
    }

    /**
     * Builder class in charge of building RestHandlers and executing them over
     * and AsyncHttpClient
     */
    public static class Builder {
        RestHandler mHandler;
        MainEvent sSuccessEvent;
        MainEvent sFailureEvent;
        String sMethodCall;
        RequestParams sParams;
        long sStartTime;
        int sType;

        /**
         * Sets the RequestParams (@RequestParams) for the request.
         * @param params The request parameters to be used for the RestHandler
         * @return this instance of the class for easier execution
         */
        public Builder setRequestParams(RequestParams params){
            sParams = params;
            return this;
        }

        /**
         * Sets the Event to be posted in case of Success
         * @param event the event of type (@MainEvent)
         * @return *this instance of the class for easier execution
         */
        public Builder setEventSuccess(MainEvent event) {
            sSuccessEvent = event;
            return this;
        }

        /**
         * Sets the Event to be posted in case of Failure
         * @param event the event of type (@MainEvent)
         * @return *this instance of the class for easier execution
         */
        public Builder setEventFail(MainEvent event) {
            sFailureEvent = event;
            return this;
        }

        /**
         * Sets the method name in the api that shall be executed
         * @param methodCall the method name
         * @return this instance of the class for easier execution
         */
        public Builder setMethodCall(String methodCall){
            sMethodCall = methodCall;
            return this;
        }

        /**
         * Not yet implemented
         * @param startTime the start time when the tread would start
         * @return
         */
        public Builder setStartTime(long startTime){
            sStartTime = startTime;
            return this;
        }

        /**
         * Sets the type of HttpCall to be executed (ex: GET,POST,PUT)
         *  types can be found under RestHandler.* (ex: RestHandler.TYPE_GET)
         * @param type the type for the HttpCall
         * @return this instance of the class for easier execution
         */
        public Builder setType(int type){
            sType = type;
            return this;
        }

        /**
         * Builds the RestHandler and executes it with the build parameters set to the builder over
         * Http.
         * @param controller the controller executing this build
         *                   Needed in order to post back from the RestHandler to the controller
         *                   successes and failures for better tracking of the api calls
         */
        public void build(RestController controller) {
            mHandler = new RestHandler(controller);
            if (sSuccessEvent == null) {
                sSuccessEvent = new MainEvent();
            }
            if (sFailureEvent == null) {
                sFailureEvent = new FailedRequest(sMethodCall);
            }
            mHandler.mSuccessEvent = sSuccessEvent;
            mHandler.mFailureEvent = sFailureEvent;
            mHandler.mMethodCall = sMethodCall;
            mHandler.mStartTime = sStartTime;
            mHandler.mRequestParams = sParams;
            switch(sType){
                case TYPE_GET:
                    client.get(getAbsoluteUrl(sMethodCall),sParams,mHandler);
                    break;
                case TYPE_POST:
                    client.post(getAbsoluteUrl(sMethodCall),sParams,mHandler);
                    break;
                case TYPE_PUT:
                    client.put(getAbsoluteUrl(sMethodCall),sParams,mHandler);
                    break;
            }
            reset();
        }

        /**
         * Resets the builders member variables to null to avoid errors
         */
        private void reset(){
            mHandler = null;
            sSuccessEvent = null;
            sFailureEvent = null;
            sMethodCall = null;
            sParams = null;
            sStartTime = 0;
            sType = 0;
        }
    }
}