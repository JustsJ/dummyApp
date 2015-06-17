package dummy.justs.com.dummyapp;


import android.content.ContentValues;
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
import android.widget.TableLayout;


public class MainActivity extends AppCompatActivity {
    //TODO replace TableLAyout with something that has Adapters
    private TableLayout mTableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTableLayout=(TableLayout)findViewById(R.id.table);

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
}
