package dummy.justs.com.dummyapp.library.model;

/**
 * Created by eptron on 13/05/2015.
 */
public class FailedRequest extends MainEvent{
    private String mMethodCall;

    public FailedRequest(String methodCall){
        mMethodCall = methodCall;
    }

    public String getMethodCall(){
        return mMethodCall;
    }
}
