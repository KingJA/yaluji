package com.kingja.yaluji.page.article.list;


import com.kingja.yaluji.base.BasePresenter;
import com.kingja.yaluji.base.BaseView;
import com.kingja.yaluji.model.entiy.Article;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface ArticleListContract {
    interface View extends BaseView {
        void onGetArticleListSuccess(List<Article> articleList);
    }

    interface Presenter extends BasePresenter<View> {
        void getArticleList(RequestBody requestBody);

    }
}
