package com.kingja.yaluji.page.visitor.single;

import android.support.annotation.NonNull;

import com.kingja.yaluji.model.api.UserApi;
import com.kingja.yaluji.model.entiy.ResultObserver;
import com.kingja.yaluji.model.entiy.Visitor;
import com.kingja.yaluji.page.visitor.list.VisitorContract;

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
public class VisitorSinglePresenter implements VisitorSingleContract.Presenter {
    private UserApi mApi;
    private VisitorSingleContract.View mView;

    @Inject
    public VisitorSinglePresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void attachView(@NonNull VisitorSingleContract.View view) {
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

}