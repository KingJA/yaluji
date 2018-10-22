package com.kingja.yaluji.page.modifypassword;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kingja.yaluji.R;
import com.kingja.yaluji.base.BaseTitleActivity;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.util.CheckUtil;
import com.kingja.yaluji.util.EncryptUtil;
import com.kingja.yaluji.util.ToastUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description:修改密码
 * Create Time:2018/3/8 14:23
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ModifyPasswordActivity extends BaseTitleActivity implements ModifyPasswordContract.View {
    @Inject
    ModifyPasswordPresenter modifyPasswordPresenter;
    @BindView(R.id.et_oldPassword)
    EditText etOldPassword;
    @BindView(R.id.et_newPassword)
    EditText etNewPassword;
    @BindView(R.id.et_repeatPassword)
    EditText etRepeatPassword;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;


    @OnClick({R.id.tv_confirm})
    public void click(View view) {
        String oldPassword = etOldPassword.getText().toString().trim();
        String newPassword = etNewPassword.getText().toString().trim();
        String repeatPassword = etRepeatPassword.getText().toString().trim();
        if (CheckUtil.checkEmpty(oldPassword, "请输入旧密码") && CheckUtil.checkEmpty(newPassword, "请输入新密码") && CheckUtil
                .checkEmpty(repeatPassword, "请输入重复密码") &&
                CheckUtil.checkSame(newPassword, repeatPassword, "两次输入密码不一致")) {
            modifyPasswordPresenter.modifyPassword(EncryptUtil.getMd5(oldPassword), EncryptUtil.getMd5(newPassword));
        }
    }

    @Override
    public void initVariable() {

    }

    @Override
    protected void initComponent(AppComponent appComponent) {
        DaggerBaseCompnent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected String getContentTitle() {
        return "修改密码";
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_modify_password;
    }

    @Override
    protected void initView() {
        modifyPasswordPresenter.attachView(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initNet() {

    }

    @Override
    public void showLoading() {
        setProgressShow(true);
    }

    @Override
    public void hideLoading() {
        setProgressShow(false);
    }

    @Override
    public void onModifyPasswordSuccess() {
        ToastUtil.showText("密码修改成功");
        finish();
    }
}
