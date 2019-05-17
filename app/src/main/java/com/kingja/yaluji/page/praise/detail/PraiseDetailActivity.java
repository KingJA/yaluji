package com.kingja.yaluji.page.praise.detail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.kingja.yaluji.R;
import com.kingja.yaluji.adapter.CommonAdapter;
import com.kingja.yaluji.adapter.ViewHolder;
import com.kingja.yaluji.base.BaseTitleActivity;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.constant.Constants;
import com.kingja.yaluji.constant.Status;
import com.kingja.yaluji.event.RefreshPraiseListEvent;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.model.entiy.PraiseDetail;
import com.kingja.yaluji.model.entiy.PraiseHeadImg;
import com.kingja.yaluji.model.entiy.PraiseItem;
import com.kingja.yaluji.page.praise.PraiseContract;
import com.kingja.yaluji.page.praise.PraisePresenter;
import com.kingja.yaluji.util.DateUtil;
import com.kingja.yaluji.util.LogUtil;
import com.kingja.yaluji.util.NoDoubleClickListener;
import com.kingja.yaluji.util.ShareUtil;
import com.kingja.yaluji.view.FixedGridView;
import com.kingja.yaluji.view.dialog.ConfirmDialog;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:TODO
 * Create Time:2019/5/15 22:15
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class PraiseDetailActivity extends BaseTitleActivity implements PraiseDetailContract.View, PraiseContract.View {
    @Inject
    PraiseDetailPresenter praiseDetailPresenter;
    @Inject
    PraisePresenter praisePresenter;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.tv_praisedFailByGameOver)
    TextView tvPraisedFailByGameOver;
    @BindView(R.id.btn_repraise)
    View btnRepraise;
    @BindView(R.id.ll_praisedFailByDayOver)
    LinearLayout llPraisedFailByDayOver;
    @BindView(R.id.stv_date_day)
    TextView stvDateHour;
    @BindView(R.id.stv_date_hour)
    TextView stvDateMin;
    @BindView(R.id.stv_date_min)
    TextView stvDateSec;
    @BindView(R.id.btn_againPraise)
    View btnAgainPraise;
    @BindView(R.id.ll_praising)
    LinearLayout llPraising;
    @BindView(R.id.tv_ticketName)
    TextView tvTicketName;
    @BindView(R.id.tv_quantity)
    TextView tvQuantity;
    @BindView(R.id.ll_getSuccess)
    LinearLayout llGetSuccess;
    @BindView(R.id.tv_payamount)
    TextView tvPayamount;
    @BindView(R.id.tv_visitDate)
    TextView tvVisitDate;
    @BindView(R.id.ll_praisedSuccess)
    LinearLayout llPraisedSuccess;
    @BindView(R.id.bottomsheet)
    BottomSheetLayout bottomsheet;
    @BindView(R.id.fgv)
    FixedGridView fgv;
    private String likeUserId;
    private String likeId;
    private String tip;
    private PraiseItem praiseItem;
    private IWXAPI api;
    private View bottomSheetView;
    String shareUrl;
    String shareDes;
    private CommonAdapter recommendAdapter;
    private Timer timer;

    @OnClick({R.id.ll_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            default:
                break;
        }
    }

    private void regToWeixin() {
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID_WEIXIN, true);
        api.registerApp(Constants.APP_ID_WEIXIN);
    }

    @Override
    public void initVariable() {
        likeUserId = getIntent().getStringExtra(Constants.Extra.LikeUserId);
        likeId = getIntent().getStringExtra(Constants.Extra.LikeId);
        praiseItem = (PraiseItem) getIntent().getSerializableExtra(Constants.Extra.PraiseItem);
        regToWeixin();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_praise_detail;
    }

    @Override
    protected void initComponent(AppComponent appComponent) {
        DaggerBaseCompnent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
        praiseDetailPresenter.attachView(this);
        praisePresenter.attachView(this);
    }

    @Override
    protected String getContentTitle() {
        return "点赞详情";
    }

    @Override
    protected void initView() {
        recommendAdapter = new CommonAdapter<PraiseHeadImg>(this, null, R.layout
                .item_praise_head) {
            @Override
            public void convert(ViewHolder helper, PraiseHeadImg item) {
                helper.setText(R.id.tv_friendNickname, item.getFriendNickname());
                helper.setCircleByUrl(R.id.iv_friendHeadimg, item.getFriendHeadimg());
            }

        };
        fgv.setAdapter(recommendAdapter);
    }

    @Override
    protected void initData() {
        initBottomSheet();
    }

    @Override
    protected void initNet() {
        praiseDetailPresenter.getPraiseDetail(likeUserId);
    }

    @Override
    public void onGetPraiseDetailSuccess(PraiseDetail praiseDetail) {
        llPraising.setVisibility(View.GONE);
        llPraisedSuccess.setVisibility(View.GONE);
        llPraisedFailByDayOver.setVisibility(View.GONE);
        tvPraisedFailByGameOver.setVisibility(View.GONE);
        List<PraiseHeadImg> headImgList = praiseDetail.getLikeProgressList();
        if (headImgList != null && headImgList.size() > 0) {
            recommendAdapter.setData(headImgList);
        }

        switch (praiseDetail.getStatus()) {
            case Status.PraiseDetailStatus.Praising:
                //1代表 用户集赞进行中
                tip = String.format("已有<font color=\"#f51305\">%d人</font>点赞，还差<font color=\"#f51305\">%d个赞</font>",
                        praiseDetail.getAlreadyLikeCount(), praiseDetail.getRemainLikeCount());
                llPraising.setVisibility(View.VISIBLE);

                stopTimer();


                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        int[] deadlineDate = DateUtil.getDeadlineDayDate(praiseDetail.getEndDateTime());
                        stvDateHour.setText(String.format("%02d", deadlineDate[0]));
                        stvDateMin.setText(String.format("%02d", deadlineDate[1]));
                        stvDateSec.setText(String.format("%02d", deadlineDate[2]));
                        Log.e(TAG, String.format("%d：%d：%d", deadlineDate[0], deadlineDate[1], deadlineDate[2]));

                    }
                }, 0, 1000);

                btnAgainPraise.setOnClickListener(new NoDoubleClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {
                        shareUrl = praiseDetail.getH5ShareUrl();
                        shareDes = String.format("集赞%d个以上，即获得价值%d元%s全额抵用券%d张", praiseItem.getLikeCount(),
                                praiseItem.getCouponAmount(), praiseItem.getTitle(), praiseItem.getCouponUnitCount());
                        bottomsheet.showWithSheetView(bottomSheetView);
                    }
                });
                break;
            case Status.PraiseDetailStatus.PraisedSuccess:
                //2代表用户集赞成功
                tip = "恭喜！已完成点赞数\n详情查看券包";
                llPraisedSuccess.setVisibility(View.VISIBLE);
                tvTicketName.setText(praiseDetail.getTitle());
                tvPayamount.setText(String.valueOf(praiseDetail.getCouponAmount()));
                tvQuantity.setText(String.valueOf(praiseDetail.getCouponUnitCount()));
                tvVisitDate.setText(String.valueOf(praiseDetail.getCouponUsePeriod()));
                break;
            case Status.PraiseDetailStatus.PraisedFailByDayOver:
                //3代表用户集赞超过24小时失败
                tip = String.format("已有<font color=\"#f51305\">%d人</font>点赞，还差<font color=\"#f51305\">%d个赞</font>",
                        praiseDetail.getAlreadyLikeCount(), praiseDetail.getRemainLikeCount());
                llPraisedFailByDayOver.setVisibility(View.VISIBLE);
                btnRepraise.setOnClickListener(new NoDoubleClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {
                        praisePresenter.checkPraise(likeId, praiseItem);
                    }
                });
                break;
            case Status.PraiseDetailStatus.PraisedFailByGameOver:
                //4代表活动已结束但未完成集赞
                tip = String.format("已有<font color=\"#f51305\">%d人</font>点赞，还差<font color=\"#f51305\">%d个赞</font>",
                        praiseDetail.getAlreadyLikeCount(), praiseDetail.getRemainLikeCount());
                tvPraisedFailByGameOver.setVisibility(View.VISIBLE);

                break;
        }
        tvTip.setText(Html.fromHtml(tip));
    }

    public static void goActivity(Activity context, String likeUserId, String likeId, PraiseItem praiseItem) {
        Intent intent = new Intent(context, PraiseDetailActivity.class);
        intent.putExtra(Constants.Extra.LikeUserId, likeUserId);
        intent.putExtra(Constants.Extra.LikeId, likeId);
        intent.putExtra(Constants.Extra.PraiseItem, praiseItem);
        context.startActivity(intent);
    }

    @Override
    public boolean ifRegisterLoadSir() {
        return true;
    }

    @Override
    public void showLoadingCallback() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected boolean ifHideTitle() {
        return true;
    }

    @Override
    public void onCheckPraiseSuccess(String shareUrl, PraiseItem praiseItem) {
        String likeUserId = shareUrl.substring(shareUrl.indexOf("="));
        LogUtil.e(TAG, "onCheckPraiseSuccess likeUserId:" + likeUserId);
        bottomsheet.showWithSheetView(bottomSheetView);
        this.shareUrl = shareUrl;
        this.shareDes = String.format("集赞%d个以上，即获得价值%d元%s全额抵用券%d张", praiseItem.getLikeCount(),
                praiseItem.getCouponAmount(), praiseItem.getTitle(), praiseItem.getCouponUnitCount());
    }

    @Override
    public void onPraiseSuccess(String shareUrl) {
        EventBus.getDefault().post(new RefreshPraiseListEvent());
        String likeUserId = shareUrl.substring(shareUrl.indexOf("=") + 1);
        LogUtil.e(TAG, "likeUserId:" + likeUserId);
        praiseDetailPresenter.getPraiseDetail(likeUserId);
    }

    @Override
    public void showErrorMessage(int code, String message) {
        message = message.replace("#", "\n");
        ConfirmDialog errorDialog = new ConfirmDialog(this, message);
        errorDialog.setOnConfirmListener(() -> {
            finish();
        });
        errorDialog.show();
    }

    private void share(int shareTo) {
        //初始化一个WXWebpageObject，填写url
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = shareUrl;
        //用 WXWebpageObject 对象初始化一个 WXMediaMessage 对象
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = getString(R.string.share_title);
        msg.description = shareDes;
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
            praisePresenter.onPraiseSuccess(likeId);
        }
    }

    private String buildTransaction(String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    private void initBottomSheet() {
        bottomSheetView = LayoutInflater.from(this).inflate(R.layout.bottom_share, bottomsheet, false);
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
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }
}
