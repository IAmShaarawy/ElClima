package net.elshaarawy.elclima;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.provider.Browser;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.preference.PreferenceManager;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import static net.elshaarawy.elclima.ElclimaMainService.startMe;

/**
 * Created by elshaarawy on 26-Apr-17.
 */

public class ElClimaJobService extends JobService {

    private SharedPreferences preferences;
    private LocalBroadcastManager broadcastManager;
    private NotificationBroadcaster mReceiver;

    @Override
    public boolean onStartJob(JobParameters job) {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        //get Instance from LocalBroadcastManager
        broadcastManager = LocalBroadcastManager.getInstance(this);
        //get feeding values for ElclimaMainService
        String regionID = preferences.getString(getString(R.string.prefK_location_id), getString(R.string.prefD_location_id));
        String unit = preferences.getString(getString(R.string.prefK_unit), getString(R.string.prefD_unit));
        //start ElclimaMainService
        startMe(this, regionID, unit, true);
        mReceiver = new NotificationBroadcaster();
        //register mReceiver BroadcastReceiver
        broadcastManager.registerReceiver(mReceiver, mReceiver.getFilter());
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        //UnRegister mReceiver BroadcastReceiver
        broadcastManager.unregisterReceiver(mReceiver);
        return false;
    }
}
