package com.kingja.yaluji.page.order.list;

import android.support.annotation.NonNull;


import com.kingja.yaluji.model.api.UserApi;
import com.kingja.yaluji.model.entiy.Order;
import com.kingja.yaluji.model.entiy.ResultObserver;

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
public class TicketListPresenter implements TicketListContract.Presenter {
    private UserApi mApi;
    private TicketListContract.View mView;


    @Inject
    public TicketListPresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void attachView(@NonNull TicketListContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

    @Override
    public void getOrders(Integer page, Integer pageSize, Integer status) {
        mApi.getUserService().getOrders(page, pageSize, status).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribe
                (new ResultObserver<List<Order>>(mView) {
                    @Override
                    protected void onSuccess(List<Order> orders) {
                        mView.onGetOrdersSuccess(orders);
                    }
                });
    }
}