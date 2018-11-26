package com.kingja.yaluji.page.article.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.kingja.yaluji.R;
import com.kingja.yaluji.adapter.CommonAdapter;
import com.kingja.yaluji.adapter.ViewHolder;
import com.kingja.yaluji.base.BaseTitleActivity;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.constant.Constants;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.model.entiy.ArticleDetail;
import com.kingja.yaluji.model.entiy.ArticleSimpleItem;
import com.kingja.yaluji.page.search.result.SearchResultActivity;
import com.kingja.yaluji.util.LogUtil;
import com.kingja.yaluji.view.FixedListView;
import com.kingja.yaluji.view.StringTextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Description:TODO
 * Create Time:2018/11/4 10:35
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ArticleDetailActivity extends BaseTitleActivity implements ArticleDetailContract.View {
    @Inject
    ArticleDetailPresenter articleDetailPresenter;
    @BindView(R.id.tv_article_title)
    StringTextView tvArticleTitle;
    @BindView(R.id.tv_article_cndate)
    StringTextView tvArticleCndate;
//    @BindView(R.id.wb_article)
//    WebView wbArticle;
    @BindView(R.id.ll_article_get)
    LinearLayout llArticleGet;
    @BindView(R.id.tv_article_previousArticle)
    StringTextView tvArticlePreviousArticle;
    @BindView(R.id.tv_article_nextArticle)
    StringTextView tvArticleNextArticle;
    @BindView(R.id.flv)
    FixedListView flv;
    @BindView(R.id.ll_article_recommendArticles)
    LinearLayout llArticleRecommendArticles;
    @BindView(R.id.ll_article_previousArticle)
    LinearLayout llArticlePreviousArticle;
    @BindView(R.id.ll_article_nextArticle)
    LinearLayout llArticleNextArticle;
    @BindView(R.id.sv_articleDetail)
    ScrollView svArticleDetail;
    @BindView(R.id.ll_wb_root)
    LinearLayout llWbRoot;
    private String articleId;
    private CommonAdapter recommendAdapter;
    private List<ArticleSimpleItem> recommendArticles = new ArrayList<>();
    private String keyword;

    @OnItemClick(R.id.flv)
    public void itemClick(AdapterView<?> parent, View view, int position, long id) {
        ArticleSimpleItem articlesBean = (ArticleSimpleItem) parent
                .getItemAtPosition(position);
        articleDetailPresenter.getArticleDetail(articlesBean.getId());
    }

    @OnClick({R.id.ll_article_get})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.ll_article_get:
                SearchResultActivity.goActivity(this, keyword);
                break;
            default:
                break;
        }
    }

    @Override
    public void initVariable() {
        articleId = getIntent().getStringExtra(Constants.Extra.ArticleId);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_article_detail;
    }

    @Override
    protected void initComponent(AppComponent appComponent) {
        DaggerBaseCompnent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
        articleDetailPresenter.attachView(this);

    }

    @Override
    protected String getContentTitle() {
        return "";
    }

    @Override
    protected void initView() {
        recommendAdapter = new CommonAdapter<ArticleSimpleItem>(this, recommendArticles, R.layout
                .item_article_recommend) {
            @Override
            public void convert(ViewHolder helper, ArticleSimpleItem item) {
                helper.setText(R.id.tv_articleTitle, item.getTitle());
                helper.setText(R.id.tv_createdAt, item.getCreatedAt());
                helper.setImageByUrl(R.id.iv_headimg, item.getHeadimg());
            }
        };
        flv.setAdapter(recommendAdapter);

    }

    @Override
    protected void initData() {
//        wbArticle.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                //这个是一定要加上那个的,配合scrollView和WebView的height=wrap_content属性使用
//                LogUtil.e(TAG, "onPageFinished");
//                int w = View.MeasureSpec.makeMeasureSpec(0,
//                        View.MeasureSpec.UNSPECIFIED);
//                int h = View.MeasureSpec.makeMeasureSpec(0,
//                        View.MeasureSpec.UNSPECIFIED);
//                //重新测量
//                view.measure(w, h);
//            }
//        });
    }

    @Override
    protected void initNet() {
        articleDetailPresenter.getArticleDetail(articleId);
    }

    @Override
    public void onGetArticleDetailSuccess(ArticleDetail articleDetail) {
        svArticleDetail.scrollTo(0, 0);
        ArticleDetail.ArticleBean article = articleDetail.getArticle();
        if (article != null) {
            keyword = article.getKeywords();
            llArticleGet.setVisibility(TextUtils.isEmpty(keyword) ? View.GONE : View.VISIBLE);
            tvArticleTitle.setString(article.getTitle());
            tvArticleCndate.setString(article.getCndate());
            WebView wbArticle = new WebView(this);
            wbArticle.loadDataWithBaseURL("about:blank", article.getContent(), "text/html", "utf-8",
                    null);
            if (llWbRoot.getChildCount() == 0) {
                llWbRoot.addView(wbArticle);
            }
        }
        ArticleDetail.PreviousArticleBean previousArticle = articleDetail.getPreviousArticle();
        if (previousArticle != null) {
            tvArticlePreviousArticle.setString(previousArticle.getTitle());
            llArticlePreviousArticle.setOnClickListener(view -> {
                articleDetailPresenter.getArticleDetail(previousArticle.getId());
            });
        } else {
            tvArticlePreviousArticle.setString("没有啦");
            llArticlePreviousArticle.setOnClickListener(null);
        }
        ArticleDetail.NextArticleBean nextArticle = articleDetail.getNextArticle();
        if (nextArticle != null) {
            tvArticleNextArticle.setString(nextArticle.getTitle());
            llArticleNextArticle.setOnClickListener(view -> {
                articleDetailPresenter.getArticleDetail(nextArticle.getId());
            });
        } else {
            tvArticleNextArticle.setString("没有啦");
            llArticleNextArticle.setOnClickListener(null);
        }
        List<ArticleSimpleItem> recommendArticles = articleDetail.getRecommendArticles();
        if (recommendArticles != null && recommendArticles.size() > 0) {
            llArticleRecommendArticles.setVisibility(View.VISIBLE);
            recommendAdapter.setData(recommendArticles);
        } else {
            llArticleRecommendArticles.setVisibility(View.GONE);
        }

    }

    public static void goActivity(Context context, String articleId) {
        Intent intent = new Intent(context, ArticleDetailActivity.class);
        intent.putExtra(Constants.Extra.ArticleId, articleId);
        context.startActivity(intent);
    }

    @Override
    public boolean ifRegisterLoadSir() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
