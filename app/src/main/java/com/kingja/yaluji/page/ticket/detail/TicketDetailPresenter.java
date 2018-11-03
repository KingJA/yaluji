package com.kingja.yaluji.page.ticket.detail;

import android.support.annotation.NonNull;

import com.kingja.yaluji.model.api.UserApi;
import com.kingja.yaluji.model.entiy.OrderResult;
import com.kingja.yaluji.model.entiy.ResultObserver;
import com.kingja.yaluji.model.entiy.TicketDetail;
import com.kingja.yaluji.model.entiy.Visitor;

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
    public void getTicketDetail(String productId) {
        mApi.getApiService().getTicketDetail(productId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                .mainThread()).subscribe
                (new ResultObserver<TicketDetail>(mView) {
                    @Override
                    protected void onSuccess(TicketDetail ticketDetail) {
                        mView.onGetTicketDetailSuccess(ticketDetail);
                    }
                });
    }

    @Override
    public void getVisitors(Integer page, Integer pageSize) {
        mApi.getApiService().getVisitors(page, pageSize).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                .mainThread()).subscribe
                (new ResultObserver<List<Visitor>>(mView) {
                    @Override
                    protected void onSuccess(List<Visitor> visitors) {
                        mView.onGetVisitorsSuccess(visitors);
                    }

                    @Override
                    protected void onLoginFail() {
                        mView.onLoginFail();
                    }
                });
    }

    @Override
    public void sumbitOrder(String productId, String touristIds, int quantity, String from) {
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