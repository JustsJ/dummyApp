package dummy.justs.com.dummyapp.adapters;

import android.support.v4.app.FragmentStatePagerAdapter;
import dummy.justs.com.dummyapp.fragments.AddDataFragment;
import dummy.justs.com.dummyapp.fragments.ColorFragment;
import dummy.justs.com.dummyapp.fragments.DrawableFragment;
import dummy.justs.com.dummyapp.fragments.ForecastFragment;
import dummy.justs.com.dummyapp.fragments.RetrofitFragment;
import dummy.justs.com.dummyapp.fragments.SensorFragment;
import dummy.justs.com.dummyapp.fragments.ViewFragment;

/**
 * Created by eptron on 6/27/2015.
 */
public class MyViewPagerAdapter extends FragmentStatePagerAdapter {
    private static final int ITEM_COUNT=7;
    public MyViewPagerAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int i) {
        switch(i)
        {
            case 0:
                return new ForecastFragment();
            case 1:
                return new AddDataFragment();
            case 2:
                return new ViewFragment();
            case 3:
                return new DrawableFragment();
            case 4:
                return new RetrofitFragment();
            case 5:
                return new ColorFragment();
            case 6:
                return new SensorFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return ITEM_COUNT;
    }

}
