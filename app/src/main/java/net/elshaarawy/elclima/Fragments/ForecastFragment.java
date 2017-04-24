package net.elshaarawy.elclima.Fragments;


import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.elshaarawy.elclima.Activities.SettingsActivity;
import net.elshaarawy.elclima.ForecastAdapter;
import net.elshaarawy.elclima.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ForecastFragment extends android.support.v4.app.Fragment implements
        ForecastAdapter.ListItemClickListener,
        LoaderManager.LoaderCallbacks<String[]>{

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
                triggerLoader(region);
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
    public Loader<String[]> onCreateLoader(int id,  Bundle args) {

        return null;
    }

    @Override
    public void onLoadFinished(Loader<String[]> loader, String[] data) {

        if (data != null) {

            weekForecast = new ArrayList<>(Arrays.asList(data));
            mForecastAdapter.resetData(new ArrayList<>(Arrays.asList(data)));
        }
    }

    @Override
    public void onLoaderReset(Loader<String[]> loader) {

    }

    private void triggerLoader(String regionId) {

        Bundle bundle = new Bundle();
        bundle.putString(REGION_ID_EXTRA, regionId);
        bundle.putString(UNIT_EXTRA,mSharedPreferences.getString(getString(R.string.prefK_unit),getString(R.string.prefD_unit)));

        if (loaderManager == null) {
            loaderManager.initLoader(LOADER_ID, bundle, this).forceLoad();
        } else {
            loaderManager.restartLoader(LOADER_ID, bundle, this).forceLoad();
        }
    }
}