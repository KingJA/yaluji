package com.kingja.yaluji.page.praise.detail;

import android.support.annotation.NonNull;

import com.kingja.yaluji.model.api.UserApi;
import com.kingja.yaluji.model.entiy.LoadSirObserver;
import com.kingja.yaluji.model.entiy.PraiseDetail;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Description：TODO
 * Create Time：2016/10/10 16:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class PraiseDetailPresenter implements PraiseDetailContract.Presenter {
    private UserApi mApi;
    private PraiseDetailContract.View mView;


    @Inject
    public PraiseDetailPresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void attachView(@NonNull PraiseDetailContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

    @Override
    public void getPraiseDetail(String likeUserId) {
        mApi.getApiService().getPraiseDetail(likeUserId).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribe
                (new LoadSirObserver<PraiseDetail>(mView) {
                    @Override
                    protected void onSuccess(PraiseDetail praiseDetail) {
                        mView.onGetPraiseDetailSuccess(praiseDetail);
                    }
                });
    }
}