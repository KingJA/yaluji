package com.kingja.yaluji.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.kingja.yaluji.R;


/**
 * Description:TODO
 * Create Time:2018/8/4 12:19
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class QcodePagerAdapter extends PagerAdapter {
    private Context context;
    private String[] qcodes;

    public QcodePagerAdapter(Context context, String[] qcodes) {
        this.context = context;
        this.qcodes = qcodes;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getCount() {
        return qcodes.length;
    }

    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View rootView =  View.inflate(context, R.layout.layout_webview, null);
        WebView wb = rootView.findViewById(R.id.wb);
        ProgressBar pb = rootView.findViewById(R.id.pb);
        wb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        wb.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    pb.setVisibility(View.GONE);
                }

            }
        });
        WebSettings webSettings = wb.getSettings();
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        wb.loadUrl(qcodes[position]);
        container.addView(rootView);
        return rootView;
    }
}
