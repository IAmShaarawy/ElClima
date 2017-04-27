package net.elshaarawy.elclima.Fragments;


import android.content.Intent;
import android.content.SharedPreferences;

import android.database.Cursor;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.os.Bundle;

import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.elshaarawy.elclima.Activities.SettingsActivity;
import net.elshaarawy.elclima.Data.ElClimaContract;
import net.elshaarawy.elclima.Data.ElClimaEntity;
import net.elshaarawy.elclima.ForecastAdapter;
import net.elshaarawy.elclima.R;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static net.elshaarawy.elclima.Data.ElClimaContract.ElClimaColumns.*;
import static net.elshaarawy.elclima.ElclimaMainService.startMe;

public class ForecastFragment extends android.support.v4.app.Fragment implements
        ForecastAdapter.ListItemClickListener,
        LoaderManager.LoaderCallbacks<Cursor> {

    private List<String> weekForecast;
    private ForecastAdapter mForecastAdapter;
    private RecyclerView mForecastRV;
    private final static int LOADER_ID = 44;
    private final static String REGION_ID_EXTRA = "regionIdExtra";
    private final static String UNIT_EXTRA = "unitExtra";
    private LoaderManager loaderManager;
    private SharedPreferences mSharedPreferences;

    public ForecastFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        mForecastRV = (RecyclerView) rootView.findViewById(R.id.rv_forecast);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        mForecastRV.setLayoutManager(linearLayoutManager);

        mForecastRV.setAdapter(mForecastAdapter);

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String[] data = {
                "Mon 6/23 - Sunny - 31/17",
                "Tue 6/24 - Foggy - 21/8",
                "Wed 6/25 - Cloudy - 22/17",
                "Thurs 6/26 - Rainy - 18/11",
                "Fri 6/27 - Foggy - 21/10",
                "Sat 6/28 - TRAPPED IN WEATHERSTATION - 23/18",
                "Sun 6/29 - Sunny - 20/7"
        };

        weekForecast = new ArrayList<>(Arrays.asList(data));
        mForecastAdapter = new ForecastAdapter(weekForecast, this);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("DataList")) {
                mForecastAdapter.resetData(savedInstanceState.getStringArrayList("DataList"));
            }
        }

        loaderManager = this.getLoaderManager();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (loaderManager == null) {
            loaderManager.initLoader(LOADER_ID, null, this).forceLoad();
        } else {
            loaderManager.restartLoader(LOADER_ID, null, this).forceLoad();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.forecasr_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_refresh:

                String region = mSharedPreferences.getString(getString(R.string.prefK_location_id), getString(R.string.prefD_location_id));
                //"349340"
                launchService(region);
                return true;
            case R.id.action_settings:
                getActivity().startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("DataList", (ArrayList<String>) weekForecast);
    }

    @Override
    public void onListItemClick(int position, List<String> dataSet) {
        Toast.makeText(this.getActivity(), dataSet.get(position), Toast.LENGTH_SHORT).show();
    }

    //Loader Callbacks
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new CursorLoader(getContext(), ElClimaContract.ProviderUris.CONTENT_URI_FORECAST, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        int columnsCount = data.getColumnCount();
        int rowsCount = data.getCount();

        ElClimaEntity entity;
        if (columnsCount > 0 && rowsCount > 0) {

            String[] w = new String[rowsCount];
            data.moveToFirst();
            for (int i = 0; i < rowsCount; i++) {

                entity = new ElClimaEntity(
                        data.getLong(data.getColumnIndex(COLUMN_DATE)),
                        data.getDouble(data.getColumnIndex(COLUMN_T_DAY)),
                        data.getDouble(data.getColumnIndex(COLUMN_T_MIN)),
                        data.getDouble(data.getColumnIndex(COLUMN_T_MAX)),
                        data.getDouble(data.getColumnIndex(COLUMN_T_NIGHT)),
                        data.getDouble(data.getColumnIndex(COLUMN_T_EVE)),
                        data.getDouble(data.getColumnIndex(COLUMN_T_MORN)),
                        data.getDouble(data.getColumnIndex(COLUMN_PRESSURE)),
                        data.getDouble(data.getColumnIndex(COLUMN_SPEED)),
                        data.getInt(data.getColumnIndex(COLUMN_HUMIDITY)),
                        data.getInt(data.getColumnIndex(COLUMN_W_ID)),
                        data.getInt(data.getColumnIndex(COLUMN_DEG)),
                        data.getInt(data.getColumnIndex(COLUMN_CLOUDS)),
                        data.getString(data.getColumnIndex(COLUMN_W_MAIN)),
                        data.getString(data.getColumnIndex(COLUMN_W_DESCRIPTION)),
                        data.getString(data.getColumnIndex(COLUMN_W_ICON))
                );
                DateTime dateTime = new DateTime(entity.getDate(), DateTimeZone.UTC);
                w[i] = dateTime.toString("EEE MMM dd") + " - " +
                        entity.getwMain() + " - " +
                        formatHighLows(entity.gettMax(), entity.gettMin());
                data.moveToNext();
            }
            weekForecast = new ArrayList<>(Arrays.asList(w));
            mForecastAdapter.resetData(new ArrayList<>(Arrays.asList(w)));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void launchService(String regionId) {
        startMe(getContext(),
                regionId,
                mSharedPreferences.getString(getString(R.string.prefK_unit), getString(R.string.prefD_unit)),
                false);
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