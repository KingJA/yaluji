package com.kingja.yaluji.page.business;


import com.kingja.yaluji.base.BasePresenter;
import com.kingja.yaluji.base.BaseView;

import okhttp3.RequestBody;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface BusinessDetailContract {
    interface View extends BaseView {
        void onDoBusinessSuccess();
    }

    interface Presenter extends BasePresenter<View> {
        void doBusiness(RequestBody requestBody);

    }
}
