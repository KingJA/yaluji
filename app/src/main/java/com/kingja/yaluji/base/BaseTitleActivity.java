package com.kingja.yaluji.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.kingja.loadsir.core.Transport;
import com.kingja.yaluji.R;
import com.kingja.yaluji.callback.EmptyCallback;
import com.kingja.yaluji.callback.ErrorMessageCallback;
import com.kingja.yaluji.callback.LoadingCallback;
import com.kingja.yaluji.injector.component.AppComponent;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description：TODO
 * Create Time：2017/3/20 14:17
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public abstract class BaseTitleActivity extends BaseActivity {

    protected View rootView;
    private TextView mTvTitle;
    private Unbinder bind;
    protected LoadService mBaseLoadService;
    private LinearLayout mLlTitleRoot;
    private LinearLayout mLlBack;

    @Override
    public View getContentId() {
        rootView = View.inflate(this, R.layout.activity_title, null);
        FrameLayout flContent = rootView.findViewById(R.id.fl_content);
        mLlTitleRoot = rootView.findViewById(R.id.ll_title_root);
        mTvTitle = rootView.findViewById(R.id.tv_title_title);
        mLlBack = rootView.findViewById(R.id.ll_title_back);
        mTvTitle.setText(getContentTitle() == null ? "" : getContentTitle());
        mLlBack.setOnClickListener(v -> onBack());
        View content = View.inflate(this, getContentView(), null);
        if (content != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT);
            flContent.addView(content, params);
            bind = ButterKnife.bind(this, rootView);
            // register after ButterKnife.bind()
            if (ifRegisterLoadSir()) {
                mBaseLoadService = LoadSir.getDefault().register(content, new Callback.OnReloadListener() {
                    @Override
                    public void onReload(View v) {
                        onNetReload(v);
                    }
                });
            }

        }
        if (ifHideTitle()) {
            mLlTitleRoot.setVisibility(View.GONE);
        }
        if (ifHideBack()) {
            mLlBack.setVisibility(View.GONE);
        }
        return rootView;
    }


    @Override
    public void showLoadingCallback() {
        mBaseLoadService.showCallback(LoadingCallback.class);
    }

    @Override
    public void showEmptyCallback() {
        mBaseLoadService.showCallback(EmptyCallback.class);
    }

    @Override
    public void showErrorCallback() {
        mBaseLoadService.showCallback(ErrorMessageCallback.class);
    }
    @Override
    public void showErrorMessage(int code, String message) {
        if (ifRegisterLoadSir()) {
            mBaseLoadService.setCallBack(ErrorMessageCallback.class, new Transport() {
                @Override
                public void order(Context context, View view) {
                    TextView tvErrorMsg = view.findViewById(R.id.tv_layout_errorMsg);
                    tvErrorMsg.setText(message);
                }
            });
            mBaseLoadService.showCallback(ErrorMessageCallback.class);
        } else {
            super.showErrorMessage(code, message);
        }
    }
    @Override
    public void showSuccessCallback() {
        mBaseLoadService.showSuccess();
    }


    private void onNetReload(View v) {
        initNet();
    }


    protected void onBack() {
        finish();
    }

    protected boolean ifHideBack() {
        return false;
    }

    protected boolean ifHideTitle() {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();
        }
    }

    public void setContentTitle(String title) {
        mTvTitle.setText(title);
    }

    public void setMenuRes(int resId, View.OnClickListener onClickListener) {
        LinearLayout llTitleMenu = rootView.findViewById(R.id.ll_title_menu);
        llTitleMenu.setVisibility(View.VISIBLE);
        llTitleMenu.setOnClickListener(onClickListener);
        ImageView ivMenu = rootView.findViewById(R.id.iv_menu);
        ivMenu.setBackgroundResource(resId);
    }

    public void setRightClick(String rightText, View.OnClickListener onClickListener) {
        TextView tv_right_text = rootView.findViewById(R.id.tv_right_text);
        tv_right_text.setText(rightText);
        tv_right_text.setVisibility(View.VISIBLE);
        tv_right_text.setOnClickListener(onClickListener);
    }

    @Override
    public abstract void initVariable();

    protected abstract int getContentView();

    @Override
    protected abstract void initComponent(AppComponent appComponent);

    protected abstract String getContentTitle();

    @Override
    protected abstract void initView();

    @Override
    protected abstract void initData();

    @Override
    protected abstract void initNet();


}
