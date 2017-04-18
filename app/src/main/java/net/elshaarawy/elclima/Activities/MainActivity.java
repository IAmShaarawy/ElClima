package net.elshaarawy.elclima.Activities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import net.elshaarawy.elclima.Data.ElClimaDbHelper;
import net.elshaarawy.elclima.Data.ElClimaEntity;
import net.elshaarawy.elclima.R;

import java.sql.Array;

import static net.elshaarawy.elclima.Data.ElClimaContract.ElClimaColumns.*;

public class MainActivity extends AppCompatActivity {
//    ElClimaDbHelper dbHelper;
//    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        dbHelper = new ElClimaDbHelper(this);
//        sqLiteDatabase = dbHelper.getReadableDatabase();
    }


    @Override
    protected void onResume() {
        super.onResume();
//        ElClimaEntity entity = new ElClimaEntity(157857577, 15.2f, 16.2f, 18.6f, 1.2f, 1.333f, 4.5f, 15.6f, 1.2f, (byte) 14, 25, 18, 80,
//                "this is main", "this is description", "mok");
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_DATE, entity.getDate());
//        values.put(COLUMN_T_DAY, entity.gettDay());
//        values.put(COLUMN_T_EVE, entity.gettEvening());
//        values.put(COLUMN_T_MAX, entity.gettMax());
//        values.put(COLUMN_T_MIN, entity.gettMin());
//        values.put(COLUMN_T_MORN, entity.gettMorning());
//        values.put(COLUMN_T_NIGHT, entity.gettNight());
//        values.put(COLUMN_PRESSURE, entity.getPressure());
//        values.put(COLUMN_HUMIDITY, entity.getHumidity());
//        values.put(COLUMN_W_ID, entity.getwID());
//        values.put(COLUMN_W_MAIN, entity.getwMain());
//        values.put(COLUMN_W_DESCRIPTION, entity.getwDescription());
//        values.put(COLUMN_W_ICON, entity.getwIcon());
//        values.put(COLUMN_SPEED, entity.getSpeed());
//        values.put(COLUMN_DEG, entity.getDeg());
//        values.put(COLUMN_CLOUDS, entity.getClouds());
//        try{
//            long id = sqLiteDatabase.insert(TABLE_NAME_FORECAST,null,values);
//            Toast.makeText(this,String.valueOf(id),Toast.LENGTH_LONG).show();
//        }catch (SQLiteException e){
//            Toast.makeText(this,"لا حول ولا قوة الا بالله",Toast.LENGTH_LONG).show();
//        }
//        String[] columns = {COLUMN_T_DAY, COLUMN_T_NIGHT,COLUMN_W_ICON};
//        Cursor cursor = sqLiteDatabase.query(TABLE_NAME_FORECAST, columns, null, null, null, null, null);
//        while (cursor.moveToNext()) {
//
//            String s = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_W_ICON)) + "";
//            Toast.makeText(this, s, Toast.LENGTH_LONG).show();
//        }

    }
}
