package dummy.justs.com.dummyapp.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import dummy.justs.com.dummyapp.R;
import dummy.justs.com.dummyapp.adapters.FirstDummyCursorAdapter;
import dummy.justs.com.dummyapp.adapters.SecondDummyCursorAdapter;
import dummy.justs.com.dummyapp.tables.FirstDummyTable;
import dummy.justs.com.dummyapp.tables.SecondDummyTable;

/**
 * Created by eptron on 6/19/2015.
 */
public class AddDataFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {

    private static final int URL_LOADER_FIRST = 1;
    private static final int URL_LOADER_SECOND = 2;
    private ListView mFirstListView;
    private ListView mSecondListView;

    //Also a SimpleCursorAdapter can be used
    private FirstDummyCursorAdapter mFirstAdapter;
    private SecondDummyCursorAdapter mSecondAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_data_fragment, container, false);
        view.findViewById(R.id.first_button).setOnClickListener(this);
        view.findViewById(R.id.second_button).setOnClickListener(this);
        view.findViewById(R.id.button_sql_view).setOnClickListener(this);

        mFirstListView = (ListView) view.findViewById(R.id.first_list);
        mFirstListView.setAdapter(mFirstAdapter);
        mSecondListView = (ListView) view.findViewById(R.id.second_list);
        mSecondListView.setAdapter(mSecondAdapter);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirstAdapter = new FirstDummyCursorAdapter(getActivity());
        mSecondAdapter = new SecondDummyCursorAdapter(getActivity());

        getLoaderManager().initLoader(URL_LOADER_FIRST, null, this);
        getLoaderManager().initLoader(URL_LOADER_SECOND, null, this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        //returns a Loader based on the given id
        switch (id) {
            case URL_LOADER_FIRST:
                return new CursorLoader(
                        getActivity(),   // Parent activity context
                        FirstDummyTable.CONTENT_URI,        // Table to query
                        FirstDummyTable.PROJECTION,     // Projection to return
                        null,            // No selection clause
                        null,            // No selection arguments
                        null             // Default sort order
                );
            case URL_LOADER_SECOND:
                return new CursorLoader(
                        getActivity(),   // Parent activity context
                        SecondDummyTable.CONTENT_URI,        // Table to query
                        SecondDummyTable.PROJECTION,     // Projection to return
                        null,            // No selection clause
                        null,            // No selection arguments
                        null             // Default sort order
                );
            default:
                // An invalid id was passed in
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.i("Loader onLoadFinished", "loaderId=" + loader.getId() + " hasName:" + data.getColumnIndex(FirstDummyTable.NAME));
        switch (loader.getId()) {
            case URL_LOADER_FIRST:
                Log.i("Loader onLoadFinished", "first case");
                mFirstAdapter.changeCursor(data);
                break;
            case URL_LOADER_SECOND:
                Log.i("Loader onLoadFinished", "second case");
                mSecondAdapter.changeCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //Should remove all references of cursor, to prevent memory leaks
        switch (loader.getId()) {
            case URL_LOADER_FIRST:
                mFirstAdapter.changeCursor(null);
                break;
            case URL_LOADER_SECOND:
                mSecondAdapter.changeCursor(null);
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onClick(View v) {
        ContentValues values = new ContentValues();
        Log.i("onAddEntry", "id=" + v.getId());
        switch (v.getId()) {
            case R.id.first_button:
                values.put(FirstDummyTable.NAME,
                        ((EditText) getView().findViewById(R.id.name)).getText().toString());

                getActivity().getContentResolver().insert(
                        FirstDummyTable.CONTENT_URI, values);
                break;
            case R.id.second_button:

                values.put(SecondDummyTable.COUNT,
                        ((EditText) getView().findViewById(R.id.count)).getText().toString());

                getActivity().getContentResolver().insert(
                        SecondDummyTable.CONTENT_URI, values);
                break;
            case R.id.button_sql_view:
                Intent i = new Intent(getActivity(), ViewFragment.class);
                startActivity(i);
        }


    }
}
