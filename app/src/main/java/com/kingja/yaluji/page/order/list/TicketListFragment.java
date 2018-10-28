package com.kingja.yaluji.page.order.list;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.kingja.yaluji.R;
import com.kingja.yaluji.adapter.TicketAdapter;
import com.kingja.yaluji.base.App;
import com.kingja.yaluji.base.BaseFragment;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.callback.EmptyOrderCallback;
import com.kingja.yaluji.constant.Constants;
import com.kingja.yaluji.constant.Status;
import com.kingja.yaluji.event.ResetLoginStatusEvent;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.model.entiy.Order;
import com.kingja.yaluji.model.entiy.Ticket;
import com.kingja.yaluji.util.AppUtil;
import com.kingja.yaluji.util.LoginChecker;
import com.kingja.yaluji.view.RefreshSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Description:全部订单列表
 * Create Time:2018/2/4 15:04
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class TicketListFragment extends BaseFragment implements TicketListContract.View, SwipeRefreshLayout
        .OnRefreshListener {
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.srl)
    RefreshSwipeRefreshLayout srl;
    private List<Ticket> ticketList = new ArrayList<>();
    @Inject
    TicketListPresenter ticketListPresenter;
    private TicketAdapter mTicketAdapter;
    private int ticketStatus;

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

    }

    @Override
    protected void initData() {
        srl.setOnRefreshListener(this);
        srl.setProgressViewEndTarget(true, AppUtil.dp2px(60));
        mTicketAdapter = new TicketAdapter(getActivity(), ticketList);
        lv.setAdapter(mTicketAdapter);
    }


    @Override
    protected void initNet() {
        if (LoginChecker.isLogin()) {
            ticketListPresenter.getTicketList(Constants.PAGE_FIRST, Constants.PAGE_SIZE, ticketStatus);
        } else {
            showUnLoginCallback();
        }
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_ticket_list;
    }

    @Override
    public boolean ifRegisterLoadSir() {
        return true;
    }

    @Override
    public void showLoading() {
        srl.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        srl.setRefreshing(false);
    }

    @Override
    public void onGetTicketListSuccess(List<Ticket> ticketList) {
        if (ticketList != null && ticketList.size() == 0) {
            mTicketAdapter.setData(ticketList);
        } else {
            showEmptyCallback();
        }
    }

    @Override
    public void onRefresh() {
        initNet();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void resetLoginStatus(ResetLoginStatusEvent resetLoginStatusEvent) {
        initNet();
    }
}
