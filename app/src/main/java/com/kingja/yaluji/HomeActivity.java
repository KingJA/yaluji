package com.kingja.yaluji;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.yaluji.activity.FirstDialogActivity;
import com.kingja.yaluji.base.BaseTitleActivity;
import com.kingja.yaluji.fragment.MineFragment;
import com.kingja.yaluji.fragment.OrderFragment;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.page.home.HomeFragment;
import com.kingja.yaluji.update.VersionUpdateSir;
import com.kingja.yaluji.util.GoUtil;
import com.kingja.yaluji.util.SpSir;
import com.kingja.yaluji.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeActivity extends BaseTitleActivity {


    @BindView(R.id.fl_home)
    FrameLayout flHome;
    @BindView(R.id.iv_tab_duck)
    ImageView ivTabDuck;
    @BindView(R.id.tv_tab_duck)
    TextView tvTabDuck;
    @BindView(R.id.iv_tab_deer)
    ImageView ivTabDeer;
    @BindView(R.id.tv_tab_deer)
    TextView tvTabDeer;
    @BindView(R.id.iv_tab_chick)
    ImageView ivTabChick;
    @BindView(R.id.tv_tab_chick)
    TextView tvTabChick;

    private FragmentManager supportFragmentManager;
    private Fragment currentFragment;
    private static SparseArray<Fragment> fragmentMap = new SparseArray<>();
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_MESSAGE = 1;
    private static final int FRAGMENT_MINE = 2;
    private int currentTabIndex = 0;

    @OnClick({R.id.ll_tab_duck, R.id.ll_tab_deer, R.id.ll_tab_chick})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_tab_duck:
                switchFragment(FRAGMENT_HOME);
                break;
            case R.id.ll_tab_deer:
                switchFragment(FRAGMENT_MESSAGE);
                break;
            case R.id.ll_tab_chick:
                switchFragment(FRAGMENT_MINE);
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
        return R.layout.activity_home;
    }

    @Override
    protected void initComponent(AppComponent appComponent) {

    }

    @Override
    protected String getContentTitle() {
        return "";
    }

    @Override
    protected void initView() {
        supportFragmentManager = getSupportFragmentManager();
        fragmentMap.put(FRAGMENT_HOME, currentFragment = new HomeFragment());
        fragmentMap.put(FRAGMENT_MESSAGE, new OrderFragment());
        fragmentMap.put(FRAGMENT_MINE, new MineFragment());
        getSupportFragmentManager().beginTransaction().add(R.id.fl_home, currentFragment).commit();
    }

    @Override
    protected void initData() {
        if (SpSir.getInstance().isFirstSee()) {
            GoUtil.goActivity(this, FirstDialogActivity.class);
        }
    }

    @Override
    protected void initNet() {
        new VersionUpdateSir(HomeActivity.this).checkUpdate();
    }

    @Override
    protected boolean ifHideBack() {
        return true;
    }

    @Override
    protected boolean ifHideTitle() {
        return true;
    }

    private void switchFragment(int fragmentId) {
        if (fragmentId == currentTabIndex) {
            return;
        }
        Fragment targetFragment = fragmentMap.get(fragmentId);
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        if (!targetFragment.isAdded()) {
            transaction.hide(currentFragment).add(R.id.fl_home, targetFragment).commit();
        } else {
            transaction.hide(currentFragment).show(targetFragment).commit();
        }
        currentFragment = targetFragment;
        currentTabIndex = fragmentId;
        setTabStatus(fragmentId);
    }

    private void setTabStatus(int fragmentId) {
        tvTabDuck.setTextColor(fragmentId == FRAGMENT_HOME ? ContextCompat.getColor(this, R.color.orange_hi) :
                ContextCompat
                        .getColor(this, R.color.c_3));
        tvTabDeer.setTextColor(fragmentId == FRAGMENT_MESSAGE ? ContextCompat.getColor(this, R.color.orange_hi) :
                ContextCompat
                        .getColor(this, R.color.c_3));
        tvTabChick.setTextColor(fragmentId == FRAGMENT_MINE ? ContextCompat.getColor(this, R.color.orange_hi) :
                ContextCompat
                        .getColor(this, R.color.c_3));
        ivTabDuck.setBackgroundResource(fragmentId == FRAGMENT_HOME ? R.mipmap.ic_tab_duck_sel : R.mipmap
                .ic_tab_duck_nor);
        ivTabDeer.setBackgroundResource(fragmentId == FRAGMENT_MESSAGE ? R.mipmap.ic_tab_deer_sel : R.mipmap
                .ic_tab_deer_nor);
        ivTabChick.setBackgroundResource(fragmentId == FRAGMENT_MINE ? R.mipmap.ic_tab_chick_sel : R.mipmap
                .ic_tab_chick_nor);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fragmentMap.clear();
        SpSir.getInstance().clearMsgCount();
    }

    //防止Fragment重生重叠
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    }

    private long mLastTime;

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - mLastTime < 500) {
            finish();
            System.exit(0);
        } else {
            ToastUtil.showText("连续点击退出");
            mLastTime = currentTime;
        }
    }

}
