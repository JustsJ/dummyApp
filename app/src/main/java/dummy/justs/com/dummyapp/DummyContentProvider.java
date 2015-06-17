package dummy.justs.com.dummyapp;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import java.sql.SQLException;

import dummy.justs.com.dummyapp.tables.FirstDummyTable;
import dummy.justs.com.dummyapp.tables.SecondDummyTable;

/**
 * Created by eptron on 6/17/2015.
 */
public class DummyContentProvider extends ContentProvider {

    //Provides data from DummyDatabase
    private DummyDatabase mDb;

    //AUTHORITY should match the one specified in Manifest
    public static final String AUTHORITY = "com.justs.dummyapp.DummyContentProvider";

    public static final int FIRST_STUFF = 1;
    public static final int FIRST_STUFF_ID = 2;
    public static final int SECOND_STUFF = 3;
    public static final int SECOND_STUFF_ID = 4;

    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/com.justs.stuff";
    public static final String CONTENT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/com.justs.stuff";

    //used to match given URI with one of the defined patterns
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    //TODO copied from tutorial, how does this syntax even work? Refresh your Java
    static {
        sURIMatcher.addURI(AUTHORITY, FirstDummyTable.BASE_PATH, FIRST_STUFF);
        sURIMatcher.addURI(AUTHORITY, FirstDummyTable.BASE_PATH + "/#", FIRST_STUFF_ID);
        sURIMatcher.addURI(AUTHORITY, SecondDummyTable.BASE_PATH, SECOND_STUFF);
        sURIMatcher.addURI(AUTHORITY, SecondDummyTable.BASE_PATH + "/#", SECOND_STUFF_ID);
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




        //Matches the given uri to pre-defined patterns, and acts according to pattern
        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case FIRST_STUFF:
            case SECOND_STUFF:
                break;
            case FIRST_STUFF_ID:
                queryBuilder.setTables(FirstDummyTable.TABLE);
                queryBuilder.appendWhere(FirstDummyTable.ID + "="
                        + uri.getLastPathSegment());
                break;
            case SECOND_STUFF_ID:
                queryBuilder.setTables(SecondDummyTable.TABLE);
                queryBuilder.appendWhere(SecondDummyTable.ID + "="
                        + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: "+uri);
        }
        Log.i("DummyContentProvider","uriType: "+uriType);
        Cursor cursor = queryBuilder.query(mDb.getReadableDatabase(),
                projection, selection, selectionArgs, null, null, sortOrder);
        //Places a watch on cursor, so that if cursor data changes, the change is noticed
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        switch (sURIMatcher.match(uri)) {
            case FIRST_STUFF:
            case SECOND_STUFF:
                return CONTENT_DIR_TYPE;

            case FIRST_STUFF_ID:
            case SECOND_STUFF_ID:
                return CONTENT_ITEM_TYPE;

            default:
                throw new IllegalArgumentException("Wrong URI: "+uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType=sURIMatcher.match(uri);

        long rowId=-1;

        switch (uriType){
            case FIRST_STUFF:
                rowId=mDb.getWritableDatabase().insert(FirstDummyTable.TABLE,null,values);
                break;
            case SECOND_STUFF:
                rowId=mDb.getWritableDatabase().insert(SecondDummyTable.TABLE,null,values);
                break;
            default:
                throw new IllegalArgumentException("wrong URI: "+uri);
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
        String id=uri.getLastPathSegment();;
        switch (sURIMatcher.match(uri)){
            case FIRST_STUFF:
                rowsAffected=mDb.getWritableDatabase().delete(FirstDummyTable.TABLE,selection,selectionArgs);
                break;
            case SECOND_STUFF:
                rowsAffected=mDb.getWritableDatabase().delete(SecondDummyTable.TABLE,selection,selectionArgs);
                break;
            case FIRST_STUFF_ID:
                rowsAffected=mDb.getWritableDatabase().delete(FirstDummyTable.TABLE, FirstDummyTable.ID+" LIKE ? ",new String[]{id});
                break;
            case SECOND_STUFF_ID:
                rowsAffected=mDb.getWritableDatabase().delete(SecondDummyTable.TABLE, SecondDummyTable.ID+" LIKE ? ",new String[]{id});
                break;
            default:
                throw new IllegalArgumentException("wrong URI: "+uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return rowsAffected;
    }

    @Override
    public int update (Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        int rowsAffected=0;
        String id=uri.getLastPathSegment();
        switch (sURIMatcher.match(uri)){
            case FIRST_STUFF:
                rowsAffected=mDb.getWritableDatabase().update(FirstDummyTable.TABLE,values,selection,selectionArgs);
                break;
            case SECOND_STUFF:
                rowsAffected=mDb.getWritableDatabase().update(SecondDummyTable.TABLE,values,selection,selectionArgs);
                break;
            case FIRST_STUFF_ID:
                rowsAffected=mDb.getWritableDatabase().update(FirstDummyTable.TABLE,values, FirstDummyTable.ID+" LIKE ? ",new String[]{id});
                break;
            case SECOND_STUFF_ID:
                rowsAffected=mDb.getWritableDatabase().update(SecondDummyTable.TABLE,values, SecondDummyTable.ID+" LIKE ? ",new String[]{id});
                break;
            default:
                throw new IllegalArgumentException("wrong URI: "+uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return rowsAffected;
    }
}
