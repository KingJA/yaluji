package com.kingja.yaluji.view;


import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Description:TODO
 * Create Time:2018/1/27 20:18
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */

public class DeleteTextView extends StringTextView {

    private int mAdditionalPadding;

    public DeleteTextView(Context context) {
        super(context);
        init();
    }

    public DeleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setPaintFlags(getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }
}