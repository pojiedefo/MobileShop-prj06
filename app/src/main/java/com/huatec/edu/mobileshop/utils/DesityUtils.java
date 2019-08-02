package com.huatec.edu.mobileshop.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import static com.nostra13.universalimageloader.core.ImageLoader.TAG;

/**
 * Created by Administrator on 2016/11/7.
 */

public class DesityUtils {

    public static int getWidth(Context context) {
        int width = 0;
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);

        Log.i(TAG, "屏幕宽度：" + metrics.widthPixels + "px");
        return metrics.widthPixels;
    }
}
