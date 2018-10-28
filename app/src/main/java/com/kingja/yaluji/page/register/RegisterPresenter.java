package com.kingja.yaluji.page.register;

import android.support.annotation.NonNull;


import com.kingja.yaluji.model.api.UserApi;
import com.kingja.yaluji.model.entiy.ResultObserver;
import com.kingja.yaluji.util.ToastUtil;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Description：TODO
 * Create Time：2016/10/10 16:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class RegisterPresenter implements RegisterContract.Presenter {
    private UserApi mApi;
    private RegisterContract.View mView;

    @Inject
    public RegisterPresenter(UserApi mApi) {
        this.mApi = mApi;
    }


    @Override
    public void attachView(@NonNull RegisterContract.View view) {
        this.mView=view;
    }

    @Override
    public void detachView() {

    }

    @Override
    public void register(String mobile, String password, String code) {
        mApi.getApiService().register(mobile, password,code).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe
                (new ResultObserver<Object>(mView) {
                    @Override
                    protected void onSuccess(Object msg) {
                        mView.onRegisterSuccess();
                    }
                });
    }

    @Override
    public void getCode(String mobile, int flag) {
        mApi.getApiService().sms(mobile, flag).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe
                (new ResultObserver<String>(mView) {
                    @Override
                    protected void onSuccess(String code) {
                        ToastUtil.showText("验证码已发送");
                    }
                });
    }
}