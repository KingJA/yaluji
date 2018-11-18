package com.kingja.yaluji.page.article.list;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.kingja.yaluji.R;
import com.kingja.yaluji.adapter.ArticleAdapter;
import com.kingja.yaluji.base.BaseTitleActivity;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.model.entiy.Article;
import com.kingja.yaluji.page.article.detail.ArticleDetailActivity;
import com.kingja.yaluji.view.PullToBottomListView;
import com.kingja.yaluji.view.RefreshSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import okhttp3.MultipartBody;

/**
 * Description:TODO
 * Create Time:2018/10/30 22:40
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ArticleListActivity extends BaseTitleActivity implements ArticleListContract.View, SwipeRefreshLayout
        .OnRefreshListener {
    @BindView(R.id.plv)
    PullToBottomListView plv;
    @BindView(R.id.srl)
    RefreshSwipeRefreshLayout srl;
    @BindView(R.id.iv_go_top)
    ImageView ivGoTop;
    private List<Article> articleList = new ArrayList<>();
    private ArticleAdapter articleAdapter;

    @Inject
    ArticleListPresenter articleListPresenter;

    @OnItemClick(R.id.plv)
    public void itemClick(AdapterView<?> parent, View view, int position, long id) {
        Article article = (Article) parent.getItemAtPosition(position);
        ArticleDetailActivity.goActivity(this, article.getId());
    }

    @Override
    public void initVariable() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_article_list;
    }

    @Override
    protected void initComponent(AppComponent appComponent) {
        DaggerBaseCompnent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
        articleListPresenter.attachView(this);
    }

    @Override
    protected String getContentTitle() {
        return "鸭说";
    }

    @Override
    protected void initView() {
        articleAdapter = new ArticleAdapter(this, articleList);
        plv.setAdapter(articleAdapter);
        plv.setGoTop(ivGoTop);
    }

    @Override
    protected void initData() {
        srl.setOnRefreshListener(this);
    }

    @Override
    protected void initNet() {
        articleListPresenter.getArticleList(new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("categoryId", "")
                .addFormDataPart("page", "1")
                .addFormDataPart("pageSize", "10")
                .addFormDataPart("type", "2")
                .build());
    }

    @Override
    public void onGetArticleListSuccess(List<Article> articleList) {
        if (articleList != null && articleList.size() > 0) {
            articleAdapter.setData(articleList);
        } else {
            showEmptyCallback();
        }
    }

    @Override
    public void onRefresh() {
        srl.setRefreshing(false);
        initNet();
    }
}
