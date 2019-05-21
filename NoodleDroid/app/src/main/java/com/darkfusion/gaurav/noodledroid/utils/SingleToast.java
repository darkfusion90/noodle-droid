package com.darkfusion.gaurav.noodledroid.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Used to avoid stacking up of multiple toasts
 * Credit: https://stackoverflow.com/a/18676736/9721712
 * */
public class SingleToast {

    private static Toast mToast;

    public static void show(Context context, String text, int duration) {
        if (mToast != null) mToast.cancel();
        mToast = Toast.makeText(context, text, duration);
        mToast.show();
    }
}