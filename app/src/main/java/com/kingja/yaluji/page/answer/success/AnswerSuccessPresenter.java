package com.kingja.yaluji.page.answer.success;

import android.support.annotation.NonNull;

import com.kingja.yaluji.model.api.UserApi;
import com.kingja.yaluji.model.entiy.Article;
import com.kingja.yaluji.model.entiy.Order;
import com.kingja.yaluji.model.entiy.PrefectVisitorResult;
import com.kingja.yaluji.model.entiy.ResultObserver;
import com.kingja.yaluji.page.article.list.ArticleListContract;

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
public class AnswerSuccessPresenter implements AnswerSuccessContract.Presenter {
    private UserApi mApi;
    private AnswerSuccessContract.View mView;

    @Inject
    public AnswerSuccessPresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void prefectVisitor(RequestBody requestBody) {
        mApi.getApiService().prefectVisitor(requestBody).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe
                (new ResultObserver<Order>(mView) {
                    @Override
                    protected void onSuccess(Order order) {
                        mView.onPrefectVisitorSuccess(order);
                    }
                });
    }


    @Override
    public void attachView(@NonNull AnswerSuccessContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

}