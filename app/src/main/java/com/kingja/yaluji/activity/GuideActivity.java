package com.kingja.yaluji.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.kingja.yaluji.HomeActivity;
import com.kingja.yaluji.R;
import com.kingja.yaluji.adapter.ViewAdapter;
import com.kingja.yaluji.util.GoUtil;
import com.kingja.yaluji.util.SpSir;

import java.util.ArrayList;
import java.util.List;


/**
 * Description:引导页
 * Create Time:2018/5/14 13:20
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class GuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);
        SpSir.getInstance().putFirstBoot(false);

        ViewPager vp = findViewById(R.id.vp);

        List<View> pageViews = new ArrayList<>();
        View view1 = new View(this);
        view1.setBackgroundResource(R.mipmap.bg_guide_1);
        View view2 = new View(this);
        view2.setBackgroundResource(R.mipmap.bg_guide_2);
        view2.setOnClickListener(v -> {
            GoUtil.goActivityAndFinish(GuideActivity.this, HomeActivity.class);
        });
        pageViews.add(view1);
        pageViews.add(view2);
        vp.setAdapter(new ViewAdapter(pageViews));
    }

}
