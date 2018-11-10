package com.kingja.yaluji.page.home;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kingja.yaluji.R;
import com.kingja.yaluji.activity.SearchDetailActivity;
import com.kingja.yaluji.adapter.CommonAdapter;
import com.kingja.yaluji.adapter.LunBoTuPageAdapter;
import com.kingja.yaluji.adapter.ViewHolder;
import com.kingja.yaluji.base.BaseFragment;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.imgaeloader.ImageLoader;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.model.entiy.ArticleSimpleItem;
import com.kingja.yaluji.model.entiy.LunBoTu;
import com.kingja.yaluji.page.article.detail.ArticleDetailActivity;
import com.kingja.yaluji.page.article.list.ArticleListActivity;
import com.kingja.yaluji.page.message.MsgActivity;
import com.kingja.yaluji.page.search.question.list.QuestionListActivity;
import com.kingja.yaluji.page.search.result.SearchResultActivity;
import com.kingja.yaluji.page.ticket.list.TicketListActivity;
import com.kingja.yaluji.util.AppUtil;
import com.kingja.yaluji.util.GoUtil;
import com.kingja.yaluji.util.LogUtil;
import com.kingja.yaluji.util.LoginChecker;
import com.kingja.yaluji.util.NoDoubleClickListener;
import com.kingja.yaluji.util.SpSir;
import com.kingja.yaluji.view.FixedListView;
import com.kingja.yaluji.view.RefreshSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.Unbinder;

