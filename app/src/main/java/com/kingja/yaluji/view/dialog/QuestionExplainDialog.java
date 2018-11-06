package com.kingja.yaluji.view.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.kingja.supershapeview.view.SuperShapeTextView;
import com.kingja.yaluji.R;
import com.kingja.yaluji.activity.SearchDetailActivity;
import com.kingja.yaluji.util.GoUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description:TODO
 * Create Time:2018/11/6 23:42
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class QuestionExplainDialog extends BaseDialog {
    public QuestionExplainDialog(@NonNull Context context) {
        super(context, R.style.Dialog_R16_ORANGE);
    }

    @OnClick({R.id.stv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.stv_confirm:
                dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_question_explain;
    }
}
