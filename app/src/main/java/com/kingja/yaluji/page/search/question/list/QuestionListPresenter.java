package com.kingja.yaluji.page.search.question.list;

import android.support.annotation.NonNull;

import com.kingja.yaluji.model.api.UserApi;
import com.kingja.yaluji.model.entiy.Question;
import com.kingja.yaluji.model.entiy.ResultObserver;
import com.kingja.yaluji.page.search.question.search.QuestionListSearchContract;

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
public class QuestionListPresenter implements QuestionListContract.Presenter {
    private UserApi mApi;
    private QuestionListContract.View mView;

    @Inject
    public QuestionListPresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void getQuestionList(RequestBody requestBody) {
        mApi.getApiService().getQuestionList(requestBody).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe
                (new ResultObserver<List<Question>>(mView) {
                    @Override
                    protected void onSuccess(List<Question> questionList) {
                        mView.onGetQuestionListSuccess(questionList);
                    }
                });
    }


    @Override
    public void attachView(@NonNull QuestionListContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

}