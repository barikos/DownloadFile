package com.minutes111.downloadfile.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.minutes111.downloadfile.Const;
import com.minutes111.downloadfile.network.FileDownloader;
import com.minutes111.downloadfile.util.PreferencesUtil;

/**
 * Class {@link DownloadService}
 *
 * @author Alexandr Barkovskiy
 * @version 1.0
 * @since 20.05.16
 */

public class DownloadService extends Service {

    private PreferencesUtil mPreferences;

    public DownloadService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mPreferences = PreferencesUtil.getInstance(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String url = intent.getStringExtra(Const.EX_ATTR_FILE_URL);
        int process = intent.getIntExtra(Const.EX_ATTR_PROC,0);
        new FileDownloader(url,this,process).execute();

        mPreferences.setPreferences(url);
        Log.d(Const.LOG_TAG,"before stop");
        stopSelfResult(startId);
        return Service.START_FLAG_REDELIVERY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
