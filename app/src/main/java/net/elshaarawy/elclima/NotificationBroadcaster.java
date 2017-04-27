package net.elshaarawy.elclima;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Entity;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.RemoteViews;

import com.squareup.picasso.Picasso;

import net.elshaarawy.elclima.Activities.DetailActivity;
import net.elshaarawy.elclima.Data.ElClimaEntity;

import java.io.IOException;

/**
 * Created by elshaarawy on 27-Apr-17.
 */

public class NotificationBroadcaster extends BroadcastReceiver {
    private static final String EXTRA_ENTITY = "entity_extra";
    private static final String ACTION_MAIN = "action_main";

    @Override
    public void onReceive(final Context context, Intent intent) {
        ElClimaEntity elClimaEntity;
        if (intent == null || !intent.hasExtra(EXTRA_ENTITY)) {
            return;
        }

        elClimaEntity = intent.getParcelableExtra(EXTRA_ENTITY);
        String description = elClimaEntity.getwDescription();
        String highLow = formatHighLows(elClimaEntity.gettMax(), elClimaEntity.gettMin());

        String iconUrl = "http://openweathermap.org/img/w/" + elClimaEntity.getwIcon() + ".png";

        final NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        final Notification.Builder builder = new Notification.Builder(context);
        //Intent to launch DetailActivity
        Intent detailIntent = new Intent(context, DetailActivity.class);
        //declare Pending Intent to launch detailActivity intent from notification
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1001, detailIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //get Default Notification Ringtone Uri
        Uri defaultNotificationRingTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //build Notification
        builder.setContentTitle(description)
                .setContentText("Expected Temperature is " +highLow)
                .setSmallIcon(R.drawable.ic_brightness_7_white_24dp)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(defaultNotificationRingTone);
        //AsyncTask Execution to load weather icon and push notification
        new AsyncTask<Object, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Object... params) {
                Context lContext = (Context)params[0];
                Bitmap bitmap;
                try {
                    bitmap = Picasso.with(lContext).load((String)params[1]).get();
                } catch (IOException e) {
                    bitmap = BitmapFactory.decodeResource(lContext.getResources(),R.drawable.ic_launcher);
                }
                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                builder.setLargeIcon(bitmap);
                manager.notify(1001,builder.build());
            }
        }.execute(context,iconUrl);

    }

    public IntentFilter getFilter(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_MAIN);
        return filter;
    }

    public static void startNotificationBroadcaster(@NonNull Context context, @NonNull ElClimaEntity entity) {
        Intent intent = new Intent(ACTION_MAIN);
        intent.putExtra(EXTRA_ENTITY, entity);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    private String formatHighLows(double high, double low) {
        return new StringBuilder()
                .append(Math.round(high))
                .append("/")
                .append(Math.round(low))
                .toString();
    }


}
