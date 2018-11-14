package com.kingja.yaluji.page.ticket.confirm;

import android.support.annotation.NonNull;

import com.kingja.yaluji.model.api.UserApi;
import com.kingja.yaluji.model.entiy.OrderResult;
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
public class TicketConfirmPresenter implements TicketConfirmContract.Presenter {
    private UserApi mApi;
    private TicketConfirmContract.View mView;

    @Inject
    public TicketConfirmPresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void attachView(@NonNull TicketConfirmContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }
    @Override
    public void sumbitOrder(String productId, String touristIds, String quantity, String from) {
        mApi.getApiService().sumbitOrder(productId,  touristIds,  quantity,  from).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                .mainThread()).subscribe
                (new ResultObserver<OrderResult>(mView) {
                    @Override
                    protected void onSuccess(OrderResult orderResult) {
                        mView.onSumbitOrderSuccess(orderResult);
                    }

                });
    }
}