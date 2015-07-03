package dummy.justs.com.dummyapp.library;

import dummy.justs.com.dummyapp.library.model.FailedRequest;
import dummy.justs.com.dummyapp.library.model.MainEvent;

/**
 * Created by eptron on 7/2/2015.
 */
public class DummyController extends RestController {
    private static DummyController sInstance;
    private static final String API_URL="Content/GetContentByCategoryWithoutContent";


    private DummyController() {
    }

    public static DummyController getController() {
        if (sInstance == null) {
            sInstance = new DummyController();
            sBuilder = new RestHandler.Builder();
            BASE_URL = "https://fitnesscz-test.azurewebsites.net/api/";
        }
        return sInstance;
    }

    public void getNews(){
        doRestCall(API_URL,ParamsBuilder.buildListParams(0,100),RestHandler.TYPE_GET,new MainEvent());

    }
}
