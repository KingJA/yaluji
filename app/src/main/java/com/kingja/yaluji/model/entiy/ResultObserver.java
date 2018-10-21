package com.kingja.yaluji.model.entiy;

import android.util.Log;

import com.google.gson.Gson;
import com.kingja.yaluji.base.BaseView;
import com.kingja.yaluji.constant.Status;
import com.kingja.yaluji.event.ResetLoginStatusEvent;
import com.kingja.yaluji.util.RxRe;
import com.kingja.yaluji.util.SpSir;
import com.kingja.yaluji.util.ToastUtil;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.observers.DefaultObserver;

/**
 * Description：TODO
 * Create Time：2016/10/12 15:56
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public abstract class ResultObserver<T> extends DefaultObserver<HttpResult<T>> {
    private static final String TAG = "ResultObserver";
    private BaseView baseView;

    public ResultObserver(BaseView baseView) {
        this.baseView = baseView;
    }

    @Override
    protected void onStart() {
        super.onStart();
        baseView.showLoading();
        RxRe.getInstance().add(baseView, this);
        Log.e(TAG, "onStart: ");
    }

    @Override
    public void onNext(HttpResult<T> httpResult) {

        Logger.json(new Gson().toJson(httpResult));
        baseView.hideLoading();
        if (httpResult.getCode() == Status.ResultCode.SUCCESS) {
            onSuccess(httpResult.getData());
        } else if (httpResult.getCode() == Status.ResultCode.ERROR_SERVER) {
            onServerError(httpResult.getCode(), httpResult.getMsg());
        } else if (httpResult.getCode() == Status.ResultCode.ERROR_LOGIN_FAIL) {
            onLoginFail();
        } else {
            onError(httpResult.getCode(), httpResult.getMsg());
        }
    }

    protected abstract void onSuccess(T t);

    protected void onError(int code, String msg) {
        ToastUtil.showText(msg);
    }

    protected void onServerError(int code, String msg) {
        ToastUtil.showText("系统错误，请联系客服");
    }

    protected void onLoginFail() {
        ToastUtil.showText("用户未登录或登录已过期，请重新登录");
        SpSir.getInstance().clearData();
        EventBus.getDefault().post(new ResetLoginStatusEvent());
    }


    @Override
    public void onError(Throwable e) {
        //记录错误
        Log.e(TAG, "onError: " + e.toString());
        baseView.hideLoading();
    }

    @Override
    public void onComplete() {
        Log.e(TAG, "onComplete: ");
    }

    public void cancleRequest() {
        Log.e(TAG, "cancleRequest: ");
        cancel();
    }
}
