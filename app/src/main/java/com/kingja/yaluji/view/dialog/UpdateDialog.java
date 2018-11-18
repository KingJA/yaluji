package com.kingja.yaluji.view.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.kingja.supershapeview.view.SuperShapeTextView;
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
public class UpdateDialog extends BaseDialog {

    @BindView(R.id.tv_versionName)
    TextView tvVersionName;
    @BindView(R.id.tv_content)
    TextView tvContent;
    private VersionInfo versionInfo;

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

    public UpdateDialog(@NonNull Context context, VersionInfo versionInfo) {
        super(context, R.style.Dialog_R8_WHITE);
        this.versionInfo = versionInfo;
    }


    @Override
    protected void initData() {
        tvVersionName.setText(versionInfo.getLatestVersionName());
        tvContent.setText(versionInfo.getLatestContent());
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_update;
    }
}
