package com.kingja.yaluji.view.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.kingja.yaluji.R;
import com.kingja.yaluji.model.entiy.VersionInfo;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description:TODO
 * Create Time:2018/11/8 19:52
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class LocationDialog extends BaseDialog {


    @OnClick({R.id.tv_confirm, R.id.tv_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm:
                if (onConfirmListener != null) {
                    onConfirmListener.onConfirm();
                }
                dismiss();
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
            default:
                break;
        }
    }

    public LocationDialog(@NonNull Context context) {
        super(context, R.style.Dialog_BASE);
    }


    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_location;
    }
}
