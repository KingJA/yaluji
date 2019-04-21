package com.kingja.yaluji.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.kingja.yaluji.R;
import com.kingja.yaluji.util.SpSir;

/**
 * Description:TODO
 * Create Time:2019/4/21 16:39
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class FirstDialogActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_dialog);
        findViewById(R.id.iv_button_close).setOnClickListener(v -> {
            finish();
        });
        findViewById(R.id.iv_close).setOnClickListener(v -> {
            finish();
        });
        SpSir.getInstance().putFirstSee(false);
    }
}
