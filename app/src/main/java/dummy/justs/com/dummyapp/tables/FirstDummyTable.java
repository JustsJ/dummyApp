package dummy.justs.com.dummyapp.tables;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import dummy.justs.com.dummyapp.DummyContentProvider;

/**
 * Created by eptron on 6/17/2015.
 */
public class FirstDummyTable {

    public static final String TABLE="dummyTableOne";

    public static final String ID="_id";
    public static final String NAME="name";
    public static final String COUNT="count";

    public static final String BASE_PATH = "first_stuff";
    public static final Uri CONTENT_URI = Uri.parse("content://" + DummyContentProvider.AUTHORITY
            + "/" + BASE_PATH);

    public static final String[] PROJECTION={ID, NAME, COUNT};

    //is all caps really neccessary? or is it just for readability?
    private static final String CREATE_TABLE="CREATE TABLE "+TABLE+" ("+
            ID+" integer primary key autoincrement, "+
            NAME+" text not null, "+COUNT+" integer);";

    public static void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE);
    }

    public static void onUpgrade(SQLiteDatabase db){
        db.execSQL("drop table if exists " + TABLE);
        onCreate(db);
    }

}
