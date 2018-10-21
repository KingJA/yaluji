package com.kingja.yaluji.page.order.list;


import com.kingja.yaluji.base.BasePresenter;
import com.kingja.yaluji.base.BaseView;
import com.kingja.yaluji.model.entiy.Order;

import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface TicketListContract {
    interface View extends BaseView {
        void onGetOrdersSuccess(List<Order> orders);
    }

    interface Presenter extends BasePresenter<View> {
        void getOrders(Integer page, Integer pageSize, Integer status);

    }
}
