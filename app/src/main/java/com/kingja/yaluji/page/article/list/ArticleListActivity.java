package com.kingja.yaluji.page.article.list;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.kingja.yaluji.R;
import com.kingja.yaluji.adapter.ArticleAdapter;
import com.kingja.yaluji.base.BaseTitleActivity;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.constant.Constants;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.model.entiy.Article;
import com.kingja.yaluji.page.article.detail.ArticleDetailActivity;
import com.kingja.yaluji.util.ToastUtil;
import com.kingja.yaluji.view.PullToMoreListView;
import com.kingja.yaluji.view.RefreshSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnItemClick;
import okhttp3.MultipartBody;

/**
 * Description:TODO
 * Create Time:2018/10/30 22:40
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ArticleListActivity extends BaseTitleActivity implements ArticleListContract.View, SwipeRefreshLayout
        .OnRefreshListener, PullToMoreListView.OnScrollToBottom {
    @BindView(R.id.plv)
    PullToMoreListView plv;
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
        plv.setOnScrollToBottom(this);
    }

    @Override
    protected void initNet() {
        callNet();
    }

    private void callNet() {
        articleListPresenter.getArticleList(new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("categoryId", "")
                .addFormDataPart("page", String.valueOf(plv.getPageIndex()))
                .addFormDataPart("pageSize", String.valueOf(Constants.PAGE_SIZE_5))
                .addFormDataPart("type", "2")
                .build());
    }

    @Override
    public void onGetArticleListSuccess(List<Article> articleList) {
        if (articleList != null && articleList.size() > 0) {
            if (plv.getPageIndex() == 1) {
                articleAdapter.setData(articleList);
            } else {
                articleAdapter.addData(articleList);
            }
        } else {
            if (plv.getPageIndex() == 1) {
                showEmptyCallback();
            } else {
                ToastUtil.showText("到底啦");
            }
        }
    }

    @Override
    public void onRefresh() {
        plv.reset();
        callNet();
    }

    @Override
    public boolean ifRegisterLoadSir() {
        return true;
    }

    @Override
    public void onScrollToBottom(int pageIndex) {
        callNet();
    }

    @Override
    public void showLoadingCallback() {
        srl.setRefreshing(true);
    }

    @Override
    public void showSuccessCallback() {
        super.showSuccessCallback();
        srl.setRefreshing(false);
    }
}
