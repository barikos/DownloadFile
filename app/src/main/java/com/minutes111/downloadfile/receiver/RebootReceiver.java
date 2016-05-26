package com.minutes111.downloadfile.receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.minutes111.downloadfile.util.NotificationUtil;
import com.minutes111.downloadfile.util.PreferencesUtil;

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
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        PreferencesUtil preferences = PreferencesUtil.getInstance(context);

        NotificationUtil nu = NotificationUtil.getInstance(context);

        if (preferences.isPreferencesExist(PreferencesUtil.PREF_ATTR_FURL)) {
            /*Intent serviceIntent = new Intent(Const.INT_FILTER_SERVICE);
            serviceIntent.putExtra(Const.EX_ATTR_FILE_URL, Const.FILE_URL);
            serviceIntent.putExtra(Const.EX_ATTR_PROC, Const.EX_PROC_UPDATE);
            PendingIntent pServiceIntent = PendingIntent.getService(context, 0, serviceIntent, 0);

            Intent activityIntent = new Intent(Const.INT_FILTER_ACTIVITY);
            PendingIntent pActivityIntent = PendingIntent.getActivity(context, 0, activityIntent, PendingIntent.FLAG_ONE_SHOT);

            Resources res = context.getResources();
            builder.setSmallIcon(R.drawable.ic_file_download_white_18dp);
            builder.setTicker(res.getString(R.string.txt_notify_renew_ticker));

            RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.custom_notif);
            contentView.setImageViewResource(R.id.img_notify_main, R.drawable.ic_file_download_white_18dp);
            contentView.setTextViewText(R.id.txt_notify_title, res.getString(R.string.txt_notify_renew_title));
            contentView.setTextViewText(R.id.txt_notify_text, res.getString(R.string.txt_notify_renew_text));
            contentView.setImageViewResource(R.id.img_notify_renew, R.drawable.ic_autorenew_white_18dp);
            contentView.setOnClickPendingIntent(R.id.img_notify_renew, pServiceIntent);
            contentView.setOnClickPendingIntent(R.id.rlay_custom_not, pActivityIntent);

            Notification notification = builder.build();
            notification.contentView = contentView;
            nm.notify(NotificationUtil.NOTIFY_ID, notification);*/
            nu.sendCustomNotify();
        }
    }
}
