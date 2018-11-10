package com.kingja.yaluji.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.supershapeview.view.SuperShapeTextView;
import com.kingja.yaluji.R;
import com.kingja.yaluji.model.entiy.Answer;
import com.kingja.yaluji.model.entiy.Discount;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2018/1/22 16:01
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class DiscountTabAdapter extends BaseLvAdapter<Discount> {
    public DiscountTabAdapter(Context context, List<Discount> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_discount_tab, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_tab.setText(list.get(position).getDiscountName());
        viewHolder.iv_tab_sel.setVisibility(selectPosition == position ? View.VISIBLE : View.GONE);
        viewHolder.tv_tab.setBackgroundResource(selectPosition == position ? R.drawable.shape_visitor_sel : R.drawable
                .shape_visitor_nor);
        viewHolder.tv_tab.setTextColor(selectPosition == position ? ContextCompat.getColor(context, R.color.orange_hi) :
                ContextCompat.getColor(context, R.color.c_6));

        return convertView;
    }


    public class ViewHolder {
        public final View root;
        public TextView tv_tab;
        public ImageView iv_tab_sel;

        public ViewHolder(View root) {
            this.root = root;
            tv_tab = root.findViewById(R.id.tv_tab);
            iv_tab_sel = root.findViewById(R.id.iv_tab_sel);
        }
    }
}
