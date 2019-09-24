package com.kingja.yaluji.page.visitor.edit;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kingja.yaluji.R;
import com.kingja.yaluji.base.BaseTitleActivity;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.event.PrfectVisitorEvent;
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
 * Description:游客信息修改
 * Create Time:2018/7/4 14:54
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class VisitorEditActivity extends BaseTitleActivity implements VisitorEditContract.View {
    @BindView(R.id.et_visitor_name)
    EditText etVisitorName;
    @BindView(R.id.et_visitor_phone)
    EditText etVisitorPhone;
    @BindView(R.id.et_visitor_idcode)
    EditText etVisitorIdcode;
    @BindView(R.id.tv_visitor_confirm)
    TextView tvVisitorConfirm;
    @Inject
    VisitorEditPresenter visitorEditPresenter;
    private Visitor visitor;

    @OnClick({R.id.tv_visitor_confirm})
    public void click(View view) {
        String name = etVisitorName.getText().toString().trim();
        String phone = etVisitorPhone.getText().toString().trim();
        String idcode = etVisitorIdcode.getText().toString().trim();
        if (CheckUtil.checkEmpty(name, "请输入姓名")
                && CheckUtil.checkPhoneFormat(phone)
                && CheckUtil.checkIdCard(idcode,"身份证号错误")) {
            editVisitor(name,phone,idcode);
        }

    }

    private void editVisitor(String name, String phone, String idcode) {
        visitorEditPresenter.editVisitor(visitor.getId(),name,phone,idcode);
    }

    @Override
    public void initVariable() {
        visitor = (Visitor) getIntent().getSerializableExtra("visitor");
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
        return "修改游客信息";
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_visitor_add;
    }

    @Override
    protected void initView() {
        visitorEditPresenter.attachView(this);
    }

    @Override
    protected void initData() {
        if (visitor != null) {
            etVisitorName.setText(visitor.getName());
            etVisitorPhone.setText(visitor.getMobile());
            etVisitorIdcode.setText(visitor.getIdcode());
        }
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
    public void onEditVisitorSuccess(Visitor visitor) {
        ToastUtil.showText("修改游客信息成功");
        EventBus.getDefault().post(new RefreshVisitorsEvent());
        EventBus.getDefault().post(new PrfectVisitorEvent(visitor));
        finish();
    }

    public static void goActivity(Context context, Visitor visitor) {
        Intent intent = new Intent(context, VisitorEditActivity.class);
        intent.putExtra("visitor",visitor);
        context.startActivity(intent);
    }
}
