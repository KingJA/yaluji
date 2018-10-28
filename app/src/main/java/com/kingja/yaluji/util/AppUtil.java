package com.kingja.yaluji.util;

import android.content.Context;
import android.util.DisplayMetrics;

import com.kingja.yaluji.base.App;


/**
 * Description:TODO
 * Create Time:2018/7/21 9:48
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AppUtil {
    public static Context getContext() {
        return App.getContext();
    }

    /**
     * dp转换为px
     *
     * @param dp
     * @return
     */
    public static int dp2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }

    /**
     * px转换为dp
     *
     * @param px
     * @return
     */
    public static int px2dp(int px) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5);
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public static int getScreenWidth() {
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    public static int getScreenHeight() {
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        return dm.heightPixels;
    }
}
