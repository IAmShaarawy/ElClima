package net.elshaarawy.elclima.Data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by elshaarawy on 13-Apr-17.
 */

public class ElClimaContract {
    // the Unique Identifier of this app
    public static final String AUTHORITY = "net.elshaarawy.elclima";

    public static final Uri BASE_CONTENT_URI = new Uri.Builder().scheme("content").authority(AUTHORITY).build();

    public static class ElClimaColumns implements BaseColumns{
        public static final String TABLE_NAME_FORECAST = "elclimaData";

        public static final String COLUMN_DATE = "a1";
        public static final String COLUMN_T_DAY = "a2";
        public static final String COLUMN_T_MIN = "a3";
        public static final String COLUMN_T_MAX = "a4";
        public static final String COLUMN_T_NIGHT = "a5";
        public static final String COLUMN_T_EVE = "a6";
        public static final String COLUMN_T_MORN = "a7";
        public static final String COLUMN_PRESSURE = "a8";
        public static final String COLUMN_HUMIDITY = "a9";
        public static final String COLUMN_W_ID = "a10";
        public static final String COLUMN_W_MAIN = "a11";
        public static final String COLUMN_W_DESCRIPTION = "a12";
        public static final String COLUMN_W_ICON = "a13";
        public static final String COLUMN_SPEED = "a14";
        public static final String COLUMN_DEG = "a15";
        public static final String COLUMN_CLOUDS = "a16";

        //Content Provider URIs
        public static final Uri ELCLIMA_DATA_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME_FORECAST).build();
    }


}
