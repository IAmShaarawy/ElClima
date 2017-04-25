package net.elshaarawy.elclima;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.Time;

import net.elshaarawy.elclima.Data.ElClimaEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import static net.elshaarawy.elclima.Data.ElClimaContract.ElClimaColumns.*;
import static net.elshaarawy.elclima.Data.ElClimaContract.ProviderUris.CONTENT_URI_FORECAST;

/**
 * Created by elshaarawy on 24-Apr-17.
 */

public class ElclimaMainService extends IntentService {

    private static final String EXTRA_REGION_ID = "regionIdExtra";
    private final static String EXTRA_UNIT = "unitExtra";

    public ElclimaMainService() {
        super("ElclimaMainService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String regionId = "2521886";// for Almeria
        String unit = "metric";
        if (intent != null) {
            regionId = intent.getStringExtra(EXTRA_REGION_ID);
            unit = intent.getStringExtra(EXTRA_UNIT);
        }

        Uri weatherBaseUri = Uri.parse("http://api.openweathermap.org/data/2.5/forecast/daily");
        Uri weatherUri = weatherBaseUri.buildUpon()
                .appendQueryParameter("APPID", "88d1c681c8761123026e5a34857459bd")
                .appendQueryParameter("mode", "json")
                .appendQueryParameter("units", unit)
                .appendQueryParameter("cnt", "16")
                .appendQueryParameter("id", regionId)
                .build();
        loadData(weatherUri);
    }

    //http connection
    private void loadData(Uri weatherUri) {
        HttpURLConnection urlConnection = null;
        BufferedReader bufferedReader = null;
        String forecastJsonStr = null;
        try {
            URL url = new URL(weatherUri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream == null)
                return ;
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuffer buffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0)
                return ;

            forecastJsonStr = buffer.toString();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();

            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            getWeatherDataFromJson(forecastJsonStr, 7);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //Parse JSON String
    private void getWeatherDataFromJson(String forecastJsonStr, int numDays) throws JSONException {

        final String OWM_LIST = "list";
        final String OWM_DT = "dt";
        final String OWM_TEMPERATURE = "temp";
        final String OWM_DAY = "day";
        final String OWM_MIN = "min";
        final String OWM_MAX = "max";
        final String OWM_NIGHT = "night";
        final String OWM_EVE = "eve";
        final String OWM_MORN = "morn";

        final String OWM_PRESSURE = "pressure";
        final String OWM_HUMIDITY = "humidity";

        final String OWM_WEATHER = "weather";
        final String OWM_W_ID = "id";
        final String OWM_W_MAIN = "main";
        final String OWM_W_DESCRIPTION = "description";
        final String OWM_W_ICON = "icon";

        final String OWM_SPEED = "speed";
        final String OWM_DEG = "deg";
        final String OWM_CLOUDS = "clouds";
        if (forecastJsonStr == null)
            return ;
        JSONObject forecastJson = new JSONObject(forecastJsonStr);
        JSONArray weatherArray = forecastJson.getJSONArray(OWM_LIST);


        ContentValues[] valuesArray = new ContentValues[numDays];
        ContentValues values ;
        JSONObject dayObject;
        JSONObject tempObject;
        JSONObject weatherObject;
        ElClimaEntity entity;

        for (int i = 0; i < numDays; i++) {
            dayObject  = weatherArray.getJSONObject(i);
            tempObject = dayObject.getJSONObject(OWM_TEMPERATURE);
            weatherObject = dayObject.getJSONArray(OWM_WEATHER).optJSONObject(0);
            entity = new ElClimaEntity(dayObject.getLong(OWM_DT),
                    tempObject.getDouble(OWM_DAY),
                    tempObject.getDouble(OWM_MIN),
                    tempObject.getDouble(OWM_MAX),
                    tempObject.getDouble(OWM_NIGHT),
                    tempObject.getDouble(OWM_EVE),
                    tempObject.getDouble(OWM_MORN),
                    dayObject.getDouble(OWM_PRESSURE),
                    dayObject.getDouble(OWM_SPEED),
                    dayObject.getInt(OWM_HUMIDITY),
                    weatherObject.getInt(OWM_W_ID),
                    dayObject.getInt(OWM_DEG),
                    dayObject.getInt(OWM_CLOUDS),
                    weatherObject.getString(OWM_W_MAIN),
                    weatherObject.getString(OWM_W_DESCRIPTION),
                    weatherObject.getString(OWM_W_ICON)
                    );
            values =  new ContentValues();
            values.put(COLUMN_DATE,entity.getDate());
            values.put(COLUMN_T_DAY,entity.gettDay());
            values.put(COLUMN_T_MIN,entity.gettMin());
            values.put(COLUMN_T_MAX,entity.gettMax());
            values.put(COLUMN_T_NIGHT,entity.gettNight());
            values.put(COLUMN_T_EVE,entity.gettEvening());
            values.put(COLUMN_T_MORN,entity.gettMorning());
            values.put(COLUMN_PRESSURE,entity.getPressure());
            values.put(COLUMN_HUMIDITY,entity.getHumidity());
            values.put(COLUMN_W_ID,entity.getwID());
            values.put(COLUMN_W_MAIN,entity.getwMain());
            values.put(COLUMN_W_DESCRIPTION,entity.getwDescription());
            values.put(COLUMN_W_ICON,entity.getwIcon());
            values.put(COLUMN_SPEED,entity.getSpeed());
            values.put(COLUMN_DEG,entity.getDeg());
            values.put(COLUMN_CLOUDS,entity.getClouds());
            valuesArray[i]= values;
        }
        this.getContentResolver().delete(CONTENT_URI_FORECAST,"",null);
        this.getContentResolver().bulkInsert(CONTENT_URI_FORECAST,valuesArray);
    }

    //format date
    private String getReadableDateString(long time) {
        SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEE MMM dd");
        return shortenedDateFormat.format(time);
    }

    //format High and low
    private String formatHighLows(double high, double low) {
        return new StringBuilder()
                .append(Math.round(high))
                .append("/")
                .append(Math.round(low))
                .toString();
    }

    //start service with a static function to avoid any conflict
    public static void startMe(@NonNull Context context, @NonNull String regionID, @NonNull String unit) {
        Intent intent = new Intent(context, ElclimaMainService.class);
        intent
                .putExtra(EXTRA_REGION_ID, regionID)
                .putExtra(EXTRA_UNIT, unit);
        context.startService(intent);
    }
}
