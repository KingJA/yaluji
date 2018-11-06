package com.kingja.yaluji.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.WindowManager;

import com.kingja.yaluji.R;
import com.kingja.yaluji.base.App;
import com.kingja.yaluji.util.AppUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description:TODO
 * Create Time:2018/11/6 23:30
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public abstract class BaseDialog extends AlertDialog {
    private Unbinder bind;


    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        setCanceledOnTouchOutside(true);
    }

    public BaseDialog(@NonNull Context context) {
        super(context, R.style.Dialog_R8_WHITE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initView();
        initData();
    }

    protected abstract void initData();

    protected abstract void initView();

    protected abstract int getLayoutId();

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity= Gravity.CENTER;
        layoutParams.width= (int) (AppUtil.getScreenWidth()*0.8f);
        layoutParams.height= WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);

    }
}
