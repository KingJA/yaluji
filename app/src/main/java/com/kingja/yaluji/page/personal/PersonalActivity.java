package com.kingja.yaluji.page.personal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.kingja.supershapeview.view.SuperShapeImageView;
import com.kingja.yaluji.R;
import com.kingja.yaluji.base.BaseTitleActivity;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.event.RefreshHeadImgEvent;
import com.kingja.yaluji.event.RefreshNicknameEvent;
import com.kingja.yaluji.imgaeloader.ImageLoader;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.page.nickname.ModifyNicknameActivity;
import com.kingja.yaluji.util.DialogUtil;
import com.kingja.yaluji.util.FileUtil;
import com.kingja.yaluji.util.GoUtil;
import com.kingja.yaluji.util.SpSir;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Description:TODO
 * Create Time:2018/2/26 16:07
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class PersonalActivity extends BaseTitleActivity implements PersonalContract.View {
    @BindView(R.id.iv_personal_head)
    SuperShapeImageView ivPersonalHead;
    @BindView(R.id.rl_personal_head)
    RelativeLayout rlPersonalHead;
    @BindView(R.id.rl_personal_nickanme)
    RelativeLayout rlPersonalNickanme;
    private static final int REQUEST_CODE_CHOOSE = 0;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @Inject
    PersonalPresenter personalPresenter;

    List<Uri> mSelected;
    private RxPermissions rxPermissions;

    @OnClick({R.id.rl_personal_head, R.id.rl_personal_nickanme})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_personal_head:
                checkPhotoPermission();
                break;
            case R.id.rl_personal_nickanme:
                GoUtil.goActivity(this, ModifyNicknameActivity.class);
                break;
            default:
                break;
        }
    }

    public void checkPhotoPermission() {

        Disposable disposable = rxPermissions.requestEach(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            openCamera();
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            DialogUtil.showDoubleDialog(PersonalActivity.this, "为保证您正常浏览图片，需要获取读写手机存储权限，请允许", new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    checkPhotoPermission();
                                }
                            });
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            DialogUtil.showDoubleDialog(PersonalActivity.this, "未取得读写手机存储权限，将无法为部分图片提供预览。请前往应用权限设置打开权限。", new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    startAppSettings();
                                }
                            });

                        }
                    }
                });

    }
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        startActivity(intent);
    }

    private void openCamera() {
        Matisse.from(this)
                .choose(MimeType.allOf())
                .countable(true)
//                .capture(true)
                .theme(R.style.PhotoTheme)//主题  暗色主题 R.style.Matisse_Dracula
                .maxSelectable(1) // 图片选择的最多数量
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f) // 缩略图的比例
                .imageEngine(new GlideEngine()) // 使用的图片加载引擎
                .forResult(REQUEST_CODE_CHOOSE); // 设置作为标记的请求码
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            uploadHeadImg(mSelected.get(0));
        }
    }

    private void uploadHeadImg(Uri uri) {
        Logger.d("uri:" + uri.toString());
        File headImgFile = FileUtil.getFileByUri(uri, this);
        RequestBody body = RequestBody.create(MediaType.parse("image/jpg"), headImgFile);
        MultipartBody.Part photoPart = MultipartBody.Part.createFormData("headimg", headImgFile.getName(), body);
        personalPresenter.uploadHeadImg(photoPart);
    }

    @Override
    public void initVariable() {
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
        return "个人信息";
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_personal;
    }

    @Override
    protected void initView() {
        personalPresenter.attachView(this);
        rxPermissions = new RxPermissions(this);
    }

    @Override
    protected void initData() {
        String headImg = SpSir.getInstance().getHeadImg();
        ImageLoader.getInstance().loadImage(this, headImg, ivPersonalHead);
        tvNickname.setText(SpSir.getInstance().getNickname());
    }

    @Override
    protected void initNet() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setNickname(RefreshNicknameEvent refreshNicknameEvent) {
        tvNickname.setText(SpSir.getInstance().getNickname());
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
    public void onUploadHeadImgSuccess(String url) {
        SpSir.getInstance().putHeadImg(url);
        EventBus.getDefault().post(new RefreshHeadImgEvent());
        ImageLoader.getInstance().loadImage(this, url, ivPersonalHead);
    }
}
