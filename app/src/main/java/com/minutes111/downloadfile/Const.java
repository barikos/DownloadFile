package com.minutes111.downloadfile;

/**
 * Created by barikos on 13.05.16.
 */
public class Const {
    public static final String LOG_TAG = "myLogs";

    public static final String FILE_URL = "http://www.ex.ua/get/26916652";
    public static final String FILE_NAME = "test.mp3";
    public static final String DIR_NAME = "/mystorage/";

    public static final int NOTIFY_ID = 110;

    public static final String EX_ATTR_FILE_URL = "ex_fileUrl";
    public static final String EX_ATTR_FILE_NAME = "ex_fileName";
    public static final String EX_ATTR_PROC = "process";
    public static final String EX_PROC_DOWNLOAD = "download";
    public static final String EX_PROC_UPDATE = "update";


    public static final String PREF_NAME = "name_pref";
    public static final String PREF_ATTR_FURL = "p_file_URL";
    public static final String PREF_ATTR_DATE = "p_date";

    public static final String INT_FILTER_SERVICE = "com.minutes111.downloadfile.sevice.downloadservice";
    public static final String INT_FILTER_ACTIVITY = "com.minutes111.downloadfile.ui.mainactivity";
}
