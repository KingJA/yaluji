package com.kingja.yaluji.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.yaluji.R;
import com.kingja.yaluji.event.AddVisitorEvent;
import com.kingja.yaluji.event.PrfectVisitorEvent;
import com.kingja.yaluji.model.entiy.Visitor;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2018/7/12 13:14
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class VisitorTabAdapter extends BaseRvAdaper<Visitor> {
    public VisitorTabAdapter(Context context, List<Visitor> list) {
        super(context, list);
    }

    @Override
    protected ViewHolder createViewHolder(View v) {
        return new VisitorTabViewHolder(v);
    }

    @Override
    protected int getItemView() {
        return R.layout.item_visitor_tab;
    }

    @Override
    protected void bindHolder(ViewHolder baseHolder, Visitor visitor, int position) {
        final VisitorTabViewHolder holder = (VisitorTabViewHolder) baseHolder;
        holder.tv_visitor_tab.setText(visitor.getName());
        holder.iv_tab_sel.setVisibility(visitor.isSelected() ? View.VISIBLE : View.GONE);
        holder.tv_visitor_tab.setBackgroundResource(visitor.isSelected() ? R.drawable.shape_visitor_sel : R.drawable
                .shape_visitor_nor);
        holder.tv_visitor_tab.setTextColor(visitor.isSelected() ? ContextCompat.getColor(context, R.color.orange_hi) :
                ContextCompat.getColor(context, R.color.c_6));
    }

    public void addFirst(AddVisitorEvent visitorEvent) {
        for (Visitor visitor : this.list) {
            visitor.setSelected(false);
        }
        this.list.add(0,visitorEvent);
        notifyDataSetChanged();
    }

    public void prfectVisitor(PrfectVisitorEvent visitorEvent) {
        for (Visitor visitor : this.list) {
            if (visitor.getId().equals(visitorEvent.getId())) {
                visitor.setName(visitorEvent.getName());
                visitor.setMobile(visitorEvent.getMobile());
                visitor.setIdcode(visitorEvent.getIdcode());
            }
        }
        notifyDataSetChanged();
    }

    public boolean has(AddVisitorEvent visitorEvent) {
        for (int i = 0; i < list.size(); i++) {
            if (visitorEvent.getId().equals(list.get(i).getId())) {
                return true;
            }
        }
        return false;
    }

    class VisitorTabViewHolder extends ViewHolder {
        TextView tv_visitor_tab;
        ImageView iv_tab_sel;

        VisitorTabViewHolder(View itemView) {
            super(itemView);
            tv_visitor_tab = itemView.findViewById(R.id.tv_visitor_tab);
            iv_tab_sel = itemView.findViewById(R.id.iv_tab_sel);
        }
    }

    public void select(int position) {
        for (int i = 0; i < this.list.size(); i++) {
            if (i == position) {
                this.list.get(i).setSelected(true);
            } else {
                this.list.get(i).setSelected(false);
            }
        }
        notifyDataSetChanged();
    }
}
