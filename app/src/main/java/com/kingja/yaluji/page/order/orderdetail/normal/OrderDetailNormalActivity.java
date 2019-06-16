package com.kingja.yaluji.page.order.orderdetail.normal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kingja.supershapeview.view.SuperShapeRelativeLayout;
import com.kingja.yaluji.R;
import com.kingja.yaluji.adapter.QcodePagerAdapter;
import com.kingja.yaluji.base.BaseTitleActivity;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.constant.Constants;
import com.kingja.yaluji.imgaeloader.ImageLoader;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.model.entiy.OrderDetail;
import com.kingja.yaluji.page.order.orderdetail.OrderDetailContract;
import com.kingja.yaluji.page.order.orderdetail.OrderDetailPresenter;
import com.kingja.yaluji.util.AppUtil;
import com.kingja.yaluji.view.DeleteTextView;
import com.kingja.yaluji.view.StringTextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description:TODO
 * Create Time:2018/7/5 15:24
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class OrderDetailNormalActivity extends BaseTitleActivity implements OrderDetailContract.View {

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
    @BindView(R.id.tv_quantity)
    StringTextView tvQuantity;
    @BindView(R.id.tv_visitMethod)
    StringTextView tvVisitMethod;
    @BindView(R.id.tv_useRemarks)
    StringTextView tvUseRemarks;
    @BindView(R.id.tv_marketPrice)
    DeleteTextView tvMarketPrice;
    @BindView(R.id.tv_payamount)
    StringTextView tvPayamount;
    @BindView(R.id.tv_idcode)
    StringTextView tvIdcode;
    @BindView(R.id.vp_order)
    ViewPager vpOrder;
    @BindView(R.id.ll_pointContainer)
    LinearLayout llPointContainer;
    @BindView(R.id.iv_qcodeImg)
    ImageView ivQcodeImg;
    @BindView(R.id.ssrl_qcode_img)
    SuperShapeRelativeLayout ssrlQcodeImg;
    @BindView(R.id.ssrl_qcode)
    SuperShapeRelativeLayout ssrlQcode;
    private List<View> points = new ArrayList<>();
    private String orderId;
    @Inject
    OrderDetailPresenter orderDetailPresenter;

    @Override
    public void initVariable() {
        orderId = getIntent().getStringExtra(Constants.Extra.OrderId);

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_order_detail_normal;
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
        tvUseDate.setString(orderDetail.getUseDate());
        tvIdcode.setString(orderDetail.getIdcode());
        tvPaidAt.setString(orderDetail.getPaidAt());
        tvOrderNo.setString(orderDetail.getOrderNo());
        tvTicketcode.setString(orderDetail.getTicketcode());
        tvQuantity.setString(orderDetail.getQuantity());
        tvVisitMethod.setString(orderDetail.getVisitMethod());
        tvUseRemarks.setString(orderDetail.getUseRemarks());
        tvMarketPrice.setString(String.format("¥%d元", orderDetail.getMarketPrice()));
        tvPayamount.setString(String.format("抵用%d元/张", orderDetail.getPayamount()));
        String qrcodeurl = orderDetail.getQrcodeurl();
        if (!TextUtils.isEmpty(qrcodeurl)) {
            if (qrcodeurl.endsWith("png")) {
                //鸭鹿鸡平台链接
                ssrlQcodeImg.setVisibility(View.VISIBLE);
                ImageLoader.getInstance().loadImage(this, orderDetail.getQrcodeurl(), ivQcodeImg);
            } else {
                //自我游链接
                ssrlQcode.setVisibility(View.VISIBLE);
                String[] qcodes = qrcodeurl.split(",");
                initDot(qcodes);
                vpOrder.setAdapter(new QcodePagerAdapter(this, qcodes));
                vpOrder.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        if (qcodes.length < 2) {
                            return;
                        }
                        for (int i = 0; i < points.size(); i++) {
                            if (i == position) {
                                points.get(i).setBackgroundResource(R.mipmap.ic_dot_sel);
                            } else {
                                points.get(i).setBackgroundResource(R.mipmap.ic_dot_nor);
                            }
                        }
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
            }
        }

    }

    private void initDot(String[] qcodes) {
        if (qcodes.length < 2) {
            return;
        }
        for (int i = 0; i < qcodes.length; i++) {
            View view = new View(this);
            if (i == 0) {
                view.setBackgroundResource(R.mipmap.ic_dot_sel);
            } else {
                view.setBackgroundResource(R.mipmap.ic_dot_nor);
            }
            points.add(view);
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(AppUtil.dp2px(10), AppUtil.dp2px(10));
        layoutParams.setMargins(0, 0, AppUtil.dp2px(10), 0);
        for (int i = 0; i < qcodes.length; i++) {
            llPointContainer.addView(points.get(i), layoutParams);
        }

    }

    public static void goActivity(Context context, String orderId) {
        Intent intent = new Intent(context, OrderDetailNormalActivity.class);
        intent.putExtra(Constants.Extra.OrderId, orderId);
        context.startActivity(intent);
    }

    @Override
    public boolean ifRegisterLoadSir() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}