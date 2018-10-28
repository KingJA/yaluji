package com.kingja.yaluji.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.kingja.yaluji.R;
import com.kingja.yaluji.model.entiy.Visitor;
import com.kingja.yaluji.view.DrawHelperLayout;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2018/1/22 16:01
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class VisitorAdapter extends BaseLvAdapter<Visitor> {
    private OnVistorOperListener onVistorOperListener;

    public VisitorAdapter(Context context, List<Visitor> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_visitor, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_visitor_name.setText(list.get(position).getName());
        viewHolder.tv_visitor_phone.setText(list.get(position).getMobile());
        viewHolder.tv_visitor_idcard.setText(list.get(position).getIdcode());
        viewHolder.iv_visitor_edit.setOnClickListener(v -> {
            if (onVistorOperListener != null) {
                onVistorOperListener.onEditVisitor(list.get(position));
            }
        });
        viewHolder.tv_visitor_delete.setOnClickListener(v -> {
            if (onVistorOperListener != null) {
                onVistorOperListener.onDeleteVisitor(list.get(position).getId(), position);
            }
        });
        viewHolder.ll_visitor_default.setOnClickListener(v -> {
            if (onVistorOperListener != null) {
                onVistorOperListener.onDefaultVisitor(list.get(position).getId(), position);
            }
        });
        viewHolder.drawHelperLayout.setOnRootClickListener(() -> {
            if (onVistorOperListener != null) {
                onVistorOperListener.onSelectVisitor(list.get(position));
            }
        });
        if (list.get(position).getIsdefault() == 1) {
            viewHolder.iv_visitor_default.setBackgroundResource(R.mipmap.ic_default_sel);
            viewHolder.tv_visitor_default.setText("默认游客");
            viewHolder.tv_visitor_default.setTextColor(ContextCompat.getColor(context, R.color.red_hi));
        } else {
            viewHolder.iv_visitor_default.setBackgroundResource(R.mipmap.ic_default_nor);
            viewHolder.tv_visitor_default.setText("设为默认");
            viewHolder.tv_visitor_default.setTextColor(ContextCompat.getColor(context, R.color.c_6));
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public class ViewHolder {
        public final View root;
        TextView tv_visitor_name;
        TextView tv_visitor_phone;
        TextView tv_visitor_idcard;
        TextView tv_visitor_default;
        TextView tv_visitor_delete;
        ImageView iv_visitor_default;
        ImageView iv_visitor_edit;
        LinearLayout ll_visitor_default;
        DrawHelperLayout drawHelperLayout;

        public ViewHolder(View root) {
            this.root = root;
            tv_visitor_name = root.findViewById(R.id.tv_visitor_name);
            tv_visitor_phone = root.findViewById(R.id.tv_visitor_phone);
            tv_visitor_idcard = root.findViewById(R.id.tv_visitor_idcard);
            tv_visitor_default = root.findViewById(R.id.tv_visitor_default);
            iv_visitor_default = root.findViewById(R.id.iv_visitor_default);
            iv_visitor_edit = root.findViewById(R.id.iv_visitor_edit);
            tv_visitor_delete = root.findViewById(R.id.tv_visitor_delete);
            ll_visitor_default = root.findViewById(R.id.ll_visitor_default);
            drawHelperLayout = root.findViewById(R.id.drawHelperLayout);
        }
    }

    public interface OnVistorOperListener extends DontObfuscateInterface{
        void onDeleteVisitor(String touristId, int position);
        void onDefaultVisitor(String touristId, int position);
        void onEditVisitor(Visitor visitor);
        void onSelectVisitor(Visitor visitor);
    }

    public void setOnVistorOperListener(OnVistorOperListener onVistorOperListener) {
        this.onVistorOperListener = onVistorOperListener;
    }

    public void setDefault(int position) {
        for (Visitor visitor : list) {
            visitor.setIsdefault(0);
        }
        list.get(position).setIsdefault(1);
        notifyDataSetChanged();
    }
}
