package com.kingja.yaluji.view.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.kingja.supershapeview.view.SuperShapeTextView;
import com.kingja.yaluji.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description:TODO
 * Create Time:2018/11/10 21:49
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ConfirmDialog extends BaseDialog {
    private String content = "";
    private String confirm = "确定";
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_confirm)
    SuperShapeTextView tvConfirm;

    @OnClick({R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm:
                if (onConfirmListener != null) {
                    onConfirmListener.onConfirm();
                }
                dismiss();
                break;
            default:
                break;
        }
    }


    public ConfirmDialog(@NonNull Context context, String content) {
        this(context, content, "确定");
    }

    public ConfirmDialog(@NonNull Context context, String content, String confirm) {
        super(context, R.style.Dialog_R8_WHITE);
        this.content = content;
        this.confirm = confirm;
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        tvContent.setText(content);
        tvConfirm.setText(confirm);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_confirm;
    }
}
