package com.kingja.yaluji.page.relife;

import android.support.annotation.NonNull;

import com.kingja.yaluji.model.api.UserApi;
import com.kingja.yaluji.model.entiy.ResultObserver;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Description：TODO
 * Create Time：2016/10/10 16:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class RelifePresenter implements RelifeContract.Presenter {
    private UserApi mApi;
    private RelifeContract.View mView;

    @Inject
    public RelifePresenter(UserApi mApi) {
        this.mApi = mApi;
    }


    @Override
    public void attachView(@NonNull RelifeContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

    @Override
    public void reLife(String paperId) {
        mApi.getApiService().reLife(paperId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe
                (new ResultObserver<Object>(mView) {
                    @Override
                    protected void onSuccess(Object msg) {
                        mView.onReLifeSuccess();
                    }
                });
    }
}