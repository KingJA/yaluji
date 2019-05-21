package com.kingja.yaluji.page.home;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
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
import com.kingja.yaluji.constant.Constants;
import com.kingja.yaluji.event.MsgCountEvent;
import com.kingja.yaluji.event.RefreshHome;
import com.kingja.yaluji.imgaeloader.ImageLoader;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.model.entiy.ArticleSimpleItem;
import com.kingja.yaluji.model.entiy.LunBoTu;
import com.kingja.yaluji.page.article.detail.ArticleDetailActivity;
import com.kingja.yaluji.page.article.list.ArticleListActivity;
import com.kingja.yaluji.page.message.MsgActivity;
import com.kingja.yaluji.page.praise.list.PraiseListActivity;
import com.kingja.yaluji.page.search.question.list.QuestionListActivity;
import com.kingja.yaluji.page.search.result.SearchResultActivity;
import com.kingja.yaluji.page.ticket.list.TicketListActivity;
import com.kingja.yaluji.util.AppUtil;
import com.kingja.yaluji.util.GoUtil;
import com.kingja.yaluji.util.LogUtil;
import com.kingja.yaluji.util.LoginChecker;
import com.kingja.yaluji.util.NoDoubleClickListener;
import com.kingja.yaluji.util.SpSir;
import com.kingja.yaluji.view.MoveSwipeRefreshLayout;
import com.kingja.yaluji.view.PullToBottomListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;

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

    @Inject
    HomePresenter homePresenter;
    @BindView(R.id.ll_dot)
    LinearLayout llDot;
    @BindView(R.id.rsl)
    MoveSwipeRefreshLayout rsl;
    @BindView(R.id.rl_msg)
    RelativeLayout rlMsg;
    @BindView(R.id.tv_keyword)
    TextView tvKeyword;
    @BindView(R.id.plv)
    PullToBottomListView plv;
    @BindView(R.id.iv_go_top)
    ImageView ivGoTop;
    private CommonAdapter adapter;
    private List<ArticleSimpleItem> articleSimpleItemList = new ArrayList<>();
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
                GoUtil.goActivity(getActivity(), PraiseListActivity.class);
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

    @OnItemClick(R.id.plv)
    public void itemClick(AdapterView<?> parent, View view, int position, long id) {
        ArticleSimpleItem articleSimpleItem = (ArticleSimpleItem) parent.getItemAtPosition(position);
        ArticleDetailActivity.goActivity(getActivity(), articleSimpleItem.getId());

    }

    @Override
    protected void initVariable() {
        EventBus.getDefault().register(this);
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
                helper.setRoundImageByUrl(R.id.iv_article, item.getHeadimg(), Constants.CORNER);
            }
        };
        plv.setAdapter(adapter);
        plv.setGoTop(ivGoTop);
    }

    @Override
    protected void initData() {
        rsl.setOnRefreshListener(this);
        rsl.setDistanceToTriggerSync(AppUtil.dp2px(100));

    }

    private void initHint() {
        String historyKeyword = SpSir.getInstance().getHistoryKeyword();
        LogUtil.e(TAG, "historyKeyword:" + historyKeyword);
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
            if (simpleOnPageChangeListener != null) {
                vp.removeOnPageChangeListener(simpleOnPageChangeListener);
            }
            vp.addOnPageChangeListener(simpleOnPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
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
        }

    }

    private ViewPager.SimpleOnPageChangeListener simpleOnPageChangeListener;

    @Override
    public void onGetArticleListSuccess(List<ArticleSimpleItem> articleSimpleItemList) {
        if (articleSimpleItemList != null && articleSimpleItemList.size() > 0) {
            adapter.setData(articleSimpleItemList);
        }

    }

    private void initDot(List<LunBoTu> lunBoTuList) {
        llDot.removeAllViews();
        points.clear();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(AppUtil.dp2px(6), AppUtil.dp2px(6));
        layoutParams.setMargins(0, 0, AppUtil.dp2px(6), 0);
        for (int i = 0; i < lunBoTuList.size(); i++) {
            View view = new View(getActivity());
            view.setLayoutParams(layoutParams);
            if (i == 0) {
                view.setBackgroundResource(R.mipmap.ic_dot_sel);
            } else {
                view.setBackgroundResource(R.mipmap.ic_dot_nor);
            }
            points.add(view);
            llDot.addView(points.get(i), layoutParams);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtil.e(TAG, "轮播 onHiddenChanged:" + hidden);
        if (hidden) {
            autoHandler.removeCallbacks(autoTask);
        } else {
            autoHandler.postDelayed(autoTask, Constants.AUTO_LUNBOTU);
        }
    }

    @Override
    public void onStart() {
        LogUtil.e(TAG, "轮播 onStart");
        initHint();
        autoHandler.postDelayed(autoTask, Constants.AUTO_LUNBOTU);
        super.onStart();
    }

    @Override
    public void onPause() {
        LogUtil.e(TAG, "轮播 onPause");
        autoHandler.removeCallbacks(autoTask);
        super.onPause();
    }

    private Handler autoHandler = new Handler();
    private AutoRannable autoTask = new AutoRannable();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        autoHandler.removeCallbacks(autoTask);
    }

    @Override
    public void onRefresh() {
        rsl.setRefreshing(false);
        initNet();
    }

    class AutoRannable implements Runnable {
        @Override
        public void run() {
            autoHandler.removeCallbacks(autoTask);
            vp.setCurrentItem(vp.getCurrentItem() + 1);
            autoHandler.postDelayed(autoTask, Constants.AUTO_LUNBOTU);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshMsgCount(MsgCountEvent msgCountEvent) {
        resetMsgCount();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshHome(RefreshHome refreshHome) {
        initNet();
    }

    private void resetMsgCount() {
        int msgCount = SpSir.getInstance().getMsgCount();
        if (msgCount != 0) {
            tvMsgCount.setVisibility(View.VISIBLE);
            tvMsgCount.setText(String.valueOf(msgCount));
        } else {
            tvMsgCount.setVisibility(View.GONE);
        }
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }
}
