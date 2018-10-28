package com.kingja.yaluji.page.home;


import com.kingja.yaluji.base.BasePresenter;
import com.kingja.yaluji.base.BaseView;
import com.kingja.yaluji.model.entiy.ArticleSimpleItem;
import com.kingja.yaluji.model.entiy.LunBoTu;

import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface HomeContract {
    interface View extends BaseView {
        void onGetLunBoTuListSuccess(List<LunBoTu> lunBoTuList);

        void onGetArticleListSuccess(List<ArticleSimpleItem> articleSimpleItemList);
    }

    interface Presenter extends BasePresenter<View> {
        void getLunBoTuList();

        void getArticleList();

    }
}
