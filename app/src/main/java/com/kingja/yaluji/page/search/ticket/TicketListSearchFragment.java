package com.kingja.yaluji.page.search.ticket;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.kingja.yaluji.R;
import com.kingja.yaluji.adapter.TicketAdapter;
import com.kingja.yaluji.base.BaseFragment;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.constant.Constants;
import com.kingja.yaluji.constant.Status;
import com.kingja.yaluji.i.OnSearchListener;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.model.entiy.Ticket;
import com.kingja.yaluji.page.ticket.detail.TicketDetailActivity;
import com.kingja.yaluji.page.ticket.list.TicketListContract;
import com.kingja.yaluji.page.ticket.list.TicketListPresenter;
import com.kingja.yaluji.util.ToastUtil;
import com.kingja.yaluji.view.PullToMoreListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnItemClick;
import okhttp3.MultipartBody;

/**
 * Description:TODO
 * Create Time:2018/11/4 15:49
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class TicketListSearchFragment extends BaseFragment implements OnSearchListener, TicketListContract.View,
        SwipeRefreshLayout.OnRefreshListener, PullToMoreListView.OnScrollToBottom {
    @BindView(R.id.plv)
    PullToMoreListView plv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    @BindView(R.id.iv_go_top)
    ImageView ivGoTop;
    private List<Ticket> ticketList = new ArrayList<>();
    @Inject
    TicketListPresenter ticketListPresenter;
    private TicketAdapter ticketAdapter;
    private String keyword;

    @OnItemClick(R.id.plv)
    public void itemClick(AdapterView<?> parent, View view, int position, long id) {
        Ticket ticket = (Ticket) parent.getItemAtPosition(position);
        TicketDetailActivity.goActivity(getActivity(), ticket.getId());
    }

    public static TicketListSearchFragment newInstance(String keyword) {
        TicketListSearchFragment fragment = new TicketListSearchFragment();
        Bundle args = new Bundle();
        args.putString(Constants.Extra.Keyword, keyword);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initVariable() {
        if (getArguments() != null) {
            keyword = getArguments().getString(Constants.Extra.Keyword, "");
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
        ticketAdapter = new TicketAdapter(getActivity(), ticketList);
        plv.setAdapter(ticketAdapter);
        plv.setGoTop(ivGoTop);
    }

    @Override
    protected void initData() {
        srl.setOnRefreshListener(this);
        plv.setOnScrollToBottom(this);
    }

    @Override
    protected void initNet() {
        ticketListPresenter.getTicketList(new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("keyword", keyword)
                .addFormDataPart("status", String.valueOf(Status.TicketSellStatus.ALL))
                .addFormDataPart("page", String.valueOf(plv.getPageIndex()))
                .addFormDataPart("pageSize", String.valueOf(Constants.PAGE_SIZE_20))
                .build());
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_ticket_list;
    }

    @Override
    public void search(String keyword) {
        this.keyword = keyword;
        initNet();
    }

    private int currentPageSize = 1;

    @Override
    public void onGetTicketListSuccess(List<Ticket> ticketList) {
        if (ticketList != null && ticketList.size() > 0) {
            if (plv.getPageIndex() == 1) {
                ticketAdapter.setData(ticketList);
            } else {
                ticketAdapter.addData(ticketList);
            }
        } else {
            if (plv.getPageIndex() == 1) {
                showEmptyCallback();
            } else {
                ToastUtil.showText("到底啦");
            }
        }

    }

    @Override
    public void onRefresh() {
        plv.reset();
        initNet();
    }

    @Override
    public boolean ifRegisterLoadSir() {
        return true;
    }
    @Override
    public void showLoadingCallback() {
        srl.setRefreshing(true);
    }

    @Override
    public void showSuccessCallback() {
        super.showSuccessCallback();
        srl.setRefreshing(false);
    }

    @Override
    public void onScrollToBottom(int pageIndex) {
        initNet();
    }
}
