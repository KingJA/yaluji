package com.kingja.yaluji.page.article.detail;

import android.support.annotation.NonNull;

import com.kingja.yaluji.model.api.UserApi;
import com.kingja.yaluji.model.entiy.ArticleDetail;
import com.kingja.yaluji.model.entiy.ResultObserver;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Description：TODO
 * Create Time：2016/10/10 16:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ArticleDetailPresenter implements ArticleDetailContract.Presenter {
    private UserApi mApi;
    private ArticleDetailContract.View mView;

    @Inject
    public ArticleDetailPresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void getArticleDetail(String articleId) {
        mApi.getApiService().getArticleDetail(articleId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe
                (new ResultObserver<ArticleDetail>(mView) {
                    @Override
                    protected void onSuccess(ArticleDetail articleDetail) {
                        mView.onGetArticleDetailSuccess(articleDetail);
                    }
                });
    }


    @Override
    public void attachView(@NonNull ArticleDetailContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

}