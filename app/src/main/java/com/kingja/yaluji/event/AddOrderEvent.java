package com.kingja.yaluji.event;

import com.kingja.yaluji.model.entiy.Order;

/**
 * Description:TODO
 * Create Time:2018/11/11 0:50
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AddOrderEvent {
    private Order order;

    public AddOrderEvent(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
