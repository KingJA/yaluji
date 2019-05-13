package com.kingja.yaluji.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingja.supershapeview.view.SuperShapeImageView;
import com.kingja.yaluji.R;
import com.kingja.yaluji.constant.Constants;
import com.kingja.yaluji.imgaeloader.ImageLoader;
import com.kingja.yaluji.model.entiy.PraiseItem;
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

        viewHolder.ll_question.setBackgroundResource(Constants.BG_GRADIENTS[position % Constants.BG_GRADIENTS.length]);
        viewHolder.stv_question_title.setString(list.get(position).getTitle());
        viewHolder.stv_praise_require.setString(String.format("集赞%d个以上，即获得价值%d元", list.get(position).getLikeCount(),
                list.get(position).getCouponAmount()));
        viewHolder.stv_ticketInfo.setString(String.format("%s全额抵用券%d张", list.get(position).getTitle(),
                list.get(position).getCouponUnitCount()));


//        viewHolder.tv_question_btn.setText(EnumUtil.getByCode(list.get(position).getUserStatus(), Status
// .QuestionStatus
//                .class).getMsg());
//        viewHolder.tv_question_btn.setBackgroundResource(list.get(position).getUserStatus() == Status.QuestionStatus
//                .RELIFT.getCode() || list.get(position).getUserStatus() == Status.QuestionStatus.ANSWER.getCode() ? R
//                .drawable.shape_r16_white : R.drawable.shape_r16_gray);
//        viewHolder.tv_question_btn.setTextColor(list.get(position).getUserStatus() == Status.QuestionStatus
//                .RELIFT.getCode() || list.get(position).getUserStatus() == Status.QuestionStatus.ANSWER.getCode() ?
//                ContextCompat.getColor(context, R.color.orange_hi) : ContextCompat.getColor(context, R.color.c_6));
        ImageLoader.getInstance().loadRoundImage(context, list.get(position).getHeadimg(), R.drawable.ic_placeholder,
                viewHolder.siv_question_headimg, 8);
        return convertView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public class ViewHolder {
        public final View root;
        StringTextView stv_question_title;
        StringTextView stv_praise_require;
        StringTextView stv_ticketInfo;
        TextView tv_question_btn;
        ImageView siv_question_headimg;
        LinearLayout ll_question;

        public ViewHolder(View root) {
            this.root = root;
            stv_question_title = root.findViewById(R.id.stv_question_title);
            siv_question_headimg = root.findViewById(R.id.siv_question_headimg);
            stv_praise_require = root.findViewById(R.id.stv_praise_require);
            stv_ticketInfo = root.findViewById(R.id.stv_ticketInfo);
            tv_question_btn = root.findViewById(R.id.tv_question_btn);
            ll_question = root.findViewById(R.id.ll_question);
        }
    }
}
