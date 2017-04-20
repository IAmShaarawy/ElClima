package net.elshaarawy.elclima.Data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by elshaarawy on 13-Apr-17.
 */

public class ElClimaContract {
    // the Unique Identifier of this app
    public static final String AUTHORITY = "net.elshaarawy.elclima";

    // Base Uri that we will use to build upon it other uris
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

    }

    //we list all the available paths in this class
    public static class Paths{
        public static final String FORECAST = "elclimaData";
    }

    //we list all final Uris here
    public static class ProviderUris{
        public static final Uri FORECAST_DATA_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(Paths.FORECAST).build();
    }

    //matching codes of Elclima Content Provider
    public static class MatchingCodes{
        public static final int FORECAST_DATA = 101;
        public static final int FORECAST_DATA_ID = 102;

    }

}
