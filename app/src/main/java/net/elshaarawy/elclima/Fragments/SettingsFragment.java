package net.elshaarawy.elclima.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.widget.Toast;

import net.elshaarawy.elclima.R;

import static net.elshaarawy.elclima.ElclimaMainService.startMe;


/**
 * Created by elshaarawy on 08-Apr-17.
 */

public class SettingsFragment extends PreferenceFragmentCompat
        implements SharedPreferences.OnSharedPreferenceChangeListener,
        Preference.OnPreferenceChangeListener {
    private SharedPreferences preferences;
    private Preference unitPreference;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_settings);

        PreferenceScreen preferenceScreen = getPreferenceScreen();
        preferences = preferenceScreen.getSharedPreferences();
        unitPreference = findPreference(getString(R.string.prefK_location_id));

        preferences.registerOnSharedPreferenceChangeListener(this);
        unitPreference.setOnPreferenceChangeListener(this);

        for (int i = 0; i < preferenceScreen.getPreferenceCount(); i++) {
            Preference preference = preferenceScreen.getPreference(i);
            setPreferenceSummary(preference);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        preferences.unregisterOnSharedPreferenceChangeListener(this);
        unitPreference.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String pKey = preference.getKey();
        if (pKey.equals(getString(R.string.prefK_location_id))) {
            Toast eToast = Toast.makeText(this.getContext(), "NOT VALID ID", Toast.LENGTH_SHORT);
            String value = (String) newValue;
            try {
                Integer.parseInt(value);
                if (value.length() < 5 || value.length() > 7) {
                    eToast.show();
                    return false;
                }
            } catch (NumberFormatException e) {
                eToast.show();
                return false;
            }


        }
        return true;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference changedPreference = findPreference(key);
        setPreferenceSummary(changedPreference);
        if (key.equals(getString(R.string.prefK_location_id)) ||
                key.equals(getString(R.string.prefK_unit))) {

            startMe(getContext(),
                    sharedPreferences.getString(getString(R.string.prefK_location_id), getString(R.string.prefD_location_id)),
                    sharedPreferences.getString(getString(R.string.prefK_unit), getString(R.string.prefD_unit))
            );
        }
    }

    private void setPreferenceSummary(Preference preference) {

        if (preference instanceof ListPreference) {
            ListPreference myLP = (ListPreference) preference;
            // get preference value and its index
            String value = myLP.getValue();
            int valueIndex = myLP.findIndexOfValue(value);
            //to Show the Entry not Entry Value
            preference.setSummary(myLP.getEntries()[valueIndex]);
        }

        if (preference instanceof EditTextPreference) {
            preference.setSummary(((EditTextPreference) preference).getText());
        }
    }
}
