package com.kingja.yaluji.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingja.yaluji.R;
import com.kingja.yaluji.model.entiy.Message;
import com.kingja.yaluji.page.message.MsgActivity;
import com.kingja.yaluji.view.DrawHelperLayout;

import java.util.List;


/**
 * Description:TODO
 * Create Time:2018/1/22 16:01
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class MsgAdapter extends BaseLvAdapter<Message> {
    private OnMsgOperListener onMsgOperListener;

    public MsgAdapter(Context context, List<Message> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_msg, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_msg_title.setText(list.get(position).getTitle());
        viewHolder.tv_msg_content.setText(list.get(position).getContent());
        viewHolder.tv_msg_date.setText(list.get(position).getCreatedAt());
        viewHolder.v_isread.setVisibility(list.get(position).getIsread() == 1 ? View.GONE : View.VISIBLE);

        viewHolder.ll_delete.setOnClickListener(v -> {
            if (onMsgOperListener != null) {
                onMsgOperListener.onDeleteMsg(list.get(position).getId(), position);
            }
        });
        viewHolder.drawHelperLayout.setOnRootClickListener(() -> {
            if (onMsgOperListener != null) {
                onMsgOperListener.onReadMsg(list.get(position), position);
            }
        });
        return convertView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public class ViewHolder {
        public final View root;
        TextView tv_msg_title;
        TextView tv_msg_content;
        TextView tv_msg_date;
        LinearLayout ll_delete;
        DrawHelperLayout drawHelperLayout;
        View v_isread;

        public ViewHolder(View root) {
            this.root = root;
            tv_msg_title = root.findViewById(R.id.tv_msg_title);
            tv_msg_content = root.findViewById(R.id.tv_msg_content);
            tv_msg_date = root.findViewById(R.id.tv_msg_date);
            v_isread = root.findViewById(R.id.v_isread);
            ll_delete = root.findViewById(R.id.ll_delete);
            drawHelperLayout = root.findViewById(R.id.drawHelperLayout);
        }
    }

    public interface OnMsgOperListener extends DontObfuscateInterface{
        void onDeleteMsg(String messageId, int position);

        void onReadMsg(Message message, int position);
    }

    public void setOnVistorOperListener(OnMsgOperListener onMsgOperListener) {
        this.onMsgOperListener = onMsgOperListener;
    }

    public void setRead(int position) {
        list.get(position).setIsread(MsgActivity.MSG_OPER_READ);
        notifyDataSetChanged();
    }
}
