package com.kingja.yaluji.page.order.list;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.kingja.yaluji.R;
import com.kingja.yaluji.adapter.OrderAdapter;
import com.kingja.yaluji.base.BaseFragment;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.constant.Constants;
import com.kingja.yaluji.constant.Status;
import com.kingja.yaluji.event.AddOrderEvent;
import com.kingja.yaluji.event.RefreshOrderEvent;
import com.kingja.yaluji.event.ResetLoginStatusEvent;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.model.entiy.Order;
import com.kingja.yaluji.page.order.orderdetail.OrderDetailActivity;
import com.kingja.yaluji.util.AppUtil;
import com.kingja.yaluji.util.DialogUtil;
import com.kingja.yaluji.util.LogUtil;
import com.kingja.yaluji.util.LoginChecker;
import com.kingja.yaluji.view.MoveSwipeRefreshLayout;
import com.kingja.yaluji.view.PullToBottomListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Description:全部订单列表
 * Create Time:2018/2/4 15:04
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class OrderListFragment extends BaseFragment implements OrderListContract.View, SwipeRefreshLayout
        .OnRefreshListener {
    @BindView(R.id.srl)
    MoveSwipeRefreshLayout srl;
    @BindView(R.id.plv)
    PullToBottomListView plv;
    @BindView(R.id.iv_go_top)
    ImageView ivGoTop;
    @BindView(R.id.iv_service)
    ImageView ivService;
    private List<Order> orderList = new ArrayList<>();
    @Inject
    OrderListPresenter orderListPresenter;
    private OrderAdapter mOrderAdapter;
    private int ticketStatus;

    @OnClick({R.id.iv_service})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.iv_service:
                DialogUtil.showDoubleDialog(getActivity(), "客服电话:0577-88755877", "拨打", "取消", (dialog, which) -> {
                    callPhone(getString(R.string.service_number));
                });
                break;

        }
    }

    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    public static OrderListFragment newInstance(int ticketStatus) {
        OrderListFragment fragment = new OrderListFragment();
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
        orderListPresenter.attachView(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        srl.setOnRefreshListener(this);
        srl.setProgressViewEndTarget(true, AppUtil.dp2px(60));
        mOrderAdapter = new OrderAdapter(getActivity(), orderList);
        plv.setAdapter(mOrderAdapter);
        plv.setGoTop(ivGoTop);
        mOrderAdapter.setOnItemOperateListener(new OrderAdapter.OnItemOperateListener() {
            @Override
            public void onDelete(int position, String orderId) {
                orderListPresenter.deleteOrder(position, orderId);
            }

            @Override
            public void onItemClick(String orderId) {
                OrderDetailActivity.goActivity(getActivity(), orderId);
            }
        });
        ivService.setVisibility(ticketStatus == Status.TicketStatus.WAIT_USE ? View.VISIBLE : View.GONE);
    }


    @Override
    protected void initNet() {
        if (LoginChecker.isLogin()) {
            orderListPresenter.getTicketList(Constants.PAGE_FIRST, Constants.PAGE_SIZE, ticketStatus);
        } else {
            showUnLoginCallback();
        }
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_order_list;
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
    public void onGetTicketListSuccess(List<Order> orderList) {
        checkList(orderList);
    }

    private void checkList(List<Order> orderList) {
        if (orderList != null) {
            mOrderAdapter.setData(orderList);
        }
        if (orderList.size() == 0) {
            showEmptyCallback();
        }
    }

    @Override
    public void onDeleteOrderSuccess(String orderId, int position) {
        if (ticketStatus != Status.TicketStatus.WAIT_USE) {
            mOrderAdapter.removeItem(position);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void addOrder(AddOrderEvent event) {
        showSuccessCallback();
        mOrderAdapter.addOrder(event.getOrder());
        LogUtil.e(TAG, "订单状态:" + ticketStatus + " 数量:" + mOrderAdapter.getCount());
    }

    public void refreshOrder(RefreshOrderEvent event) {
        initNet();
    }
}
