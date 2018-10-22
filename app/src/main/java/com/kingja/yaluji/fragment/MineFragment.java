package com.kingja.yaluji.fragment;

import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.kingja.supershapeview.view.SuperShapeImageView;
import com.kingja.yaluji.R;
import com.kingja.yaluji.base.BaseFragment;
import com.kingja.yaluji.constant.Constants;
import com.kingja.yaluji.event.MsgCountEvent;
import com.kingja.yaluji.event.RefreshHeadImgEvent;
import com.kingja.yaluji.event.RefreshNicknameEvent;
import com.kingja.yaluji.event.ResetLoginStatusEvent;
import com.kingja.yaluji.imgaeloader.ImageLoader;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.page.login.LoginActivity;
import com.kingja.yaluji.page.personal.PersonalActivity;
import com.kingja.yaluji.util.GoUtil;
import com.kingja.yaluji.util.SpSir;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jpush.android.api.JPushInterface;

/**
 * Description:我的界面
 * Create Time:2018/1/22 13:24
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class MineFragment extends BaseFragment  {

    @BindView(R.id.iv_mine_head)
    SuperShapeImageView ivMineHead;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_quit)
    TextView tvQuit;
    @BindView(R.id.tv_msgCount)
    TextView tvMsgCount;
    @BindView(R.id.rl_mine_visitor)
    RelativeLayout rlMineVisitor;
    @BindView(R.id.rl_mine_password)
    RelativeLayout rlMinePassword;
    @BindView(R.id.rl_mine_contract)
    RelativeLayout rlMineContract;
    Unbinder unbinder;
    @BindView(R.id.ll_mine_personal)
    LinearLayout llMinePersonal;

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initComponent(AppComponent appComponent) {
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        initLoginStatus();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initLoginStatus() {
        String token = SpSir.getInstance().getToken();
        if (!TextUtils.isEmpty(token)) {
            //已登录
            String nickname = SpSir.getInstance().getNickname();
            String headImg = SpSir.getInstance().getHeadImg();
            ImageLoader.getInstance().loadImage(getActivity(), headImg, ivMineHead);
            tvNickname.setText(nickname);
            tvQuit.setVisibility(View.VISIBLE);
            llMinePersonal.setOnClickListener(v -> {
                GoUtil.goActivity(getActivity(), PersonalActivity.class);
            });
        } else {
            //未登录
            tvQuit.setVisibility(View.GONE);
            tvNickname.setText("注册/登录");
            ivMineHead.setImageResource(R.mipmap.ic_logo);
            llMinePersonal.setOnClickListener(v -> {
                GoUtil.goActivity(getActivity(), LoginActivity.class);
            });
        }
    }

    @Override
    protected void initNet() {
//        VersionUpdateSir.getInstance(getActivity()).checkUpdate();
        Logger.d("RegistrationID:"+ JPushInterface.getRegistrationID(getActivity()));
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_mine;
    }


    @OnClick({R.id.iv_mine_msg, R.id.rl_mine_visitor, R.id.rl_mine_personal, R.id.rl_mine_password, R.id
            .rl_mine_contract, R.id.tv_quit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_mine_msg:
                //消息列表
//                LoginChecker.goActivity(getActivity(), MsgActivity.class);
                break;
            case R.id.rl_mine_visitor:
                //游客信息
//                LoginChecker.goActivity(getActivity(), VisitorListActivity.class);
                break;
            case R.id.rl_mine_personal:
                //个人信息
//                LoginChecker.goActivity(getActivity(), PersonalActivity.class);
                break;
            case R.id.rl_mine_password:
                //修改密码
//                LoginChecker.goActivity(getActivity(), ModifyPasswordActivity.class);
                break;
            case R.id.rl_mine_contract:
                //联系我们
//                GoUtil.goActivity(getActivity(), ContactUsActivity.class);
                break;
            case R.id.tv_quit:
                //退出登录
                showQuitDialog();
                break;
            default:
                break;
        }
    }

    public void showQuitDialog() {
        new MaterialDialog.Builder(getActivity())
                .content("确认退出账号?")
                .positiveText("确认")
                .negativeText("取消")
                .positiveColor(ContextCompat.getColor(getActivity(), R.color.gray_hi))
                .onPositive((dialog, which) -> {
                    quit();
                })
                .show();
    }

    private void quit() {
        SpSir.getInstance().clearData();
        EventBus.getDefault().post(new ResetLoginStatusEvent());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void resetLoginStatus(ResetLoginStatusEvent resetLoginStatusEvent) {
        initLoginStatus();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setNickname(RefreshNicknameEvent refreshNicknameEvent) {
        tvNickname.setText(SpSir.getInstance().getNickname());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setHeadImg(RefreshHeadImgEvent refreshHeadImgEvent) {
        String headImg = SpSir.getInstance().getHeadImg();
        ImageLoader.getInstance().loadImage(getActivity(), headImg, ivMineHead);
    }

    @Override
    public void showLoading() {
        setProgressShow(true);
    }

    @Override
    public void hideLoading() {
        setProgressShow(false);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshMsgCount(MsgCountEvent msgCountEvent) {
        resetMsgCount();
    }

    @Override
    public void onStart() {
        super.onStart();
        resetMsgCount();
//        CrashReport.testJavaCrash();
//        Beta.checkUpgrade(true,false);
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
}
