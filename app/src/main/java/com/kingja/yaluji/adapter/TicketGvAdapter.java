package com.kingja.yaluji.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kingja.yaluji.R;
import com.kingja.yaluji.imgaeloader.ImageLoader;
import com.kingja.yaluji.model.entiy.Ticket;
import com.kingja.yaluji.view.DeleteTextView;
import com.kingja.yaluji.view.StringTextView;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2018/1/22 16:01
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class TicketGvAdapter extends BaseLvAdapter<Ticket> {
    public TicketGvAdapter(Context context, List<Ticket> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_ticket_simple, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_buyPrice_tag.setString(list.get(position).getBuyPrice());
        viewHolder.tv_ticketName.setString(list.get(position).getTicketName());
        viewHolder.tv_buyLimit.setString(list.get(position).getBuyLimit());
        viewHolder.tv_sellCount.setString(list.get(position).getSellCount());
        viewHolder.tv_totalCount.setString(list.get(position).getTotalCount());
        viewHolder.tv_marketPrice.setText(String.valueOf(list.get(position).getMarketPrice()));
        viewHolder.tv_tv_buyPrice.setString(list.get(position).getBuyPrice());
        ImageLoader.getInstance().loadImage(context, list.get(position).getHeadImg(), viewHolder.iv_headImg);
        return convertView;
    }

    private int getProgressValue(int sellCount, int totalCount) {
        float percent = sellCount * 0.1f / totalCount;
        return (int) (percent * 100f);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public class ViewHolder {
        public final View root;
        StringTextView tv_buyPrice_tag;
        StringTextView tv_ticketName;
        StringTextView tv_buyLimit;
        StringTextView tv_sellCount;
        StringTextView tv_totalCount;
        DeleteTextView tv_marketPrice;
        StringTextView tv_tv_buyPrice;
        ImageView iv_headImg;

        public ViewHolder(View root) {
            this.root = root;
            tv_buyPrice_tag = root.findViewById(R.id.tv_buyPrice_tag);
            tv_ticketName = root.findViewById(R.id.tv_ticketName);
            tv_buyLimit = root.findViewById(R.id.tv_buyLimit);
            tv_sellCount = root.findViewById(R.id.tv_sellCount);
            tv_totalCount = root.findViewById(R.id.tv_totalCount);
            tv_marketPrice = root.findViewById(R.id.tv_marketPrice);
            tv_tv_buyPrice = root.findViewById(R.id.tv_tv_buyPrice);
            iv_headImg = root.findViewById(R.id.iv_headImg);
        }
    }
}
