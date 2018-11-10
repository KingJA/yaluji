package com.kingja.yaluji.page.ticket.success;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingja.yaluji.R;
import com.kingja.yaluji.activity.SearchDetailActivity;
import com.kingja.yaluji.adapter.TicketGvAdapter;
import com.kingja.yaluji.base.BaseTitleActivity;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.constant.Constants;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.model.entiy.Ticket;
import com.kingja.yaluji.page.ticket.detail.TicketDetailActivity;
import com.kingja.yaluji.util.GoUtil;
import com.kingja.yaluji.view.FixedGridView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Description:TODO
 * Create Time:2018/11/8 23:14
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class TicketSuccessActivity extends BaseTitleActivity implements TicketSuccessContract.View {

    @BindView(R.id.tv_ticketName)
    TextView tvTicketName;
    @BindView(R.id.tv_quantity)
    TextView tvQuantity;
    @BindView(R.id.ll_getSuccess)
    LinearLayout llGetSuccess;
    @BindView(R.id.tv_buyPrice)
    TextView tvBuyPrice;
    @BindView(R.id.tv_visitDate)
    TextView tvVisitDate;
    @BindView(R.id.tv_backToList)
    TextView tvBackToList;
    @BindView(R.id.fgv)
    FixedGridView fgv;
    private String ticketName;
    private String buyPrice;
    private String visitDate;
    private String quantity;
    @Inject
    TicketSuccessPresenter ticketSuccessPresenter;
    private List<Ticket> ticketList = new ArrayList<>();
    private TicketGvAdapter ticketGvAdapter;

    @OnItemClick(R.id.fgv)
    public void itemClick(AdapterView<?> parent, View view, int position, long id) {
        Ticket ticket = (Ticket) parent.getItemAtPosition(position);
        TicketDetailActivity.goActivity(this, ticket.getId());
    }

    @OnClick({R.id.tv_backToList})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_backToList:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void initVariable() {
        ticketName = getIntent().getStringExtra(Constants.Extra.TicketName);
        buyPrice = getIntent().getStringExtra(Constants.Extra.BuyPrice);
        visitDate = getIntent().getStringExtra(Constants.Extra.VisitDate);
        quantity = getIntent().getStringExtra(Constants.Extra.Quantity);

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_ticket_success;
    }

    @Override
    protected void initComponent(AppComponent appComponent) {
        DaggerBaseCompnent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
        ticketSuccessPresenter.attachView(this);
    }

    @Override
    protected String getContentTitle() {
        return "领券成功";
    }

    @Override
    protected void initView() {
        ticketGvAdapter = new TicketGvAdapter(this, ticketList);
        fgv.setAdapter(ticketGvAdapter);

    }

    @Override
    protected void initData() {
        tvBuyPrice.setText(buyPrice);
        tvQuantity.setText(quantity);
        tvTicketName.setText(ticketName);
        tvVisitDate.setText(visitDate);
    }

    @Override
    protected void initNet() {
        ticketSuccessPresenter.getRecommendList();
    }

    public static void goActivity(Activity context, String ticketName, String buyPrice, String visitDate, String
            quantity) {
        Intent intent = new Intent(context, TicketSuccessActivity.class);
        intent.putExtra(Constants.Extra.TicketName, ticketName);
        intent.putExtra(Constants.Extra.BuyPrice, buyPrice);
        intent.putExtra(Constants.Extra.VisitDate, visitDate);
        intent.putExtra(Constants.Extra.Quantity, quantity);
        context.startActivity(intent);
        context.finish();
    }

    @Override
    public void onGetRecommendListSuccess(List<Ticket> ticketList) {
        if (ticketList != null && ticketList.size() > 0) {
            ticketGvAdapter.setData(ticketList);
        }

    }
}
