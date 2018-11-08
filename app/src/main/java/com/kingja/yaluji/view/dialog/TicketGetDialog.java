package com.kingja.yaluji.view.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.kingja.supershapeview.view.SuperShapeTextView;
import com.kingja.yaluji.R;
import com.kingja.yaluji.activity.SearchDetailActivity;
import com.kingja.yaluji.util.GoUtil;
import com.kingja.yaluji.util.SpSir;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Description:TODO
 * Create Time:2018/11/8 19:52
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class TicketGetDialog extends BaseDialog {
    @BindView(R.id.cb_noShow)
    CheckBox cbNoShow;
    @BindView(R.id.tv_confirm)
    SuperShapeTextView tvConfirm;
    @BindView(R.id.tv_cancel)
    SuperShapeTextView tvCancel;

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

    @OnCheckedChanged({R.id.cb_noShow})
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        SpSir.getInstance().putNoShow(checked);
    }

    public TicketGetDialog(@NonNull Context context) {
        super(context, R.style.Dialog_R8_WHITE);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_ticket_get;
    }
}
