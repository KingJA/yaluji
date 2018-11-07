package com.kingja.yaluji.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kingja.supershapeview.view.SuperShapeTextView;
import com.kingja.yaluji.R;
import com.kingja.yaluji.imgaeloader.ImageLoader;
import com.kingja.yaluji.model.entiy.Ticket;
import com.kingja.yaluji.util.DateUtil;

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
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_ticket, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        int[] deadlineDate = DateUtil.getDeadlineDate(list.get(position).getEndTime());
        viewHolder.stv_date_day.setText(String.valueOf(deadlineDate[0]));
        viewHolder.stv_date_hour.setText(String.valueOf(deadlineDate[1]));
        viewHolder.stv_date_min.setText(String.valueOf(deadlineDate[2]));
        viewHolder.tv_ticket_areaText.setText(list.get(position).getAreaText());
        viewHolder.tv_ticket_title.setText(list.get(position).getTicketName());
        viewHolder.tv_ticket_selledCount.setText(String.valueOf(list.get(position).getSellCount()));
        viewHolder.tv_ticket_totalCount.setText(String.valueOf(list.get(position).getTotalCount()));
        viewHolder.dtv_ticket_marketPrice.setText(String.valueOf(list.get(position).getMarketPrice()));
        viewHolder.dtv_ticket_buyPrice.setText(String.valueOf(list.get(position).getBuyPrice()));
        viewHolder.tv_ticket_useDate.setText(list.get(position).getUseDate());
        viewHolder.pb_ticket_sell.setProgress(getProgressValue(list.get(position).getSellCount(), list.get(position)
                .getTotalCount()));
        ImageLoader.getInstance().loadImage(context, list.get(position).getHeadImg(), R.drawable.ic_placeholder,
                viewHolder.iv_ticket_img);
        viewHolder.iv_isSellout.setVisibility(list.get(position).isSellOut()?View.VISIBLE:View.GONE);
        viewHolder.tv_ticket_qiang.setVisibility(list.get(position).isSellOut()?View.GONE:View.VISIBLE);
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
        ImageView iv_ticket_img;
        ImageView iv_isSellout;

        public ViewHolder(View root) {
            this.root = root;
            tv_ticket_qiang = root.findViewById(R.id.tv_ticket_qiang);
            stv_date_day = root.findViewById(R.id.stv_date_day);
            stv_date_hour = root.findViewById(R.id.stv_date_hour);
            stv_date_min = root.findViewById(R.id.stv_date_min);
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
}
