package com.kingja.yaluji.page.praise.list;

import android.support.annotation.NonNull;

import com.kingja.yaluji.model.api.UserApi;
import com.kingja.yaluji.model.entiy.LoadSirObserver;
import com.kingja.yaluji.model.entiy.PraiseItem;
import com.kingja.yaluji.model.entiy.ResultObserver;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;


/**
 * Description：TODO
 * Create Time：2016/10/10 16:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class PraiseListPresenter implements PraiseListContract.Presenter {
    private UserApi mApi;
    private PraiseListContract.View mView;


    @Inject
    public PraiseListPresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void attachView(@NonNull PraiseListContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

    @Override
    public void getPraiseListByVisitor(RequestBody requestBody) {
        mApi.getApiService().getPraiseListByVisitor(requestBody).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribe
                (new LoadSirObserver<List<PraiseItem>>(mView) {
                    @Override
                    protected void onSuccess(List<PraiseItem> praiseItemList) {
                        mView.onGetPraiseListSuccess(praiseItemList);
                    }
                });
    }

    @Override
    public void getPraiseList(RequestBody requestBody) {
        mApi.getApiService().getPraiseList(requestBody).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribe
                (new LoadSirObserver<List<PraiseItem>>(mView) {
                    @Override
                    protected void onSuccess(List<PraiseItem> praiseItemList) {
                        mView.onGetPraiseListSuccess(praiseItemList);
                    }
                });
    }

    @Override
    public void checkPraise(String likeId, PraiseItem praiseItem) {
        mApi.getApiService().checkPraise(likeId).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribe
                (new ResultObserver<String>(mView) {
                    @Override
                    protected void onSuccess(String shareUrl) {
                        mView.onCheckPraiseSuccess(shareUrl,praiseItem);
                    }
                });
    }
}