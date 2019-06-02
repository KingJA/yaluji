package com.kingja.yaluji.page.praise.list;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.kingja.yaluji.R;
import com.kingja.yaluji.adapter.BaseLvAdapter;
import com.kingja.yaluji.adapter.PraiseAdapter;
import com.kingja.yaluji.base.BaseFragment;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.constant.Constants;
import com.kingja.yaluji.constant.Status;
import com.kingja.yaluji.event.RefreshPraiseListEvent;
import com.kingja.yaluji.event.ResetLoginStatusEvent;
import com.kingja.yaluji.i.OnSearchListener;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.model.entiy.PraiseItem;
import com.kingja.yaluji.page.login.LoginActivity;
import com.kingja.yaluji.page.praise.detail.PraiseDetailActivity;
import com.kingja.yaluji.util.GoUtil;
import com.kingja.yaluji.util.LoginChecker;
import com.kingja.yaluji.util.ShareUtil;
import com.kingja.yaluji.util.SpSir;
import com.kingja.yaluji.view.PullToBottomListView;
import com.kingja.yaluji.view.dialog.ConfirmDialog;
import com.kingja.yaluji.view.dialog.PraiseExplainDialog;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.MultipartBody;

/**
 * Description:TODO
 * Create Time:2018/11/4 15:49
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class PraiseListSearchFragment extends BaseFragment implements OnSearchListener,
        SwipeRefreshLayout.OnRefreshListener, PraiseListContract.View {
    @BindView(R.id.plv)
    PullToBottomListView plv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    @BindView(R.id.iv_go_top)
    ImageView ivGoTop;
    @Inject
    PraiseListPresenter praiseListPresenter;
    @BindView(R.id.bottomsheet)
    BottomSheetLayout bottomsheet;
    Unbinder unbinder;
    private String likeId;
    private String keyword;
    private PraiseAdapter praiseAdapter;
    private List<PraiseItem> praiseItemList = new ArrayList<>();
    private View bottomSheetView;

    public static PraiseListSearchFragment newInstance(String keyword) {
        PraiseListSearchFragment fragment = new PraiseListSearchFragment();
        Bundle args = new Bundle();
        args.putString(Constants.Extra.Keyword, keyword);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initVariable() {
        EventBus.getDefault().register(this);
        regToWeixin();
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
        praiseListPresenter.attachView(this);
    }

    @Override
    protected void initView() {
        praiseAdapter = new PraiseAdapter(getActivity(), praiseItemList);
        praiseAdapter.setOnItemClickListener((BaseLvAdapter.OnItemClickListener<PraiseItem>) praiseItem -> {
            if (TextUtils.isEmpty(SpSir.getInstance().getToken())) {
                GoUtil.goActivity(getActivity(), LoginActivity.class);
                return;
            }
            switch (praiseItem.getUserStatus()) {
                case Status.PraiseStatus.Praising:
                    //0 进行中 已参加：按钮显示查看详情（可点击查看)
                    PraiseDetailActivity.goActivity(getActivity(), praiseItem.getLikeUserId(), praiseItem
                            .getId(), praiseItem);
                    break;
                case Status.PraiseStatus.UnPraised:
                    //1 进行中 未参加：按钮显示去转发（可点击转发)
                    praiseListPresenter.checkPraise(praiseItem.getId(), praiseItem);
                    break;
                case Status.PraiseStatus.OverPraised:
                    //2 已结束 已参加：盖章,不变灰，按钮显示查看详情（可点击查看)
                    PraiseDetailActivity.goActivity(getActivity(), praiseItem.getLikeUserId(), praiseItem
                            .getId(), praiseItem);
                    break;
                case Status.PraiseStatus.OverUnpraised:
                    //3 已结束 未参加：盖章,变灰，按钮显示去转发（不可点击)
                    break;
            }
        });
        plv.setAdapter(praiseAdapter);
        plv.setGoTop(ivGoTop);
    }
    @Override
    protected void initData() {
        initBottomSheet();
        srl.setOnRefreshListener(this);
    }

    private void initBottomSheet() {
        bottomSheetView = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_share, bottomsheet, false);
        LinearLayout ll_share_friendGroup = bottomSheetView.findViewById(R.id.ll_share_friendGroup);
        LinearLayout ll_share_friends = bottomSheetView.findViewById(R.id.ll_share_friends);
        TextView tv_share_cancel = bottomSheetView.findViewById(R.id.tv_share_cancel);
        ll_share_friendGroup.setOnClickListener(v -> {
            bottomsheet.dismissSheet();
            share(SendMessageToWX.Req.WXSceneTimeline);
        });
        ll_share_friends.setOnClickListener(v -> {
            bottomsheet.dismissSheet();
            share(SendMessageToWX.Req.WXSceneSession);
        });
        tv_share_cancel.setOnClickListener(v -> {
            bottomsheet.dismissSheet();
        });
    }

    @Override
    protected void initNet() {
        if (LoginChecker.isLogin()) {
            praiseListPresenter.getPraiseList(new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("keyword", keyword)
                    .addFormDataPart("page", String.valueOf(currentPageSize))
                    .addFormDataPart("pageSize", String.valueOf(Constants.PAGE_SIZE_100))
                    .build());
        } else {
            praiseListPresenter.getPraiseListByVisitor(new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("keyword", keyword)
                    .addFormDataPart("page", String.valueOf(currentPageSize))
                    .addFormDataPart("pageSize", String.valueOf(Constants.PAGE_SIZE_100))
                    .build());
        }
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_praise;
    }

    @Override
    public void search(String keyword) {
        this.keyword = keyword;
        initNet();
    }

    private int currentPageSize = 1;


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
        if (!TextUtils.isEmpty(SpSir.getInstance().getToken())) {
            initNet();
        }
    }


    @Override
    public void onGetPraiseListSuccess(List<PraiseItem> praiseItemList) {
        if (praiseItemList != null && praiseItemList.size() > 0) {
            praiseAdapter.setData(praiseItemList);
        } else {
            showEmptyCallback();
        }
    }

    @Override
    public void onCheckPraiseSuccess(String shareUrl, PraiseItem praiseItem) {
        likeId = praiseItem.getId();
        this.shareUrl = shareUrl;
        this.shareDes = String.format("集赞%d个以上，即获得价值%d元%s%d张", praiseItem.getLikeCount(),
                praiseItem.getCouponAmount(), praiseItem.getTitle(), praiseItem.getCouponUnitCount());
        bottomsheet.showWithSheetView(bottomSheetView);
    }

    @Override
    public void showLoadingCallback() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void onPraiseSuccess() {
        initNet();
    }

    @Override
    public void showErrorMessage(int code, String message) {
        message = message.replace("#", "\n");
        ConfirmDialog errorDialog = new ConfirmDialog(getActivity(), message);
        errorDialog.setOnConfirmListener(() -> {
        });
        errorDialog.show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshPraiseList(RefreshPraiseListEvent event) {
        if (!TextUtils.isEmpty(SpSir.getInstance().getToken())) {
            initNet();
        }
    }

    String shareUrl;
    String shareDes;

    private void share(int shareTo) {
        //初始化一个WXWebpageObject，填写url
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = shareUrl;
        //用 WXWebpageObject 对象初始化一个 WXMediaMessage 对象
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = getString(R.string.share_title);
        msg.description = shareDes;
        if (shareTo == SendMessageToWX.Req.WXSceneTimeline) {
            msg.title = shareDes;
        }
        Bitmap thumbBmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_share);
        msg.thumbData = ShareUtil.bmpToByteArray(thumbBmp, true);
        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = shareTo;
        //调用api接口，发送数据到微信
        boolean ifShareSuccess = api.sendReq(req);
        if (ifShareSuccess) {
            praiseListPresenter.onPraiseSuccess(likeId);
        }
    }

    private IWXAPI api;

    private String buildTransaction(String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    private void regToWeixin() {
        api = WXAPIFactory.createWXAPI(getActivity(), Constants.APP_ID_WEIXIN, true);
        api.registerApp(Constants.APP_ID_WEIXIN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}