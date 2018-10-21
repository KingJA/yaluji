package com.kingja.yaluji.page.order.list;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.kingja.yaluji.R;
import com.kingja.yaluji.adapter.AllOrderAdapter;
import com.kingja.yaluji.base.BaseFragment;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.callback.EmptyOrderCallback;
import com.kingja.yaluji.callback.UnLoginCallback;
import com.kingja.yaluji.constant.Constants;
import com.kingja.yaluji.event.ResetLoginStatusEvent;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.model.entiy.Order;
import com.kingja.yaluji.util.LoginChecker;
import com.kingja.yaluji.view.RefreshSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
public class TicketListFragment extends BaseFragment implements TicketListContract.View, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.lv_unused)
    ListView lv_unused;
    @BindView(R.id.srl_unused)
    RefreshSwipeRefreshLayout srl_unused;
    private LoadService loadService;

    private List<Order> orders = new ArrayList<>();
    @Inject
    TicketListPresenter ticketListPresenter;
    private AllOrderAdapter mAllOrderAdapter;

    @Override
    protected void initVariable() {
        EventBus.getDefault().register(this);
    }
    @Override
    protected void initComponent(AppComponent appComponent) {
        DaggerBaseCompnent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        ticketListPresenter.attachView(this);
        srl_unused.setScrollUpChild(lv_unused);
        srl_unused.setOnRefreshListener(this);
        mAllOrderAdapter = new AllOrderAdapter(getActivity(), orders);
        lv_unused.setAdapter(mAllOrderAdapter);
        loadService = LoadSir.getDefault().register(lv_unused);
    }


    @Override
    protected void initNet() {
//        if (LoginChecker.isLogin()) {
//            ticketListPresenter.getOrders(Constants.PAGE_FIRST, Constants.PAGE_SIZE,Constants.ORDER_STATUS_ALL);
//        } else {
//            loadService.showCallback(UnLoginCallback.class);
//        }

    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_ticket_list;
    }

    @Override
    public void showLoading() {
        srl_unused.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        srl_unused.setRefreshing(false);
    }

    @Override
    public void onGetOrdersSuccess(List<Order> orders) {
        if (orders.size() == 0) {
            loadService.showCallback(EmptyOrderCallback.class);
        } else {
            loadService.showSuccess();
            mAllOrderAdapter.setData(orders);
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
