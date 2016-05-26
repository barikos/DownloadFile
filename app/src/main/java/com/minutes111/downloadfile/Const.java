package com.minutes111.downloadfile;

/**
 * Created by barikos on 13.05.16.
 */
public class Const {
    public static final String LOG_TAG = "myLogs";

    public static final String FILE_URL = "http://www.ex.ua/get/26916652";
    public static final String FILE_NAME = "test.mp3";
    public static final String DIR_NAME = "/mystorage/";

    public static final int PHASE_BEGIN = 210;
    public static final int PHASE_END = 211;

    public static final String EX_ATTR_FILE_URL = "ex_fileUrl";
    public static final String EX_ATTR_PROC = "process";
    public static final int EX_PROC_DOWNLOAD = 201;
    public static final int EX_PROC_UPDATE = 202;

    public static final String INT_FILTER_SERVICE = "com.minutes111.downloadfile.sevice.downloadservice";
    public static final String INT_FILTER_ACTIVITY = "com.minutes111.downloadfile.ui.mainactivity";
}
