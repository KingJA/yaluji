package com.kingja.yaluji.page.register;


import com.kingja.yaluji.base.BasePresenter;
import com.kingja.yaluji.base.BaseView;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface RegisterContract {
    interface View extends BaseView {
        void onRegisterSuccess();

        void onGetCodeSuccess(String code);
    }

    interface Presenter extends BasePresenter<View> {
        void register(String mobile, String password, String code);

        void getCode(String mobile, int flag);

    }
}
