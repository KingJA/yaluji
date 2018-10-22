package com.kingja.yaluji.util;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Description:TODO
 * Create Time:2018/4/17 14:35
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class CountTimer extends CountDownTimer {
    private final TextView textView;

    public CountTimer(int count, TextView textView) {
        super(count*1000+50, 1000);
        this.textView = textView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        textView.setText(Math.round((double) millisUntilFinished / 1000) + " 秒后重发");
    }

    @Override
    public void onFinish() {
        textView.setText("获取验证码");
        textView.setClickable(true);
    }
}
