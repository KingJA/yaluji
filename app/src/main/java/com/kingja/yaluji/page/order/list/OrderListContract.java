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
public interface OrderListContract {
    interface View extends BaseView {
        void onGetTicketListSuccess(List<Order> orders);
        void onDeleteOrderSuccess(int position);
    }

    interface Presenter extends BasePresenter<View> {
        void getTicketList(Integer page, Integer pageSize, Integer status);
        void deleteOrder(int position,String orderId);

    }
}
