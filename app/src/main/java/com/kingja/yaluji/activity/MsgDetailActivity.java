package com.kingja.yaluji.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;


import com.kingja.yaluji.R;
import com.kingja.yaluji.base.BaseTitleActivity;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.model.entiy.Message;

import butterknife.BindView;

/**
 * Description:消息详情
 * Create Time:2018/3/10 10:20
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class MsgDetailActivity extends BaseTitleActivity {
    @BindView(R.id.tv_msg_title)
    TextView tvMsgTitle;
    @BindView(R.id.tv_msg_date)
    TextView tvMsgDate;
    @BindView(R.id.tv_msg_content)
    TextView tvMsgContent;
    private Message message;

    @Override
    public void initVariable() {
        message = (Message) getIntent().getSerializableExtra("message");
    }

    @Override
    protected void initComponent(AppComponent appComponent) {

    }

    @Override
    protected String getContentTitle() {
        return "消息详情";
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_msg_detail;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
         tvMsgTitle.setText(message.getTitle());
         tvMsgDate.setText(message.getCreatedAt());
         tvMsgContent.setText(message.getContent());
    }

    @Override
    protected void initNet() {

    }

    public static void goActivity(Context context, Message message) {
        Intent intent = new Intent(context, MsgDetailActivity.class);
        intent.putExtra("message",message);
        context.startActivity(intent);
    }

}
