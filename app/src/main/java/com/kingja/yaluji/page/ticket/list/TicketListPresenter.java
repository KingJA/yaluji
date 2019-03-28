package com.kingja.yaluji.page.ticket.list;

import android.support.annotation.NonNull;

import com.kingja.yaluji.model.api.UserApi;
import com.kingja.yaluji.model.entiy.LoadSirObserver;
import com.kingja.yaluji.model.entiy.ResultObserver;
import com.kingja.yaluji.model.entiy.Ticket;
import com.kingja.yaluji.page.register.RegisterContract;
import com.kingja.yaluji.util.ToastUtil;

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
    public void getTicketList(RequestBody requestBody) {
        mApi.getApiService().getTicketList(requestBody).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                .mainThread()).subscribe
                (new ResultObserver<List<Ticket>>(mView) {
                    @Override
                    protected void onSuccess(List<Ticket> ticketList) {
                        mView.onGetTicketListSuccess(ticketList);
                    }
                });
    }

}