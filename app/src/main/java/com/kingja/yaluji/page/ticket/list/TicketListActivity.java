package com.kingja.yaluji.page.ticket.list;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingja.popwindowsir.PopConfig;
import com.kingja.popwindowsir.PopHelper;
import com.kingja.popwindowsir.PopSpinner;
import com.kingja.yaluji.R;
import com.kingja.yaluji.activity.SearchDetailActivity;
import com.kingja.yaluji.adapter.ScenicTypeAdapter;
import com.kingja.yaluji.adapter.TicketAdapter;
import com.kingja.yaluji.adapter.TicketPageAdapter;
import com.kingja.yaluji.base.BaseFragment;
import com.kingja.yaluji.base.BaseTitleActivity;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.constant.Status;
import com.kingja.yaluji.event.TicketFilterEvent;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.model.entiy.City;
import com.kingja.yaluji.model.entiy.HotSearch;
import com.kingja.yaluji.model.entiy.ScenicType;
import com.kingja.yaluji.model.entiy.Ticket;
import com.kingja.yaluji.page.ticket.detail.TicketDetailActivity;
import com.kingja.yaluji.service.initialize.InitializeContract;
import com.kingja.yaluji.service.initialize.InitializePresenter;
import com.kingja.yaluji.util.AppUtil;
import com.kingja.yaluji.util.GoUtil;
import com.kingja.yaluji.util.LogUtil;
import com.kingja.yaluji.util.SpSir;
import com.kingja.yaluji.view.CityPop;
import com.kingja.yaluji.view.DatePop;
import com.kingja.yaluji.view.DiscountPop;
import com.kingja.yaluji.view.PullToBottomListView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Description:TODO
 * Create Time:2018/10/29 22:28
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class TicketListActivity extends BaseTitleActivity implements TicketListContract.View, InitializeContract
        .View, SwipeRefreshLayout.OnRefreshListener {
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
    InitializePresenter initializePresenter;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.iv_go_top)
    ImageView ivGoTop;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.vp)
    ViewPager vp;
    private List<ScenicType> scenicTypes = new ArrayList<>();
    private List<City> cities = new ArrayList<>();
    private List<Ticket> ticketList = new ArrayList<>();
    private TicketAdapter ticketAdapter;
    private CityPop cityPop;
    private PopConfig popConfig;
    private String productTypeId = "";
    private String useDates = "";
    private String areaId = "";
    private String discountOrder = "";
    private String buyLimit = "";
    private String[] tabTitles = {"抢券中", "待上架"};
    private int[] tabImgs = {R.mipmap.ic_ticket_selling, R.mipmap.ic_ticket_tosell};
    private BaseFragment[] fragments = new BaseFragment[2];

    @OnClick({R.id.ll_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_search:
                GoUtil.goActivity(this, SearchDetailActivity.class);
                break;
            default:
                break;
        }
    }

    @OnItemClick(R.id.plv)
    public void itemClick(AdapterView<?> parent, View view, int position, long id) {
        Ticket ticket = (Ticket) parent.getItemAtPosition(position);
        TicketDetailActivity.goActivity(this, ticket.getId());
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
        initializePresenter.attachView(this);
    }

    @Override
    protected String getContentTitle() {
        return "鹿券";
    }

    @Override
    protected void initView() {
        ticketAdapter = new TicketAdapter(this, ticketList);
        plv.setAdapter(ticketAdapter);
        plv.setGoTop(ivGoTop);
    }

    @Override
    protected void initData() {
        tab.setTabMode(TabLayout.MODE_FIXED);
        tab.addTab(tab.newTab().setText(tabTitles[0]));
        tab.addTab(tab.newTab().setText(tabTitles[1]));
        fragments[0]=TicketListFragment.newInstance(Status.TicketSellStatus.SELLING);
        fragments[1]=TicketListFragment.newInstance(Status.TicketSellStatus.TOSELL);
        TicketPageAdapter ticketPageAdapter = new TicketPageAdapter(this, fragments, tabImgs, tabTitles);
        vp.setAdapter(ticketPageAdapter);
        vp.setOffscreenPageLimit(tabTitles.length);
        tab.setupWithViewPager(vp);
        for (int i = 0; i < tab.getTabCount(); i++) {
            TabLayout.Tab t = tab.getTabAt(i);
            t.setCustomView(ticketPageAdapter.getTabView(i));
        }

        srl.setOnRefreshListener(this);
        initHint();
        popConfig = new PopConfig.Builder()
                .setPopHeight((int) (AppUtil.getScreenHeight() * 0.55f))
                .build();
        initScenicTypeData();
        initScenicTypePop();
        initCityData();
        initCityPop();
        initDatePop();
        initDiscountPop();
    }

    private void initDiscountPop() {
        DiscountPop discountPop = new DiscountPop(this);
        discountPop.setOnDismissListener(() -> {
            spinerDiscount.close();
        });
        discountPop.setOnDiscountSelectedLintener(new DiscountPop.OnDiscountSelectedLintener() {

            @Override
            public void onDiscountSelected(String discountOrder, String buyLimit) {
                TicketListActivity.this.discountOrder = discountOrder;
                TicketListActivity.this.buyLimit = buyLimit;
                postRefreshTicketList();
            }
        });
        spinerDiscount.setOnSpinnerStatusChangedListener(opened -> {
            if (opened) {
                discountPop.showAsDropDown(llSpinnerRoot);
            } else {
                discountPop.dismiss();
            }
        });
    }

    private void initHint() {
        String historyKeyword = SpSir.getInstance().getHistoryKeyword();
        Log.e(TAG, "historyKeyword: " + historyKeyword);
        if (!TextUtils.isEmpty(historyKeyword)) {
            tvSearch.setHint(historyKeyword);
        }
    }

    private void initScenicTypeData() {
        String scenicTypeGson = SpSir.getInstance().getScenicType();
        if (!TextUtils.isEmpty(scenicTypeGson)) {
            LogUtil.e(TAG, "有初始化景区类型缓存");
            scenicTypes = new Gson().fromJson(scenicTypeGson, new TypeToken<List<ScenicType>>() {
            }.getType());
        } else {
            LogUtil.e(TAG, "没有初始化景区类型缓存");
            initializePresenter.getScenicType("3");
        }
    }

    private void initCityData() {
        String cityGson = SpSir.getInstance().getCity();
        if (!TextUtils.isEmpty(cityGson)) {
            LogUtil.e(TAG, "有初始化城市缓存:" + cityGson);
            cities = new Gson().fromJson(cityGson, new TypeToken<List<City>>() {
            }.getType());
        } else {
            LogUtil.e(TAG, "没有初始化城市缓存");
            initializePresenter.getCity();
        }
    }

    private void initScenicTypePop() {
        if (scenicTypes != null && scenicTypes.size() > 0) {
            ScenicType scenicType = new ScenicType();
            scenicType.setCode("");
            scenicType.setDesc("不限");
            scenicTypes.add(0, scenicType);
            ScenicTypeAdapter scenicTypeAdapter = new ScenicTypeAdapter(this, scenicTypes);
            scenicTypeAdapter.selectItem(0);
            new PopHelper.Builder(this)
                    .setAdapter(scenicTypeAdapter)
                    .setPopSpinner(spinerType)
                    .setOnItemClickListener((PopHelper.OnItemClickListener<ScenicType>) (item, position, popSpinner)
                            -> {
                        Log.e(TAG, "景区ID: " + item.getCode() + " 景区名称: " + item.getDesc());
                        productTypeId = item.getCode();
                        scenicTypeAdapter.selectItem(position);
                        postRefreshTicketList();
                    })
                    .build();
        }
    }

    private void initDatePop() {
        DatePop datePop = new DatePop(this, popConfig);
        datePop.setOnDismissListener(() -> {
            spinerDate.close();
        });
        datePop.setOnDateSelectedListener(dates -> {
            useDates = dates;
            datePop.dismiss();
            postRefreshTicketList();
        });
        spinerDate.setOnSpinnerStatusChangedListener(opened -> {
            if (opened) {
                datePop.showAsDropDown(llSpinnerRoot);
            } else {
                datePop.dismiss();
            }
        });
    }


    private void initCityPop() {
        if (cities != null && cities.size() > 0) {
            cityPop = new CityPop(this, popConfig);
            cityPop.setCities(cities);
            cityPop.setOnAreaSelectListener((areaId, areaName) -> {
                Log.e(TAG, "区域ID: " + areaId + " 区域名称: " + areaName);
                this.areaId = areaId;
                cityPop.dismiss();
                postRefreshTicketList();
            });
            cityPop.setOnDismissListener(() -> {
                spinerArea.close();
            });
            spinerArea.setOnSpinnerStatusChangedListener(opened -> {
                if (opened) {
                    cityPop.showAsDropDown(llSpinnerRoot);
                } else {
                    cityPop.dismiss();
                }
            });
        }

    }

    private void postRefreshTicketList() {
        EventBus.getDefault().post(new TicketFilterEvent(areaId,productTypeId,useDates,buyLimit,discountOrder));
    }

    @Override
    protected void initNet() {
    }

    @Override
    public void onGetTicketListSuccess(List<Ticket> ticketList) {
        if (ticketList != null && ticketList.size() > 0) {
            ticketAdapter.setData(ticketList);
        } else {
            ticketAdapter.setData(new ArrayList<>());
        }
    }

    @Override
    public void onGetHotSearch(List<HotSearch> hotSearches) {
        SpSir.getInstance().putHotSearch(new Gson().toJson(hotSearches));
    }

    @Override
    public void onGetScenicTypeSuccess(List<ScenicType> scenicTypes) {
        this.scenicTypes = scenicTypes;
        initScenicTypePop();
    }

    @Override
    public void onGetCitySuccess(List<City> cities) {
        this.cities = cities;
        initCityPop();
    }

    @Override
    public void onRefresh() {
        srl.setRefreshing(false);
        initNet();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
