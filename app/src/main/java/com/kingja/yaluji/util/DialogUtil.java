package com.kingja.yaluji.util;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.kingja.yaluji.R;
import com.kingja.yaluji.page.login.LoginActivity;

/**
 * Description:TODO
 * Create Time:2018/8/21 0021 上午 8:56
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class DialogUtil {
    private static String CONFIRM = "确定";

    public static void showConfirmDialog(Context context, String message, MaterialDialog.SingleButtonCallback
            callback) {
        new MaterialDialog.Builder(context)
                .content(message)
                .positiveText(CONFIRM)
                .onPositive(callback)
                .show();
    }

    public static void showQuitDialog(Activity context, String message) {
        new MaterialDialog.Builder(context)
                .content(message)
                .positiveText(CONFIRM)
                .cancelable(false)
                .canceledOnTouchOutside(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        context.finish();
                    }
                })
                .show();
    }

    public static void showDoubleDialog(Context context, String message, MaterialDialog.SingleButtonCallback
            positiveCallback) {
        new MaterialDialog.Builder(context)
                .content(message)
                .positiveText("确认")
                .negativeText("取消")
                .positiveColor(ContextCompat.getColor(context, R.color.orange_hi))
                .negativeColor(ContextCompat.getColor(context, R.color.gray_hi))
                .onPositive(positiveCallback)
                .show();
    }

    public static void showDoubleDialog(Context context, String message, MaterialDialog.SingleButtonCallback
            negativecallback, MaterialDialog.SingleButtonCallback positiveCallback) {
        new MaterialDialog.Builder(context)
                .content(message)
                .positiveText("确认")
                .negativeText("取消")
                .positiveColor(ContextCompat.getColor(context, R.color.orange_hi))
                .negativeColor(ContextCompat.getColor(context, R.color.gray_hi))
                .onPositive(positiveCallback)
                .onNegative(negativecallback)
                .show();
    }
    public static void showLoginActivity(Activity context) {
        DialogUtil.showConfirmDialog(context, "亲，您还未登录，是否马上登录", (dialog, which) -> {
            GoUtil.goActivity(context, LoginActivity.class);
        });
    }
}
