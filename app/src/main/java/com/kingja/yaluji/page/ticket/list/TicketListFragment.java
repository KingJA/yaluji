package com.kingja.yaluji.page.ticket.list;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.kingja.yaluji.R;
import com.kingja.yaluji.adapter.TicketAdapter;
import com.kingja.yaluji.base.BaseFragment;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.constant.Constants;
import com.kingja.yaluji.constant.Status;
import com.kingja.yaluji.event.TicketFilterEvent;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.model.entiy.Ticket;
import com.kingja.yaluji.page.ticket.detail.TicketDetailActivity;
import com.kingja.yaluji.view.PullToBottomListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.Unbinder;
import okhttp3.MultipartBody;

/**
 * Description:TODO
 * Create Time:2018/12/5 20:17
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class TicketListFragment extends BaseFragment implements TicketListContract.View, SwipeRefreshLayout
        .OnRefreshListener {
    @Inject
    TicketListPresenter ticketListPresenter;
    @BindView(R.id.plv)
    PullToBottomListView plv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    @BindView(R.id.iv_go_top)
    ImageView ivGoTop;
    private int ticketStatus;
    private TicketAdapter ticketAdapter;

    @OnItemClick(R.id.plv)
    public void itemClick(AdapterView<?> parent, View view, int position, long id) {
        Ticket ticket = (Ticket) parent.getItemAtPosition(position);
        TicketDetailActivity.goActivity(getActivity(), ticket.getId());
    }


    public static TicketListFragment newInstance(int ticketStatus) {
        TicketListFragment fragment = new TicketListFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.Extra.TicketStatus, ticketStatus);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initVariable() {
        EventBus.getDefault().register(this);
        Bundle arguments = getArguments();
        if (arguments != null) {
            ticketStatus = arguments.getInt(Constants.Extra.TicketStatus, Status.TicketStatus.ALL);
        }
    }

    @Override
    protected void initComponent(AppComponent appComponent) {
        DaggerBaseCompnent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
        ticketListPresenter.attachView(this);
    }

    @Override
    protected void initView() {
        ticketAdapter = new TicketAdapter(getActivity(), null);
        plv.setAdapter(ticketAdapter);
        plv.setGoTop(ivGoTop);
    }

    @Override
    protected void initData() {
        srl.setOnRefreshListener(this);
    }

    private String areaId="";
    private String productTypeId="";
    private String useDates="";
    private String buyLimit="";
    private String discountOrder="";

    @Override
    protected void initNet() {
        ticketListPresenter.getTicketList(new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("areaId", areaId)
                .addFormDataPart("productTypeId", productTypeId)
                .addFormDataPart("useDates", useDates)
                .addFormDataPart("discountRate", "")
                .addFormDataPart("keyword", "")
                .addFormDataPart("page", String.valueOf(Constants.PAGE_FIRST))
                .addFormDataPart("pageSize", String.valueOf(Constants.PAGE_SIZE_100))
                .addFormDataPart("status", String.valueOf(ticketStatus))
                .addFormDataPart("buyLimit", buyLimit)
                .addFormDataPart("discountOrder", discountOrder)
                .build());
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_ticket_list;
    }

    @Override
    public boolean ifRegisterLoadSir() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void filterTicket(TicketFilterEvent filterEvent) {
        this.areaId = filterEvent.getAreaId();
        this.productTypeId = filterEvent.getProductTypeId();
        this.useDates = filterEvent.getUseDates();
        this.buyLimit = filterEvent.getBuyLimit();
        this.discountOrder = filterEvent.getDiscountOrder();
        ticketListPresenter.getTicketList(new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("areaId", areaId)
                .addFormDataPart("productTypeId", productTypeId)
                .addFormDataPart("useDates", useDates)
                .addFormDataPart("discountRate", "")
                .addFormDataPart("keyword", "")
                .addFormDataPart("page", String.valueOf(Constants.PAGE_FIRST))
                .addFormDataPart("pageSize", String.valueOf(Constants.PAGE_SIZE_100))
                .addFormDataPart("status", String.valueOf(ticketStatus))
                .addFormDataPart("buyLimit", buyLimit)
                .addFormDataPart("discountOrder", discountOrder)
                .build());
    }

    @Override
    public void onGetTicketListSuccess(List<Ticket> ticketList) {
        if (ticketList != null && ticketList.size() > 0) {
            ticketAdapter.setData(ticketList);
        }else{
            showEmptyCallback();
        }

    }

    @Override
    public void onRefresh() {
        srl.setRefreshing(false);
        initNet();
    }
}
