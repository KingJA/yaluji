package com.kingja.yaluji.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.kingja.yaluji.page.login.LoginActivity;


/**
 * Description:TODO
 * Create Time:2018/7/7 10:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class LoginChecker {

    public static void goActivity(Activity context, Class targetActivity) {
        if (TextUtils.isEmpty(SpSir.getInstance().getToken())) {
            DialogUtil.showConfirmDialog(context, "亲，您还未登录，是否马上登录", (dialog, which) -> {
                GoUtil.goActivity(context, LoginActivity.class);
            });
        }else{
            GoUtil.goActivity(context,targetActivity);
        }
    }

    public static void goActivity(Activity context, Intent intent) {
        if (TextUtils.isEmpty(SpSir.getInstance().getToken())) {
            DialogUtil.showConfirmDialog(context, "亲，您还未登录，是否马上登录", (dialog, which) -> {
                GoUtil.goActivity(context, LoginActivity.class);
            });
        }else{
            context.startActivity(intent);
        }
    }
    public static void goActivity(Context context, Intent intent) {
        if (TextUtils.isEmpty(SpSir.getInstance().getToken())) {
            DialogUtil.showConfirmDialog(context, "亲，您还未登录，是否马上登录", (dialog, which) -> {
                GoUtil.goActivity(context, LoginActivity.class);
            });
        }else{
            context.startActivity(intent);
        }
    }

    public static boolean isLogin() {
        return !TextUtils.isEmpty(SpSir.getInstance().getToken());
    }
}
