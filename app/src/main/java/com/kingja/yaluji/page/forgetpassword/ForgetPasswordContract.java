package com.kingja.yaluji.page.forgetpassword;


import com.kingja.yaluji.base.BasePresenter;
import com.kingja.yaluji.base.BaseView;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface ForgetPasswordContract {
    interface View extends BaseView {
        void onModifyPasswordSuccess();
        void onGetCodeSuccess(String code);
    }

    interface Presenter extends BasePresenter<View> {
        void modifyPassword(String mobile, String password, String code);

        void getCode(String mobile, int flag);

    }
}
