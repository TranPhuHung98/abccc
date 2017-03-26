package com.stdiohue.basestrcuture.utils;

import android.util.Log;

/**
 * Created by hung.nguyendk on 3/27/17.
 */

public class LogUtils {

    private static final String TAG = "StdioHueLog";

    LogUtils() {

    }

    public static void logE(String msg) {
        if (Constant.debug) {
            Log.e(TAG, msg);
        }
    }

    public static void logD(String msg) {
        if (Constant.debug) {
            Log.d(TAG, msg);
        }
    }
}
