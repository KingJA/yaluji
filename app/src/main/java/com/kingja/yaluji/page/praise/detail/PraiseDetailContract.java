package com.kingja.yaluji.page.praise.detail;


import com.kingja.yaluji.base.BasePresenter;
import com.kingja.yaluji.base.BaseView;
import com.kingja.yaluji.model.entiy.PraiseDetail;
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
public interface PraiseDetailContract {
    interface View extends BaseView {
        void onGetPraiseDetailSuccess(PraiseDetail praiseDetail);

    }

    interface Presenter extends BasePresenter<View> {
        void getPraiseDetail(String likeUserId);

    }
}
