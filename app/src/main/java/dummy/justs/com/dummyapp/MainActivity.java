package dummy.justs.com.dummyapp;



import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import dummy.justs.com.dummyapp.adapters.MyViewPagerAdapter;
import dummy.justs.com.dummyapp.graphics.OGLActivity;


public class MainActivity extends FragmentActivity {

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

        mViewPager.setPageTransformer(true,new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
              //  page.setAlpha(1-Math.abs(position));
                int color=255-(int)((Math.abs(position)-1f)*255);
                page.setBackgroundColor(Color.rgb(color,color,color));

            }
        }
        );

        mActionBar = getActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


       ActionBar.TabListener tabListener=new ActionBar.TabListener() {
           @Override
           public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
               mViewPager.setCurrentItem(tab.getPosition());
           }

           @Override
           public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {

           }

           @Override
           public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {

           }
       };


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

        mActionBar.addTab(
                mActionBar.newTab()
                        .setText("Sunshine").setTabListener(tabListener));
        mActionBar.addTab(
                mActionBar.newTab()
                        .setText("Add data").setTabListener(tabListener));

        mActionBar.addTab(
                mActionBar.newTab()
                        .setText("View data").setTabListener(tabListener));

        mActionBar.addTab(
                mActionBar.newTab()
                        .setText("WOMBATS!").setTabListener(tabListener));
        mActionBar.addTab(
                mActionBar.newTab()
                        .setText("Retrofit").setTabListener(tabListener));

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

    public void launchGraphics(View view){
        Intent i=new Intent(this,OGLActivity.class);
        startActivity(i);
    }

}
