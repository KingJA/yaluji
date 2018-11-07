package com.kingja.yaluji.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.supershapeview.view.SuperShapeTextView;
import com.kingja.yaluji.R;
import com.kingja.yaluji.model.entiy.Answer;
import com.kingja.yaluji.model.entiy.ScenicType;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2018/1/22 16:01
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AnswerAdapter extends BaseLvAdapter<Answer> {
    public AnswerAdapter(Context context, List<Answer> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_answer, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.stv_answer.setText(list.get(position).getContent());

        return convertView;
    }


    public class ViewHolder {
        public final View root;
        public SuperShapeTextView stv_answer;

        public ViewHolder(View root) {
            this.root = root;
            stv_answer = root.findViewById(R.id.stv_answer);
        }
    }
}
