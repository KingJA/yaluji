package com.kingja.yaluji.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kingja.supershapeview.view.SuperShapeImageView;
import com.kingja.supershapeview.view.SuperShapeTextView;
import com.kingja.yaluji.R;
import com.kingja.yaluji.imgaeloader.ImageLoader;
import com.kingja.yaluji.model.entiy.Question;
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
public class QuestionAdapter extends BaseLvAdapter<Question> {
    public QuestionAdapter(Context context, List<Question> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_question, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.stv_question_title.setString(list.get(position).getTitle());
        viewHolder.stv_question_des.setString(String.format("连续答对%d题，获得%d元优惠券", list.get(position).getCorrectCount(),
                list.get(position).getCouponAmount()));
        ImageLoader.getInstance().loadImage(context, list.get(position).getHeadimg(), viewHolder.siv_question_headimg);
        return convertView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public class ViewHolder {
        public final View root;
        StringTextView stv_question_title;
        StringTextView stv_question_des;
        SuperShapeTextView stv_question_btn;
        SuperShapeImageView siv_question_headimg;

        public ViewHolder(View root) {
            this.root = root;
            stv_question_title = root.findViewById(R.id.stv_question_title);
            siv_question_headimg = root.findViewById(R.id.siv_question_headimg);
            stv_question_des = root.findViewById(R.id.stv_question_des);
            stv_question_btn = root.findViewById(R.id.stv_question_btn);
        }
    }
}
