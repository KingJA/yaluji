package com.kingja.yaluji.page.relife;


import com.kingja.yaluji.base.BasePresenter;
import com.kingja.yaluji.base.BaseView;

import retrofit2.http.Field;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface RelifeContract {
    interface View extends BaseView {
        void onReLifeSuccess();
    }

    interface Presenter extends BasePresenter<View> {
        void reLife(String paperId);

    }
}
