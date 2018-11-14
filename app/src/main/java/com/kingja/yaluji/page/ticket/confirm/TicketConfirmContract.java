package com.kingja.yaluji.page.ticket.confirm;

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
public interface TicketConfirmContract {
    interface View extends BaseView {
        void onSumbitOrderSuccess(OrderResult orderResult);

    }

    interface Presenter extends BasePresenter<View> {
        void sumbitOrder(String productId, String touristIds, String quantity, String from);
    }
}
