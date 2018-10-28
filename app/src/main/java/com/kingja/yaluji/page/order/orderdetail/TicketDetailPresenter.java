package com.kingja.yaluji.page.order.orderdetail;

import android.support.annotation.NonNull;

import com.kingja.yaluji.model.api.UserApi;
import com.kingja.yaluji.model.entiy.OrderDetail;
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
public class TicketDetailPresenter implements TicketDetailContract.Presenter {
    private UserApi mApi;
    private TicketDetailContract.View mView;


    @Inject
    public TicketDetailPresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void attachView(@NonNull TicketDetailContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

    @Override
    public void getOrderDetail(String orderId) {
        mApi.getApiService().getOrderDetail(orderId).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribe
                (new ResultObserver<OrderDetail>(mView) {
                    @Override
                    protected void onSuccess(OrderDetail orderDetail) {
                        mView.onGetOrderDetailSuccess(orderDetail);
                    }
                });
    }
}