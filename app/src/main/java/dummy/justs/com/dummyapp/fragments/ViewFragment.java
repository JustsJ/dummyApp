package dummy.justs.com.dummyapp.fragments;

import android.app.Activity;




import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.google.gson.Gson;

import dummy.justs.com.dummyapp.R;
import dummy.justs.com.dummyapp.tables.ViewTable;

/**
 * Created by eptron on 6/18/2015.
 */
public class ViewFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private ListView mListView;
    private SimpleCursorAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_sql_view, container, false);
        mListView=(ListView)view.findViewById(R.id.sql_view_list);

        mAdapter= new SimpleCursorAdapter(getActivity(),R.layout.view_item,null,ViewTable.PROJECTION,
                new int[]{R.id.id,R.id.name,R.id.count},0);
        mListView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



      getLoaderManager().initLoader(1,null,this);

    }


    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (menuVisible && mAdapter!=null)
        {
            //there is a better way than this
            getLoaderManager().restartLoader(1,null,this);
            Log.i("ViewFragmnet","I'M VISIBLE!!!");
        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getActivity(),
                ViewTable.CONTENT_URI,
                ViewTable.PROJECTION,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Gson gson=new Gson();
        Log.i("ViewFragment","cursor columns: "+gson.toJson(data.getColumnNames()));
        mAdapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.changeCursor(null);
    }
}
