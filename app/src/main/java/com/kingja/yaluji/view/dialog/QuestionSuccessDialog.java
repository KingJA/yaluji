package com.kingja.yaluji.view.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.kingja.supershapeview.view.SuperShapeTextView;
import com.kingja.yaluji.R;
import com.kingja.yaluji.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description:TODO
 * Create Time:2018/11/7 0:27
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class QuestionSuccessDialog extends BaseDialog {
    @BindView(R.id.tv_ticketMoney)
    TextView tvTicketMoney;
    @BindView(R.id.stv_get_ticket)
    SuperShapeTextView stvGetTicket;

    @OnClick({R.id.stv_get_ticket})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.stv_get_ticket:
                ToastUtil.showText("立即领取");
                break;
            default:
                break;
        }
    }

    public QuestionSuccessDialog(@NonNull Context context) {
        super(context);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_question_success;
    }
}
