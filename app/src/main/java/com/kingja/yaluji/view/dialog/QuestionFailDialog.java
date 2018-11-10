package com.kingja.yaluji.view.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.kingja.supershapeview.view.SuperShapeLinearLayout;
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
public class QuestionFailDialog extends BaseDialog {
    @BindView(R.id.sll_shape)
    SuperShapeLinearLayout sllShape;
    @BindView(R.id.tv_rebornTimes)
    TextView tvRebornTimes;
    private int rebornTimes;


    public QuestionFailDialog(@NonNull Context context,int rebornTimes) {
        super(context);
        this.rebornTimes = rebornTimes;
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    @OnClick({R.id.sll_shape})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sll_shape:
                ToastUtil.showText("分享");
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
        tvRebornTimes.setText(String.valueOf(rebornTimes));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_question_fail;
    }
}
