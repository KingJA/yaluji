package com.kingja.yaluji.page.feedback;


import com.kingja.yaluji.base.BasePresenter;
import com.kingja.yaluji.base.BaseView;

import okhttp3.RequestBody;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface FeedbackContract {
    interface View extends BaseView {
        void onSendFeedbackSuccess();
    }

    interface Presenter extends BasePresenter<View> {
        void sendFeedback(RequestBody requestBody);
        void sendNoImgFeedback(RequestBody requestBody);

    }
}
