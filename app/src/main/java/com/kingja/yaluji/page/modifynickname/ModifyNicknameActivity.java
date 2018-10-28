package com.kingja.yaluji.page.modifynickname;

import android.text.Editable;
import android.view.View;
import android.widget.ImageView;

import com.kingja.supershapeview.view.SuperShapeEditText;
import com.kingja.yaluji.R;
import com.kingja.yaluji.base.BaseTitleActivity;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.event.RefreshNicknameEvent;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.util.CheckUtil;
import com.kingja.yaluji.util.SimpleTextWatcher;
import com.kingja.yaluji.util.SpSir;
import com.kingja.yaluji.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description:修改昵称
 * Create Time:2018/3/8 14:57
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ModifyNicknameActivity extends BaseTitleActivity implements ModifyNicknameContract
        .View {
    @BindView(R.id.set_nickname)
    SuperShapeEditText setNickname;
    @BindView(R.id.iv_nickname_clear)
    ImageView ivNicknameClear;

    @Inject
    ModifyNicknamePresenter modifyNicknamePresenter;
    private String nickname;

    @OnClick({R.id.iv_nickname_clear,R.id.tv_confirm})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.iv_nickname_clear:
                setNickname.setText("");
                break;
            case R.id.tv_confirm:
                nickname = setNickname.getText().toString().trim();
                if (CheckUtil.checkEmpty(nickname, "请输入昵称")) {
                    modifyNicknamePresenter.modifyNickname(nickname);
                }
                break;

        }

    }

    @Override
    public void initVariable() {

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
        return "修改昵称";
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_modify_nickname;
    }

    @Override
    protected void initView() {
        modifyNicknamePresenter.attachView(this);
    }

    @Override
    protected void initData() {
        setNickname.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                ivNicknameClear.setVisibility(s.length() > 0 ? View.VISIBLE : View.GONE);
            }
        });
        initNickname();
    }

    private void initNickname() {
        String nickname = SpSir.getInstance().getNickname();
        setNickname.setText(nickname);
        setNickname.setSelection(nickname.length());
    }

    @Override
    protected void initNet() {

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
    public void onModifyNicknameSuccess() {
        SpSir.getInstance().putNickName(nickname);
        EventBus.getDefault().post(new RefreshNicknameEvent());
        ToastUtil.showText("昵称修改成功");
        finish();
    }

}
