package com.kingja.yaluji.page.business;

import android.support.annotation.NonNull;

import com.kingja.yaluji.model.api.UserApi;
import com.kingja.yaluji.model.entiy.ResultObserver;
import com.kingja.yaluji.page.feedback.FeedbackContract;

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
public class BusinessDetailPresenter implements BusinessDetailContract.Presenter {
    private UserApi mApi;
    private BusinessDetailContract.View mView;

    @Inject
    public BusinessDetailPresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void doBusiness(RequestBody requestBody) {
        mApi.getApiService().doBusiness(requestBody).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe
                (new ResultObserver<Object>(mView) {
                    @Override
                    protected void onSuccess(Object object) {
                        mView.onDoBusinessSuccess();
                    }
                });
    }


    @Override
    public void attachView(@NonNull BusinessDetailContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

}