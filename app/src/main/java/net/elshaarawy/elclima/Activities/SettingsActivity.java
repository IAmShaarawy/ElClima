package net.elshaarawy.elclima.Activities;

import android.os.CountDownTimer;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceScreen;
import android.view.MenuItem;
import android.widget.Toast;

import net.elshaarawy.elclima.R;

import java.util.Timer;
import java.util.TimerTask;

public class SettingsActivity extends AppCompatActivity {

    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public void onBackPressed() {

        Toast t = Toast.makeText(this, "One more Time", Toast.LENGTH_SHORT);
        if (counter > 0) {
            super.onBackPressed();
        } else {
            t.show();
            counter++;
        }
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                counter = 0;
            }
        }, 1500);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
