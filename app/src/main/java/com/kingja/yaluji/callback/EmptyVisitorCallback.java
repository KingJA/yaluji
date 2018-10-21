package com.kingja.yaluji.callback;


import android.content.Context;
import android.view.View;

import com.kingja.loadsir.callback.Callback;
import com.kingja.yaluji.R;


/**
 * Description:TODO
 * Create Time:2017/9/4 10:20
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */

public class EmptyVisitorCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.layout_empty_visitor;
    }
    @Override
    protected boolean onReloadEvent(Context context, View view) {
//        (view.findViewById(R.id.stv_add_visitor)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                GoUtil.goActivity((Activity) context, VisitorAddActivity.class);
//            }
//        });
        return true;
    }
}
