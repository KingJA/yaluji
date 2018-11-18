package com.kingja.yaluji.update;

import android.support.annotation.NonNull;

import com.kingja.yaluji.model.api.UserApi;
import com.kingja.yaluji.model.entiy.Article;
import com.kingja.yaluji.model.entiy.ResultObserver;
import com.kingja.yaluji.model.entiy.VersionInfo;
import com.kingja.yaluji.page.article.list.ArticleListContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;


/**
 * Description：TODO
 * Create Time：2016/10/10 16:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class UpdatePresenter implements UpdateContract.Presenter {
    private UserApi mApi;
    private UpdateContract.View mView;

    @Inject
    public UpdatePresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void getVersion(String version, int flag) {
        mApi.getApiService().getVersion( version,  flag).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe
                (new ResultObserver<VersionInfo>(mView) {
                    @Override
                    protected void onSuccess(VersionInfo versionInfo) {
                        mView.onGetVersionSuccess(versionInfo);
                    }
                });
    }


    @Override
    public void attachView(@NonNull UpdateContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

}