package com.kingja.yaluji.page.answer.detail;


import com.kingja.yaluji.base.BasePresenter;
import com.kingja.yaluji.base.BaseView;
import com.kingja.yaluji.model.entiy.AnswerResult;
import com.kingja.yaluji.model.entiy.QuestionDetail;

import okhttp3.RequestBody;
import retrofit2.http.Body;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface QuestionDetailContract {
    interface View extends BaseView {
        void onGetQuestionDetailSuccess(QuestionDetail questionDetail);

        void onSubmitAnswerSuccess(AnswerResult answerResult);
    }

    interface Presenter extends BasePresenter<View> {
        void getQuestionDetail(String paperId);
        void submitAnswer(RequestBody requestBody);

    }
}
