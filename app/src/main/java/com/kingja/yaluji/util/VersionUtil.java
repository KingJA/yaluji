package com.kingja.yaluji.util;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Description:TODO
 * Create Time:2018/8/1 0001 下午 3:49
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class VersionUtil {
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            versionCode = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }
}
