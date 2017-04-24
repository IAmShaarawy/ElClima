package net.elshaarawy.elclima;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by elshaarawy on 24-Apr-17.
 */

public class ElclimaMainService extends IntentService {


    public ElclimaMainService() {
        super("ElclimaMainService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
    }
}
