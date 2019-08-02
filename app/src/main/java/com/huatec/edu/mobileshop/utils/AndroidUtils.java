package com.huatec.edu.mobileshop.utils;

import android.content.Context;

/**
 * Created by Dawei on 4/27/15.
 */
public class AndroidUtils {
    /**
     * 将 px单位转换成dp
     *
     * @param paramContext
     * @param paramFloat
     * @return
     */
    public static int dp2px(Context paramContext, float paramFloat) {
        return (int) (0.5F + paramFloat * paramContext.getResources().getDisplayMetrics().density);
    }

    /**
     * 将dp单位转换为px
     *
     * @param paramContext
     * @param paramFloat
     * @return
     */
    public static int px2dp(Context paramContext, float paramFloat) {
        return (int) (0.5F + paramFloat / paramContext.getResources().getDisplayMetrics().density);
    }

}
