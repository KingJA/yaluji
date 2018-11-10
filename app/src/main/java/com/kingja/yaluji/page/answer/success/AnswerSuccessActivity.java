package com.kingja.yaluji.page.answer.success;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingja.yaluji.R;
import com.kingja.yaluji.base.BaseTitleActivity;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.constant.Constants;
import com.kingja.yaluji.event.RefreshQuestionEvent;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.page.article.detail.ArticleDetailActivity;
import com.kingja.yaluji.page.search.question.list.QuestionListActivity;
import com.kingja.yaluji.util.GoUtil;
import com.kingja.yaluji.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;

/**
 * Description:TODO
 * Create Time:2018/11/10 19:19
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AnswerSuccessActivity extends BaseTitleActivity implements AnswerSuccessContract.View {
    @BindView(R.id.tv_ticketName)
    TextView tvTicketName;
    @BindView(R.id.tv_quantity)
    TextView tvQuantity;
    @BindView(R.id.ll_getSuccess)
    LinearLayout llGetSuccess;
    @BindView(R.id.tv_buyPrice)
    TextView tvBuyPrice;
    @BindView(R.id.tv_visitDate)
    TextView tvVisitDate;
    private String ticketName;
    private String quantity;
    private String buyPrice;
    private String visitDate;
    private String paperId;
    private String touristId;
    @Inject
    AnswerSuccessPresenter answerSuccessPresenter;


    @OnClick({R.id.stv_share, R.id.stv_backToList})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.stv_share:
                ToastUtil.showText("分享");
                break;
            case R.id.stv_backToList:
                EventBus.getDefault().post(new RefreshQuestionEvent());
                GoUtil.goActivity(this, QuestionListActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void initVariable() {
        ticketName = getIntent().getStringExtra(Constants.Extra.TicketName);
        quantity = getIntent().getStringExtra(Constants.Extra.Quantity);
        buyPrice = getIntent().getStringExtra(Constants.Extra.BuyPrice);
        visitDate = getIntent().getStringExtra(Constants.Extra.VisitDate);
        paperId = getIntent().getStringExtra(Constants.Extra.PaperId);
        touristId = getIntent().getStringExtra(Constants.Extra.TouristId);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_answer_success;
    }

    @Override
    protected void initComponent(AppComponent appComponent) {
        DaggerBaseCompnent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
        answerSuccessPresenter.attachView(this);
    }

    @Override
    protected String getContentTitle() {
        return "领券成功";
    }

    @Override
    protected void initView() {

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
        answerSuccessPresenter.prefectVisitor(new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("paperId", paperId)
                .addFormDataPart("touristId", touristId)
                .build());
    }

    public static void goActivity(Activity context, String ticketName, String quantity, String buyPrice, String
            visitDate, String paperId, String touristId) {
        Intent intent = new Intent(context, AnswerSuccessActivity.class);
        intent.putExtra(Constants.Extra.TicketName, ticketName);
        intent.putExtra(Constants.Extra.Quantity, quantity);
        intent.putExtra(Constants.Extra.BuyPrice, buyPrice);
        intent.putExtra(Constants.Extra.VisitDate, visitDate);
        intent.putExtra(Constants.Extra.PaperId, paperId);
        intent.putExtra(Constants.Extra.TouristId, touristId);
        context.startActivity(intent);
        context.finish();
    }

    @Override
    public void onPrefectVisitorSuccess() {

    }
}
