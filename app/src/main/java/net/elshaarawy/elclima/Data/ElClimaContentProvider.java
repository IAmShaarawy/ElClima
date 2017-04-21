package net.elshaarawy.elclima.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static net.elshaarawy.elclima.Data.ElClimaContract.*;

/**
 * Created by elshaarawy on 20-Apr-17.
 */

public class ElClimaContentProvider extends ContentProvider {
    private ElClimaDbHelper mDbHelper;
    static UriMatcher sMatcher;


    @Override
    public boolean onCreate() {

        mDbHelper = new ElClimaDbHelper(getContext());
        sMatcher = ElclimaMatcher();

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        //get writable database to insert
        final SQLiteDatabase sqLiteDatabase = mDbHelper.getWritableDatabase();
        // initialize new long id to assign the insert return
        long id = -1;
        //matching received Uri to get its code from the matcher otherwise throw SQLException
        switch (sMatcher.match(uri)) {

            case MatchingCodes.FORECAST_DATA:
                id = sqLiteDatabase.insert(ElClimaColumns.TABLE_NAME_FORECAST, null, values);
                break;
            default: throw new UnsupportedOperationException("Unknown Uri: "+uri.toString());

        }

        //check if the operation is successful or throw exception
        if (id > 0){
            getContext().getContentResolver().notifyChange(uri,null);
            sqLiteDatabase.close();//by default insert function close the statement that execute we can comment this line
            return ContentUris.withAppendedId(uri,id);
        }else {
            throw new SQLException("insertion failure to " + uri.toString());
        }

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    private static UriMatcher ElclimaMatcher() {
        //initialize the constructor with default code
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        //adding URIs and its codes to the matcher
        uriMatcher.addURI(AUTHORITY, Paths.FORECAST, MatchingCodes.FORECAST_DATA);//for all data
        uriMatcher.addURI(AUTHORITY, Paths.FORECAST.concat("/#"), MatchingCodes.FORECAST_DATA_ID);//for one row of data

        return uriMatcher;
    }
}
