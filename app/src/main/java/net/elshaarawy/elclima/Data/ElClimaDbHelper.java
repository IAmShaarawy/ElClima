package net.elshaarawy.elclima.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static net.elshaarawy.elclima.Data.ElClimaContract.ElClimaColumns.*;

/**
 * Created by elshaarawy on 13-Apr-17.
 */

public class ElClimaDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "elclima.db";

    public ElClimaDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_ELCLIMA_TABLE = "CREATE TABLE " + TABLE_NAME_FORECAST + "( " +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT "+
                COLUMN_DATE + " INTEGER NOT NULL , "+
                COLUMN_T_DAY + " REAL NOT NULL , "+
                COLUMN_T_MIN + " REAL NOT NULL , "+
                COLUMN_T_MAX + " REAL NOT NULL , "+
                COLUMN_T_NIGHT + " REAL NOT NULL , "+
                COLUMN_T_EVE + " REAL NOT NULL , "+
                COLUMN_T_MORN + " REAL NOT NULL , "+
                COLUMN_PRESSURE + " REAL NOT NULL , "+
                COLUMN_HUMIDITY + " INTEGER NOT NULL , "+
                COLUMN_W_ID + " INTEGER NOT NULL , "+
                COLUMN_W_MAIN + " TEXT NOT NULL , "+
                COLUMN_W_DESCRIPTION + " TEXT NOT NULL , "+
                COLUMN_W_ICON + " TEXT NOT NULL , "+
                COLUMN_SPEED + " REAL NOT NULL , "+
                COLUMN_DEG + " INTEGER NOT NULL , "+
                COLUMN_CLOUDS + " INTEGER NOT NULL );";

        db.execSQL(CREATE_ELCLIMA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST"+ TABLE_NAME_FORECAST);
        this.onCreate(db);
    }
}
