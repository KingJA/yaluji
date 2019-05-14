package com.kingja.yaluji.page.praise.list;


import com.kingja.yaluji.base.BasePresenter;
import com.kingja.yaluji.base.BaseView;
import com.kingja.yaluji.model.entiy.PraiseItem;

import java.util.List;

import okhttp3.RequestBody;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface PraiseListContract {
    interface View extends BaseView {
        void onGetPraiseListSuccess(List<PraiseItem> praiseItemList);

        void onCheckPraiseSuccess(String shareUrl, PraiseItem praiseItem);
    }

    interface Presenter extends BasePresenter<View> {
        void getPraiseListByVisitor(RequestBody requestBody);

        void getPraiseList(RequestBody requestBody);

        void checkPraise(String likeId, PraiseItem praiseItem);

    }
}
