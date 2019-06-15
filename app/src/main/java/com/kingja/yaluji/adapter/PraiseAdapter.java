package com.kingja.yaluji.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingja.yaluji.R;
import com.kingja.yaluji.constant.Constants;
import com.kingja.yaluji.constant.Status;
import com.kingja.yaluji.imgaeloader.ImageLoader;
import com.kingja.yaluji.model.entiy.PraiseItem;
import com.kingja.yaluji.util.NoDoubleClickListener;
import com.kingja.yaluji.view.StringTextView;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2018/1/22 16:01
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class PraiseAdapter extends BaseLvAdapter<PraiseItem> {
    public PraiseAdapter(Context context, List<PraiseItem> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_praise, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        PraiseItem praiseItem = list.get(position);
        viewHolder.stv_question_title.setTextColor(ContextCompat.getColor(context, R.color.c_f));
        viewHolder.stv_praise_description.setTextColor(ContextCompat.getColor(context, R.color.c_f));

        viewHolder.iv_stamp_over.setVisibility(View.GONE);
        viewHolder.tv_praise_btn.setTextColor(ContextCompat.getColor(context, R.color.orange_hi));
        viewHolder.tv_praise_btn.setBackgroundResource(R.drawable.shape_r16_white);
        viewHolder.ll_question.setBackgroundResource(Constants.BG_GRADIENTS[position % Constants.BG_GRADIENTS.length]);
        viewHolder.stv_question_title.setString(praiseItem.getTitle());
        viewHolder.stv_couponPeriod.setString(TextUtils.isEmpty(praiseItem.getCouponStartDateStr()) ? String.format
                ("有效期:获券之日起%d天有效", praiseItem.getCouponPeriod()) : String.format
                ("使用日期：%s至%s", praiseItem.getCouponStartDateStr(), praiseItem.getCouponEndDateStr()));
        viewHolder.stv_praise_description.setString(TextUtils.isEmpty(praiseItem.getDescription()) ? String.format
                ("集赞%d个以上，即获得价值%d元%s%d张", praiseItem.getLikeCount(), praiseItem.getCouponAmount(), praiseItem
                                .getTitle(),praiseItem.getCouponUnitCount()) : praiseItem.getDescription());
        switch (praiseItem.getUserStatus()) {
            case Status.PraiseStatus.Praising:
                //0 进行中 已参加：按钮显示查看详情（可点击查看)
                viewHolder.tv_praise_btn.setText("查看详情");
                break;
            case Status.PraiseStatus.UnPraised:
                //1 进行中 未参加：按钮显示去转发（可点击转发)
                viewHolder.tv_praise_btn.setText("去转发");
                break;
            case Status.PraiseStatus.OverPraised:
                //2 已结束 已参加：盖章,不变灰，按钮显示查看详情（可点击查看)
                viewHolder.tv_praise_btn.setText("查看详情");
                viewHolder.iv_stamp_over.setVisibility(View.VISIBLE);
                break;
            case Status.PraiseStatus.OverUnpraised:
                //3 已结束 未参加：盖章,变灰，按钮显示去转发（不可点击)
                viewHolder.stv_question_title.setTextColor(ContextCompat.getColor(context, R.color.c_3));
                viewHolder.stv_praise_description.setTextColor(ContextCompat.getColor(context, R.color.c_c3));
                viewHolder.tv_praise_btn.setText("去转发");
                viewHolder.tv_praise_btn.setTextColor(0xff606060);
                viewHolder.tv_praise_btn.setBackgroundResource(R.drawable.shape_r16_gray);
                viewHolder.iv_stamp_over.setVisibility(View.VISIBLE);
                viewHolder.ll_question.setBackgroundResource(R.drawable.gradient_gray);
                break;
        }
        viewHolder.tv_praise_btn.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(praiseItem);
                }
            }
        });
        ImageLoader.getInstance().loadRoundImage(context, praiseItem.getHeadimg(), R.drawable.ic_placeholder,
                viewHolder.siv_question_headimg, 8);
        return convertView;
    }

    // "userStatus": "integer,用户状态     * 0已参与点赞进行中      * 1未参与点赞      * 2已参与点赞成功      * 3已参与点赞失败"
    @Override
    public int getCount() {
        return list.size();
    }

    public class ViewHolder {
        public final View root;
        StringTextView stv_question_title;
        StringTextView stv_praise_description;
        StringTextView stv_couponPeriod;
        TextView tv_praise_btn;
        ImageView iv_stamp_over;
        ImageView siv_question_headimg;
        LinearLayout ll_question;

        public ViewHolder(View root) {
            this.root = root;
            iv_stamp_over = root.findViewById(R.id.iv_stamp_over);
            stv_question_title = root.findViewById(R.id.stv_question_title);
            siv_question_headimg = root.findViewById(R.id.siv_question_headimg);
            stv_praise_description = root.findViewById(R.id.stv_praise_description);
            stv_couponPeriod = root.findViewById(R.id.stv_couponPeriod);
            tv_praise_btn = root.findViewById(R.id.tv_praise_btn);
            ll_question = root.findViewById(R.id.ll_question);
        }
    }

}
