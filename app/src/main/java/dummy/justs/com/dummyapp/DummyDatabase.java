package dummy.justs.com.dummyapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by eptron on 6/17/2015.
 */
public class DummyDatabase extends SQLiteOpenHelper {

    private static final String DB_NAME="DummyDatabase";
    private static final int DB_VERSION=1;

    //Like in fitcijn, multiple tables could use their own class
    public static final String TABLE="dummyTable";

    //does _id really have to use the underscore?
    public static final String ID="_id";
    public static final String NAME="name";
    public static final String COUNT="count";

    //is all caps really neccessary? or is it just for readability?
    private static final String CREATE_TABLE="create table "+TABLE+" ("+
            ID+" integer primary key autoincrement, "+
            NAME+" text not null, "+COUNT+" integer);";

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
        //TODO can multiple tables be created with a single execSQL? Should it even?
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //this is where you would call onUpgrade for every table class,
        //TODO can upgrades be done better than simply destroying the whole table?
        db.execSQL("drop table if exists " + TABLE);
        onCreate(db);
    }
}
