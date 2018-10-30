package com.kingja.yaluji.page.article.list;

import android.support.annotation.NonNull;

import com.kingja.yaluji.model.api.UserApi;
import com.kingja.yaluji.model.entiy.Article;
import com.kingja.yaluji.model.entiy.ResultObserver;
import com.kingja.yaluji.page.headimg.PersonalContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * Description：TODO
 * Create Time：2016/10/10 16:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ArticleListPresenter implements ArticleListContract.Presenter {
    private UserApi mApi;
    private ArticleListContract.View mView;

    @Inject
    public ArticleListPresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void getArticleList(RequestBody requestBody) {
        mApi.getApiService().getArticleList(requestBody).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe
                (new ResultObserver<List<Article>>(mView) {
                    @Override
                    protected void onSuccess(List<Article> articleList) {
                        mView.onGetArticleListSuccess(articleList);
                    }
                });
    }


    @Override
    public void attachView(@NonNull ArticleListContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

}