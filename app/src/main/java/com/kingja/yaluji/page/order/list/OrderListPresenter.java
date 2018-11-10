package com.kingja.yaluji.page.order.list;

import android.support.annotation.NonNull;

import com.kingja.yaluji.model.api.UserApi;
import com.kingja.yaluji.model.entiy.ResultObserver;
import com.kingja.yaluji.model.entiy.Order;

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
public class OrderListPresenter implements OrderListContract.Presenter {
    private UserApi mApi;
    private OrderListContract.View mView;


    @Inject
    public OrderListPresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void attachView(@NonNull OrderListContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

    @Override
    public void getTicketList(Integer page, Integer pageSize, Integer status) {
        mApi.getApiService().getOrders(page, pageSize, status).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribe
                (new ResultObserver<List<Order>>(mView) {
                    @Override
                    protected void onSuccess(List<Order> orderList) {
                        mView.onGetTicketListSuccess(orderList);
                    }
                });
    }

    @Override
    public void deleteOrder(int position, String orderId) {
        mApi.getApiService().deleteOrder(orderId).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribe
                (new ResultObserver<Object>(mView) {
                    @Override
                    protected void onSuccess(Object object) {
                        mView.onDeleteOrderSuccess(position);
                    }
                });
    }
}