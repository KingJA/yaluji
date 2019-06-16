package com.kingja.yaluji.page.order.orderdetail.phone;


import com.kingja.yaluji.base.BasePresenter;
import com.kingja.yaluji.base.BaseView;
import com.kingja.yaluji.model.entiy.OrderDetail;

import retrofit2.http.Field;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface OrderDetailPhoneContract {
    interface View extends BaseView {
        void onRechargeMobileSuccess();
    }

    interface Presenter extends BasePresenter<View> {
        void rechargeMobile(String orderId,  String mobile);

    }
}
