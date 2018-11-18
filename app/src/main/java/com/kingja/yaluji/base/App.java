package com.kingja.yaluji.base;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.kingja.loadsir.core.LoadSir;
import com.kingja.yaluji.callback.EmptyCallback;
import com.kingja.yaluji.callback.EmptyMsgCallback;
import com.kingja.yaluji.callback.EmptyOrderCallback;
import com.kingja.yaluji.callback.EmptyVisitorCallback;
import com.kingja.yaluji.callback.ErrorMessageCallback;
import com.kingja.yaluji.callback.LoadingCallback;
import com.kingja.yaluji.callback.UnLoginCallback;
import com.kingja.yaluji.constant.Constants;
import com.kingja.yaluji.event.RefreshHome;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.injector.component.DaggerAppComponent;
import com.kingja.yaluji.injector.module.ApiModule;
import com.kingja.yaluji.injector.module.AppModule;
import com.kingja.yaluji.util.LogUtil;
import com.kingja.yaluji.util.SimpleActivityLifecycleCallbacks;
import com.kingja.yaluji.util.SpSir;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

import org.greenrobot.eventbus.EventBus;

import cn.jpush.android.api.JPushInterface;


/**
 * Description：App
 * Create Time：2016/10/14:04
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class App extends MultiDexApplication {
    private static App sInstance;
    private AppComponent appComponent;
    private static SharedPreferences mSharedPreferences;
    private AppModule appModule;
    private int activityCount;

    @Override
    public void onCreate() {
        super.onCreate();
        initJPush();
        initLoadSir();
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
        Logger.addLogAdapter(new AndroidLogAdapter());
        sInstance = this;
        mSharedPreferences = getSharedPreferences(Constants.APPLICATION_NAME, MODE_PRIVATE);
        setupComponent();
        initBugly();
        SpSir.getInstance().clearMsgCount();
        registerActivityLifecycleCallbacks(new SimpleActivityLifecycleCallbacks() {
            @Override
            public void onActivityStarted(Activity activity) {
                activityCount++;
                if (activityCount == 1) {
                    LogUtil.e("App", "回到前台");
                    EventBus.getDefault().post(new RefreshHome());
                }
            }

            @Override
            public void onActivityStopped(Activity activity) {
                activityCount--;
                //如果mCount==0，说明是前台到后台
                if (activityCount == 0){
                    LogUtil.e("App", "回到后台");
                }
            }
        });
    }

    private void initBugly() {
        Beta.enableNotification = true;
        Beta.autoCheckUpgrade = false;
        Bugly.init(getApplicationContext(), Constants.APP_ID_BUDLY, false);
    }

    private void initJPush() {
        // 设置开启日志,发布时请关闭日志
        JPushInterface.setDebugMode(true);
        // 初始化 JPush
        JPushInterface.init(this);
    }

    public static SharedPreferences getSp() {
        return mSharedPreferences;
    }

    private void initLoadSir() {
        LoadSir.beginBuilder()
                .addCallback(new EmptyCallback())
                .addCallback(new ErrorMessageCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new EmptyOrderCallback())
                .addCallback(new EmptyMsgCallback())
                .addCallback(new EmptyVisitorCallback())
                .addCallback(new UnLoginCallback())
                .commit();
    }

    private void setupComponent() {
        appComponent = DaggerAppComponent.builder()
                .apiModule(new ApiModule())
                .appModule(new AppModule(this))
                .build();
        appModule = new AppModule(this);
    }

    public static App getContext() {
        return sInstance;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public AppModule getAppModule() {
        return appModule;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);/*64K说拜拜*/
    }
}
