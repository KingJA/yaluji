package com.kingja.yaluji.page.ticket.confirm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.kingja.supershapeview.view.SuperShapeTextView;
import com.kingja.yaluji.R;
import com.kingja.yaluji.base.BaseTitleActivity;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.constant.Constants;
import com.kingja.yaluji.constant.Status;
import com.kingja.yaluji.event.AddOrderEvent;
import com.kingja.yaluji.event.RefreshOrderEvent;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.model.entiy.Order;
import com.kingja.yaluji.model.entiy.OrderResult;
import com.kingja.yaluji.model.entiy.TicketDetail;
import com.kingja.yaluji.page.ticket.success.TicketSuccessActivity;
import com.kingja.yaluji.view.DeleteTextView;
import com.kingja.yaluji.view.StringTextView;
import com.kingja.yaluji.view.dialog.BaseDialog;
import com.kingja.yaluji.view.dialog.ConfirmDialog;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:TODO
 * Create Time:2018/11/14 22:43
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class TicketConfirmActivity extends BaseTitleActivity implements TicketConfirmContract.View {
    @BindView(R.id.tv_subject)
    StringTextView tvSubject;
    @BindView(R.id.tv_tourists)
    StringTextView tvTourists;
    @BindView(R.id.tv_quantity)
    StringTextView tvQuantity;
    @BindView(R.id.tv_marketPrice)
    DeleteTextView tvMarketPrice;
    @BindView(R.id.tv_payamount)
    StringTextView tvPayamount;
    @BindView(R.id.tv_useDate)
    StringTextView tvUseDate;
    @BindView(R.id.tv_visitMethod)
    StringTextView tvVisitMethod;
    @BindView(R.id.tv_useRemarks)
    StringTextView tvUseRemarks;

    @Inject
    TicketConfirmPresenter ticketConfirmPresenter;
    private String productId;
    private String touristId;
    private String visitorName;
    private String visitorPhone;
    private String quantity;
    private TicketDetail ticketDetail;

    @OnClick({R.id.stv_confirm, R.id.stv_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.stv_confirm:
                ticketConfirmPresenter.sumbitOrder(productId, touristId, quantity, Constants
                        .PLATFORM_ANDROID);
                break;
            case R.id.stv_cancel:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void initVariable() {
        productId = getIntent().getStringExtra(Constants.Extra.ProductId);
        touristId = getIntent().getStringExtra(Constants.Extra.TouristId);
        visitorName = getIntent().getStringExtra(Constants.Extra.VisitorName);
        visitorPhone = getIntent().getStringExtra(Constants.Extra.VisitorPhone);
        quantity = getIntent().getStringExtra(Constants.Extra.Quantity);
        ticketDetail = (TicketDetail) getIntent().getSerializableExtra(Constants.Extra.TicketDetail);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_ticket_confirm;
    }

    @Override
    protected void initComponent(AppComponent appComponent) {
        DaggerBaseCompnent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
        ticketConfirmPresenter.attachView(this);
    }

    @Override
    protected String getContentTitle() {
        return "";
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        tvSubject.setString(ticketDetail.getTicketName());
        tvTourists.setString(visitorName + " " + visitorPhone);
        tvQuantity.setString(String.format("%s张", quantity));
        tvMarketPrice.setString(String.format("¥%d元", ticketDetail.getMarketPrice()));
        tvPayamount.setString(String.format("抵用%d元/张", ticketDetail.getBuyPrice()));
        tvUseDate.setString(ticketDetail.getVisitDate());
        tvVisitMethod.setString(ticketDetail.getVisitMethod());
        tvUseRemarks.setString(ticketDetail.getUseRemarks());

    }

    @Override
    protected void initNet() {

    }

    public static void goActivity(Activity context, String productId, String touristId, String visitorName, String
            visitorPhone, String quantity, TicketDetail ticketDetail) {
        Intent intent = new Intent(context, TicketConfirmActivity.class);
        intent.putExtra(Constants.Extra.ProductId, productId);
        intent.putExtra(Constants.Extra.TouristId, touristId);
        intent.putExtra(Constants.Extra.VisitorName, visitorName);
        intent.putExtra(Constants.Extra.VisitorPhone, visitorPhone);
        intent.putExtra(Constants.Extra.Quantity, quantity);
        intent.putExtra(Constants.Extra.TicketDetail, ticketDetail);
        context.startActivity(intent);
    }

    @Override
    public void onSumbitOrderSuccess(OrderResult orderResult) {
        Order order = new Order();
        order.setId(orderResult.getOrderId());
        order.setStatus(Status.TicketStatus.WAIT_USE);
        order.setPayamount(ticketDetail.getBuyPrice());
        order.setSubject(ticketDetail.getTicketName());
        order.setQuantity(Integer.valueOf(quantity));
        order.setVisitDate(ticketDetail.getVisitDate());
        EventBus.getDefault().post(new AddOrderEvent(order));
        TicketSuccessActivity.goActivity(this, ticketDetail.getTicketName(), String.valueOf(ticketDetail.getBuyPrice
                ()), ticketDetail.getVisitDate(), quantity);
    }

    @Override
    public void showErrorMessage(int code, String message) {
        message.replace("#","\n");
        ConfirmDialog errorDialog = new ConfirmDialog(this, message);
        errorDialog.setOnConfirmListener(() -> {
            finish();
        });
        errorDialog.show();
    }
}
