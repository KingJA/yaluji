package com.kingja.yaluji.page.order.orderdetail.phone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.kingja.supershapeview.view.SuperShapeEditText;
import com.kingja.yaluji.R;
import com.kingja.yaluji.base.BaseTitleActivity;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.constant.Constants;
import com.kingja.yaluji.event.RefreshOrderListEvent;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.model.entiy.OrderDetail;
import com.kingja.yaluji.page.order.orderdetail.OrderDetailContract;
import com.kingja.yaluji.page.order.orderdetail.OrderDetailPresenter;
import com.kingja.yaluji.page.order.orderdetail.normal.OrderDetailNormalActivity;
import com.kingja.yaluji.util.CheckUtil;
import com.kingja.yaluji.util.DialogUtil;
import com.kingja.yaluji.view.DeleteTextView;
import com.kingja.yaluji.view.StringTextView;
import com.kingja.yaluji.view.dialog.ConfirmDialog;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:TODO
 * Create Time:2019/6/16 11:30
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class OrderDetailPhoneActivity extends BaseTitleActivity implements OrderDetailContract.View,
        OrderDetailPhoneContract.View {
    @Inject
    OrderDetailPresenter orderDetailPresenter;
    @Inject
    OrderDetailPhonePresenter orderDetailPhonePresenter;
    @BindView(R.id.tv_subject)
    StringTextView tvSubject;
    @BindView(R.id.tv_tourists)
    StringTextView tvTourists;
    @BindView(R.id.tv_quantity)
    StringTextView tvQuantity;
    @BindView(R.id.tv_payamount)
    StringTextView tvPayamount;
    @BindView(R.id.tv_useDate)
    StringTextView tvUseDate;
    @BindView(R.id.tv_paidAt)
    StringTextView tvPaidAt;
    @BindView(R.id.tv_useRemarks)
    StringTextView tvUseRemarks;
    @BindView(R.id.set_phone)
    SuperShapeEditText setPhone;
    private String orderId;

    @OnClick({R.id.stv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.stv_confirm:
                String phone = setPhone.getText().toString().trim();
                if (CheckUtil.checkPhoneFormat(phone)) {
                    orderDetailPhonePresenter.rechargeMobile(orderId, phone);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void initVariable() {
        orderId = getIntent().getStringExtra(Constants.Extra.OrderId);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_order_detail_phone;
    }

    @Override
    protected void initComponent(AppComponent appComponent) {
        DaggerBaseCompnent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
        orderDetailPresenter.attachView(this);
        orderDetailPhonePresenter.attachView(this);
    }

    @Override
    protected String getContentTitle() {
        return "订单详情";
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initNet() {
        orderDetailPresenter.getOrderDetail(orderId);
    }

    @Override
    public void onGetOrderDetailSuccess(OrderDetail orderDetail) {
        tvSubject.setString(orderDetail.getSubject());
        tvTourists.setString(orderDetail.getTourists());
        tvQuantity.setString(String.format("%d张", orderDetail.getQuantity()));
        tvPayamount.setString(String.format("%d元", orderDetail.getPayamount()));
        tvUseDate.setString(orderDetail.getUseDate());
        tvPaidAt.setString(orderDetail.getPaidAt());
        tvUseRemarks.setString(orderDetail.getUseRemarks());
    }

    @Override
    public void onRechargeMobileSuccess() {
        EventBus.getDefault().post(new RefreshOrderListEvent());
        ConfirmDialog successDialog = new ConfirmDialog(this, "充值成功");
        successDialog.setOnConfirmListener(() -> {
            finish();
        });
        successDialog.show();
    }

    public static void goActivity(Context context, String orderId) {
        Intent intent = new Intent(context, OrderDetailPhoneActivity.class);
        intent.putExtra(Constants.Extra.OrderId, orderId);
        context.startActivity(intent);
    }

    @Override
    public boolean ifRegisterLoadSir() {
        return true;
    }
    @Override
    public void showErrorMessage(int code, String message) {
        message = message.replace("#", "\n");
        ConfirmDialog errorDialog = new ConfirmDialog(this, message);
        errorDialog.setOnConfirmListener(() -> {
            finish();
        });
        errorDialog.show();
    }
}
