package com.kingja.yaluji.page.ticket.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.kingja.supershapeview.view.SuperShapeLinearLayout;
import com.kingja.yaluji.R;
import com.kingja.yaluji.adapter.BaseRvAdaper;
import com.kingja.yaluji.adapter.VisitorTabAdapter;
import com.kingja.yaluji.base.BaseTitleActivity;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.constant.Constants;
import com.kingja.yaluji.constant.Status;
import com.kingja.yaluji.event.AddVisitorEvent;
import com.kingja.yaluji.event.PrfectVisitorEvent;
import com.kingja.yaluji.event.RefreshOrderEvent;
import com.kingja.yaluji.event.ResetLoginStatusEvent;
import com.kingja.yaluji.imgaeloader.ImageLoader;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.model.entiy.OrderResult;
import com.kingja.yaluji.model.entiy.TicketDetail;
import com.kingja.yaluji.model.entiy.Visitor;
import com.kingja.yaluji.page.introduce.SceneryIntroduceActivity;
import com.kingja.yaluji.page.ticket.success.TicketSuccessActivity;
import com.kingja.yaluji.page.visitor.list.VisitorListActivity;
import com.kingja.yaluji.page.visitor.prefect.VisitorPrefectActivity;
import com.kingja.yaluji.util.DateUtil;
import com.kingja.yaluji.util.DialogUtil;
import com.kingja.yaluji.util.LoginChecker;
import com.kingja.yaluji.util.SpSir;
import com.kingja.yaluji.util.ToastUtil;
import com.kingja.yaluji.view.ChangeNumberView;
import com.kingja.yaluji.view.DeleteTextView;
import com.kingja.yaluji.view.RvItemDecoration;
import com.kingja.yaluji.view.StringTextView;
import com.kingja.yaluji.view.dialog.BaseDialog;
import com.kingja.yaluji.view.dialog.TicketGetDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
 * Create Time:2018/11/3 19:00
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class TicketDetailActivity extends BaseTitleActivity implements TicketDetailContract.View, BaseRvAdaper
        .OnItemClickListener<Visitor> {
    @BindView(R.id.iv_detail_headImg)
    ImageView ivDetailHeadImg;
    @BindView(R.id.tv_detail_ticketName)
    StringTextView tvDetailTicketName;
    @BindView(R.id.tv_detail_marketPrice)
    DeleteTextView tvDetailMarketPrice;
    @BindView(R.id.tv_detail_buyPrice)
    StringTextView tvDetailBuyPrice;
    @BindView(R.id.tv_detail_visitDate)
    StringTextView tvDetailVisitDate;
    @BindView(R.id.tv_detail_buyPrice_small)
    StringTextView tvDetailBuyPriceSmall;
    @BindView(R.id.tv_detail_areaText)
    StringTextView tvDetailAreaText;
    @BindView(R.id.tv_detail_visitTime)
    StringTextView tvDetailVisitTime;
    @BindView(R.id.tv_detail_visitMethod)
    StringTextView tvDetailVisitMethod;
    @BindView(R.id.tv_detail_useRemarks)
    StringTextView tvDetailUseRemarks;
    @BindView(R.id.tv_detail_remarks)
    StringTextView tvDetailRemarks;
    @BindView(R.id.rv_ticket_detail)
    RecyclerView rvTicketDetail;
    @BindView(R.id.tv_visitor_name)
    StringTextView tvVisitorName;
    @BindView(R.id.tv_visitor_phone)
    StringTextView tvVisitorPhone;
    @BindView(R.id.tv_visitor_idcode)
    StringTextView tvVisitorIdcode;
    @BindView(R.id.ssll_visitor_info)
    SuperShapeLinearLayout ssllVisitorInfo;
    @BindView(R.id.ccv_ticket_detail)
    ChangeNumberView ccvTicketDetail;
    @BindView(R.id.tv_detail_sellCount)
    StringTextView tvDetailSellCount;
    @BindView(R.id.tv_detail_totalCount)
    StringTextView tvDetailTotalCount;
    @BindView(R.id.tv_detail_buyLimit)
    StringTextView tvDetailBuyLimit;
    @Inject
    TicketDetailPresenter ticketDetailPresenter;
    @BindView(R.id.ll_detail_get)
    LinearLayout llDetailGet;
    @BindView(R.id.rl_ticket_introduce)
    RelativeLayout rlTicketIntroduce;
    @BindView(R.id.stv_confirm)
    StringTextView stvConfirm;
    private String productId;
    private String touristId;
    private List<Visitor> visitors = new ArrayList<>();
    private VisitorTabAdapter visitorTabAdapter;
    private int idcodeNeed;
    private int status;
    private String endTime;
    private TicketGetDialog ticketGetDialog;
    private int buyPrice;
    private String visitDate;
    private String ticketName;
    private String scenicid;

    @OnClick({R.id.rl_ticket_introduce, R.id.ll_detail_get})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.rl_ticket_introduce:
                SceneryIntroduceActivity.goActivity(this, scenicid);
                break;
            case R.id.ll_detail_get:
                if (LoginChecker.isLogin()) {
                    checkBuyInfo();
                } else {
                    DialogUtil.showLoginActivity(this);
                }
                break;
            default:
                break;
        }
    }

    private void checkBuyInfo() {
        String idcode = tvVisitorIdcode.getText().toString().trim();
        if (TextUtils.isEmpty(touristId)) {
            if (visitorTabAdapter.getItemCount() == 1) {
                ToastUtil.showText("没有游客信息，请新增一位游客");
            } else {
                ToastUtil.showText("请选择一位出行游客信息");
            }
        } else if (idcodeNeed == 1 && TextUtils.isEmpty(idcode)) {
            showPrefectDialog();
        } else {
            checkBuyable();
        }
    }

    private void checkBuyable() {
        switch (status) {
            case Status.SellStatus.OVER:
                ToastUtil.showText("已结束");
                break;
            case Status.SellStatus.SELLOUT:
                ToastUtil.showText("已领完");
                break;
            case Status.SellStatus.SELLING:
                sumbmitOrder();
                break;
            default:
                break;
        }
    }

    private void sumbmitOrder() {
        if (!SpSir.getInstance().getNoShow()) {
            ticketGetDialog.show();
        } else {
            ticketDetailPresenter.sumbitOrder(productId, touristId, ccvTicketDetail.getNumber(), Constants
                    .PLATFORM_ANDROID);
        }

    }

    private void showPrefectDialog() {
        DialogUtil.showDoubleDialog(this, "该景区需要提供身份证号码，是否去完善", new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                String name = tvVisitorName.getText().toString().trim();
                String phone = tvVisitorPhone.getText().toString().trim();
                VisitorPrefectActivity.goActivity(TicketDetailActivity.this, new Visitor(touristId, name, phone));
            }
        });
    }

    @Override
    public void initVariable() {
        EventBus.getDefault().register(this);
        productId = getIntent().getStringExtra(Constants.Extra.ProductId);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_ticket_detail;
    }

    @Override
    protected void initComponent(AppComponent appComponent) {
        DaggerBaseCompnent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
        ticketDetailPresenter.attachView(this);
    }

    @Override
    protected String getContentTitle() {
        return "立即抢券";
    }

    @Override
    protected void initView() {
        visitorTabAdapter = new VisitorTabAdapter(this, visitors);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        rvTicketDetail.setLayoutManager(layoutManager);
        rvTicketDetail.setAdapter(visitorTabAdapter);
        rvTicketDetail.setItemAnimator(new DefaultItemAnimator());
        rvTicketDetail.addItemDecoration(new RvItemDecoration(this, RvItemDecoration.LayoutStyle.HORIZONTAL_LIST,
                12, 0x00ffffff));
    }

    @Override
    protected void initData() {
        visitorTabAdapter.setOnItemClickListener(this);
        ticketGetDialog = new TicketGetDialog(this);
        ticketGetDialog.setOnConfirmListener(() -> ticketDetailPresenter.sumbitOrder(productId, touristId,
                ccvTicketDetail.getNumber(), Constants
                        .PLATFORM_ANDROID));
    }

    @Override
    protected void initNet() {
        ticketDetailPresenter.getTicketDetail(productId);
        ticketDetailPresenter.getVisitors(Constants.PAGE_FIRST, Constants.PAGE_SIZE_100);
    }

    @Override
    public void onGetTicketDetailSuccess(TicketDetail ticketDetail) {
        showSuccessCallback();
        scenicid = ticketDetail.getScenicid();
        idcodeNeed = ticketDetail.getIdcodeNeed();
        status = ticketDetail.getStatus();
        endTime = ticketDetail.getEndTime();
        buyPrice = ticketDetail.getBuyPrice();
        visitDate = ticketDetail.getVisitDate();
        ticketName = ticketDetail.getTicketName();
        ImageLoader.getInstance().loadImage(this, ticketDetail.getHeadImg(), R.drawable.ic_placeholder,
                ivDetailHeadImg);
        tvDetailTicketName.setString(ticketName);
        tvDetailMarketPrice.setString(ticketDetail.getMarketPrice());
        tvDetailBuyPrice.setString(buyPrice);
        tvDetailVisitDate.setString(visitDate);
        tvDetailBuyPriceSmall.setString(buyPrice);
        tvDetailAreaText.setString(ticketDetail.getAreaText());
        tvDetailVisitTime.setString(ticketDetail.getVisitTime());
        tvDetailVisitMethod.setString(ticketDetail.getVisitMethod());
        tvDetailUseRemarks.setString(ticketDetail.getUseRemarks());
        tvDetailRemarks.setString(ticketDetail.getRemarks());
        tvDetailSellCount.setString(ticketDetail.getSellCount());
        tvDetailTotalCount.setString(ticketDetail.getTotalCount());
        tvDetailBuyLimit.setString(ticketDetail.getBuyLimit());
        setTicketStatus();
        initTimer();

    }

    public void setTicketStatus() {
        switch (status) {
            case Status.SellStatus.SELLOUT:
                llDetailGet.setBackgroundColor(ContextCompat.getColor(this, R.color.gray_hi));
                llDetailGet.setEnabled(false);
                stvConfirm.setText("已领完");
                break;
            case Status.SellStatus.OVER:
                llDetailGet.setBackgroundColor(ContextCompat.getColor(this, R.color.gray_hi));
                llDetailGet.setEnabled(false);
                stvConfirm.setText("已结束");
                break;
            case Status.SellStatus.SELLING:
                llDetailGet.setBackgroundResource(R.mipmap.bg_ticket_detail);
                llDetailGet.setEnabled(true);
                stvConfirm.setText("领取");
                break;
        }
    }

    private void initTimer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (DateUtil.isOverDue(endTime)) {
                    llDetailGet.setBackgroundColor(ContextCompat.getColor(TicketDetailActivity.this, R.color.gray_hi));
                    llDetailGet.setEnabled(false);
                    stvConfirm.setText("已结束");
                    timer.cancel();
                }

            }
        }, 0, 1000);
    }

    @Override
    public void onGetVisitorsSuccess(List<Visitor> visitors) {
        if (visitors.size() == 0) {
            ssllVisitorInfo.setVisibility(View.GONE);
        } else if (hasDefault(visitors)) {
            ssllVisitorInfo.setVisibility(View.VISIBLE);
            fillVisitorInfo(visitors.get(0));
        } else {
            ssllVisitorInfo.setVisibility(View.GONE);
        }
        visitorTabAdapter.setData(getSelectVisitor(visitors));
    }

    private void fillVisitorInfo(Visitor visitor) {
        ssllVisitorInfo.setVisibility(View.VISIBLE);
        tvVisitorName.setText(visitor.getName());
        tvVisitorPhone.setText(visitor.getMobile());
        tvVisitorIdcode.setText(visitor.getIdcode());
        touristId = visitor.getId();
    }

    private boolean hasDefault(List<Visitor> visitors) {
        for (Visitor visitor : visitors) {
            if (visitor.getIsdefault() == 1) {
                return true;
            }
        }
        return false;
    }

    private List<Visitor> getSelectVisitor(List<Visitor> visitors) {
        if (visitors.size() > 3) {
            visitors = visitors.subList(0, 3);
        }
        Visitor addVisitor = new Visitor();
        addVisitor.setName("新增/更换 >");
        visitors.add(addVisitor);
        for (Visitor visitor : visitors) {
            if (visitor.getIsdefault() == 1) {
                visitor.setSelected(true);
            }
        }
        return visitors;
    }

    @Override
    public void onSumbitOrderSuccess(OrderResult orderResult) {
        EventBus.getDefault().post(new RefreshOrderEvent());
        TicketSuccessActivity.goActivity(this, ticketName, String.valueOf(buyPrice), visitDate, String.valueOf
                (ccvTicketDetail.getNumber()));
    }

    @Override
    public void onLoginFail() {
        ssllVisitorInfo.setVisibility(View.GONE);
        visitorTabAdapter.setData(getSelectVisitor(visitors));
    }

    @Override
    public boolean ifRegisterLoadSir() {
        return true;
    }

    public static void goActivity(Context context, String productId) {
        Intent intent = new Intent(context, TicketDetailActivity.class);
        intent.putExtra(Constants.Extra.ProductId, productId);
        context.startActivity(intent);
    }

    @Subscribe
    public void addVisitor(AddVisitorEvent visitorEvent) {
        if (visitorTabAdapter.has(visitorEvent)) {
            ToastUtil.showText("已经存在该游客信息");
            return;
        }
        visitorTabAdapter.addFirst(visitorEvent);
        fillVisitorInfo(visitorEvent);
        rvTicketDetail.scrollToPosition(0);
    }

    @Subscribe
    public void prfectVisitors(PrfectVisitorEvent visitorEvent) {
        fillVisitorInfo(visitorEvent);
        visitorTabAdapter.prfectVisitor(visitorEvent);
    }

    @Subscribe
    public void resetLoginStatus(ResetLoginStatusEvent resetLoginStatusEvent) {
        ticketDetailPresenter.getVisitors(Constants.PAGE_FIRST, Constants.PAGE_SIZE_100);
    }


    @Override
    public void onItemClick(Visitor visitor, int position) {
        if (position == visitorTabAdapter.getItemCount() - 1) {
            Intent intent = new Intent(this, VisitorListActivity.class);
            intent.putExtra(Constants.Extra.FromTitketDetail, true);
            LoginChecker.goActivity(this, intent);
        } else {
            visitorTabAdapter.select(position);
            fillVisitorInfo(visitor);
        }
    }

}
