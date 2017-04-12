package net.elshaarawy.elclima;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

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

/**
 * Created by elshaarawy on 08-Apr-17.
 */

public class WeatherLoaderTask extends AsyncTaskLoader<String[]> {
    private static final String REGION_ID_EXTRA = "regionIdExtra";
    private final static String UNIT_EXTRA = "unitExtra";
    Bundle args;
    Uri weatherUri;

    public WeatherLoaderTask(Context context, Bundle args) {
        super(context);
        this.args = args;
        Uri weatherBaseUri = Uri.parse("http://api.openweathermap.org/data/2.5/forecast/daily");
        weatherUri = weatherBaseUri.buildUpon()
                .appendQueryParameter("APPID", "88d1c681c8761123026e5a34857459bd")
                .appendQueryParameter("mode", "json")
                .appendQueryParameter("units",  args.getString(UNIT_EXTRA))
                .appendQueryParameter("cnt", "16")
                .appendQueryParameter("id", args.getString(REGION_ID_EXTRA))
                .build();
    }


    @Override
    public String[] loadInBackground() {

        String[] data = loadData(weatherUri);
        return data;
    }


    //http connection
    private String[] loadData(Uri weatherUri) {
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
                return null;
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuffer buffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0)
                return null;

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
            return getWeatherDataFromJson(forecastJsonStr, 7);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Parse JSON String
    private String[] getWeatherDataFromJson(String forecastJsonStr, int numDays) throws JSONException {

        final String OWM_LIST = "list";
        final String OWM_TEMPERATURE = "temp";
        final String OWM_MIN = "min";
        final String OWM_MAX = "max";
        final String OWM_WEATHER = "weather";
        final String OWM_DESCRIPTION = "main";
        if (forecastJsonStr == null)
            return null;
        JSONObject forecastJson = new JSONObject(forecastJsonStr);
        JSONArray weatherArray = forecastJson.getJSONArray(OWM_LIST);

        Time dayTime = new Time();
        dayTime.setToNow();
        int julianStartDay = Time.getJulianDay(System.currentTimeMillis(), dayTime.gmtoff);
        dayTime.setToNow();

        String[] resultString = new String[numDays];
        for (int i = 0; i < numDays; i++) {
            String day;
            String description;
            String highAndLow;

            //get ith weather object
            JSONObject dayForecast = weatherArray.getJSONObject(i);

            // form the date
            day = getReadableDateString(dayTime.setJulianDay(julianStartDay + i));

            //get description
            JSONObject weatherObject = dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
            description = weatherObject.getString(OWM_DESCRIPTION);

            //get Temp
            JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMPERATURE);
            double high = temperatureObject.getDouble(OWM_MAX);
            double low = temperatureObject.getDouble(OWM_MIN);
            highAndLow = formatHighLows(high, low);

            // build row of weather data
            resultString[i] = new StringBuilder()
                    .append(day).append(" - ")
                    .append(description).append(" - ")
                    .append(highAndLow).toString();
        }
        return resultString;
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
}
