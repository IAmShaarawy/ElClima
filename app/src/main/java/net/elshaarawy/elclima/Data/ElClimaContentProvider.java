package net.elshaarawy.elclima.Data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
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
        //get instance of the readable database
        final SQLiteDatabase sqLiteDatabase = mDbHelper.getReadableDatabase();
        //define the return cursor
        Cursor cursor = null;
        //matching received Uri to get its code from the matcher otherwise throw SQLException
        switch (sMatcher.match(uri)) {
            case MatchingCodes.FORECAST_DATA:
                cursor = sqLiteDatabase.query(ElClimaColumns.TABLE_NAME_FORECAST,
                        projection,
                        selection,
                        selectionArgs,
                        null, null,
                        sortOrder);
                break;
            case MatchingCodes.FORECAST_DATA_ID:
                //get the id from the Uri using uri class function
                // uri is: content://<authority>/elclimaData/id
                String id = uri.getLastPathSegment(); //we can use uri.getPathSegments().get(1) where 1 is the 2nd segment
                String mSelection = "_id=?";
                String[] mSelectionArgs = new String[]{id};

                cursor = sqLiteDatabase.query(ElClimaColumns.TABLE_NAME_FORECAST,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        null, null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);

        }
        if (cursor != null) {
            //set the uri that cursor will be notifies through it
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            return cursor;
        } else throw new SQLiteException("Unsupported Operation");
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (sMatcher.match(uri)) {
            case MatchingCodes.FORECAST_DATA:
                return MimeTypes.FORECAST_TYPE;
            case MatchingCodes.FORECAST_DATA_ID:
                return MimeTypes.FORECAST_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri.toString());
        }

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        //get writable database to insert
        final SQLiteDatabase sqLiteDatabase = mDbHelper.getWritableDatabase();
        // initialize new long id to assign the insert return
        long id;
        //matching received Uri to get its code from the matcher otherwise throw SQLException
        switch (sMatcher.match(uri)) {

            case MatchingCodes.FORECAST_DATA:
                id = sqLiteDatabase.insert(ElClimaColumns.TABLE_NAME_FORECAST, null, values);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri.toString());

        }

        //check if the operation is successful or throw exception
        if (id > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
            sqLiteDatabase.close();//by default insert function close the statement no need to be polite here xD
            return ContentUris.withAppendedId(uri, id);
        } else {
            throw new SQLiteException("insertion failure to " + uri.toString());
        }

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        if (selection == null) {
            throw new IllegalArgumentException("You shouldn't pass null, you will delete all the table entries if you want that pass empty string \"\"");
        }
        final SQLiteDatabase sqLiteDatabase = mDbHelper.getWritableDatabase();
        int effectedRows;
        switch (sMatcher.match(uri)) {
            case MatchingCodes.FORECAST_DATA:
                effectedRows = sqLiteDatabase.delete(ElClimaColumns.TABLE_NAME_FORECAST,
                        selection,
                        selectionArgs);
                break;
            case MatchingCodes.FORECAST_DATA_ID:
                String id = uri.getLastPathSegment();
                String mSelection = ElClimaColumns._ID + "=?";
                String[] mSelectionArgs = new String[]{id};
                effectedRows = sqLiteDatabase.delete(ElClimaColumns.TABLE_NAME_FORECAST,
                        mSelection,
                        mSelectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri.toString());
        }
        if (effectedRows == 1) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        sqLiteDatabase.close();//because we are polite :)
        return effectedRows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase sqLiteDatabase = mDbHelper.getWritableDatabase();
        int effectedRows;
        switch (sMatcher.match(uri)) {
            case MatchingCodes.FORECAST_DATA:
                effectedRows = sqLiteDatabase.update(ElClimaColumns.TABLE_NAME_FORECAST,
                        values,
                        selection,
                        selectionArgs);
                break;
            case MatchingCodes.FORECAST_DATA_ID:
                String id = uri.getLastPathSegment();
                String mSelection = ElClimaColumns._ID + "=?";
                String[] mSelectionArgs = new String[]{id};
                effectedRows = sqLiteDatabase.update(ElClimaColumns.TABLE_NAME_FORECAST,
                        values,
                        mSelection,
                        mSelectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri.toString());
        }

        if (effectedRows > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        sqLiteDatabase.close();
        return effectedRows;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        SQLiteDatabase sqLiteDatabase = mDbHelper.getWritableDatabase();

        switch (sMatcher.match(uri)) {
            case MatchingCodes.FORECAST_DATA:
                return multiInsertion(sqLiteDatabase,
                        ElClimaColumns.TABLE_NAME_FORECAST,
                        values,
                        getContext().getContentResolver(),
                        uri);
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    public void shutdown() {
        super.shutdown();
        mDbHelper.close();
    }

    private static UriMatcher ElclimaMatcher() {
        //initialize the constructor with default code
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        //adding URIs and its codes to the matcher
        uriMatcher.addURI(AUTHORITY, Paths.FORECAST, MatchingCodes.FORECAST_DATA);//for all data
        uriMatcher.addURI(AUTHORITY, Paths.FORECAST.concat("/#"), MatchingCodes.FORECAST_DATA_ID);//for one row of data

        return uriMatcher;
    }

    private static int multiInsertion(SQLiteDatabase db, String tableName, ContentValues[] values, ContentResolver resolver, Uri _uri) {
        int insertedRows = 0;
        db.beginTransaction();
        try {
            for (ContentValues value : values) {
                Long _id = db.insert(tableName, null, value);
                if (_id != -1)
                    insertedRows++;
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        if (insertedRows > 0) {
            resolver.notifyChange(_uri, null);
        }
        return insertedRows;
    }

}

