package dummy.justs.com.dummyapp.tables;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import dummy.justs.com.dummyapp.DummyContentProvider;

/**
 * Created by eptron on 6/18/2015.
 */
public class ViewTable {

    public static final String TABLE = "viewTable";

    public static final String ID = "_id";


    public static final String BASE_PATH = "first_stuff";
    public static final Uri CONTENT_URI = Uri.parse("content://" + DummyContentProvider.AUTHORITY
            + "/" + TABLE);

    public static final String[] PROJECTION = {ID, FirstDummyTable.NAME, SecondDummyTable.COUNT};

    private static final String CREATE_TABLE =
            "CREATE VIEW " + TABLE + " AS "
                    + " SELECT " +FirstDummyTable.TABLE+"."+ID + ", " + FirstDummyTable.NAME + ", " + SecondDummyTable.COUNT
                    + " FROM " + FirstDummyTable.TABLE + ", " + SecondDummyTable.TABLE
            +" WHERE "+FirstDummyTable.TABLE+"."+FirstDummyTable.ID+"="+SecondDummyTable.TABLE+"."+SecondDummyTable.ID;


    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    public static void onUpgrade(SQLiteDatabase db) {
        db.execSQL("drop view if exists " + TABLE);
        onCreate(db);
    }

}
