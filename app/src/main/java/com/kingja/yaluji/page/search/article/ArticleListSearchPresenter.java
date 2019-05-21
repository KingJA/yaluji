package com.kingja.yaluji.page.search.article;

import android.support.annotation.NonNull;

import com.kingja.yaluji.model.api.UserApi;
import com.kingja.yaluji.model.entiy.ArticleSimpleItem;
import com.kingja.yaluji.model.entiy.LoadSirObserver;
import com.kingja.yaluji.model.entiy.ResultObserver;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;


/**
 * Description：TODO
 * Create Time：2016/10/10 16:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ArticleListSearchPresenter implements ArticleListSearchContract.Presenter {
    private UserApi mApi;
    private ArticleListSearchContract.View mView;

    @Inject
    public ArticleListSearchPresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void getArticleList(RequestBody requestBody) {
        mApi.getApiService().getArticleSearchList(requestBody).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe
                (new LoadSirObserver<List<ArticleSimpleItem>>(mView) {
                    @Override
                    protected void onSuccess(List<ArticleSimpleItem> articleList) {
                        mView.onGetArticleListSuccess(articleList);
                    }
                });
    }


    @Override
    public void attachView(@NonNull ArticleListSearchContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

}