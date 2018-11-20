package com.kingja.yaluji.page.feedback;

import android.support.annotation.NonNull;

import com.kingja.yaluji.model.api.UserApi;
import com.kingja.yaluji.model.entiy.ResultObserver;

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
public class FeedbackPresenter implements FeedbackContract.Presenter {
    private UserApi mApi;
    private FeedbackContract.View mView;

    @Inject
    public FeedbackPresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void sendFeedback(RequestBody requestBody) {
        mApi.getApiService().sendFeedback(requestBody).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe
                (new ResultObserver<Object>(mView) {
                    @Override
                    protected void onSuccess(Object object) {
                        mView.onSendFeedbackSuccess();
                    }
                });
    }

    @Override
    public void sendNoImgFeedback(RequestBody requestBody) {
        mApi.getApiService().sendNoImgFeedback(requestBody).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe
                (new ResultObserver<Object>(mView) {
                    @Override
                    protected void onSuccess(Object object) {
                        mView.onSendFeedbackSuccess();
                    }
                });
    }


    @Override
    public void attachView(@NonNull FeedbackContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

}