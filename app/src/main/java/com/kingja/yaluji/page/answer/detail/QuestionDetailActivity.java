package com.kingja.yaluji.page.answer.detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.kingja.yaluji.R;
import com.kingja.yaluji.adapter.AnswerAdapter;
import com.kingja.yaluji.base.BaseTitleActivity;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.constant.Constants;
import com.kingja.yaluji.constant.Status;
import com.kingja.yaluji.event.RefreshQuestionEvent;
import com.kingja.yaluji.event.ShareSuccessEvent;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.model.entiy.Answer;
import com.kingja.yaluji.model.entiy.AnswerResult;
import com.kingja.yaluji.model.entiy.QuestionDetail;
import com.kingja.yaluji.page.answer.success.AnswerSuccessActivity;
import com.kingja.yaluji.page.relife.RelifeContract;
import com.kingja.yaluji.page.relife.RelifePresenter;
import com.kingja.yaluji.util.NoDoubleClickListener;
import com.kingja.yaluji.util.ShareUtil;
import com.kingja.yaluji.util.SoundPlayer;
import com.kingja.yaluji.util.SpSir;
import com.kingja.yaluji.view.FixedListView;
import com.kingja.yaluji.view.StringTextView;
import com.kingja.yaluji.view.dialog.ConfirmDialog;
import com.kingja.yaluji.view.dialog.QuestionFailDialog;
import com.kingja.yaluji.view.dialog.QuestionSuccessDialog;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
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
import butterknife.OnItemClick;
import okhttp3.MultipartBody;

