package com.kingja.yaluji.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.yaluji.R;
import com.kingja.yaluji.constant.Status;
import com.kingja.yaluji.model.entiy.Order;
import com.kingja.yaluji.util.LogUtil;
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
    final int TYPE_TO_USED = 0;
    final int TYPE_FINISHED = 1;

    public OrderAdapter(Context context, List<Order> list) {
        super(context, list);
    }

    @Override
    public int getItemViewType(int position) {
        Order order = list.get(position);
        if (order.getStatus() == Status.OrderStatus.USED.getCode() || order.getStatus() == Status.OrderStatus
                .OVER_TIME.getCode()) {
            return TYPE_FINISHED;
        } else {
            return TYPE_TO_USED;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        FinishedViewHolder finishViewHolder = null;
        int orderType = getItemViewType(position);
        Order order = list.get(position);
        if (convertView == null) {
            if (orderType == TYPE_FINISHED) {
                convertView = View.inflate(context, R.layout.item_order_finish, null);
                finishViewHolder = new FinishedViewHolder(convertView);
                convertView.setTag(finishViewHolder);
            } else {
                convertView = View.inflate(context, R.layout.item_order, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
        } else {
            if (orderType == TYPE_FINISHED) {
                finishViewHolder = (FinishedViewHolder) convertView.getTag();
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
        }
        if (orderType == TYPE_FINISHED) {
            finishViewHolder.tv_ticket_payamount.setText(String.valueOf(order.getPayamount()));
            finishViewHolder.tv_ticket_subject.setText(order.getSubject());
            finishViewHolder.tv_ticket_quantity.setText(String.valueOf(order.getQuantity()));
            finishViewHolder.tv_ticket_visitDate.setText(order.getVisitDate());
            finishViewHolder.drawHelperLayout.setDragable(!(order.getStatus() == Status.OrderStatus.TO_USED.getCode()));
            finishViewHolder.drawHelperLayout.setOnRootClickListener(() -> {
                if (onItemOperateListener != null) {
                    onItemOperateListener.onItemClick(order.getId());
                }
            });
            finishViewHolder.tv_delete.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    if (onItemOperateListener != null) {
                        onItemOperateListener.onDelete(position, order.getId());
                    }
                }
            });
            finishViewHolder.drawHelperLayout.setDragable(true);
            if (order.getStatus() == Status.OrderStatus.USED.getCode()) {
                finishViewHolder.iv_order_stamp.setBackgroundResource(R.mipmap.bg_stamp_used);
            }
            if (order.getStatus() == Status.OrderStatus.OVER_TIME.getCode()) {
                finishViewHolder.iv_order_stamp.setBackgroundResource(R.mipmap.bg_stamp_pasted);
            }
        } else {
            viewHolder.tv_ticket_payamount.setText(String.valueOf(order.getPayamount()));
            viewHolder.tv_ticket_subject.setText(order.getSubject());
            viewHolder.tv_ticket_quantity.setText(String.valueOf(order.getQuantity()));
            viewHolder.tv_ticket_visitDate.setText(order.getVisitDate());
            viewHolder.drawHelperLayout.setDragable(false);
            viewHolder.drawHelperLayout.setOnRootClickListener(() -> {
                if (onItemOperateListener != null) {
                    onItemOperateListener.onItemClick(order.getId());
                }
            });
        }


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

        public ViewHolder(View root) {
            this.root = root;
            drawHelperLayout = root.findViewById(R.id.drawHelperLayout);
            tv_delete = root.findViewById(R.id.tv_delete);
            tv_ticket_payamount = root.findViewById(R.id.tv_ticket_payamount);
            tv_ticket_subject = root.findViewById(R.id.tv_ticket_subject);
            tv_ticket_quantity = root.findViewById(R.id.tv_ticket_quantity);
            tv_ticket_visitDate = root.findViewById(R.id.tv_ticket_visitDate);
        }
    }

    public class FinishedViewHolder {
        public final View root;
        TextView tv_ticket_payamount;
        TextView tv_ticket_subject;
        TextView tv_ticket_quantity;
        TextView tv_ticket_visitDate;
        TextView tv_delete;
        DrawHelperLayout drawHelperLayout;
        ImageView iv_order_stamp;

        public FinishedViewHolder(View root) {
            this.root = root;
            drawHelperLayout = root.findViewById(R.id.drawHelperLayout);
            tv_delete = root.findViewById(R.id.tv_delete);
            tv_ticket_payamount = root.findViewById(R.id.tv_ticket_payamount);
            tv_ticket_subject = root.findViewById(R.id.tv_ticket_subject);
            tv_ticket_quantity = root.findViewById(R.id.tv_ticket_quantity);
            tv_ticket_visitDate = root.findViewById(R.id.tv_ticket_visitDate);
            iv_order_stamp = root.findViewById(R.id.iv_order_stamp);
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
