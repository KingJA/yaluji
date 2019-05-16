package com.kingja.yaluji.page.praise;

import android.support.annotation.NonNull;

import com.kingja.yaluji.model.api.UserApi;
import com.kingja.yaluji.model.entiy.LoadSirObserver;
import com.kingja.yaluji.model.entiy.PraiseItem;
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
public class PraisePresenter implements PraiseContract.Presenter {
    private UserApi mApi;
    private PraiseContract.View mView;


    @Inject
    public PraisePresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void attachView(@NonNull PraiseContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

    @Override
    public void checkPraise(String likeId, PraiseItem praiseItem) {
        mApi.getApiService().checkPraise(likeId).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribe
                (new LoadSirObserver<String>(mView) {
                    @Override
                    protected void onSuccess(String shareUrl) {
                        mView.onCheckPraiseSuccess(shareUrl, praiseItem);
                    }
                });
    }

    @Override
    public void onPraiseSuccess(String likeId) {
        mApi.getApiService().onPraiseSuccess(likeId).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribe
                (new LoadSirObserver<String>(mView) {
                    @Override
                    protected void onSuccess(String shareUrl) {
                        mView.onPraiseSuccess();
                    }
                });
    }
}