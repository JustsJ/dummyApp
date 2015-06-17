package dummy.justs.com.dummyapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by eptron on 6/17/2015.
 */
public class DummyCursorAdapter extends CursorAdapter {

    public DummyCursorAdapter(Context context) {
        super(context, null, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.dummy_item,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView name=(TextView) view.findViewById(R.id.item_name);
        TextView count=(TextView) view.findViewById(R.id.item_count);

        name.setText(cursor.getString(cursor.getColumnIndex(DummyDatabase.NAME)));
        count.setText(cursor.getString(cursor.getColumnIndex(DummyDatabase.COUNT)));
    }
}
