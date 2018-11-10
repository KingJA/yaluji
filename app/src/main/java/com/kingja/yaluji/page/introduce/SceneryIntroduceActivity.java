package com.kingja.yaluji.page.introduce;

import android.content.Context;
import android.content.Intent;
import android.webkit.WebView;


import com.kingja.yaluji.R;
import com.kingja.yaluji.base.BaseTitleActivity;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.model.entiy.SceneryIntroduce;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Description:TODO
 * Create Time:2018/7/11 14:51
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class SceneryIntroduceActivity extends BaseTitleActivity implements SceneryIntroduceContract.View {
    @BindView(R.id.wb_sceneryIntrofuce)
    WebView wbSceneryIntrofuce;
    private String scenicId;
    @Inject
    SceneryIntroducePresenter sceneryIntroducePresenter;

    @Override
    public void initVariable() {
        scenicId = getIntent().getStringExtra("scenicId");
    }

    @Override
    protected void initComponent(AppComponent appComponent) {
        DaggerBaseCompnent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected String getContentTitle() {
        return "景区简介";
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_scenery_introfuce;
    }

    @Override
    protected void initView() {
        sceneryIntroducePresenter.attachView(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initNet() {
        sceneryIntroducePresenter.getSceneryIntroduce(scenicId);
    }

    public static void goActivity(Context context, String scenicId) {
        Intent intent = new Intent(context, SceneryIntroduceActivity.class);
        intent.putExtra("scenicId", scenicId);
        context.startActivity(intent);
    }

    @Override
    public void showLoading() {
        setProgressShow(true);
    }

    @Override
    public void hideLoading() {
        setProgressShow(false);
    }

    @Override
    public void onGetSceneryIntroduceSuccess(SceneryIntroduce sceneryIntroduce) {
        wbSceneryIntrofuce.loadDataWithBaseURL("about:blank", sceneryIntroduce.getContent(), "text/html", "utf-8",
                null);
    }
}
