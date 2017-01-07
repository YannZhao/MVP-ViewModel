package com.baha.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;

import com.baha.BahaApplication;

public class BahaUtil {
    public static void showToast(int id) {
        Toast.makeText(BahaApplication.app, BahaApplication.RESOURCES.getString(id), Toast.LENGTH_SHORT).show();
    }

    public static void showToast(String string) {
        if (!TextUtils.isEmpty(string)) {
            Toast.makeText(BahaApplication.app, string, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showToastLong(int id) {
        Toast.makeText(BahaApplication.app, BahaApplication.RESOURCES.getString(id),
                Toast.LENGTH_LONG).show();
    }

    public static void showToastLong(String string) {
        if (!TextUtils.isEmpty(string)) {
            Toast.makeText(BahaApplication.app, string, Toast.LENGTH_LONG).show();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public static int[] getDeviceSize() {
        int[] size = new int[2];

        int measuredWidth = 0;
        int measuredHeight = 0;
        Point point = new Point();
        WindowManager wm = ((WindowManager) BahaApplication.app.getSystemService(Context.WINDOW_SERVICE));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            wm.getDefaultDisplay().getSize(point);
            measuredWidth = point.x;
            measuredHeight = point.y;
        } else {
            DisplayMetrics dm = BahaApplication.app.getResources().getDisplayMetrics();
            measuredWidth = dm.widthPixels;
            measuredHeight = dm.heightPixels;
        }

        size[0] = measuredWidth;
        size[1] = measuredHeight;
        return size;
    }

    public static int dip2px(float dpValue) {
        final float scale = BahaApplication.RESOURCES.getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(float pxValue) {
        final float scale = BahaApplication.RESOURCES.getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getScreenWidth() {
        DisplayMetrics dm = BahaApplication.RESOURCES.getDisplayMetrics();
        int w_screen = dm.widthPixels;
        return w_screen;
    }

    public static int getScreenHeight() {
        DisplayMetrics dm = BahaApplication.RESOURCES.getDisplayMetrics();
        int h_screen = dm.heightPixels;
        return h_screen;
    }

    public static int getDeviceWidth() {
        return getDeviceSize()[0];
    }

    public static int getDeviceHeight() {
        return getDeviceSize()[1];
    }

}
