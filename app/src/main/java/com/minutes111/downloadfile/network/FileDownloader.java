package com.minutes111.downloadfile.network;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.minutes111.downloadfile.Const;
import com.minutes111.downloadfile.R;
import com.minutes111.downloadfile.ui.MainActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Class {@link FileDownloader}
 * Download or update file from network by url
 * @author Alexandr Barkovskiy
 * @version 1.0
 * @since 20.05.16
 */
public class FileDownloader extends AsyncTask<String, Integer, Void> {

    private String mUrl;
    private String mProcess;
    private Context mContext;

    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;

    public FileDownloader(String url, Context context, String process) {
        mUrl = url;
        mProcess = process;
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(mContext);
        sendBaseNotify();
    }

    @Override
    protected Void doInBackground(String... params) {

        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(mUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.d(Const.LOG_TAG, "Server returned HTTP " + connection.getResponseCode() + " " + connection.getResponseMessage());
            }

            int fileLength = connection.getContentLength();
            inputStream = connection.getInputStream();
            File file = new File(Environment.getExternalStorageDirectory() + Const.DIR_NAME, Const.FILE_NAME);
            outputStream = new FileOutputStream(file);

            byte[] data = new byte[4096];
            long total = 0;
            long tempProgress = 0;
            int count;

            while ((count = inputStream.read(data)) != -1) {
                total += count;
                if (fileLength > 0) {
                    int progress = (int) (total * 100 / fileLength);
                    if (progress != tempProgress) {
                        publishProgress(progress);
                        tempProgress = progress;
                    }
                    outputStream.write(data, 0, count);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null)
                    outputStream.close();
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (connection != null)
                connection.disconnect();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        sendProgressNotify(values[0]);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        sendFinishNotify();
    }

    private void sendFinishNotify(){
        Intent intent = new Intent(mContext, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(mContext, 0, intent, 0);

        Resources res = mContext.getResources();
        if (mProcess.equals(Const.EX_PROC_DOWNLOAD)){
            mBuilder.setContentTitle(res.getString(R.string.txt_notify_title_down_stop));
            mBuilder.setContentText(res.getString(R.string.txt_notify_down_prog_finish));
        }else {
            mBuilder.setContentTitle(res.getString(R.string.txt_notify_title_upd_stop));
            mBuilder.setContentText(res.getString(R.string.txt_notify_upd_prog_finish));
        }

        mBuilder.setProgress(0,0,false);
        mBuilder.setContentIntent(pIntent);
        mNotificationManager.notify(Const.NOTIFY_ID, mBuilder.build());

    }

    private void sendBaseNotify(){
        Resources res = mContext.getResources();
        mBuilder.setProgress(100, 0, false);
        mBuilder.setAutoCancel(false);
        if (mProcess.equals(Const.EX_PROC_DOWNLOAD)){
            mBuilder.setSmallIcon(R.drawable.ic_file_download_white_18dp);
            mBuilder.setTicker(res.getString(R.string.txt_notify_down_ticker));
            mBuilder.setContentTitle(res.getString(R.string.txt_notify_title_down_start));
            mBuilder.setContentText(res.getString(R.string.txt_notify_down_progress));
        }else {
            mBuilder.setSmallIcon(R.drawable.ic_autorenew_white_18dp);
            mBuilder.setTicker(res.getString(R.string.txt_notify_upd_ticker));
            mBuilder.setContentTitle(res.getString(R.string.txt_notify_title_upd_start));
            mBuilder.setContentText(res.getString(R.string.txt_notify_upd_progress));
        }

        mNotificationManager.notify(Const.NOTIFY_ID, mBuilder.build());
    }

    private void sendProgressNotify(Integer progress) {
        mBuilder.setProgress(100, progress, false);
        mNotificationManager.notify(Const.NOTIFY_ID, mBuilder.build());
    }
}
