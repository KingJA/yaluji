package com.kingja.yaluji.page.business;

import android.view.View;

import com.kingja.yaluji.R;
import com.kingja.yaluji.base.BaseTitleActivity;
import com.kingja.yaluji.constant.Status;
import com.kingja.yaluji.injector.component.AppComponent;

import butterknife.OnClick;

/**
 * Description:我要合作
 * Create Time:2018/11/10 12:02
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class BusinessActivity extends BaseTitleActivity {

    @OnClick({R.id.stv_provider, R.id.stv_advertisement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.stv_provider:
                BusinessDetailActivity.goActivity(this, Status.BusinsessType.PROVIDER);
                break;
            case R.id.stv_advertisement:
                BusinessDetailActivity.goActivity(this, Status.BusinsessType.ADVERTISEMENT);
                break;
            default:
                break;
        }
    }

    @Override
    public void initVariable() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_business;
    }

    @Override
    protected void initComponent(AppComponent appComponent) {

    }

    @Override
    protected String getContentTitle() {
        return "我要合作";
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initNet() {

    }
}
