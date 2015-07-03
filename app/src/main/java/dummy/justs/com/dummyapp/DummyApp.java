package dummy.justs.com.dummyapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by eptron on 7/2/2015.
 */
public class DummyApp extends Application {

    private static Context inst;

    @Override
    public void onCreate() {
        super.onCreate();
        inst = this;
    }

    public static Context getApp(){
        return inst;
    }
}
