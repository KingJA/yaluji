package com.kingja.yaluji.page.search.question.search;


import com.kingja.yaluji.base.BasePresenter;
import com.kingja.yaluji.base.BaseView;
import com.kingja.yaluji.model.entiy.ArticleSimpleItem;
import com.kingja.yaluji.model.entiy.Question;

import java.util.List;

import okhttp3.RequestBody;

/**
 * Description：TODO
 * Create Time：2016/10/10 14:38
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface QuestionListSearchContract {
    interface View extends BaseView {
        void onGetQuestionListSuccess(List<Question> questionList);
    }

    interface Presenter extends BasePresenter<View> {
        void getQuestionSearchList(RequestBody requestBody);

    }
}