/**
 * Description:TODO
 * Create Time:2018/10/21 19:36
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class HomeFragment extends BaseFragment implements HomeContract.View, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.iv_msg)
    ImageView ivMsg;
    @BindView(R.id.tv_msgCount)
    TextView tvMsgCount;
    @BindView(R.id.iv_article)
    ImageView ivArticle;
    @BindView(R.id.iv_ticket)
    ImageView ivTicket;
    @BindView(R.id.iv_question)
    ImageView ivQuestion;
    @BindView(R.id.flv)
    FixedListView flv;

    @Inject
    HomePresenter homePresenter;
    @BindView(R.id.ll_dot)
    LinearLayout llDot;
    @BindView(R.id.rsl)
    RefreshSwipeRefreshLayout rsl;
    Unbinder unbinder;
    @BindView(R.id.rl_msg)
    RelativeLayout rlMsg;
    Unbinder unbinder1;
    @BindView(R.id.tv_keyword)
    TextView tvKeyword;
    private CommonAdapter adapter;
    private List<ArticleSimpleItem> articleSimpleItemList = new ArrayList<>();
    private List<LunBoTu> lunBoTuList = new ArrayList<>();
    private List<View> points = new ArrayList<>();

    @OnClick({R.id.iv_article, R.id.iv_ticket, R.id.iv_question, R.id.rl_msg, R.id.ll_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_article:
                GoUtil.goActivity(getActivity(), ArticleListActivity.class);
                break;
            case R.id.iv_ticket:
                GoUtil.goActivity(getActivity(), TicketListActivity.class);
                break;
            case R.id.iv_question:
                LoginChecker.goActivity(getActivity(), QuestionListActivity.class);
                break;
            case R.id.rl_msg:
                LoginChecker.goActivity(getActivity(), MsgActivity.class);
                break;
            case R.id.ll_search:
                GoUtil.goActivity(getActivity(), SearchDetailActivity.class);
                break;
            default:
                break;
        }
    }

    @OnItemClick(R.id.flv)
    public void itemClick(AdapterView<?> parent, View view, int position, long id) {
        ArticleSimpleItem articleSimpleItem = (ArticleSimpleItem) parent.getItemAtPosition(position);
        ArticleDetailActivity.goActivity(getActivity(), articleSimpleItem.getId());

    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initComponent(AppComponent appComponent) {
        DaggerBaseCompnent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
        homePresenter.attachView(this);
    }

    @Override
    protected void initView() {
        adapter = new CommonAdapter<ArticleSimpleItem>(getActivity(), articleSimpleItemList, R.layout
                .item_article_simple) {
            @Override
            public void convert(ViewHolder helper, ArticleSimpleItem item) {
                helper.setText(R.id.tv_articleTitle, item.getTitle());
                helper.setRoundImageByUrl(R.id.iv_article, item.getHeadimg(), 6);
            }
        };
        flv.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        rsl.setOnRefreshListener(this);
        rsl.setDistanceToTriggerSync(AppUtil.dp2px(100));

    }

    private void initHint() {
        String historyKeyword = SpSir.getInstance().getHistoryKeyword();
        LogUtil.e(TAG,"historyKeyword:"+historyKeyword);
        if (!TextUtils.isEmpty(historyKeyword)) {
            tvKeyword.setHint(historyKeyword);
        }
    }

    @Override
    protected void initNet() {
        homePresenter.getLunBoTuList();
        homePresenter.getArticleList();
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onGetLunBoTuListSuccess(List<LunBoTu> lunBoTuList) {
        if (lunBoTuList != null && lunBoTuList.size() > 0) {
            initDot(lunBoTuList);
            List<ImageView> imageViews = new ArrayList<>();
            for (LunBoTu lunBoTu : lunBoTuList) {
                ImageView iv = new ImageView(getActivity());
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                iv.setOnClickListener(new NoDoubleClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {
                        SearchResultActivity.goActivity(getActivity(), lunBoTu.getKeyword());
                    }
                });
                ImageLoader.getInstance().loadImage(getActivity(), lunBoTu.getBanner(), iv);
                imageViews.add(iv);
            }

            vp.setAdapter(new LunBoTuPageAdapter(imageViews));
            vp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    for (int i = 0; i < points.size(); i++) {
                        if (i == (position % points.size())) {
                            points.get(i).setBackgroundResource(R.mipmap.ic_dot_sel);
                        } else {
                            points.get(i).setBackgroundResource(R.mipmap.ic_dot_nor);
                        }
                    }
                }
            });
            autoHandler.postDelayed(autoTask, delayMillis);
        }

    }


    @Override
    public void onGetArticleListSuccess(List<ArticleSimpleItem> articleSimpleItemList) {
        if (articleSimpleItemList != null && articleSimpleItemList.size() > 0) {
            adapter.setData(articleSimpleItemList);
        }

    }

    private void initDot(List<LunBoTu> lunBoTuList) {
        for (int i = 0; i < lunBoTuList.size(); i++) {
            View view = new View(getActivity());
            view.setLayoutParams(new LinearLayout.LayoutParams(AppUtil.dp2px(10), AppUtil.dp2px(10)));
            if (i == 0) {
                view.setBackgroundResource(R.mipmap.ic_dot_sel);
            } else {
                view.setBackgroundResource(R.mipmap.ic_dot_nor);
            }
            points.add(view);
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(AppUtil.dp2px(10), AppUtil.dp2px(10));
        layoutParams.setMargins(0, 0, AppUtil.dp2px(10), 0);
        for (int i = 0; i < lunBoTuList.size(); i++) {
            llDot.addView(points.get(i), layoutParams);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtil.e(TAG, "是否隐藏:" + hidden);
        if (hidden) {
            autoHandler.removeCallbacks(autoTask);
        } else {
            autoHandler.postDelayed(autoTask, delayMillis);
        }
    }

    @Override
    public void onStart() {
        initHint();
        autoHandler.postDelayed(autoTask, delayMillis);
        super.onStart();
    }

    @Override
    public void onPause() {
        LogUtil.e(TAG, "不可见:");
        autoHandler.removeCallbacks(autoTask);
        super.onPause();
    }

    private Handler autoHandler = new Handler();
    private AutoRannable autoTask = new AutoRannable();
    private long delayMillis = 3000;
    private int currentLunBoTuIndex;


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {
        rsl.setRefreshing(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    class AutoRannable implements Runnable {
        @Override
        public void run() {
            autoHandler.removeCallbacks(autoTask);
            currentLunBoTuIndex++;
            vp.setCurrentItem(currentLunBoTuIndex);
            autoHandler.postDelayed(autoTask, delayMillis);
        }
    }


}
