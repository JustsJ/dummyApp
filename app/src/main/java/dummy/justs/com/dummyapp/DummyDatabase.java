package dummy.justs.com.dummyapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import dummy.justs.com.dummyapp.tables.FirstDummyTable;
import dummy.justs.com.dummyapp.tables.SecondDummyTable;
import dummy.justs.com.dummyapp.tables.ViewTable;

/**
 * Created by eptron on 6/17/2015.
 */
public class DummyDatabase extends SQLiteOpenHelper {

    private static final String DB_NAME="DummyDatabase";
    private static final int DB_VERSION=11;


    public DummyDatabase(Context context) {
        //TODO investigate CursorFactory
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /**
         * This is where all tables should be created
         * could use create() methods from every table class
         */
        //TODO can I do this in a loop?
        FirstDummyTable.onCreate(db);
        SecondDummyTable.onCreate(db);
        ViewTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //this is where you would call onUpgrade for every table class,
        //TODO can upgrades be done better than simply destroying the whole table?
        FirstDummyTable.onUpgrade(db);
        SecondDummyTable.onUpgrade(db);
        ViewTable.onUpgrade(db);
    }
}
