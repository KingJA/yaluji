package com.kingja.yaluji.page.order.orderdetail.phone;

import android.support.annotation.NonNull;

import com.kingja.yaluji.model.api.UserApi;
import com.kingja.yaluji.model.entiy.LoadSirObserver;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Description：TODO
 * Create Time：2016/10/10 16:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class OrderDetailPhonePresenter implements OrderDetailPhoneContract.Presenter {
    private UserApi mApi;
    private OrderDetailPhoneContract.View mView;


    @Inject
    public OrderDetailPhonePresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void attachView(@NonNull OrderDetailPhoneContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

    @Override
    public void rechargeMobile(String orderId, String mobile) {
        mApi.getApiService().rechargeMobile(orderId, mobile).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribe
                (new LoadSirObserver<Object>(mView) {
                    @Override
                    protected void onSuccess(Object o) {
                        mView.onRechargeMobileSuccess();
                    }
                });
    }
}