package com.kingja.yaluji.page.nickname;


import com.kingja.yaluji.base.BasePresenter;
import com.kingja.yaluji.base.BaseView;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface ModifyNicknameContract {
    interface View extends BaseView {
        void onModifyNicknameSuccess();
    }

    interface Presenter extends BasePresenter<View> {
        void modifyNickname(String nickname);

    }
}
