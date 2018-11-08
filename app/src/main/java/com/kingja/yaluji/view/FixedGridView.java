package com.kingja.yaluji.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Description:TODO
 * Create Time:2018/9/14 0014 下午 3:36
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class FixedGridView extends GridView {
    public FixedGridView(Context context) {
        super(context);
    }

    public FixedGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixedGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}