package dummy.justs.com.dummyapp.tables;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import dummy.justs.com.dummyapp.DummyContentProvider;

/**
 * Created by eptron on 6/17/2015.
 */
public class SecondDummyTable {

    public static final String TABLE="dummyTableTwo";

    public static final String ID="_id";
    public static final String COUNT="count";

    public static final String BASE_PATH = "second_stuff";
    public static final Uri CONTENT_URI = Uri.parse("content://" + DummyContentProvider.AUTHORITY
            + "/" + TABLE);

    public static final String[] PROJECTION={ID, COUNT};

    private static final String CREATE_TABLE="CREATE TABLE "+TABLE+" ("+
            ID+" integer primary key autoincrement, "+
            COUNT+" integer);";


    //Could create a parent abstract class for all tables
    public static void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE);
    }

    public static void onUpgrade(SQLiteDatabase db){
        db.execSQL("drop table if exists " + TABLE);
        onCreate(db);
    }

}
