package com.kingja.yaluji.page.search.question.search;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;

import com.kingja.yaluji.R;
import com.kingja.yaluji.adapter.QuestionAdapter;
import com.kingja.yaluji.base.BaseFragment;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.constant.Constants;
import com.kingja.yaluji.constant.Status;
import com.kingja.yaluji.i.OnSearchListener;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.model.entiy.Question;
import com.kingja.yaluji.page.answer.detail.QuestionDetailActivity;
import com.kingja.yaluji.view.PullToBottomListView;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnItemClick;
import okhttp3.MultipartBody;

/**
 * Description:TODO
 * Create Time:2018/11/4 15:49
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class QuestionListSearchFragment extends BaseFragment implements OnSearchListener,
        SwipeRefreshLayout.OnRefreshListener, QuestionListSearchContract.View {
    @BindView(R.id.plv)
    PullToBottomListView plv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    private List<Question> questionList = new ArrayList<>();
    @Inject
    QuestionListSearchPresenter questionListSearchPresenter;
    private String keyword;
    private QuestionAdapter questionAdapter;

    @OnItemClick(R.id.plv)
    public void itemClick(AdapterView<?> parent, View view, int position, long id) {
        Question question = (Question) parent.getItemAtPosition(position);
        QuestionDetailActivity.goActivity(getActivity(), question.getId());
    }

    public static QuestionListSearchFragment newInstance(String keyword) {
        QuestionListSearchFragment fragment = new QuestionListSearchFragment();
        Bundle args = new Bundle();
        args.putString(Constants.Extra.Keyword, keyword);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initVariable() {
        if (getArguments() != null) {
            keyword = getArguments().getString(Constants.Extra.Keyword, "");
        }
    }

    @Override
    protected void initComponent(AppComponent appComponent) {
        DaggerBaseCompnent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
        questionListSearchPresenter.attachView(this);
    }

    @Override
    protected void initView() {
        questionAdapter = new QuestionAdapter(getActivity(), questionList);
        plv.setAdapter(questionAdapter);

    }

    @Override
    protected void initData() {
        srl.setOnRefreshListener(this);
    }

    @Override
    protected void initNet() {
        questionListSearchPresenter.getQuestionSearchList(new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("keyword", keyword)
                .addFormDataPart("page", String.valueOf(currentPageSize))
                .addFormDataPart("pageSize", String.valueOf(Constants.PAGE_SIZE))
                .build());
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_question_list;
    }

    @Override
    public void search(String keyword) {
        this.keyword = keyword;
        initNet();
    }

    private int currentPageSize = 1;


    @Override
    public void onRefresh() {
        srl.setRefreshing(false);
    }

    @Override
    public boolean ifRegisterLoadSir() {
        return true;
    }

    @Override
    public void onGetQuestionListSuccess(List<Question> questionList) {
        if (questionList != null && questionList.size() > 0) {
            questionAdapter.setData(questionList);
        } else {
            showEmptyCallback();
        }
    }
}
