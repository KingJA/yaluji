package com.kingja.yaluji.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingja.supershapeview.view.SuperShapeImageView;
import com.kingja.yaluji.R;
import com.kingja.yaluji.imgaeloader.ImageLoader;
import com.kingja.yaluji.model.entiy.Article;
import com.kingja.yaluji.util.LogUtil;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2018/1/22 16:01
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ArticleAdapter extends BaseLvAdapter<Article> {
    public ArticleAdapter(Context context, List<Article> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        boolean isSubArticle = isSubArticle(position);
        if (convertView == null) {
            if (isSubArticle) {
                convertView = View.inflate(context, R.layout.item_article_small, null);
            } else {
                convertView = View.inflate(context, R.layout.item_article_big, null);
            }
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (isSubArticle) {
//            viewHolder.v_divider.setVisibility(isLastSubArticle(position) ? View.GONE : View.VISIBLE);
        } else {
            viewHolder.tv_article_date.setText(list.get(position).getPeriodDate());
        }

        viewHolder.tv_articleTitle.setText(list.get(position).getTitle());
        ImageLoader.getInstance().loadImage(context, list.get(position).getHeadimg(), viewHolder.iv_article);
        return convertView;
    }

    private boolean isSubArticle(int position) {
        if (position == 0) {
            return false;
        }
        return list.get(position).getPeriodDate().equals(list.get(position - 1).getPeriodDate());
    }

    private boolean isLastSubArticle(int position) {
        if (position == (list.size() - 1)) {
            return true;
        }
        boolean result = list.get(position).getPeriodDate().equals(list.get(position + 1).getPeriodDate());
        LogUtil.e("ArticleAdapter","position:"+position +" result:"+result);
        return result;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public class ViewHolder {
        public final View root;
        LinearLayout ll_article_date;
        TextView tv_article_date;
        TextView tv_articleTitle;
        SuperShapeImageView iv_article;
        View v_divider;

        public ViewHolder(View root) {
            this.root = root;
            ll_article_date = root.findViewById(R.id.ll_article_date);
            tv_article_date = root.findViewById(R.id.tv_article_date);
            tv_articleTitle = root.findViewById(R.id.tv_articleTitle);
            iv_article = root.findViewById(R.id.iv_article);
            v_divider = root.findViewById(R.id.v_divider);
        }
    }
}
