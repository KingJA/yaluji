package com.kingja.yaluji.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Description:TODO
 * Create Time:2018/7/7 16:27
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class PullToMoreListView extends ListView {
    private OnScrollToBottom onScrollToBottom;
    private View topView;
    private int pageIndex = 1;

    public PullToMoreListView(Context context) {
        super(context);
    }

    public PullToMoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
                        if (onScrollToBottom != null) {
                            onScrollToBottom.onScrollToBottom(++pageIndex);
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (topView != null) {
                    topView.setVisibility(firstVisibleItem == 0 ? View.GONE : View.VISIBLE);
                }

            }
        });
    }

    public void reset() {
        this.pageIndex = 1;
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    public void setGoTop(View topView) {
        this.topView = topView;
        topView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelection(0);
                topView.setVisibility(View.GONE);
            }
        });
    }

    public interface OnScrollToBottom {
        void onScrollToBottom(int pageIndex);
    }

    public void setOnScrollToBottom(OnScrollToBottom onScrollToBottom) {
        this.onScrollToBottom = onScrollToBottom;
    }

}
