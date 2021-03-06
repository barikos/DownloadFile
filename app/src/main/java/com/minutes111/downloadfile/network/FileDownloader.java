package com.minutes111.downloadfile.network;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.minutes111.downloadfile.Const;
import com.minutes111.downloadfile.util.NotificationUtil;

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
 *
 * @author Alexandr Barkovskiy
 * @version 1.0
 * @since 20.05.16
 */
public class FileDownloader extends AsyncTask<String, Integer, Void> {

    private String mUrl;
    private int mProcess;
    private Context mContext;

    private NotificationUtil mNotification;

    public FileDownloader(String url, Context context, int process) {
        mUrl = url;
        mProcess = process;
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mNotification = NotificationUtil.getInstance(mContext);
        mNotification.sendNotification(mProcess, Const.PHASE_BEGIN);
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
        mNotification.sendProgressNotify(values[0]);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mNotification.sendNotification(mProcess, Const.PHASE_END);
    }
}
