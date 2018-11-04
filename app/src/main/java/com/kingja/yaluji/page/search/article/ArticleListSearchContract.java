package com.kingja.yaluji.page.search.article;


import com.kingja.yaluji.base.BasePresenter;
import com.kingja.yaluji.base.BaseView;
import com.kingja.yaluji.model.entiy.ArticleSimpleItem;

import java.util.List;

import okhttp3.RequestBody;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface ArticleListSearchContract {
    interface View extends BaseView {
        void onGetArticleListSuccess(List<ArticleSimpleItem> articleList);
    }

    interface Presenter extends BasePresenter<View> {
        void getArticleList(RequestBody requestBody);

    }
}
