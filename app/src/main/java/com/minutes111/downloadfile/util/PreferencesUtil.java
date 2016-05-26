package com.minutes111.downloadfile.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.minutes111.downloadfile.Const;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by barikos on 23.05.16.
 */
public class PreferencesUtil {

    public static final String PREF_NAME = "name_pref";
    public static final String PREF_ATTR_FURL = "p_file_URL";
    public static final String PREF_ATTR_DATE = "p_date";

    private static PreferencesUtil sPreferencesUtil = null;
    private SharedPreferences mPreferences;

    private PreferencesUtil(Context context) {
        mPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static PreferencesUtil getInstance(Context context) {
        if (sPreferencesUtil == null) {
            sPreferencesUtil = new PreferencesUtil(context);
        }
        return sPreferencesUtil;
    }

    public String getString(String attr) {
        String extra = mPreferences.getString(attr, "");
        return extra;
    }

    public void setPreferences(String url) {
        SharedPreferences.Editor editor = mPreferences.edit();
        String date = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(Calendar.getInstance().getTime());
        editor.putString(PREF_ATTR_FURL, url);
        editor.putString(PREF_ATTR_DATE, date);
        editor.commit();
    }

    public boolean isPreferencesExist(String attr) {
        return mPreferences.getString(attr, "").equals(Const.FILE_URL);
    }
}
