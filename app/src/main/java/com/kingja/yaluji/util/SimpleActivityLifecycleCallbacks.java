package com.kingja.yaluji.util;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Description:TODO
 * Create Time:2018/11/17 12:42
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public abstract class SimpleActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public abstract void onActivityStarted(Activity activity);

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public abstract void onActivityStopped(Activity activity);

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
