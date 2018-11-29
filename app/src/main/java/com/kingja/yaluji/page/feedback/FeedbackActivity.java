package com.kingja.yaluji.page.feedback;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.kingja.yaluji.R;
import com.kingja.yaluji.base.BaseTitleActivity;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.page.headimg.PersonalActivity;
import com.kingja.yaluji.util.CheckUtil;
import com.kingja.yaluji.util.DialogUtil;
import com.kingja.yaluji.util.FileUtil;
import com.kingja.yaluji.util.LogUtil;
import com.kingja.yaluji.util.ToastUtil;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Description:TODO
 * Create Time:2018/11/10 10:11
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class FeedbackActivity extends BaseTitleActivity implements FeedbackContract.View {
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_content)
    EditText etContent;
    @Inject
    FeedbackPresenter feedbackPresenter;
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;
    private String title;
    private String content;
    private RxPermissions rxPermissions;
    private static final int REQUEST_CODE_CHOOSE = 0;
    private List<Uri> photos = new ArrayList<>();
    private MultipartBody.Builder bodyBuilder;

    @OnClick({R.id.tv_confirm, R.id.iv_photo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm:
                title = etTitle.getText().toString().trim();
                content = etContent.getText().toString().trim();
                if (CheckUtil.checkEmpty(title, "请输入标题") && CheckUtil.checkEmpty(content, "请输入内容")) {
                    sendFeedBack();
                }
                break;

            case R.id.iv_photo:
                checkPhotoPermission();
                break;
            default:
                break;
        }

    }

    public void checkPhotoPermission() {
        Disposable disposable = rxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            openCamera();
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            DialogUtil.showDoubleDialog(FeedbackActivity.this, "为保证您正常浏览图片，需要获取读写手机存储权限，请允许", new
                                    MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction
                                                which) {
                                            checkPhotoPermission();
                                        }
                                    });
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            DialogUtil.showDoubleDialog(FeedbackActivity.this,
                                    "未取得读写手机存储权限，将无法为部分图片提供预览。请前往应用权限设置打开权限。", new MaterialDialog
                                            .SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction
                                                which) {
                                            startAppSettings();
                                        }
                                    });

                        }
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            photos = Matisse.obtainResult(data);
            if (photos != null && photos.size() > 0) {
                ivPhoto.setImageURI(photos.get(0));
            }
        }
    }

    private void openCamera() {
        Matisse.from(this)
                .choose(MimeType.allOf())
                .countable(true)
                .theme(R.style.PhotoTheme)//主题  暗色主题 R.style.Matisse_Dracula
                .maxSelectable(1) // 图片选择的最多数量
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f) // 缩略图的比例
                .imageEngine(new GlideEngine()) // 使用的图片加载引擎
                .forResult(REQUEST_CODE_CHOOSE); // 设置作为标记的请求码
    }

    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        startActivity(intent);
    }

    private void sendFeedBack() {
        bodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("title", title)
                .addFormDataPart("content", content);
        if (photos != null && photos.size() > 0) {
            compressPhoto(photos.get(0));
        }
        feedbackPresenter.sendNoImgFeedback(bodyBuilder.build());
    }

    private <T> void compressPhoto(Uri uri) {
        File headImgFile = FileUtil.getFileByUri(uri, this);
        Luban.with(this)
                .load(headImgFile)
                .ignoreBy(100)
                .setFocusAlpha(false)
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        LogUtil.e(TAG, "path:"+path);
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        LogUtil.e(TAG, "开始压缩");
                    }

                    @Override
                    public void onSuccess(File file) {
                        bodyBuilder.addFormDataPart("imageFile", file.getName(), RequestBody.create(MediaType.parse
                                ("image/*"), file));
                        feedbackPresenter.sendFeedback(bodyBuilder.build());
                    }

                    @Override
                    public void onError(Throwable e) {
                        File photoFile = FileUtil.getFileByUri(uri, FeedbackActivity.this);
                        bodyBuilder.addFormDataPart("imageFile", photoFile.getName(), RequestBody.create(MediaType.parse
                                ("image/*"), photoFile));
                        feedbackPresenter.sendFeedback(bodyBuilder.build());
                    }
                }).launch();
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
        feedbackPresenter.attachView(this);
    }

    @Override
    protected String getContentTitle() {
        return "我要反馈";
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initView() {
        rxPermissions = new RxPermissions(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initNet() {

    }

    @Override
    public void onSendFeedbackSuccess() {
        showSuccessAndFinish("感谢您的参与");
    }
}
