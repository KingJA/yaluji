package com.kingja.yaluji.page.ticket.success;


import com.kingja.yaluji.base.BasePresenter;
import com.kingja.yaluji.base.BaseView;
import com.kingja.yaluji.model.entiy.Ticket;

import java.util.List;

import okhttp3.RequestBody;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface TicketSuccessContract {
    interface View extends BaseView {
        void onGetRecommendListSuccess(List<Ticket> ticketList);
    }

    interface Presenter extends BasePresenter<View> {
        void getRecommendList();

    }
}
