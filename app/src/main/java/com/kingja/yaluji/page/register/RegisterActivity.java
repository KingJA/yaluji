package com.kingja.yaluji.page.register;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
 * Description:注册
 * Create Time:2018/3/8 13:43
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class RegisterActivity extends BaseTitleActivity implements RegisterContract.View {
    @BindView(R.id.et_mobile)
    EditText etRegisterMobile;
    @BindView(R.id.et_code)
    EditText etRegisterCode;
    @BindView(R.id.stv_coutDown)
    SuperShapeTextView stvRegisterGetCode;
    @BindView(R.id.et_password)
    EditText etRegisterPassword;
    @BindView(R.id.tv_confirm)
    TextView tvRegisterConfirm;
    private CountTimer countTimer;
    private boolean isShow;
    @Inject
    RegisterPresenter registerPresenter;

    @OnClick({R.id.stv_coutDown,R.id.tv_confirm})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.stv_coutDown:
                String mobile = etRegisterMobile.getText().toString().trim();
                if (CheckUtil.checkPhoneFormat(mobile)) {
                    getCode(mobile);
                }
                break;
            case R.id.tv_confirm:
                register();
                break;
            default:
                break;

        }

    }


    @Override
    public void initVariable() {

    }

    private void register() {
        String mobile = etRegisterMobile.getText().toString().trim();
        String code = etRegisterCode.getText().toString().trim();
        String password = etRegisterPassword.getText().toString().trim();
        if (CheckUtil.checkPhoneFormat(mobile) && CheckUtil.checkEmpty(code, "请输入验证码") && CheckUtil.checkEmpty
                (password, "请输入密码")) {
            registerPresenter.register(mobile, EncryptUtil.getMd5(password), code);
        }
    }


    private void getCode(String mobile) {
        countTimer = new CountTimer(10, stvRegisterGetCode);
        stvRegisterGetCode.setClickable(false);
        countTimer.start();
        registerPresenter.getCode(mobile, 1);

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
        return "注册";
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        registerPresenter.attachView(this);
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
    public void onRegisterSuccess() {
        ToastUtil.showText("恭喜你，注册成功!");
        finish();
    }

    @Override
    public void onGetCodeSuccess(String code) {
        ToastUtil.showText("已发送验证码至该手机");
    }
}
