package com.kingja.yaluji.page.ticket.list;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.kingja.popwindowsir.PopSpinner;
import com.kingja.yaluji.R;
import com.kingja.yaluji.adapter.TicketAdapter;
import com.kingja.yaluji.base.BaseTitleActivity;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.model.entiy.Ticket;
import com.kingja.yaluji.page.ticket.detail.TicketDetailActivity;
import com.kingja.yaluji.view.PullToBottomListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnItemClick;
import okhttp3.MultipartBody;

/**
 * Description:TODO
 * Create Time:2018/10/29 22:28
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class TicketListActivity extends BaseTitleActivity implements TicketListContract.View {
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.spiner_area)
    PopSpinner spinerArea;
    @BindView(R.id.spiner_type)
    PopSpinner spinerType;
    @BindView(R.id.spiner_date)
    PopSpinner spinerDate;
    @BindView(R.id.spiner_discount)
    PopSpinner spinerDiscount;
    @BindView(R.id.ll_spinner_root)
    LinearLayout llSpinnerRoot;
    @BindView(R.id.plv)
    PullToBottomListView plv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    @Inject
    TicketListPresenter ticketListPresenter;
    private List<Ticket> ticketList = new ArrayList<>();
    private TicketAdapter ticketAdapter;

    @OnItemClick(R.id.plv)
    public void itemClick(AdapterView<?> parent, View view, int position, long id) {
        Ticket ticket = (Ticket) parent.getItemAtPosition(position);
        TicketDetailActivity.goActivity(this,ticket.getId());

    }
    @Override
    public void initVariable() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_ticket_list;
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
    protected String getContentTitle() {
        return "鹿券";
    }

    @Override
    protected void initView() {
        ticketAdapter = new TicketAdapter(this, ticketList);
        plv.setAdapter(ticketAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initNet() {
        ticketListPresenter.getTicketList(new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("areaId", "")
                .addFormDataPart("productTypeId", "")
                .addFormDataPart("useDates", "")
                .addFormDataPart("discountRate", "")
                .addFormDataPart("keyword", "")
                .addFormDataPart("page", "1")
                .addFormDataPart("pageSize", "5")
                .addFormDataPart("status", "1")
                .addFormDataPart("buyLimit", "")
                .addFormDataPart("discountOrder", "")
                .build());
    }

    @Override
    public void onGetTicketListSuccess(List<Ticket> ticketList) {
        if (ticketList != null && ticketList.size() > 0) {
            ticketAdapter.setData(ticketList);
        }

    }
}
