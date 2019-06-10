package com.kingja.yaluji.page.search.article;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.kingja.yaluji.R;
import com.kingja.yaluji.adapter.CommonAdapter;
import com.kingja.yaluji.adapter.ViewHolder;
import com.kingja.yaluji.base.BaseFragment;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.constant.Constants;
import com.kingja.yaluji.i.OnSearchListener;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.model.entiy.ArticleSimpleItem;
import com.kingja.yaluji.page.article.detail.ArticleDetailActivity;
import com.kingja.yaluji.util.ToastUtil;
import com.kingja.yaluji.view.PullToBottomListView;
import com.kingja.yaluji.view.PullToMoreListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.Unbinder;
import okhttp3.MultipartBody;

/**
 * Description:TODO
 * Create Time:2018/11/4 15:49
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ArticleListSearchFragment extends BaseFragment implements OnSearchListener,
        SwipeRefreshLayout.OnRefreshListener, ArticleListSearchContract.View, PullToMoreListView.OnScrollToBottom {
    @BindView(R.id.plv)
    PullToMoreListView plv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    @BindView(R.id.iv_go_top)
    ImageView ivGoTop;
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
        plv.setGoTop(ivGoTop);
    }

    @Override
    protected void initData() {
        srl.setOnRefreshListener(this);
        plv.setOnScrollToBottom(this);
    }

    @Override
    protected void initNet() {
        articleListSearchPresenter.getArticleList(new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("keyword", keyword)
                .addFormDataPart("page", String.valueOf(plv.getPageIndex()))
                .addFormDataPart("pageSize", String.valueOf(Constants.PAGE_SIZE_20))
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
        plv.reset();
        initNet();
    }

    @Override
    public boolean ifRegisterLoadSir() {
        return true;
    }

    @Override
    public void onGetArticleListSuccess(List<ArticleSimpleItem> articleList) {
        if (articleList != null && articleList.size() > 0) {
            if (plv.getPageIndex() == 1) {
                adapter.setData(articleList);
            } else {
                adapter.addData(articleList);
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
    public void showLoadingCallback() {
        srl.setRefreshing(true);
    }

    @Override
    public void showSuccessCallback() {
        super.showSuccessCallback();
        srl.setRefreshing(false);
    }

    @Override
    public void onScrollToBottom(int pageIndex) {
        initNet();
    }
}
