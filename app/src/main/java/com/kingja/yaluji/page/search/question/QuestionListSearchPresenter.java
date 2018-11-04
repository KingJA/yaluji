package com.kingja.yaluji.page.search.question;

import android.support.annotation.NonNull;

import com.kingja.yaluji.model.api.UserApi;
import com.kingja.yaluji.model.entiy.ArticleSimpleItem;
import com.kingja.yaluji.model.entiy.Question;
import com.kingja.yaluji.model.entiy.ResultObserver;
import com.kingja.yaluji.page.search.article.ArticleListSearchContract;

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
public class QuestionListSearchPresenter implements QuestionListSearchContract.Presenter {
    private UserApi mApi;
    private QuestionListSearchContract.View mView;

    @Inject
    public QuestionListSearchPresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void getQuestionSearchList(RequestBody requestBody) {
        mApi.getApiService().getQuestionSearchList(requestBody).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe
                (new ResultObserver<List<Question>>(mView) {
                    @Override
                    protected void onSuccess(List<Question> questionList) {
                        mView.onGetQuestionListSuccess(questionList);
                    }
                });
    }


    @Override
    public void attachView(@NonNull QuestionListSearchContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

}