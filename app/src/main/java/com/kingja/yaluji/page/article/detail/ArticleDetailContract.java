package com.kingja.yaluji.page.article.detail;


import com.kingja.yaluji.base.BasePresenter;
import com.kingja.yaluji.base.BaseView;
import com.kingja.yaluji.model.entiy.Article;
import com.kingja.yaluji.model.entiy.ArticleDetail;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Field;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface ArticleDetailContract {
    interface View extends BaseView {
        void onGetArticleDetailSuccess(ArticleDetail articleDetail);
    }

    interface Presenter extends BasePresenter<View> {
        void getArticleDetail(String articleId);

    }
}
