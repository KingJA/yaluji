package com.kingja.yaluji.page.forgetpassword;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kingja.supershapeview.view.SuperShapeTextView;
import com.kingja.yaluji.R;
import com.kingja.yaluji.base.BaseTitleActivity;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.util.CheckUtil;
import com.kingja.yaluji.util.CountTimer;
import com.kingja.yaluji.util.EncryptUtil;
import com.kingja.yaluji.util.ToastUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Description:忘记密码
 * Create Time:2018/3/8 13:43
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ForgetPasswordActivity extends BaseTitleActivity implements ForgetPasswordContract.View {
    @BindView(R.id.et_mobile)
    EditText etForgetpwdMobile;
    @BindView(R.id.et_code)
    EditText etForgetpwdCode;
    @BindView(R.id.stv_coutDown)
    SuperShapeTextView stvForgetpwdGetCode;
    @BindView(R.id.et_password)
    EditText etForgetpwdPassword;
    @BindView(R.id.tv_confirm)
    TextView tvForgetpwdConfirm;
    private CountTimer countTimer;
    private boolean isShow;
    @Inject
    ForgetPasswordPresenter forgetPasswordPresenter;

    @OnClick({R.id.stv_coutDown,R.id.tv_confirm})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.stv_coutDown:
                String mobile = etForgetpwdMobile.getText().toString().trim();
                if (CheckUtil.checkPhoneFormat(mobile)) {
                    getCode(mobile);
                }
                break;
            case R.id.tv_confirm:
                Forgetpwd();
                break;
            default:
                break;

        }

    }


    @Override
    public void initVariable() {

    }

    private void Forgetpwd() {
        String mobile = etForgetpwdMobile.getText().toString().trim();
        String code = etForgetpwdCode.getText().toString().trim();
        String password = etForgetpwdPassword.getText().toString().trim();
        if (CheckUtil.checkPhoneFormat(mobile) && CheckUtil.checkEmpty(code, "请输入验证码") && CheckUtil.checkEmpty
                (password, "请输入密码")) {
            forgetPasswordPresenter.modifyPassword(mobile, EncryptUtil.getMd5(password) , code);
        }
    }


    private void getCode(String mobile) {
        countTimer = new CountTimer(60, stvForgetpwdGetCode);
        stvForgetpwdGetCode.setClickable(false);
        countTimer.start();
        forgetPasswordPresenter.getCode(mobile, 2);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countTimer != null) {
            countTimer.cancel();
        }
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
        return "忘记密码";
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void initView() {
        forgetPasswordPresenter.attachView(this);
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
        ToastUtil.showText("密码设置成功");
        finish();
    }

    @Override
    public void onGetCodeSuccess(String code) {
        ToastUtil.showText("已发送验证码至该手机");
    }
}
