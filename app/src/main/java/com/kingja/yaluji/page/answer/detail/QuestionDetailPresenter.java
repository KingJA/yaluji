package com.kingja.yaluji.page.answer.detail;

import android.support.annotation.NonNull;

import com.kingja.yaluji.model.api.UserApi;
import com.kingja.yaluji.model.entiy.AnswerResult;
import com.kingja.yaluji.model.entiy.LoadSirObserver;
import com.kingja.yaluji.model.entiy.QuestionDetail;
import com.kingja.yaluji.model.entiy.ResultObserver;

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
public class QuestionDetailPresenter implements QuestionDetailContract.Presenter {
    private UserApi mApi;
    private QuestionDetailContract.View mView;

    @Inject
    public QuestionDetailPresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void getQuestionDetail(String paperId) {
        mApi.getApiService().getQuestionDetail(paperId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe
                (new LoadSirObserver<QuestionDetail>(mView) {
                    @Override
                    protected void onSuccess(QuestionDetail questionDetail) {
                        mView.onGetQuestionDetailSuccess(questionDetail);
                    }
                });
    }

    @Override
    public void getNewQuestionDetail(String paperId) {
        mApi.getApiService().getQuestionDetail(paperId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe
                (new ResultObserver<QuestionDetail>(mView) {
                    @Override
                    protected void onSuccess(QuestionDetail questionDetail) {
                        mView.onGetQuestionDetailSuccess(questionDetail);
                    }
                });
    }

    @Override
    public void submitAnswer(RequestBody requestBody) {
        mApi.getApiService().submitAnswer(requestBody).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe
                (new ResultObserver<AnswerResult>(mView) {
                    @Override
                    protected void onSuccess(AnswerResult answerResult) {
                        mView.onSubmitAnswerSuccess(answerResult);
                    }
                });
    }


    @Override
    public void attachView(@NonNull QuestionDetailContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

}