package dummy.justs.com.dummyapp.adapters;

import android.support.v4.app.FragmentStatePagerAdapter;
import dummy.justs.com.dummyapp.fragments.AddDataFragment;
import dummy.justs.com.dummyapp.fragments.ViewFragment;

/**
 * Created by eptron on 6/27/2015.
 */
public class MyViewPagerAdapter extends FragmentStatePagerAdapter {
    private static final int ITEM_COUNT=2;
    public MyViewPagerAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int i) {
        switch(i)
        {
            case 0:
                android.support.v4.app.Fragment fragment1 = new AddDataFragment();
                return fragment1;
            case 1:
                android.support.v4.app.Fragment fragment2 = new ViewFragment();
                return fragment2;

        }
        return null;
    }

    @Override
    public int getCount() {
        return ITEM_COUNT;
    }

}