/**
 * Description:TODO
 * Create Time:2018/11/7 23:44
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class QuestionDetailActivity extends BaseTitleActivity implements QuestionDetailContract.View, RelifeContract
        .View {

    @BindView(R.id.tv_paperTitle)
    StringTextView tvPaperTitle;
    @BindView(R.id.tv_questionTitle)
    StringTextView tvQuestionTitle;
    @BindView(R.id.flv)
    FixedListView flv;
    @BindView(R.id.tv_currentPosition)
    StringTextView tvCurrentPosition;
    @BindView(R.id.tv_totalCount)
    StringTextView tvTotalCount;
    @BindView(R.id.tv_time)
    StringTextView tvTime;
    @BindView(R.id.iv_sound)
    ImageView ivSound;
    private String paperId;
    @Inject
    QuestionDetailPresenter questionDetailPresenter;
    @Inject
    RelifePresenter relifePresenter;
    private List<Answer> answerList = new ArrayList<>();
    private AnswerAdapter answerAdapter;
    private QuestionFailDialog failDialog;
    private QuestionSuccessDialog successDialog;
    private DeadTime deadTime;
    private String questionId;
    private ConfirmDialog confirmDialog;
    private IWXAPI api;

    @OnItemClick(R.id.flv)
    public void itemClick(AdapterView<?> parent, View view, int position, long id) {
        Answer answer = (Answer) parent.getItemAtPosition(position);
        questionDetailPresenter.submitAnswer(new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("questionId", answer.getQuestionId())
                .addFormDataPart("itemId", answer.getId())
                .addFormDataPart("from", "android")
                .build());

    }


    private void regToWeixin() {
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID_WEIXIN, true);
        api.registerApp(Constants.APP_ID_WEIXIN);
    }

    @Override
    public void initVariable() {
        paperId = getIntent().getStringExtra(Constants.Extra.PaperId);
        EventBus.getDefault().register(this);
        regToWeixin();
        SoundPlayer.getInstance().init(this);

    }


    @Override
    protected int getContentView() {
        return R.layout.activity_question_detail;
    }

    @Override
    protected void initComponent(AppComponent appComponent) {
        DaggerBaseCompnent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
        questionDetailPresenter.attachView(this);
        relifePresenter.attachView(this);
    }

    @Override
    protected String getContentTitle() {
        return "鸡答";
    }

    @Override
    protected void initView() {
        answerAdapter = new AnswerAdapter(this, answerList);
        flv.setAdapter(answerAdapter);

    }

    @Override
    protected void initData() {
        resetSound();
        ivSound.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                SpSir.getInstance().putSound(!SpSir.getInstance().getSound());
                resetSound();
            }
        });
    }

    private void resetSound() {
        boolean allowSound = SpSir.getInstance().getSound();
        ivSound.setBackgroundResource(allowSound ? R.mipmap.ic_sound_on : R.mipmap.ic_sound_off);
    }

    @Override
    protected void initNet() {
        questionDetailPresenter.getQuestionDetail(paperId);
    }

    public static void goActivity(Context context, String paperId) {
        Intent intent = new Intent(context, QuestionDetailActivity.class);
        intent.putExtra(Constants.Extra.PaperId, paperId);
        context.startActivity(intent);
    }

    @Override
    public void onGetQuestionDetailSuccess(QuestionDetail questionDetail) {
        stopDeedTime();
        if (questionDetail != null) {
            QuestionDetail.PaperQuestion paperQuestion = questionDetail.getPaperQuestion();
            if (paperQuestion != null) {
                questionId = paperQuestion.getId();
                tvQuestionTitle.setString(String.format("%d.%s", questionDetail.getQuestionCount(), paperQuestion
                        .getTitle()));
            }
            List<Answer> questionItems = questionDetail.getQuestionItems();
            if (questionItems != null && questionItems.size() > 0) {
                answerAdapter.setData(questionItems);
            }
            tvCurrentPosition.setString(questionDetail.getQuestionCount());
            tvTotalCount.setString(questionDetail.getCorrectCount());
            startDeedTime();
        }

    }

    @Override
    public void onSubmitAnswerSuccess(AnswerResult answerResult) {
        stopDeedTime();
        switch (answerResult.getCorrectStatus()) {
            case Status.AnswerResult.RIGHT:
                if (SpSir.getInstance().getSound()) {
                    SoundPlayer.getInstance().playVoice(R.raw.answer_ok);
                }
                questionDetailPresenter.getQuestionDetail(paperId);
                break;
            case Status.AnswerResult.WRONG:
                if (SpSir.getInstance().getSound()) {
                    SoundPlayer.getInstance().playVoice(R.raw.answer_error);
                }
                failDialog = new QuestionFailDialog(this, answerResult.getRebornTimes());
                failDialog.setOnCancelListener(dialogInterface -> {
                    quitAndRefresh();
                });
                failDialog.setOnConfirmListener(() -> {
                    SpSir.getInstance().putSharePage(Status.SharePage.QUESTION_DETAIL);
                    share(SendMessageToWX.Req.WXSceneTimeline);
                });
                failDialog.show();
                break;
            case Status.AnswerResult.SUCCESS:
                if (SpSir.getInstance().getSound()) {
                    SoundPlayer.getInstance().playVoice(R.raw.answer_success);
                }
                successDialog = new QuestionSuccessDialog(this, answerResult.getCouponAmount(), String.valueOf
                        (answerResult.getCouponLimit()));
                successDialog.setOnCancelListener(dialogInterface -> {
                    quitAndRefresh();
                });
                successDialog.setOnVisitorSelectedListener(touristId -> {
                    AnswerSuccessActivity.goActivity(QuestionDetailActivity.this, answerResult.getCouponName(),
                            String.valueOf(answerResult.getCouponLimit()), answerResult.getCouponAmount(),
                            answerResult.getCouponPeriod(), paperId, touristId);
                });
                successDialog.show();
                break;
            case Status.AnswerResult.NO_RELIFE:
                if (SpSir.getInstance().getSound()) {
                    SoundPlayer.getInstance().playVoice(R.raw.answer_fail);
                }
                confirmDialog = new ConfirmDialog(this, "答题失败超过3次，即将退出当前页面");
                confirmDialog.setOnConfirmListener(() -> {
                    quitAndRefresh();
                });
                confirmDialog.setOnCancelListener(dialogInterface -> {
                    quitAndRefresh();
                });
                confirmDialog.show();
                break;
        }
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

    private void quitAndRefresh() {
        finish();
        EventBus.getDefault().post(new RefreshQuestionEvent());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopDeedTime();
        SoundPlayer.getInstance().stop();
    }

    private void stopDeedTime() {
        if (deadTime != null) {
            deadTime.cancel();
        }
    }

    private void startDeedTime() {
        deadTime = new DeadTime(Constants.DEAD_TIME);
        deadTime.start();
    }

    @Override
    public void onReLifeSuccess() {
        ConfirmDialog relifeSuccessDialog = new ConfirmDialog(this, "复活成功，请重新答题");
        relifeSuccessDialog.setOnConfirmListener(() -> {
            quitAndRefresh();
        });
        relifeSuccessDialog.setOnCancelListener(dialogInterface -> {
            quitAndRefresh();
        });
        relifeSuccessDialog.show();
    }

    public class DeadTime extends CountDownTimer {
        public DeadTime(int count) {
            super(count * 1000 + 50, 1000);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long leftSecond = Math.round((double) millisUntilFinished / 1000);
            tvTime.setText(String.valueOf(leftSecond));
            if (leftSecond == 4&&SpSir.getInstance().getSound()) {
                SoundPlayer.getInstance().playVoice(R.raw.answer_timeover);
            }
        }

        @Override
        public void onFinish() {
            tvTime.setText("0");
            questionDetailPresenter.submitAnswer(new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("questionId", questionId)
                    .addFormDataPart("itemId", "0")
                    .addFormDataPart("from", "android")
                    .build());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void shareSuccess(ShareSuccessEvent event) {
        if (SpSir.getInstance().getShapePage() == Status.SharePage.QUESTION_DETAIL) {
            relifePresenter.reLife(paperId);
        }
    }
}
