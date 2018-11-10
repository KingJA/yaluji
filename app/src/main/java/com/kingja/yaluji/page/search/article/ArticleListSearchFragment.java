package com.kingja.yaluji.page.search.article;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;

import com.kingja.yaluji.R;
import com.kingja.yaluji.adapter.CommonAdapter;
import com.kingja.yaluji.adapter.ViewHolder;
import com.kingja.yaluji.base.BaseFragment;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.constant.Constants;
import com.kingja.yaluji.i.OnSearchListener;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.model.entiy.Article;
import com.kingja.yaluji.model.entiy.ArticleSimpleItem;
import com.kingja.yaluji.page.article.detail.ArticleDetailActivity;
import com.kingja.yaluji.view.PullToBottomListView;

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
public class ArticleListSearchFragment extends BaseFragment implements OnSearchListener,
        SwipeRefreshLayout.OnRefreshListener, ArticleListSearchContract.View {
    @BindView(R.id.plv)
    PullToBottomListView plv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    private List<ArticleSimpleItem> articleList = new ArrayList<>();
    @Inject
    ArticleListSearchPresenter articleListSearchPresenter;
    private String keyword;
    private CommonAdapter adapter;

    @OnItemClick(R.id.plv)
    public void itemClick(AdapterView<?> parent, View view, int position, long id) {
        ArticleSimpleItem article = (ArticleSimpleItem) parent.getItemAtPosition(position);
        ArticleDetailActivity.goActivity(getActivity(), article.getId());
    }

    public static ArticleListSearchFragment newInstance(String keyword) {
        ArticleListSearchFragment fragment = new ArticleListSearchFragment();
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
        articleListSearchPresenter.attachView(this);
    }

    @Override
    protected void initView() {
        adapter = new CommonAdapter<ArticleSimpleItem>(getActivity(), articleList, R.layout
                .item_article_recommend) {
            @Override
            public void convert(ViewHolder helper, ArticleSimpleItem item) {
                helper.setText(R.id.tv_articleTitle, item.getTitle());
                helper.setText(R.id.tv_createdAt, item.getCreatedAt());
                helper.setImageByUrl(R.id.iv_headimg, item.getHeadimg());
            }
        };
        plv.setAdapter(adapter);

    }

    @Override
    protected void initData() {
        srl.setOnRefreshListener(this);
    }

    @Override
    protected void initNet() {
        articleListSearchPresenter.getArticleList(new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("keyword", keyword)
                .addFormDataPart("page", String.valueOf(currentPageSize))
                .addFormDataPart("pageSize", String.valueOf(Constants.PAGE_SIZE))
                .build());
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_article_list;
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
    public void onGetArticleListSuccess(List<ArticleSimpleItem> articleList) {
        if (articleList != null && articleList.size() > 0) {
            adapter.setData(articleList);
        } else {
            showEmptyCallback();
        }
    }
}
