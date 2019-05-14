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
import com.kingja.yaluji.constant.Status;
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
        PraiseItem praiseItem = list.get(position);
        viewHolder.ll_question.setBackgroundResource(Constants.BG_GRADIENTS[position % Constants.BG_GRADIENTS.length]);
        viewHolder.stv_question_title.setString(praiseItem.getTitle());
        viewHolder.stv_praise_require.setString(String.format("集赞%d个以上，即获得价值%d元", praiseItem.getLikeCount(),
                praiseItem.getCouponAmount()));
        viewHolder.stv_ticketInfo.setString(String.format("%s全额抵用券%d张", praiseItem.getTitle(),
                praiseItem.getCouponUnitCount()));
        switch (praiseItem.getUserStatus()) {
            case Status.PraiseStatus.Praising:
                //0已参与点赞进行中
                viewHolder.tv_praise_btn.setText("查看详情");
                break;
            case Status.PraiseStatus.UnPraised:
                //1未参与点赞
                viewHolder.tv_praise_btn.setText("去转发");
                break;
            case Status.PraiseStatus.PraisedSuccess:
                //2已参与点赞成功
                viewHolder.tv_praise_btn.setText("查看详情");
                break;
            case Status.PraiseStatus.PraisedFail:
                //3已参与点赞失败
                viewHolder.tv_praise_btn.setText("查看详情");
                break;
        }


//        viewHolder.tv_question_btn.setText(EnumUtil.getByCode(list.get(position).getUserStatus(), Status
// .QuestionStatus
//                .class).getMsg());
//        viewHolder.tv_question_btn.setBackgroundResource(list.get(position).getUserStatus() == Status.QuestionStatus
//                .RELIFT.getCode() || list.get(position).getUserStatus() == Status.QuestionStatus.ANSWER.getCode() ? R
//                .drawable.shape_r16_white : R.drawable.shape_r16_gray);
//        viewHolder.tv_question_btn.setTextColor(list.get(position).getUserStatus() == Status.QuestionStatus
//                .RELIFT.getCode() || list.get(position).getUserStatus() == Status.QuestionStatus.ANSWER.getCode() ?
//                ContextCompat.getColor(context, R.color.orange_hi) : ContextCompat.getColor(context, R.color.c_6));
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
        StringTextView stv_praise_require;
        StringTextView stv_ticketInfo;
        TextView tv_praise_btn;
        ImageView siv_question_headimg;
        LinearLayout ll_question;

        public ViewHolder(View root) {
            this.root = root;
            stv_question_title = root.findViewById(R.id.stv_question_title);
            siv_question_headimg = root.findViewById(R.id.siv_question_headimg);
            stv_praise_require = root.findViewById(R.id.stv_praise_require);
            stv_ticketInfo = root.findViewById(R.id.stv_ticketInfo);
            tv_praise_btn = root.findViewById(R.id.tv_praise_btn);
            ll_question = root.findViewById(R.id.ll_question);
        }
    }
}
