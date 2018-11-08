package com.kingja.yaluji.page.ticket.success;

import android.support.annotation.NonNull;

import com.kingja.yaluji.model.api.UserApi;
import com.kingja.yaluji.model.entiy.ResultObserver;
import com.kingja.yaluji.model.entiy.Ticket;
import com.kingja.yaluji.page.ticket.list.TicketListContract;

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
public class TicketSuccessPresenter implements TicketSuccessContract.Presenter {
    private UserApi mApi;
    private TicketSuccessContract.View mView;

    @Inject
    public TicketSuccessPresenter(UserApi mApi) {
        this.mApi = mApi;
    }


    @Override
    public void attachView(@NonNull TicketSuccessContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

    @Override
    public void getRecommendList() {
        mApi.getApiService().getRecommendList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                .mainThread()).subscribe
                (new ResultObserver<List<Ticket>>(mView) {
                    @Override
                    protected void onSuccess(List<Ticket> ticketList) {
                        mView.onGetRecommendListSuccess(ticketList);
                    }
                });
    }

}