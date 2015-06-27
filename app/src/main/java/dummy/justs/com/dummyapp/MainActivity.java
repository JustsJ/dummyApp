package dummy.justs.com.dummyapp;



import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;

import dummy.justs.com.dummyapp.adapters.FirstDummyCursorAdapter;
import dummy.justs.com.dummyapp.adapters.MyViewPagerAdapter;
import dummy.justs.com.dummyapp.adapters.SecondDummyCursorAdapter;
import dummy.justs.com.dummyapp.fragments.AddDataFragment;
import dummy.justs.com.dummyapp.tables.FirstDummyTable;
import dummy.justs.com.dummyapp.tables.SecondDummyTable;


public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private ActionBar mActionBar;
    private MyViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mViewPagerAdapter);

        mActionBar = getSupportActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab mainTab=mActionBar.newTab();

       mainTab.setTabListener(new ActionBar.TabListener() {

           @Override
           public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
               mViewPager.setCurrentItem(tab.getPosition());
           }

           @Override
           public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

           }

           @Override
           public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

           }
       });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                getActionBar().setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


      /*  mActionBar.addTab(
                mActionBar.newTab()
                        .setText("Add data")
                        );

        mActionBar.addTab(
                mActionBar.newTab()
                        .setText("View data")
                        );
*/
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

}
