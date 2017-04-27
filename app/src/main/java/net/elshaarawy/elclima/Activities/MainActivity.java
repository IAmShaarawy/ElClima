package net.elshaarawy.elclima.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import net.elshaarawy.elclima.ElClimaJobService;
import net.elshaarawy.elclima.R;

public class MainActivity extends AppCompatActivity {

    private static final String MAIN_JOB_TAG = "main_job_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Driver driver = new GooglePlayDriver(this);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);
        Job job = dispatcher.newJobBuilder()
                .setService(ElClimaJobService.class)
                .setTag(MAIN_JOB_TAG)
                .setConstraints(Constraint.ON_UNMETERED_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(3600,7200))
                .setReplaceCurrent(true)
                .build();
        dispatcher.schedule(job);
    }


    @Override
    protected void onResume() {
        super.onResume();

    }
}
