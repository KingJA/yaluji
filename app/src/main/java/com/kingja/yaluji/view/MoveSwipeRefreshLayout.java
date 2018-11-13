package com.kingja.yaluji.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Description:TODO
 * Create Time:2018/11/12 22:47
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class MoveSwipeRefreshLayout extends SwipeRefreshLayout {
    private GestureDetector mGestureDetector;
    public MoveSwipeRefreshLayout(@NonNull Context context) {
        this(context,null);
    }

    public MoveSwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initMoveSwipeRefreshLayout();
    }

    private void initMoveSwipeRefreshLayout() {
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
