package com.kingja.yaluji.page.praise.list;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.kingja.yaluji.R;
import com.kingja.yaluji.adapter.QuestionAdapter;
import com.kingja.yaluji.base.BaseTitleActivity;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.constant.Constants;
import com.kingja.yaluji.constant.Status;
import com.kingja.yaluji.event.RefreshQuestionEvent;
import com.kingja.yaluji.event.ResetLoginStatusEvent;
import com.kingja.yaluji.event.ShareSuccessEvent;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.model.entiy.Question;
import com.kingja.yaluji.page.answer.detail.QuestionDetailActivity;
import com.kingja.yaluji.page.relife.RelifeContract;
import com.kingja.yaluji.page.relife.RelifePresenter;
import com.kingja.yaluji.page.search.question.list.QuestionListContract;
import com.kingja.yaluji.page.search.question.list.QuestionListPresenter;
import com.kingja.yaluji.util.LogUtil;
import com.kingja.yaluji.util.LoginChecker;
import com.kingja.yaluji.util.ShareUtil;
import com.kingja.yaluji.util.SpSir;
import com.kingja.yaluji.util.ToastUtil;
import com.kingja.yaluji.view.PullToBottomListView;
import com.kingja.yaluji.view.dialog.ConfirmDialog;
import com.kingja.yaluji.view.dialog.PraiseExplainDialog;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import okhttp3.MultipartBody;

/**
 * Description:TODO
 * Create Time:2018/11/6 22:44
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class PraiseListActivity extends BaseTitleActivity implements QuestionListContract.View, SwipeRefreshLayout
        .OnRefreshListener, RelifeContract.View {
    @Inject
    QuestionListPresenter questionListPresenter;
    @Inject
    RelifePresenter relifePresenter;
    @BindView(R.id.rl_question_explain)
    RelativeLayout ivQuestionExplain;
    @BindView(R.id.plv)
    PullToBottomListView plv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    @BindView(R.id.iv_go_top)
    ImageView ivGoTop;
    @BindView(R.id.bottomsheet)
    BottomSheetLayout bottomsheet;
    private List<Question> questionList = new ArrayList<>();
    private QuestionAdapter questionAdapter;
    private PraiseExplainDialog questionExplainDialog;
    private IWXAPI api;
    private String paperId;
    private ConfirmDialog confirmDialog;
    private View bottomSheetView;

    private void regToWeixin() {
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID_WEIXIN, true);
        api.registerApp(Constants.APP_ID_WEIXIN);
    }

    @OnClick({R.id.rl_question_explain})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_question_explain:
                bottomsheet.showWithSheetView(bottomSheetView);
//                questionExplainDialog.show();
                break;
            default:
                break;
        }
    }

    @OnItemClick(R.id.plv)
    public void itemClick(AdapterView<?> parent, View view, int position, long id) {
        Question question = (Question) parent.getItemAtPosition(position);
        paperId = question.getId();
        if (question.getUserStatus() == Status.QuestionStatus.ANSWER.getCode()) {
            QuestionDetailActivity.goActivity(this, paperId);
        }
        if (question.getUserStatus() == Status.QuestionStatus.RELIFT.getCode()) {
            SpSir.getInstance().putSharePage(Status.SharePage.QUESTION_LIST);
            share(SendMessageToWX.Req.WXSceneTimeline);
        }
    }

    private void share(int shareTo) {
        //初始化一个WXWebpageObject，填写url
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "https://www.baidu.com";
        //用 WXWebpageObject 对象初始化一个 WXMediaMessage 对象
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "鸭鹿鸡 ";
        msg.description = "集赞6个";
        Bitmap thumbBmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_share);
        msg.thumbData =ShareUtil.bmpToByteArray(thumbBmp, true);
        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = shareTo;
        //调用api接口，发送数据到微信
        api.sendReq(req);
    }
    private String buildTransaction(String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    @Override
    public void initVariable() {
        EventBus.getDefault().register(this);
        regToWeixin();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_praise;
    }

    @Override
    protected void initComponent(AppComponent appComponent) {
        DaggerBaseCompnent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
        questionListPresenter.attachView(this);
        relifePresenter.attachView(this);
    }

    @Override
    protected String getContentTitle() {
        return "鸡答";
    }

    @Override
    protected void initView() {
        questionAdapter = new QuestionAdapter(this, questionList);
        plv.setAdapter(questionAdapter);
        plv.setGoTop(ivGoTop);
    }

    @Override
    protected void initData() {
        initBottomSheet();
        questionExplainDialog = new PraiseExplainDialog(this);
        confirmDialog = new ConfirmDialog(this, "复活成功，请继续答题");
        srl.setOnRefreshListener(this);
    }

    private void initBottomSheet() {
        bottomSheetView = LayoutInflater.from(this).inflate(R.layout.bottom_share, bottomsheet, false);
        LinearLayout ll_share_friendGroup = bottomSheetView.findViewById(R.id.ll_share_friendGroup);
        LinearLayout ll_share_friends = bottomSheetView.findViewById(R.id.ll_share_friends);
        TextView tv_share_cancel = bottomSheetView.findViewById(R.id.tv_share_cancel);
        ll_share_friendGroup.setOnClickListener(v -> {
            ToastUtil.showText("朋友圈");
            bottomsheet.dismissSheet();
            share(SendMessageToWX.Req.WXSceneTimeline);
        });
        ll_share_friends.setOnClickListener(v -> {
            ToastUtil.showText("微信好友");
            bottomsheet.dismissSheet();
            share(SendMessageToWX.Req.WXSceneSession);
        });
        tv_share_cancel.setOnClickListener(v -> {
            ToastUtil.showText("取消");
            bottomsheet.dismissSheet();
        });
    }

    private int currentPageSize = 1;

    @Override
    protected void initNet() {
        if (LoginChecker.isLogin()) {
            questionListPresenter.getQuestionList(new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("page", String.valueOf(currentPageSize))
                    .addFormDataPart("pageSize", String.valueOf(Constants.PAGE_SIZE_100))
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void shareSuccess(ShareSuccessEvent event) {
        LogUtil.e(TAG, "问题列表复活:" + SpSir.getInstance().getShapePage());
        if (SpSir.getInstance().getShapePage() == Status.SharePage.QUESTION_LIST) {
            relifePresenter.reLife(paperId);
        }
    }

    @Override
    public void onReLifeSuccess() {
        initNet();
        confirmDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
