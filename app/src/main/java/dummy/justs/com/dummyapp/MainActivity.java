package dummy.justs.com.dummyapp;


import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import dummy.justs.com.dummyapp.adapters.FirstDummyCursorAdapter;
import dummy.justs.com.dummyapp.adapters.SecondDummyCursorAdapter;
import dummy.justs.com.dummyapp.tables.FirstDummyTable;
import dummy.justs.com.dummyapp.tables.SecondDummyTable;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int URL_LOADER_FIRST=1;
    private static final int URL_LOADER_SECOND=2;
    private ListView mFirstListView;
    private ListView mSecondListView;

    //Also a SimpleCursorAdapter can be used
    private FirstDummyCursorAdapter mFirstAdapter;
    private SecondDummyCursorAdapter mSecondAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirstAdapter=new FirstDummyCursorAdapter(this);
        mSecondAdapter=new SecondDummyCursorAdapter(this);

        mFirstListView=(ListView)findViewById(R.id.first_list);
        mFirstListView.setAdapter(mFirstAdapter);
        mSecondListView=(ListView)findViewById(R.id.second_list);
        mSecondListView.setAdapter(mSecondAdapter);

        getLoaderManager().initLoader(URL_LOADER_FIRST, null, this);
        getLoaderManager().initLoader(URL_LOADER_SECOND, null, this);




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

    public void onAddEntry(View view) {

        ContentValues values = new ContentValues();

        switch (view.getId())
        {
            case R.id.fist_button:
                values.put(FirstDummyTable.NAME,
                        ((EditText) findViewById(R.id.name)).getText().toString());

                values.put(FirstDummyTable.COUNT,
                        ((EditText) findViewById(R.id.count)).getText().toString());

                getContentResolver().insert(
                        FirstDummyTable.CONTENT_URI, values);
                break;
            case R.id.second_button:
                values.put(SecondDummyTable.NAME,
                        ((EditText) findViewById(R.id.name)).getText().toString());

                values.put(SecondDummyTable.COUNT,
                        ((EditText) findViewById(R.id.count)).getText().toString());

                getContentResolver().insert(
                        SecondDummyTable.CONTENT_URI, values);
                break;
        }




    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        //returns a Loader based on the given id
        switch (id) {
            case URL_LOADER_FIRST:
                return new CursorLoader(
                        this,   // Parent activity context
                        FirstDummyTable.CONTENT_URI,        // Table to query
                        FirstDummyTable.PROJECTION,     // Projection to return
                        null,            // No selection clause
                        null,            // No selection arguments
                        null             // Default sort order
                );
            case URL_LOADER_SECOND:
                return new CursorLoader(
                        this,   // Parent activity context
                        FirstDummyTable.CONTENT_URI,        // Table to query
                        FirstDummyTable.PROJECTION,     // Projection to return
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
        switch (loader.getId()){
            case URL_LOADER_FIRST:
                mFirstAdapter.changeCursor(data);
                break;
            case URL_LOADER_SECOND:
                mSecondAdapter.changeCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //Should remove all references of cursor, to prevent memory leaks
        switch (loader.getId()){
            case URL_LOADER_FIRST:
                mFirstAdapter.changeCursor(null);
                break;
            case URL_LOADER_SECOND:
                mSecondAdapter.changeCursor(null);
        }

    }
}
