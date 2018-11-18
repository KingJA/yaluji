package com.kingja.yaluji.page.ticket.success;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingja.yaluji.R;
import com.kingja.yaluji.adapter.TicketGvAdapter;
import com.kingja.yaluji.base.BaseTitleActivity;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.constant.Constants;
import com.kingja.yaluji.constant.Status;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.model.entiy.Ticket;
import com.kingja.yaluji.page.ticket.detail.TicketDetailActivity;
import com.kingja.yaluji.page.ticket.list.TicketListActivity;
import com.kingja.yaluji.util.GoUtil;
import com.kingja.yaluji.util.ShareUtil;
import com.kingja.yaluji.util.SpSir;
import com.kingja.yaluji.view.FixedGridView;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Description:TODO
 * Create Time:2018/11/8 23:14
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class TicketSuccessActivity extends BaseTitleActivity implements TicketSuccessContract.View {

    @BindView(R.id.tv_ticketName)
    TextView tvTicketName;
    @BindView(R.id.tv_quantity)
    TextView tvQuantity;
    @BindView(R.id.ll_getSuccess)
    LinearLayout llGetSuccess;
    @BindView(R.id.tv_payamount)
    TextView tvBuyPrice;
    @BindView(R.id.tv_visitDate)
    TextView tvVisitDate;
    @BindView(R.id.tv_backToList)
    TextView tvBackToList;
    @BindView(R.id.fgv)
    FixedGridView fgv;
    private String ticketName;
    private String buyPrice;
    private String visitDate;
    private String quantity;
    @Inject
    TicketSuccessPresenter ticketSuccessPresenter;
    private List<Ticket> ticketList = new ArrayList<>();
    private TicketGvAdapter ticketGvAdapter;
    private IWXAPI api;

    private void regToWeixin() {
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID_WEIXIN, true);
        api.registerApp(Constants.APP_ID_WEIXIN);
    }
    @OnItemClick(R.id.fgv)
    public void itemClick(AdapterView<?> parent, View view, int position, long id) {
        Ticket ticket = (Ticket) parent.getItemAtPosition(position);
        TicketDetailActivity.goActivity(this, ticket.getId());
    }

    @OnClick({R.id.tv_backToList, R.id.iv_wxFrends, R.id.iv_wxFrend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_backToList:
                GoUtil.goActivity(this, TicketListActivity.class);
                break;
            case R.id.iv_wxFrends:
                SpSir.getInstance().putSharePage(Status.SharePage.NONE);
                share(SendMessageToWX.Req.WXSceneTimeline);
                break;
            case R.id.iv_wxFrend:
                SpSir.getInstance().putSharePage(Status.SharePage.NONE);
                share(SendMessageToWX.Req.WXSceneSession);
                break;
            default:
                break;
        }
    }

    @Override
    public void initVariable() {
        ticketName = getIntent().getStringExtra(Constants.Extra.TicketName);
        buyPrice = getIntent().getStringExtra(Constants.Extra.BuyPrice);
        visitDate = getIntent().getStringExtra(Constants.Extra.VisitDate);
        quantity = getIntent().getStringExtra(Constants.Extra.Quantity);
        regToWeixin();

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_ticket_success;
    }

    @Override
    protected void initComponent(AppComponent appComponent) {
        DaggerBaseCompnent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
        ticketSuccessPresenter.attachView(this);
    }
    private void share(int shareTo) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.bg_share);
        WXImageObject imgObj = new WXImageObject(bmp);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, Constants.THUMB_SIZE, Constants.THUMB_SIZE, true);
        bmp.recycle();
        msg.thumbData = ShareUtil.bmpToByteArray(thumbBmp, true);  // 设置所图；
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = shareTo;
        api.sendReq(req);
    }

    private String buildTransaction(String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
    @Override
    protected String getContentTitle() {
        return "领券成功";
    }

    @Override
    protected void initView() {
        ticketGvAdapter = new TicketGvAdapter(this, ticketList);
        fgv.setAdapter(ticketGvAdapter);

    }

    @Override
    protected void initData() {
        tvBuyPrice.setText(buyPrice);
        tvQuantity.setText(quantity);
        tvTicketName.setText(ticketName);
        tvVisitDate.setText(visitDate);
    }

    @Override
    protected void initNet() {
        ticketSuccessPresenter.getRecommendList();
    }

    public static void goActivity(Activity context, String ticketName, String buyPrice, String visitDate, String
            quantity) {
        Intent intent = new Intent(context, TicketSuccessActivity.class);
        intent.putExtra(Constants.Extra.TicketName, ticketName);
        intent.putExtra(Constants.Extra.BuyPrice, buyPrice);
        intent.putExtra(Constants.Extra.VisitDate, visitDate);
        intent.putExtra(Constants.Extra.Quantity, quantity);
        context.startActivity(intent);
        context.finish();
    }

    @Override
    public void onGetRecommendListSuccess(List<Ticket> ticketList) {
        if (ticketList != null && ticketList.size() > 0) {
            ticketGvAdapter.setData(ticketList);
        }

    }
}
