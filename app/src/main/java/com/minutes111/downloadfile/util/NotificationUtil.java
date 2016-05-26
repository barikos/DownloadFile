package com.minutes111.downloadfile.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.minutes111.downloadfile.Const;
import com.minutes111.downloadfile.R;
import com.minutes111.downloadfile.ui.MainActivity;

/**
 * Created by barikos on 24.05.16.
 */
public class NotificationUtil {

    public static final int NOTIFY_ID = 110;

    private String mTitle;
    private String mText;

    private static NotificationUtil sNotification;
    private NotificationManager mManager;
    private NotificationCompat.Builder mBuilder;
    private Resources mResources;
    private Context mContext;

    private NotificationUtil(Context context){
        mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(context);
        mResources = context.getResources();
        mContext = context;
    }

    public static NotificationUtil getInstance(Context context) {
        if (sNotification == null){
            sNotification = new NotificationUtil(context);
        }
        return sNotification;
    }

    public void sendBaseNotify(int process) {
        mBuilder.setProgress(100, 0, false);
        mBuilder.setAutoCancel(false);
        if (process == Const.EX_PROC_DOWNLOAD) {
            mBuilder.setSmallIcon(R.drawable.ic_file_download_white_18dp);
            mBuilder.setTicker(mResources.getString(R.string.txt_notify_down_ticker));
            mBuilder.setContentTitle(mResources.getString(R.string.txt_notify_down_title));
            mBuilder.setContentText(mResources.getString(R.string.txt_notify_down_progress));
        } else {
            mBuilder.setSmallIcon(R.drawable.ic_autorenew_white_18dp);
            mBuilder.setTicker(mResources.getString(R.string.txt_notify_upd_ticker));
            mBuilder.setContentTitle(mResources.getString(R.string.txt_notify_upd_title));
            mBuilder.setContentText(mResources.getString(R.string.txt_notify_upd_progress));
        }
        mManager.notify(NOTIFY_ID, mBuilder.build());
    }

    public void sendFinishNotify(int process) {
        Intent intent = new Intent(mContext, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
        if (process == Const.EX_PROC_DOWNLOAD) {
            mTitle = mResources.getString(R.string.txt_notify_down_title);
            mText = mResources.getString(R.string.txt_notify_down_progress_finish);
        } else {
            mTitle = mResources.getString(R.string.txt_notify_upd_title);
            mText = mResources.getString(R.string.txt_notify_down_progress_finish);
        }
        mBuilder.setContentTitle(mTitle);
        mBuilder.setContentText(mText);
        mBuilder.setProgress(0, 0, false);
        mBuilder.setContentIntent(pIntent);
        mManager.notify(NOTIFY_ID, mBuilder.build());
    }

    public void sendProgressNotify(Integer progress) {
        mBuilder.setProgress(100, progress, false);
        mManager.notify(NOTIFY_ID, mBuilder.build());
    }

    public void sendCustomNotify(){
        Intent serviceIntent = new Intent(Const.INT_FILTER_SERVICE);
        serviceIntent.putExtra(Const.EX_ATTR_FILE_URL, Const.FILE_URL);
        serviceIntent.putExtra(Const.EX_ATTR_PROC, Const.EX_PROC_UPDATE);
        PendingIntent pServiceIntent = PendingIntent.getService(mContext, 0, serviceIntent, 0);

        Intent activityIntent = new Intent(Const.INT_FILTER_ACTIVITY);
        PendingIntent pActivityIntent = PendingIntent.getActivity(mContext, 0, activityIntent, PendingIntent.FLAG_ONE_SHOT);

        mBuilder.setSmallIcon(R.drawable.ic_file_download_white_18dp);
        mBuilder.setTicker(mResources.getString(R.string.txt_notify_renew_ticker));

        RemoteViews contentView = new RemoteViews(mContext.getPackageName(), R.layout.custom_notif);
        contentView.setImageViewResource(R.id.img_notify_main, R.drawable.ic_file_download_white_18dp);
        contentView.setTextViewText(R.id.txt_notify_title, mResources.getString(R.string.txt_notify_renew_title));
        contentView.setTextViewText(R.id.txt_notify_text, mResources.getString(R.string.txt_notify_renew_text));
        contentView.setImageViewResource(R.id.img_notify_renew, R.drawable.ic_autorenew_white_18dp);
        contentView.setOnClickPendingIntent(R.id.img_notify_renew, pServiceIntent);
        contentView.setOnClickPendingIntent(R.id.rlay_custom_not, pActivityIntent);

        Notification notification = mBuilder.build();
        notification.contentView = contentView;
        mManager.notify(NotificationUtil.NOTIFY_ID, notification);
    }

    public void cancel(){
        mManager.cancel(NOTIFY_ID);
    }

    public void sendNotification(int process, int phase){
        String ticker,title, text;
        int icon;
        int maxProgress;
        if (process == Const.EX_PROC_DOWNLOAD){
            icon = R.drawable.ic_file_download_white_18dp;
            ticker = mResources.getString(R.string.txt_notify_down_ticker);
            title = mResources.getString(R.string.txt_notify_down_title);
            if (phase == Const.PHASE_BEGIN){
                text = mResources.getString(R.string.txt_notify_down_progress);
                maxProgress = 100;
            }else {
                text = mResources.getString(R.string.txt_notify_down_progress_finish);
                maxProgress = 0;
                Intent intent = new Intent(mContext, MainActivity.class);
                PendingIntent pIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
                mBuilder.setContentIntent(pIntent);
            }
        } else {
            icon = R.drawable.ic_autorenew_white_18dp;
            ticker = mResources.getString(R.string.txt_notify_upd_ticker);
            title = mResources.getString(R.string.txt_notify_upd_title);
            if (phase == Const.PHASE_BEGIN){
                text = mResources.getString(R.string.txt_notify_upd_progress);
                maxProgress = 100;
            } else {
                text = mResources.getString(R.string.txt_notify_upd_progress_finish);
                maxProgress = 0;
                Intent intent = new Intent(mContext, MainActivity.class);
                PendingIntent pIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
                mBuilder.setContentIntent(pIntent);
            }
        }

        mBuilder.setTicker(ticker);
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(text);
        mBuilder.setSmallIcon(icon);
        mBuilder.setProgress(maxProgress,0,false);
        mManager.notify(NOTIFY_ID,mBuilder.build());
    }


}
