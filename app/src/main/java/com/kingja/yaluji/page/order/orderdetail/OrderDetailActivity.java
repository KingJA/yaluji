package com.kingja.yaluji.page.order.orderdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.kingja.yaluji.R;
import com.kingja.yaluji.base.BaseTitleActivity;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.constant.Constants;
import com.kingja.yaluji.imgaeloader.ImageLoader;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.model.entiy.OrderDetail;
import com.kingja.yaluji.view.StringTextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description:TODO
 * Create Time:2018/7/5 15:24
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class OrderDetailActivity extends BaseTitleActivity implements OrderDetailContract.View {

    @BindView(R.id.tv_subject)
    StringTextView tvSubject;
    @BindView(R.id.tv_tourists)
    StringTextView tvTourists;
    @BindView(R.id.tv_useDate)
    StringTextView tvUseDate;
    @BindView(R.id.tv_paidAt)
    StringTextView tvPaidAt;
    @BindView(R.id.tv_orderNo)
    StringTextView tvOrderNo;
    @BindView(R.id.tv_ticketcode)
    StringTextView tvTicketcode;
    @BindView(R.id.iv_qcodeImg)
    ImageView ivQcodeImg;
    @BindView(R.id.tv_quantity)
    StringTextView tvQuantity;
    @BindView(R.id.tv_visitMethod)
    StringTextView tvVisitMethod;
    @BindView(R.id.tv_useRemarks)
    StringTextView tvUseRemarks;
    private String orderId;
    @Inject
    OrderDetailPresenter orderDetailPresenter;

    @Override
    public void initVariable() {
        orderId = getIntent().getStringExtra(Constants.Extra.OrderId);

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void initComponent(AppComponent appComponent) {
        DaggerBaseCompnent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
        orderDetailPresenter.attachView(this);
    }

    @Override
    protected String getContentTitle() {
        return "详情";
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
        tvUseDate.setString(orderDetail.getUseDate());
        tvPaidAt.setString(orderDetail.getPaidAt());
        tvOrderNo.setString(orderDetail.getOrderNo());
        tvTicketcode.setString(orderDetail.getTicketcode());
        tvQuantity.setString(orderDetail.getQuantity());
        tvVisitMethod.setString(orderDetail.getVisitMethod());
        tvUseRemarks.setString(orderDetail.getUseRemarks());
        ImageLoader.getInstance().loadImage(this, orderDetail.getQrcodeurl(), ivQcodeImg);

    }

    public static void goActivity(Context context, String orderId) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra(Constants.Extra.OrderId, orderId);
        context.startActivity(intent);
    }
}