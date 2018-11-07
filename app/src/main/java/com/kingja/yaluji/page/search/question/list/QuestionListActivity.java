package com.kingja.yaluji.page.search.question.list;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.kingja.yaluji.R;
import com.kingja.yaluji.activity.SearchDetailActivity;
import com.kingja.yaluji.adapter.QuestionAdapter;
import com.kingja.yaluji.base.BaseTitleActivity;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.constant.Constants;
import com.kingja.yaluji.event.ResetLoginStatusEvent;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.model.entiy.Question;
import com.kingja.yaluji.model.entiy.Ticket;
import com.kingja.yaluji.page.answer.QuestionDetailActivity;
import com.kingja.yaluji.page.ticket.detail.TicketDetailActivity;
import com.kingja.yaluji.util.GoUtil;
import com.kingja.yaluji.util.LoginChecker;
import com.kingja.yaluji.view.PullToBottomListView;
import com.kingja.yaluji.view.dialog.QuestionExplainDialog;
import com.kingja.yaluji.view.dialog.QuestionSuccessDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import okhttp3.MultipartBody;

/**
 * Description:TODO
 * Create Time:2018/11/6 22:44
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class QuestionListActivity extends BaseTitleActivity implements QuestionListContract.View, SwipeRefreshLayout
        .OnRefreshListener {
    @Inject
    QuestionListPresenter questionListPresenter;
    @BindView(R.id.iv_question_explain)
    ImageView ivQuestionExplain;
    @BindView(R.id.plv)
    PullToBottomListView plv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    private List<Question> questionList = new ArrayList<>();
    private QuestionAdapter questionAdapter;
    private QuestionExplainDialog questionExplainDialog;

    @OnClick({R.id.iv_question_explain})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_question_explain:
                questionExplainDialog.show();
//                new QuestionSuccessDialog(this).show();
                break;
            default:
                break;
        }
    }

    @OnItemClick(R.id.plv)
    public void itemClick(AdapterView<?> parent, View view, int position, long id) {
        Question question = (Question) parent.getItemAtPosition(position);
        QuestionDetailActivity.goActivity(this,question.getId());
    }

    @Override
    public void initVariable() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_question;
    }

    @Override
    protected void initComponent(AppComponent appComponent) {
        DaggerBaseCompnent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
        questionListPresenter.attachView(this);
    }

    @Override
    protected String getContentTitle() {
        return "鸡答";
    }

    @Override
    protected void initView() {
        questionAdapter = new QuestionAdapter(this, questionList);

        plv.setAdapter(questionAdapter);
    }

    @Override
    protected void initData() {
        questionExplainDialog = new QuestionExplainDialog(this);

        srl.setOnRefreshListener(this);
    }

    private int currentPageSize = 1;

    @Override
    protected void initNet() {
        if (LoginChecker.isLogin()) {
            questionListPresenter.getQuestionList(new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("page", String.valueOf(currentPageSize))
                    .addFormDataPart("pageSize", String.valueOf(Constants.PAGE_SIZE))
                    .build());
        } else {
            showUnLoginCallback();
        }
    }

    @Override
    public void onGetQuestionListSuccess(List<Question> questionList) {
        if (questionList != null && questionList.size() > 0) {
            questionAdapter.setData(questionList);
        } else {
            showEmptyCallback();
        }
    }

    @Override
    public boolean ifRegisterLoadSir() {
        return true;
    }

    @Override
    public void onRefresh() {
        initNet();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void resetLoginStatus(ResetLoginStatusEvent resetLoginStatusEvent) {
        initNet();
    }
}
