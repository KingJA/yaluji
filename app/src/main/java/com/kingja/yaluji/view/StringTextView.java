package com.kingja.yaluji.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Description:TODO
 * Create Time:2018/9/29 0029 上午 10:57
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class StringTextView extends TextView {
    public StringTextView(Context context) {
        super(context);
    }

    public StringTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StringTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setString(double value) {
        setText(String.valueOf(value));
    }
    public void setString(long value) {
        setText(String.valueOf(value));
    }

    public void setString(String value) {
        setText(TextUtils.isEmpty(value)?"":value);
    }
}
