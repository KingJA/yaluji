package com.kingja.yaluji.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kingja.supershapeview.view.SuperShapeTextView;
import com.kingja.yaluji.R;
import com.kingja.yaluji.constant.Status;
import com.kingja.yaluji.imgaeloader.ImageLoader;
import com.kingja.yaluji.model.entiy.Ticket;
import com.kingja.yaluji.util.DateUtil;
import com.kingja.yaluji.view.StringTextView;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2018/1/22 16:01
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class TicketAdapter extends BaseLvAdapter<Ticket> {
    //    0待支付 3待出票 1待使用 2已使用 8已取消
    public TicketAdapter(Context context, List<Ticket> list) {
        super(context, list);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        Ticket ticket = list.get(position);
        if (ticket.getStatus() == Status.TicketSellStatus.TOSELL) {
            return Status.TicketSellStatus.TOSELL;
        } else {
            return Status.TicketSellStatus.SELLING;
        }
    }

    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        SellingViewHolder sellingViewHolder = null;
        ToSellViewHolder toSellViewHolder = null;
        int ticketSellStatus = getItemViewType(position);
        if (convertView == null) {
            if (ticketSellStatus == Status.TicketSellStatus.TOSELL) {
                convertView = View.inflate(context, R.layout.item_ticket_tosell, null);
                toSellViewHolder = new ToSellViewHolder(convertView);
                convertView.setTag(toSellViewHolder);
            } else {
                convertView = View.inflate(context, R.layout.item_ticket_selling, null);
                sellingViewHolder = new SellingViewHolder(convertView);
                convertView.setTag(sellingViewHolder);
            }
        } else {
            if (ticketSellStatus == Status.TicketSellStatus.TOSELL) {
                toSellViewHolder = (ToSellViewHolder) convertView.getTag();
            } else {
                sellingViewHolder = (SellingViewHolder) convertView.getTag();
            }
        }

        if (ticketSellStatus == Status.TicketSellStatus.TOSELL) {
            int[] deadlineDate = DateUtil.getDeadlineDate(list.get(position).getStartTime());
            toSellViewHolder.stv_date_day.setText(String.valueOf(deadlineDate[0]));
            toSellViewHolder.stv_date_hour.setText(String.valueOf(deadlineDate[1]));
            toSellViewHolder.stv_date_min.setText(String.valueOf(deadlineDate[2]));
            toSellViewHolder.tv_ticket_areaText.setText(list.get(position).getAreaText());
            toSellViewHolder.tv_ticket_title.setText(list.get(position).getTicketName());
            toSellViewHolder.tv_ticket_totalCount.setText(String.format("限量发布%d张",list.get(position).getTotalCount()));
            toSellViewHolder.dtv_ticket_marketPrice.setText(String.valueOf(list.get(position).getMarketPrice()));
            toSellViewHolder.dtv_ticket_buyPrice.setText(String.valueOf(list.get(position).getBuyPrice()));
            toSellViewHolder.tv_ticket_useDate.setText(list.get(position).getUseDate());
            toSellViewHolder.stv_ticket_buyLimit.setString(list.get(position).getBuyLimit());
            ImageLoader.getInstance().loadImage(context, list.get(position).getHeadImg(), R.drawable.ic_placeholder,
                    toSellViewHolder.iv_ticket_img);
        } else {
            int[] deadlineDate = DateUtil.getDeadlineDate(list.get(position).getEndTime());
            sellingViewHolder.stv_date_day.setText(String.valueOf(deadlineDate[0]));
            sellingViewHolder.stv_date_hour.setText(String.valueOf(deadlineDate[1]));
            sellingViewHolder.stv_date_min.setText(String.valueOf(deadlineDate[2]));
            sellingViewHolder.tv_ticket_areaText.setText(list.get(position).getAreaText());
            sellingViewHolder.tv_ticket_title.setText(list.get(position).getTicketName());
            sellingViewHolder.tv_ticket_selledCount.setText(String.valueOf(list.get(position).getSellCount()));
            sellingViewHolder.tv_ticket_totalCount.setText(String.valueOf(list.get(position).getTotalCount()));
            sellingViewHolder.dtv_ticket_marketPrice.setText(String.valueOf(list.get(position).getMarketPrice()));
            sellingViewHolder.dtv_ticket_buyPrice.setText(String.valueOf(list.get(position).getBuyPrice()));
            sellingViewHolder.tv_ticket_useDate.setText(list.get(position).getUseDate());
            sellingViewHolder.stv_ticket_buyLimit.setString(list.get(position).getBuyLimit());
            sellingViewHolder.pb_ticket_sell.setProgress(getProgressValue(list.get(position).getSellCount(), list.get
                    (position)
                    .getTotalCount()));
            ImageLoader.getInstance().loadImage(context, list.get(position).getHeadImg(), R.drawable.ic_placeholder,
                    sellingViewHolder.iv_ticket_img);
            sellingViewHolder.iv_isSellout.setVisibility(list.get(position).isSellOut() ? View.VISIBLE : View.GONE);
            sellingViewHolder.tv_ticket_qiang.setVisibility(list.get(position).isSellOut() ? View.GONE : View.VISIBLE);
        }

        return convertView;
    }

    private int getProgressValue(int sellCount, int totalCount) {
        float percent = sellCount * 1.0f / totalCount;
        return (int) (percent * 100f);
    }

    @Override
    public int getCount() {
        return list.size();
    }


    public class SellingViewHolder {
        public final View root;
        SuperShapeTextView stv_date_day;
        SuperShapeTextView stv_date_hour;
        SuperShapeTextView stv_date_min;
        TextView tv_ticket_qiang;
        TextView tv_ticket_areaText;
        TextView tv_ticket_title;
        ProgressBar pb_ticket_sell;
        TextView tv_ticket_selledCount;
        TextView tv_ticket_totalCount;
        TextView tv_ticket_useDate;
        TextView dtv_ticket_marketPrice;
        TextView dtv_ticket_buyPrice;
        StringTextView stv_ticket_buyLimit;
        ImageView iv_ticket_img;
        ImageView iv_isSellout;

        public SellingViewHolder(View root) {
            this.root = root;
            tv_ticket_qiang = root.findViewById(R.id.tv_ticket_qiang);
            stv_date_day = root.findViewById(R.id.stv_date_day);
            stv_date_hour = root.findViewById(R.id.stv_date_hour);
            stv_date_min = root.findViewById(R.id.stv_date_min);
            stv_ticket_buyLimit = root.findViewById(R.id.stv_ticket_buyLimit);
            tv_ticket_areaText = root.findViewById(R.id.tv_ticket_areaText);
            tv_ticket_title = root.findViewById(R.id.tv_ticket_title);
            pb_ticket_sell = root.findViewById(R.id.pb_ticket_sell);
            tv_ticket_selledCount = root.findViewById(R.id.tv_ticket_selledCount);
            tv_ticket_totalCount = root.findViewById(R.id.tv_ticket_totalCount);
            tv_ticket_useDate = root.findViewById(R.id.tv_ticket_useDate);
            dtv_ticket_marketPrice = root.findViewById(R.id.dtv_ticket_marketPrice);
            dtv_ticket_buyPrice = root.findViewById(R.id.tv_ticket_buyPrice);
            iv_ticket_img = root.findViewById(R.id.iv_ticket_img);
            iv_isSellout = root.findViewById(R.id.iv_isSellout);
        }
    }

    public class ToSellViewHolder {
        public final View root;
        SuperShapeTextView stv_date_day;
        SuperShapeTextView stv_date_hour;
        SuperShapeTextView stv_date_min;
        TextView tv_ticket_areaText;
        TextView tv_ticket_title;
        TextView tv_ticket_totalCount;
        TextView tv_ticket_useDate;
        TextView dtv_ticket_marketPrice;
        TextView dtv_ticket_buyPrice;
        StringTextView stv_ticket_buyLimit;
        ImageView iv_ticket_img;

        public ToSellViewHolder(View root) {
            this.root = root;
            stv_date_day = root.findViewById(R.id.stv_date_day);
            stv_date_hour = root.findViewById(R.id.stv_date_hour);
            stv_date_min = root.findViewById(R.id.stv_date_min);
            stv_ticket_buyLimit = root.findViewById(R.id.stv_ticket_buyLimit);
            tv_ticket_areaText = root.findViewById(R.id.tv_ticket_areaText);
            tv_ticket_title = root.findViewById(R.id.tv_ticket_title);
            tv_ticket_totalCount = root.findViewById(R.id.tv_ticket_totalCount);
            tv_ticket_useDate = root.findViewById(R.id.tv_ticket_useDate);
            dtv_ticket_marketPrice = root.findViewById(R.id.dtv_ticket_marketPrice);
            dtv_ticket_buyPrice = root.findViewById(R.id.tv_ticket_buyPrice);
            iv_ticket_img = root.findViewById(R.id.iv_ticket_img);
        }
    }
}
