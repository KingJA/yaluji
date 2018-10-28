package com.kingja.yaluji.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.kingja.yaluji.R;
import com.kingja.yaluji.adapter.TicketPageAdapter;
import com.kingja.yaluji.base.BaseFragment;
import com.kingja.yaluji.constant.Status;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.page.order.list.TicketListFragment;

import butterknife.BindView;

/**
 * Description:订单列表
 * Create Time:2018/1/22 13:24
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class TicketFragment extends BaseFragment {
    @BindView(R.id.tab_order)
    TabLayout tabOrder;
    @BindView(R.id.vp_content_order)
    ViewPager vpContentOrder;
    private String[] items = {"待使用", "全部订单"};
    private Fragment mFragmentArr[] = new Fragment[2];


    @Override
    protected void initVariable() {

    }

    @Override
    protected void initComponent(AppComponent appComponent) {

    }

    @Override
    protected void initView() {
        tabOrder.setTabMode(TabLayout.MODE_FIXED);
        tabOrder.addTab(tabOrder.newTab().setText(items[0]));
        tabOrder.addTab(tabOrder.newTab().setText(items[1]));
//        tabOrder.post(() -> IndicatorUtil.setIndicator(tabOrder, 50, 50));
        mFragmentArr[0] = TicketListFragment.newInstance(Status.TicketStatus.WAIT_USE);
        mFragmentArr[1] = TicketListFragment.newInstance(Status.TicketStatus.ALL);
        TicketPageAdapter mTicketPageAdapter = new TicketPageAdapter(getChildFragmentManager(), mFragmentArr,
                items);
        vpContentOrder.setAdapter(mTicketPageAdapter);
        tabOrder.setupWithViewPager(vpContentOrder);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initNet() {

    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_ticket;
    }

}
