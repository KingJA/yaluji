package com.kingja.yaluji.page.praise;


import com.kingja.yaluji.base.BasePresenter;
import com.kingja.yaluji.base.BaseView;
import com.kingja.yaluji.model.entiy.PraiseItem;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Field;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface PraiseContract {
    interface View extends BaseView {

        void onCheckPraiseSuccess(String shareUrl, PraiseItem praiseItem);
        void onPraiseSuccess();
    }

    interface Presenter extends BasePresenter<View> {


        void checkPraise(String likeId, PraiseItem praiseItem);

        void onPraiseSuccess(String likeId);

    }
}
