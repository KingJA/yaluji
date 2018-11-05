package com.kingja.yaluji.service.initialize;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.kingja.yaluji.base.App;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.model.entiy.City;
import com.kingja.yaluji.model.entiy.HotSearch;
import com.kingja.yaluji.model.entiy.ScenicType;
import com.kingja.yaluji.util.LogUtil;
import com.kingja.yaluji.util.SpSir;

import java.util.List;

import javax.inject.Inject;

/**
 * Description:TODO
 * Create Time:2018/7/10 13:10
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class InitializeService extends IntentService implements InitializeContract.View {

    private static final String TAG = "InitializeService";
    @Inject
    InitializePresenter initializePresenter;

    public InitializeService(String name) {
        super(name);
    }

    public InitializeService() {
        super("InitializeService");

    }

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerBaseCompnent.builder()
                .appComponent(App.getContext().getAppComponent())
                .build()
                .inject(this);
        initializePresenter.attachView(this);
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        initializePresenter.getHotSearch(10);
        initializePresenter.getScenicType("3");
        initializePresenter.getCity();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e(TAG, "【数据初始化结束】");
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onGetHotSearch(List<HotSearch> hotSearches) {
        SpSir.getInstance().putHotSearch(new Gson().toJson(hotSearches));
    }

    @Override
    public void onGetScenicTypeSuccess(List<ScenicType> scenicTypes) {
        SpSir.getInstance().putScenicType(new Gson().toJson(scenicTypes));
    }

    @Override
    public void onGetCitySuccess(List<City> cities) {
        SpSir.getInstance().putCity(new Gson().toJson(cities));
    }
}
