package com.kingja.yaluji.update;

import android.app.Activity;

import com.kingja.yaluji.base.App;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.model.entiy.VersionInfo;
import com.kingja.yaluji.util.VersionUtil;
import com.kingja.yaluji.view.dialog.BaseDialog;
import com.kingja.yaluji.view.dialog.UpdateDialog;

import javax.inject.Inject;

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
