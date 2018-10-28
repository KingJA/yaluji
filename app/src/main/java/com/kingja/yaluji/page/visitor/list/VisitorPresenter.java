package com.kingja.yaluji.page.visitor.list;

import android.support.annotation.NonNull;


import com.kingja.yaluji.model.api.UserApi;
import com.kingja.yaluji.model.entiy.ResultObserver;
import com.kingja.yaluji.model.entiy.Visitor;

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
public class VisitorPresenter implements VisitorContract.Presenter {
    private UserApi mApi;
    private VisitorContract.View mView;

    @Inject
    public VisitorPresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void attachView(@NonNull VisitorContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

    @Override
    public void getVisitors(Integer page, Integer pageSize) {
        mApi.getApiService().getVisitors(page, pageSize).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                .mainThread()).subscribe
                (new ResultObserver<List<Visitor>>(mView) {
                    @Override
                    protected void onSuccess(List<Visitor> visitors) {
                        mView.onGetVisitorsSuccess(visitors);
                    }
                });
    }

    @Override
    public void deleteVisitor(String touristId, int position) {
        mApi.getApiService().deleteVisitor(touristId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                .mainThread()).subscribe
                (new ResultObserver<Object>(mView) {
                    @Override
                    protected void onSuccess(Object obj) {
                        mView.onDeleteVisitorSuccess(position);
                    }
                });
    }

    @Override
    public void defaultVisitor(String touristId, int position) {
        mApi.getApiService().defaultVisitor(touristId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                .mainThread()).subscribe
                (new ResultObserver<Object>(mView) {
                    @Override
                    protected void onSuccess(Object obj) {
                        mView.onDefaultVisitorSuccess(position);
                    }
                });
    }
}