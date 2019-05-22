package com.kingja.yaluji.page.ticket.list;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingja.popwindowsir.PopConfig;
import com.kingja.popwindowsir.PopHelper;
import com.kingja.popwindowsir.PopSpinner;
import com.kingja.yaluji.R;
import com.kingja.yaluji.activity.FirstDialogActivity;
import com.kingja.yaluji.activity.SearchDetailActivity;
import com.kingja.yaluji.adapter.ScenicTypeAdapter;
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
import com.kingja.yaluji.page.ticket.location.LocationContract;
import com.kingja.yaluji.page.ticket.location.LocationPresenter;
import com.kingja.yaluji.service.initialize.InitializeContract;
import com.kingja.yaluji.service.initialize.InitializePresenter;
import com.kingja.yaluji.util.AppUtil;
import com.kingja.yaluji.util.DateUtil;
import com.kingja.yaluji.util.DialogUtil;
import com.kingja.yaluji.util.GoUtil;
import com.kingja.yaluji.util.LogUtil;
import com.kingja.yaluji.util.SpSir;
import com.kingja.yaluji.view.CityPop;
import com.kingja.yaluji.view.DatePop;
import com.kingja.yaluji.view.DiscountPop;
import com.kingja.yaluji.view.dialog.BaseDialog;
import com.kingja.yaluji.view.dialog.LocationDialog;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Description:TODO
 * Create Time:2018/10/29 22:28
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class TicketListActivity extends BaseTitleActivity implements InitializeContract.View, LocationContract.View {
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
    @Inject
    InitializePresenter initializePresenter;
    @Inject
    LocationPresenter locationPresenter;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.iv_more_ticket)
    ImageView ivMoreTicket;
    private List<ScenicType> scenicTypes = new ArrayList<>();
    private List<City> cities = new ArrayList<>();
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
    private LocationDialog locationDialog;

    @OnClick({R.id.ll_search, R.id.iv_more_ticket})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_search:
                GoUtil.goActivity(this, SearchDetailActivity.class);
                break;
            case R.id.iv_more_ticket:
                ivMoreTicket.setVisibility(View.GONE);
                locationDialog.show();
                break;
            default:
                break;
        }
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
        locationPresenter.attachView(this);
    }

    @Override
    protected String getContentTitle() {
        return "鹿券";
    }

    @Override
    protected void initView() {
    }

    public void checkLocationPermission() {
        Disposable disposable = new RxPermissions(this).requestEach(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            uploadLocationInfo();
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            DialogUtil.showDoubleDialog(TicketListActivity.this, "开启定位,获取更多优惠券信息", "开启定位", "以后再说", new
                                    MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction
                                                which) {
                                            checkLocationPermission();
                                        }
                                    });
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            DialogUtil.showDoubleDialog(TicketListActivity.this,
                                    "未开启定位，无法获取更多优惠券信息。请前往应用权限设置打开权限。", new MaterialDialog
                                            .SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction
                                                which) {
                                            startAppSettings();
                                        }
                                    });

                        }
                    }
                });

    }

    private void uploadLocationInfo() {
        if (TextUtils.isEmpty(SpSir.getInstance().getToken())) {
            return;
        }
        //1.获得位置服务
        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null) {
            Toast.makeText(this, "没有可用的位置服务", Toast.LENGTH_SHORT).show();
            return;
        }
        String locationProdiver = getLocationProdiver(locationManager);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                locationManager.removeUpdates(this);
                Log.e("上传定位", "Latitude:" + location.getLatitude() + " Longitude:" + location
                        .getLongitude());
                locationPresenter.uploadLocation(String.valueOf(location.getLatitude()), String.valueOf(location
                        .getLongitude()));

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
            }

            @Override
            public void onProviderEnabled(String s) {
            }

            @Override
            public void onProviderDisabled(String s) {
            }
        };
        //2.判断权限绑定监听
        if (locationProdiver != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest
                    .permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(locationProdiver, 0, 0f, locationListener);
            }
        }
    }

    private String getLocationProdiver(LocationManager locationManager) {
        List<String> prodiverlist = locationManager.getProviders(true);
        if (prodiverlist.contains(LocationManager.NETWORK_PROVIDER)) {
            return LocationManager.NETWORK_PROVIDER;//网络定位
        } else if (prodiverlist.contains(LocationManager.GPS_PROVIDER)) {
            return LocationManager.GPS_PROVIDER;//GPS定位
        } else {
            Toast.makeText(this, "没有可用的定位模块", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        startActivity(intent);
    }

    @Override
    protected void initData() {
        locationDialog = new LocationDialog(this);
        locationDialog.setOnConfirmListener(new BaseDialog.OnConfirmListener() {
            @Override
            public void onConfirm() {
                checkLocationPermission();
            }
        });
        tab.setTabMode(TabLayout.MODE_FIXED);
        tab.addTab(tab.newTab().setText(tabTitles[0]));
        tab.addTab(tab.newTab().setText(tabTitles[1]));
        fragments[0] = TicketListFragment.newInstance(Status.TicketSellStatus.SELLING);
        fragments[1] = TicketListFragment.newInstance(Status.TicketSellStatus.TOSELL);
        TicketPageAdapter ticketPageAdapter = new TicketPageAdapter(this, fragments, tabImgs, tabTitles);
        vp.setAdapter(ticketPageAdapter);
        vp.setOffscreenPageLimit(tabTitles.length);
        tab.setupWithViewPager(vp);
        for (int i = 0; i < tab.getTabCount(); i++) {
            TabLayout.Tab t = tab.getTabAt(i);
            t.setCustomView(ticketPageAdapter.getTabView(i));
        }

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
        if (SpSir.getInstance().isFirstSee() && !TextUtils.isEmpty(SpSir.getInstance().getToken())) {
            GoUtil.goActivity(this, FirstDialogActivity.class);
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        moreTicketHandler.removeCallbacks(moreTicketRunnable);
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

    @Override
    public void hideLoading() {
    }

    @Override
    public void showLoading() {

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
        EventBus.getDefault().post(new TicketFilterEvent(areaId, productTypeId, useDates, buyLimit, discountOrder));
    }

    @Override
    protected void initNet() {
        if (!TextUtils.isEmpty(SpSir.getInstance().getToken())) {
            applyLocationPermission();
        }
//            if (SpSir.getInstance().hasRequirePermission()) {
//                //已经询问过
//                applyLocationPermission();
//            } else {
//                //未申请过权限
//                checkLocationPermission();
//                SpSir.getInstance().setRequirePermission(true);
//
//            }

//        }
    }

    public void applyLocationPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            //检查是否已经给了权限
            int checkpermission = ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION);
            if (checkpermission != PackageManager.PERMISSION_GRANTED) {//没有给权限
                //显示定位浮标
                ivMoreTicket.setVisibility(View.VISIBLE);
                moreTicketHandler.postDelayed(moreTicketRunnable, 10 * 1000);
            } else {
                //上传定位
                if (!DateUtil.getNowDate().equals(SpSir.getInstance().getLastLocationDate())) {
                    uploadLocationInfo();
                }

            }
        } else {
            //上传定位
            if (!DateUtil.getNowDate().equals(SpSir.getInstance().getLastLocationDate())) {
                uploadLocationInfo();
            }
        }
    }

    private Handler moreTicketHandler = new Handler();
    private Runnable moreTicketRunnable = () -> {
        ivMoreTicket.setVisibility(View.GONE);
    };

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
    public void onUploadLocationSuccess() {
        LogUtil.e(TAG, "上传成功");
        SpSir.getInstance().putLastLocationDate(DateUtil.getNowDate());
    }
}
