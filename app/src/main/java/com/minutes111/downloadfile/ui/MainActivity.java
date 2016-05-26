package com.minutes111.downloadfile.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.minutes111.downloadfile.Const;
import com.minutes111.downloadfile.R;
import com.minutes111.downloadfile.util.NotificationUtil;
import com.minutes111.downloadfile.util.PreferencesUtil;

/**
 * Class {@link MainActivity}
 *
 * @author Alexandr Barkovskiy
 * @version 1.0
 * @since 20.05.16
 */

public class MainActivity extends AppCompatActivity {

    private PreferencesUtil mPreferences;
    private NotificationUtil mNotification;

    private Button mButton;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.btn_main);
        mTextView = (TextView) findViewById(R.id.tv_main_date);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mNotification = NotificationUtil.getInstance(this);
        mPreferences = PreferencesUtil.getInstance(this);
        String fileUrl = mPreferences.getString(PreferencesUtil.PREF_ATTR_FURL);
        String date = mPreferences.getString(PreferencesUtil.PREF_ATTR_DATE);

        final Intent intent = new Intent(Const.INT_FILTER_SERVICE).putExtra(Const.EX_ATTR_FILE_URL, Const.FILE_URL);
        if (fileUrl.equals(Const.FILE_URL)) {
            mNotification.cancel();
            mTextView.setText(date);
            mButton.setText(R.string.btn_main_update);
            intent.putExtra(Const.EX_ATTR_PROC, Const.EX_PROC_UPDATE);
        } else {
            mButton.setText(R.string.btn_main_download);
            intent.putExtra(Const.EX_ATTR_PROC, Const.EX_PROC_DOWNLOAD);
        }
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(intent);
                finish();
            }
        });
    }

}
