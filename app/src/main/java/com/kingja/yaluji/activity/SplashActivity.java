package com.kingja.yaluji.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.kingja.yaluji.HomeActivity;
import com.kingja.yaluji.R;
import com.kingja.yaluji.service.initialize.InitializeService;
import com.kingja.yaluji.util.GoUtil;


/**
 * Description:启动页
 * Create Time:2018/5/14 13:20
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class SplashActivity extends AppCompatActivity {
    private int DELAY_MILLIS = 500;
    private Handler dispatchHander;
    private DispatcherRunnable dispatcherRunnable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        dispatchHander = new Handler();
        dispatcherRunnable = new DispatcherRunnable();
        dispatchHander.postDelayed(dispatcherRunnable, DELAY_MILLIS);
        startService(new Intent(this, InitializeService.class));
    }


    class DispatcherRunnable implements Runnable {

        @Override
        public void run() {
            GoUtil.goActivityAndFinish(SplashActivity.this, HomeActivity.class);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dispatchHander.removeCallbacks(dispatcherRunnable);
    }
}
