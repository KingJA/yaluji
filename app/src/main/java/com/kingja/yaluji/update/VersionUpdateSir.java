package com.kingja.yaluji.update;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.kingja.yaluji.base.App;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.constant.Constants;
import com.kingja.yaluji.model.api.TokenHeadInterceptor;
import com.kingja.yaluji.model.entiy.HttpResult;
import com.kingja.yaluji.model.entiy.VersionInfo;
import com.kingja.yaluji.model.service.ApiService;
import com.kingja.yaluji.util.AppUtil;
import com.kingja.yaluji.util.DialogUtil;
import com.kingja.yaluji.util.ToastUtil;
import com.kingja.yaluji.util.VersionUtil;
import com.kingja.yaluji.view.dialog.BaseDialog;
import com.kingja.yaluji.view.dialog.UpdateDialog;
import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Description:TODO
 * Create Time:2018/8/1 0001 下午 4:01
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class VersionUpdateSir implements UpdateContract.View {

    private Activity context;
    @Inject
    UpdatePresenter updatePresenter;

    public VersionUpdateSir(Activity context) {
        this.context = context;
        initService();
    }

    private void initService() {
        DaggerBaseCompnent.builder()
                .appComponent(App.getContext().getAppComponent())
                .build()
                .inject(this);
        updatePresenter.attachView(this);
    }

    public void checkUpdate() {
        updatePresenter.getVersion(String.valueOf(VersionUtil.getVersionCode(App.getContext())),1);
    }

    @Override
    public void onGetVersionSuccess(VersionInfo versionInfo) {
        int isLatest = versionInfo.getIsLatest();
        if (isLatest == 0) {
            //需要更新
            String downloadUrl = versionInfo.getLatestDownload();
            UpdateDialog updateDialog = new UpdateDialog(context, versionInfo);
            updateDialog.setOnConfirmListener(new BaseDialog.OnConfirmListener() {
                @Override
                public void onConfirm() {
                    new DownloadTask(context, true).execute(downloadUrl);
                }
            });
            updateDialog.show();

        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
