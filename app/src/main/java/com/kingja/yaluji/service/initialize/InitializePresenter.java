package com.kingja.yaluji.service.initialize;

import android.support.annotation.NonNull;


import com.kingja.yaluji.model.api.UserApi;
import com.kingja.yaluji.model.entiy.City;
import com.kingja.yaluji.model.entiy.HotSearch;
import com.kingja.yaluji.model.entiy.ResultObserver;
import com.kingja.yaluji.model.entiy.ScenicType;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Description：TODO
 * Create Time：2016/10/10 16:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class InitializePresenter implements InitializeContract.Presenter {
    private UserApi mApi;
    private InitializeContract.View mView;

    @Inject
    public InitializePresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void attachView(@NonNull InitializeContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

    @Override
    public void getHotSearch(int limit) {
        mApi.getApiService().getHotSearch(limit).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                .mainThread()).subscribe
                (new ResultObserver<List<HotSearch>>(mView) {
                    @Override
                    protected void onSuccess(List<HotSearch> hotSearches) {
                        mView.onGetHotSearch(hotSearches);
                    }
                });
    }

    @Override
    public void getScenicType(String categoryId) {
        mApi.getApiService().getScenicType(categoryId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                .mainThread()).subscribe
                (new ResultObserver<List<ScenicType>>(mView) {
                    @Override
                    protected void onSuccess(List<ScenicType> scenicTypes) {
                        mView.onGetScenicTypeSuccess(scenicTypes);
                    }
                });
    }

    @Override
    public void getCity() {
        mApi.getApiService().getCity().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                .mainThread()).subscribe
                (new ResultObserver<List<City>>(mView) {
                    @Override
                    protected void onSuccess(List<City> cities) {
                        mView.onGetCitySuccess(cities);
                    }
                });
    }


}