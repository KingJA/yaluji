package com.kingja.yaluji.page.ticket.detail;

import com.kingja.yaluji.base.BasePresenter;
import com.kingja.yaluji.base.BaseView;
import com.kingja.yaluji.model.entiy.OrderResult;
import com.kingja.yaluji.model.entiy.TicketDetail;
import com.kingja.yaluji.model.entiy.Visitor;

import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface TicketDetailContract {
    interface View extends BaseView {
        void onGetTicketDetailSuccess(TicketDetail ticketDetail);

        void onGetVisitorsSuccess(List<Visitor> visitors);

        void onSumbitOrderSuccess(OrderResult orderResult);

        void onLoginFail();
    }

    interface Presenter extends BasePresenter<View> {
        void getTicketDetail(String productId);

        void getVisitors(Integer page, Integer pageSize);

        void sumbitOrder(String productId, String touristIds, int quantity, String from);
    }
}
