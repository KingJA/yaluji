package com.kingja.yaluji.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Description:TODO
 * Create Time:2018/11/12 22:41
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class MoveScrollView extends ScrollView {
    private GestureDetector mGestureDetector;

    public MoveScrollView(Context context) {
        this(context, null);
    }

    public MoveScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoveScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initMoveScrollView();
    }

    private void initMoveScrollView() {
        mGestureDetector = new GestureDetector(getContext(), new YScrollDetector());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev) && mGestureDetector.onTouchEvent(ev);
    }

    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return Math.abs(distanceY) > Math.abs(distanceX);
        }
    }
}


