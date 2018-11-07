package com.kingja.yaluji.page.answer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.kingja.yaluji.R;
import com.kingja.yaluji.adapter.AnswerAdapter;
import com.kingja.yaluji.base.BaseTitleActivity;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.constant.Constants;
import com.kingja.yaluji.constant.Status;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.model.entiy.Answer;
import com.kingja.yaluji.model.entiy.AnswerResult;
import com.kingja.yaluji.model.entiy.QuestionDetail;
import com.kingja.yaluji.util.ToastUtil;
import com.kingja.yaluji.view.FixedListView;
import com.kingja.yaluji.view.StringTextView;
import com.kingja.yaluji.view.dialog.QuestionFailDialog;
import com.kingja.yaluji.view.dialog.QuestionSuccessDialog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnItemClick;
import okhttp3.MultipartBody;

/**
 * Description:TODO
 * Create Time:2018/11/7 23:44
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class QuestionDetailActivity extends BaseTitleActivity implements QuestionDetailContract.View {

    @BindView(R.id.tv_paperTitle)
    StringTextView tvPaperTitle;
    @BindView(R.id.tv_questionTitle)
    StringTextView tvQuestionTitle;
    @BindView(R.id.flv)
    FixedListView flv;
    @BindView(R.id.tv_currentPosition)
    StringTextView tvCurrentPosition;
    @BindView(R.id.tv_totalCount)
    StringTextView tvTotalCount;
    private String paperId;
    @Inject
    QuestionDetailPresenter questionDetailPresenter;
    private List<Answer> answerList = new ArrayList<>();
    private AnswerAdapter answerAdapter;
    private QuestionFailDialog failDialog;
    private QuestionSuccessDialog successDialog;


    @OnItemClick(R.id.flv)
    public void itemClick(AdapterView<?> parent, View view, int position, long id) {
        Answer answer = (Answer) parent.getItemAtPosition(position);
        questionDetailPresenter.submitAnswer(new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("questionId", answer.getQuestionId())
                .addFormDataPart("itemId", answer.getId())
                .addFormDataPart("from", "android")
                .build());

    }

    @Override
    public void initVariable() {
        paperId = getIntent().getStringExtra(Constants.Extra.PaperId);

    }


    @Override
    protected int getContentView() {
        return R.layout.activity_question_detail;
    }

    @Override
    protected void initComponent(AppComponent appComponent) {
        DaggerBaseCompnent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
        questionDetailPresenter.attachView(this);
    }

    @Override
    protected String getContentTitle() {
        return "鸡答";
    }

    @Override
    protected void initView() {
        answerAdapter = new AnswerAdapter(this, answerList);
        flv.setAdapter(answerAdapter);

    }

    @Override
    protected void initData() {
        failDialog = new QuestionFailDialog(this);
        failDialog.setOnCancelListener(dialogInterface -> {
            finish();
            //刷新问题列表
            ToastUtil.showText("刷新问题列表");

        });
        successDialog = new QuestionSuccessDialog(this);
        successDialog.setOnCancelListener(dialogInterface -> {
            finish();
            //刷新问题列表
            ToastUtil.showText("刷新问题列表");

        });

    }

    @Override
    protected void initNet() {
        questionDetailPresenter.getQuestionDetail(paperId);
    }

    public static void goActivity(Context context, String paperId) {
        Intent intent = new Intent(context, QuestionDetailActivity.class);
        intent.putExtra(Constants.Extra.PaperId, paperId);
        context.startActivity(intent);
    }

    @Override
    public void onGetQuestionDetailSuccess(QuestionDetail questionDetail) {
        if (questionDetail != null) {
            QuestionDetail.PaperQuestion paperQuestion = questionDetail.getPaperQuestion();
            if (paperQuestion != null) {
                tvQuestionTitle.setString(String.format("%d.%s", questionDetail.getQuestionCount(), paperQuestion
                        .getTitle()));
            }
            List<Answer> questionItems = questionDetail.getQuestionItems();
            if (questionItems != null && questionItems.size() > 0) {
                answerAdapter.setData(questionItems);
            }
            tvCurrentPosition.setString(questionDetail.getQuestionCount());
            tvTotalCount.setString(questionDetail.getCorrectCount());
        }

    }

    @Override
    public void onSubmitAnswerSuccess(AnswerResult answerResult) {
        switch (answerResult.getCorrectStatus()) {
            case Status.AnswerResult.RIGHT:
                questionDetailPresenter.getQuestionDetail(paperId);
                break;
            case Status.AnswerResult.WRONG:
                failDialog.show();
                break;
            case Status.AnswerResult.SUCCESS:
                successDialog.show();
                break;
        }

    }

}
