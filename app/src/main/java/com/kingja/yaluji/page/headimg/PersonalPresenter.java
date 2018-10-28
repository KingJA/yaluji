package com.kingja.yaluji.page.headimg;

import android.support.annotation.NonNull;


import com.kingja.yaluji.model.api.UserApi;
import com.kingja.yaluji.model.entiy.ResultObserver;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;


/**
 * Description：TODO
 * Create Time：2016/10/10 16:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class PersonalPresenter implements PersonalContract.Presenter {
    private UserApi mApi;
    private PersonalContract.View mView;

    @Inject
    public PersonalPresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void uploadHeadImg(MultipartBody.Part headImg) {
        mApi.getApiService().uploadHeadImg(headImg).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe
                (new ResultObserver<String>(mView) {
                    @Override
                    protected void onSuccess(String url) {
                        mView.onUploadHeadImgSuccess(url);
                    }
                });
    }


    @Override
    public void attachView(@NonNull PersonalContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

}