package com.kingja.yaluji.page.business;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kingja.yaluji.R;
import com.kingja.yaluji.base.BaseTitleActivity;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.constant.Constants;
import com.kingja.yaluji.constant.Status;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.util.CheckUtil;
import com.kingja.yaluji.util.ToastUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MultipartBody;

/**
 * Description:TODO
 * Create Time:2018/11/10 13:09
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class BusinessDetailActivity extends BaseTitleActivity implements BusinessDetailContract.View {

    @BindView(R.id.et_company)
    EditText etCompany;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_otherContact)
    EditText etOtherContact;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    private String businessType;
    @Inject
    BusinessDetailPresenter businessDetailPresenter;

    @OnClick({R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm:
                doBusiness();
                break;
            default:
                break;
        }
    }

    private void doBusiness() {
        String company = etCompany.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String otherContact = etOtherContact.getText().toString().trim();
        if (CheckUtil.checkEmpty(company, "请输入公司名称")
                && CheckUtil.checkEmpty(name, "请输入联系人")
                && CheckUtil.checkEmpty(phone, "请输入手机号码")) {
            businessDetailPresenter.doBusiness(new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("companyName", company)
                    .addFormDataPart("contactPerson", name)
                    .addFormDataPart("mobile", phone)
                    .addFormDataPart("otherContacts", otherContact)
                    .addFormDataPart("type", businessType)
                    .build());
        }
    }

    @Override
    public void initVariable() {
        businessType = getIntent().getStringExtra(Constants.Extra.BusinessType);

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_business_detail;
    }

    @Override
    protected void initComponent(AppComponent appComponent) {
        DaggerBaseCompnent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
        businessDetailPresenter.attachView(this);
    }

    @Override
    protected String getContentTitle() {
        return Status.BusinsessType.PROVIDER.equals(businessType) ? "供应商合作" : "广告合作";
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initNet() {

    }

    public static void goActivity(Activity context, String businessType) {
        Intent intent = new Intent(context, BusinessDetailActivity.class);
        intent.putExtra(Constants.Extra.BusinessType, businessType);
        context.startActivity(intent);
        context.finish();
    }

    @Override
    public void onDoBusinessSuccess() {
        ToastUtil.showText("提交成功");
        finish();
    }
}
