package com.kingja.yaluji.page.forgetpassword;

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
public class ForgetPasswordPresenter implements ForgetPasswordContract.Presenter {
    private UserApi mApi;
    private ForgetPasswordContract.View mView;

    @Inject
    public ForgetPasswordPresenter(UserApi mApi) {
        this.mApi = mApi;
    }


    @Override
    public void attachView(@NonNull ForgetPasswordContract.View view) {
        this.mView=view;
    }

    @Override
    public void detachView() {

    }

    @Override
    public void modifyPassword(String mobile, String password, String code) {
        mApi.getApiService().forgetPassword(mobile, password,code).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe
                (new ResultObserver<Object>(mView) {
                    @Override
                    protected void onSuccess(Object object) {
                        mView.onModifyPasswordSuccess();
                    }
                });
    }

    @Override
    public void getCode(String mobile, int flag) {
        mApi.getApiService().sms(mobile, flag).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe
                (new ResultObserver<String>(mView) {
                    @Override
                    protected void onSuccess(String code) {
                        mView.onGetCodeSuccess(code);
                    }
                });
    }
}