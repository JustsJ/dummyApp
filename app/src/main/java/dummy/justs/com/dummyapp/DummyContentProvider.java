package dummy.justs.com.dummyapp;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import java.sql.SQLException;

/**
 * Created by eptron on 6/17/2015.
 */
public class DummyContentProvider extends ContentProvider {

    //Provides data from DummyDatabase
    private DummyDatabase mDb;

    //AUTHORITY should match the one specified in Manifest
    private static final String AUTHORITY = "com.justs.dummyapp.DummyContentProvider";
    private static final String BASE_PATH = "stuff";

    //used to specify URI patterns- all entries (1) or single entry (2)
    public static final int STUFF = 1;
    public static final int STUFF_ID = 2;

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + BASE_PATH);

    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/some_stuff";
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/some_stuff";

    //used to match given URI with one of the defined patterns
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    //TODO copied from tutorial, how does this syntax even work? Refresh your Java
    static {
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, STUFF);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", STUFF_ID);
    }

    @Override
    public boolean onCreate() {
        mDb = new DummyDatabase(getContext());
        return mDb==null ? false:true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        //used to simplify implementation of query method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        //Specify the used table
        queryBuilder.setTables(DummyDatabase.TABLE);

        //Matches the given uri to pre-defined patterns, and acts according to pattern
        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case STUFF:
                break;
            case STUFF_ID:
                queryBuilder.appendWhere(mDb.ID + "="
                        + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Wrong URI");
        }

        Cursor cursor = queryBuilder.query(mDb.getReadableDatabase(),
                projection, selection, selectionArgs, null, null, sortOrder);
        //Places a watch on cursor, so that if cursor data changes, the change is noticed
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType=sURIMatcher.match(uri);

        long rowId=-1;

        switch (uriType){
            case STUFF:
                rowId=mDb.getWritableDatabase().insert(DummyDatabase.TABLE,null,values);
                break;
            default:
                throw new IllegalArgumentException("wrong URI");
        }

        if (rowId<0)
        {
            //do something, exception maybe?
        }
        else if (rowId > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
            return uri;
        }


        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int rowsAffected=0;

        switch (sURIMatcher.match(uri)){
            case STUFF:
                rowsAffected=mDb.getWritableDatabase().delete(mDb.TABLE,selection,selectionArgs);
                break;
            case STUFF_ID:
                String id=uri.getLastPathSegment();
                rowsAffected=mDb.getWritableDatabase().delete(mDb.TABLE, mDb.ID+" LIKE ? ",new String[]{id});
                break;
            default:
                throw new IllegalArgumentException("wrong URI");
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return rowsAffected;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        int rowsAffected=0;

        switch (sURIMatcher.match(uri)){
            case STUFF:
                rowsAffected=mDb.getWritableDatabase().update(mDb.TABLE,values,selection,selectionArgs);
                break;
            case STUFF_ID:
                String id=uri.getLastPathSegment();
                rowsAffected=mDb.getWritableDatabase().update(mDb.TABLE,values, mDb.ID+" LIKE ? ",new String[]{id});
                break;
            default:
                throw new IllegalArgumentException("wrong URI");
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return rowsAffected;
    }
}
