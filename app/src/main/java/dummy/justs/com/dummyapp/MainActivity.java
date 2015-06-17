package dummy.justs.com.dummyapp;


import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int URL_LOADER=0;
    private ListView mListView;
    //Also a SimpleCursorAdapter can be used
    private DummyCursorAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter=new DummyCursorAdapter(this);

        mListView=(ListView)findViewById(R.id.list);
        mListView.setAdapter(mAdapter);

        getLoaderManager().initLoader(URL_LOADER, null, this);




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

        values.put(DummyDatabase.NAME,
                ((EditText) findViewById(R.id.name)).getText().toString());

        values.put(DummyDatabase.COUNT,
                ((EditText) findViewById(R.id.count)).getText().toString());

        Uri uri = getContentResolver().insert(
                DummyContentProvider.CONTENT_URI, values);


    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        //returns a Loader based on the given id
        switch (id) {
            case URL_LOADER:
                // Returns a new CursorLoader
                return new CursorLoader(
                        this,   // Parent activity context
                        DummyContentProvider.CONTENT_URI,        // Table to query
                        DummyDatabase.PROJECTION,     // Projection to return
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
        mAdapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //Should remove all references of cursor, to prevent memory leaks
        mAdapter.changeCursor(null);
    }
}
