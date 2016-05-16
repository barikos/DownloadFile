package com.minutes111.downloadfile.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.minutes111.downloadfile.Const;
import com.minutes111.downloadfile.R;

/**
 * Class {@link RebootReceiver}
 *
 * @author Alexandr Barkovskiy
 * @version 1.0
 * @since 20.05.16
 */

public class RebootReceiver extends BroadcastReceiver {

    public RebootReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        SharedPreferences preferences = context.getSharedPreferences(Const.PREF_NAME,Context.MODE_PRIVATE);

        String url = preferences.getString(Const.PREF_ATTR_FURL,"");
        if (url.equals(Const.FILE_URL)){

            Intent serviceIntent = new Intent(Const.INT_FILTER_SERVICE);
            serviceIntent.putExtra(Const.EX_ATTR_FILE_URL, Const.FILE_URL);
            serviceIntent.putExtra(Const.EX_ATTR_PROC, Const.EX_PROC_UPDATE);
            PendingIntent pServiceIntent = PendingIntent.getService(context, 0, serviceIntent, 0);

            Intent activityIntent = new Intent(Const.INT_FILTER_ACTIVITY);
            PendingIntent pActivityIntent = PendingIntent.getActivity(context, 0, activityIntent, 0);

            Resources res = context.getResources();
            builder.setSmallIcon(R.drawable.ic_file_download_white_18dp);
            builder.setTicker(res.getString(R.string.txt_notify_renew_ticker));

            RemoteViews contentView = new RemoteViews(context.getPackageName(),R.layout.custom_notif);
            contentView.setImageViewResource(R.id.img_notify_main, R.drawable.ic_file_download_white_18dp);
            contentView.setTextViewText(R.id.txt_notify_title, res.getString(R.string.txt_notify_renew_title));
            contentView.setTextViewText(R.id.txt_notify_text, res.getString(R.string.txt_notify_renew_text));
            contentView.setImageViewResource(R.id.img_notify_renew, R.drawable.ic_autorenew_white_18dp);
            contentView.setOnClickPendingIntent(R.id.img_notify_renew, pServiceIntent);
            contentView.setOnClickPendingIntent(R.id.rlay_custom_not, pActivityIntent);

            Notification notification = builder.build();
            notification.contentView = contentView;
            nm.notify(Const.NOTIFY_ID, notification);
        }

    }


}
