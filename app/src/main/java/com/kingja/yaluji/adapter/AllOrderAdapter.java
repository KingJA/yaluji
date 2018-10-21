package com.kingja.yaluji.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.supershapeview.view.SuperShapeTextView;
import com.kingja.yaluji.R;
import com.kingja.yaluji.constant.Status;
import com.kingja.yaluji.imgaeloader.ImageLoader;
import com.kingja.yaluji.model.entiy.Order;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2018/1/22 16:01
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AllOrderAdapter extends BaseLvAdapter<Order> {
    //    0待支付 3待出票 1待使用 2已使用 8已取消
    public AllOrderAdapter(Context context, List<Order> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_order_all, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_order_orderno.setText(list.get(position).getOrderNo());
        return convertView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public class ViewHolder {
        public final View root;
        TextView tv_order_orderno;
        TextView tv_order_area;
        TextView tv_order_title;
        TextView tv_order_payamount;
        TextView tv_order_date;
        TextView tv_order_status;
        TextView tv_order_quantity;
        SuperShapeTextView stv_order_detail;
        ImageView iv_order_stamp;
        ImageView iv_order_img;

        public ViewHolder(View root) {
            this.root = root;
            tv_order_orderno = root.findViewById(R.id.tv_order_orderno);
            tv_order_area = root.findViewById(R.id.tv_order_area);
            tv_order_title = root.findViewById(R.id.tv_order_title);
            tv_order_payamount = root.findViewById(R.id.tv_order_payamount);
            tv_order_date = root.findViewById(R.id.tv_order_date);
            tv_order_quantity = root.findViewById(R.id.tv_order_quantity);
            tv_order_status = root.findViewById(R.id.tv_order_status);
            stv_order_detail = root.findViewById(R.id.stv_order_detail);
            iv_order_stamp = root.findViewById(R.id.iv_order_stamp);
            iv_order_img = root.findViewById(R.id.iv_order_img);
        }
    }
}
