package com.kingja.yaluji.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.yaluji.R;
import com.kingja.yaluji.model.entiy.Order;
import com.kingja.yaluji.util.NoDoubleClickListener;
import com.kingja.yaluji.util.ToastUtil;
import com.kingja.yaluji.view.DrawHelperLayout;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2018/1/22 16:01
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class OrderAdapter extends BaseLvAdapter<Order> {
    private OnItemOperateListener onItemOperateListener;

    //    订单状态 1待使用 2已使用 8已取消
    public OrderAdapter(Context context, List<Order> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_order, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_ticket_payamount.setText(String.valueOf(list.get(position).getPayamount()));
        viewHolder.tv_ticket_subject.setText(list.get(position).getSubject());
        viewHolder.tv_ticket_quantity.setText(String.valueOf(list.get(position).getQuantity()));
        viewHolder.tv_ticket_visitDate.setText(list.get(position).getVisitDate());
        viewHolder.drawHelperLayout.setOnRootClickListener(() -> {
            if (onItemOperateListener != null) {
                onItemOperateListener.onItemClick(list.get(position).getId());
            }
        });
        viewHolder.tv_delete.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (onItemOperateListener != null) {
                    onItemOperateListener.onDelete(position,list.get(position).getId());
                }
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
        TextView tv_ticket_payamount;
        TextView tv_ticket_subject;
        TextView tv_ticket_quantity;
        TextView tv_ticket_visitDate;
        TextView tv_delete;
        DrawHelperLayout drawHelperLayout;
        ImageView iv_order_stamp;

        public ViewHolder(View root) {
            this.root = root;
            drawHelperLayout = root.findViewById(R.id.drawHelperLayout);
            tv_delete = root.findViewById(R.id.tv_delete);
            tv_ticket_payamount = root.findViewById(R.id.tv_ticket_payamount);
            tv_ticket_subject = root.findViewById(R.id.tv_ticket_subject);
            tv_ticket_quantity = root.findViewById(R.id.tv_ticket_quantity);
            tv_ticket_visitDate = root.findViewById(R.id.tv_ticket_visitDate);
//            iv_order_stamp = root.findViewById(R.id.iv_order_stamp);
        }
    }

    public interface OnItemOperateListener {
        void onDelete(int position, String orderId);

        void onItemClick(String orderId);
    }

    public void setOnItemOperateListener(OnItemOperateListener onItemOperateListener) {
        this.onItemOperateListener = onItemOperateListener;
    }

}
