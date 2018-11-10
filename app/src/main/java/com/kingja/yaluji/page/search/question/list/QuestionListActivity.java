package com.kingja.yaluji.page.search.question.list;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.kingja.yaluji.R;
import com.kingja.yaluji.adapter.QuestionAdapter;
import com.kingja.yaluji.base.BaseTitleActivity;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.constant.Constants;
import com.kingja.yaluji.constant.Status;
import com.kingja.yaluji.event.RefreshQuestionEvent;
import com.kingja.yaluji.event.ResetLoginStatusEvent;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.model.entiy.Question;
import com.kingja.yaluji.page.answer.detail.QuestionDetailActivity;
import com.kingja.yaluji.util.LoginChecker;
import com.kingja.yaluji.util.ToastUtil;
import com.kingja.yaluji.view.PullToBottomListView;
import com.kingja.yaluji.view.dialog.QuestionExplainDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
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
    @BindView(R.id.rl_question_explain)
    RelativeLayout ivQuestionExplain;
    @BindView(R.id.plv)
    PullToBottomListView plv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    private List<Question> questionList = new ArrayList<>();
    private QuestionAdapter questionAdapter;
    private QuestionExplainDialog questionExplainDialog;

    @OnClick({R.id.rl_question_explain})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_question_explain:
                questionExplainDialog.show();
                break;
            default:
                break;
        }
    }

    @OnItemClick(R.id.plv)
    public void itemClick(AdapterView<?> parent, View view, int position, long id) {
        Question question = (Question) parent.getItemAtPosition(position);
        if (question.getUserStatus() == Status.QuestionStatus.ANSWER.getCode()) {
            QuestionDetailActivity.goActivity(this, question.getId());
        }
        if (question.getUserStatus() == Status.QuestionStatus.RELIFT.getCode()) {
            ToastUtil.showText("去复活");
        }

    }

    @Override
    public void initVariable() {
        EventBus.getDefault().register(this);

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
        srl.setRefreshing(false);
        initNet();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void resetLoginStatus(ResetLoginStatusEvent resetLoginStatusEvent) {
        initNet();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshQuestion(RefreshQuestionEvent event) {
        initNet();
    }
}
