package com.imark.printapp.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.os.SystemClock;

public class ViewUtils {
    private static long lastClickTime;
    public static boolean isFastClick() {
        return isFastClick(1000);
    }

    public static boolean isFastClick(int millisecond) {
        long time = SystemClock.elapsedRealtime();
        if (time - lastClickTime < millisecond) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static void alertDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setTitle(title)
                .setCancelable(true)
                .create()
                .show();
    }
}
