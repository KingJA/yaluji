package com.kingja.yaluji.page.answer.success;


import com.kingja.yaluji.base.BasePresenter;
import com.kingja.yaluji.base.BaseView;
import com.kingja.yaluji.model.entiy.Order;
import com.kingja.yaluji.model.entiy.PrefectVisitorResult;

import okhttp3.RequestBody;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface AnswerSuccessContract {
    interface View extends BaseView {
        void onPrefectVisitorSuccess(Order order);
    }

    interface Presenter extends BasePresenter<View> {
        void prefectVisitor(RequestBody requestBody);
    }
}
