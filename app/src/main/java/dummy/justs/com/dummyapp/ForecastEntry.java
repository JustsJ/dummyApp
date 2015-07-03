package dummy.justs.com.dummyapp;

/**
 * Created by eptron on 7/2/2015.
 */
public class ForecastEntry {
    private String mDay;
    private String mForecast;
    private String mTempF;
    private String mTempC;

    public ForecastEntry(String mDay, String mForecast, String mTempF, String mTempC) {
        this.mDay = mDay;
        this.mForecast = mForecast;
        this.mTempF = mTempF;
        this.mTempC = mTempC;
    }

    public String getmDay() {
        return mDay;
    }

    public void setmDay(String mDay) {
        this.mDay = mDay;
    }

    public String getmForecast() {
        return mForecast;
    }

    public void setmForecast(String mForecast) {
        this.mForecast = mForecast;
    }

    public String getmTempF() {
        return mTempF;
    }

    public void setmTempF(String mTempF) {
        this.mTempF = mTempF;
    }

    public String getmTempC() {
        return mTempC;
    }

    public void setmTempC(String mTempC) {
        this.mTempC = mTempC;
    }

    public String toString(){
        return mDay+" - "+mForecast+" - "+mTempC+"/"+mTempF;
    }
}
