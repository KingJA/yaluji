package com.kingja.yaluji.page.visitor.add;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kingja.yaluji.R;
import com.kingja.yaluji.base.BaseTitleActivity;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.event.RefreshVisitorsEvent;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.model.entiy.Visitor;
import com.kingja.yaluji.util.CheckUtil;
import com.kingja.yaluji.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description:TODO
 * Create Time:2018/7/4 14:54
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class VisitorAddActivity extends BaseTitleActivity implements VisitorAddContract.View {
    @BindView(R.id.et_visitor_name)
    EditText etVisitorName;
    @BindView(R.id.et_visitor_phone)
    EditText etVisitorPhone;
    @BindView(R.id.et_visitor_idcode)
    EditText etVisitorIdcode;
    @BindView(R.id.tv_visitor_confirm)
    TextView tvVisitorConfirm;
    @Inject
    VisitorAddPresenter visitorAddPresenter;

    @OnClick({R.id.tv_visitor_confirm})
    public void click(View view) {
        String name = etVisitorName.getText().toString().trim();
        String phone = etVisitorPhone.getText().toString().trim();
        String idcode = etVisitorIdcode.getText().toString().trim();
        if (CheckUtil.checkEmpty(name, "请输入姓名")
                && CheckUtil.checkPhoneFormat(phone)) {
            addVisitor(name,phone,idcode);
        }

    }

    private void addVisitor(String name, String phone, String idcode) {
        visitorAddPresenter.addVisitor(name,phone,idcode);
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
        return "新增游客";
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_visitor_add;
    }

    @Override
    protected void initView() {
        visitorAddPresenter.attachView(this);
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
    public void onAddVisitorSuccess(Visitor visitor) {
        ToastUtil.showText("添加游客信息成功");
        EventBus.getDefault().post(new RefreshVisitorsEvent());
        finish();
    }
}
