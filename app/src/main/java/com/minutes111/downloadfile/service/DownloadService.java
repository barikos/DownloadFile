package com.minutes111.downloadfile.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import com.minutes111.downloadfile.Const;
import com.minutes111.downloadfile.network.FileDownloader;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Class {@link DownloadService}
 *
 * @author Alexandr Barkovskiy
 * @version 1.0
 * @since 20.05.16
 */

public class DownloadService extends Service {

    private SharedPreferences mPreferences;

    public DownloadService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mPreferences = getSharedPreferences(Const.PREF_NAME,MODE_PRIVATE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String url = intent.getStringExtra(Const.EX_ATTR_FILE_URL);
        String process = intent.getStringExtra(Const.EX_ATTR_PROC);
        new FileDownloader(url,this,process).execute();
        setSharedPreferences(url);
        stopSelf();
        return Service.START_FLAG_REDELIVERY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void setSharedPreferences(String url){
        SharedPreferences.Editor editor = mPreferences.edit();
        String date = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(Calendar.getInstance().getTime());
        editor.putString(Const.PREF_ATTR_FURL,url);
        editor.putString(Const.PREF_ATTR_DATE,date);
        editor.commit();
    }
}
