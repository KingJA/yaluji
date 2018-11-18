package com.kingja.yaluji.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.supershapeview.core.SuperManager;
import com.kingja.supershapeview.view.SuperShapeImageView;
import com.kingja.supershapeview.view.SuperShapeLinearLayout;
import com.kingja.supershapeview.view.SuperShapeTextView;
import com.kingja.yaluji.R;
import com.kingja.yaluji.constant.Status;
import com.kingja.yaluji.imgaeloader.ImageLoader;
import com.kingja.yaluji.model.entiy.Article;
import com.kingja.yaluji.model.entiy.Order;
import com.kingja.yaluji.util.AppUtil;
import com.kingja.yaluji.util.LogUtil;
import com.kingja.yaluji.util.NoDoubleClickListener;

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
    public int getItemViewType(int position) {
        return isSmallArticle(position) ? Status.ArticleType.ARTICLE_SMALL : Status.ArticleType.ARTICLE_BIG;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        SmallViewHolder smallViewHolder = null;
        BigViewHolder bigViewHolder = null;
        int articleType = getItemViewType(position);
        if (convertView == null) {
            if (articleType == Status.ArticleType.ARTICLE_BIG) {
                convertView = View.inflate(context, R.layout.item_article_big, null);
                bigViewHolder = new BigViewHolder(convertView);
                convertView.setTag(bigViewHolder);
            } else {
                convertView = View.inflate(context, R.layout.item_article_small, null);
                smallViewHolder = new SmallViewHolder(convertView);
                convertView.setTag(smallViewHolder);
            }
        } else {
            if (articleType == Status.ArticleType.ARTICLE_BIG) {
                bigViewHolder = (BigViewHolder) convertView.getTag();
            } else {
                smallViewHolder = (SmallViewHolder) convertView.getTag();
            }
        }
        if (articleType == Status.ArticleType.ARTICLE_BIG) {
            bigViewHolder.tv_article_date.setText(list.get(position).getPeriodDate());
            bigViewHolder.tv_articleTitle.setText(list.get(position).getTitle());
            ImageLoader.getInstance().loadImage(context, list.get(position).getHeadimg(), bigViewHolder.iv_article);
        } else {
            boolean lastSmallArticle = isLastSmallArticle(position);
            smallViewHolder.tv_articleTitle.setText(list.get(position).getTitle());
            ImageLoader.getInstance().loadImage(context, list.get(position).getHeadimg(), smallViewHolder.iv_article);
            smallViewHolder.v_divider.setVisibility(lastSmallArticle ? View.GONE : View.VISIBLE);
            SuperManager superManager = smallViewHolder.ll_content.getSuperManager();
            superManager.setCorner(0,0,lastSmallArticle?AppUtil.dp2px(10):0,lastSmallArticle?AppUtil.dp2px(10):0);
            superManager.beSuperView();
        }
        return convertView;
    }

    private boolean isSmallArticle(int position) {
        if (position == 0) {
            return false;
        }
        return list.get(position).getPeriodDate().equals(list.get(position - 1).getPeriodDate());
    }

    private boolean isLastSmallArticle(int position) {
        if (position == (list.size() - 1)) {
            return true;
        }
        boolean result = list.get(position).getPeriodDate().equals(list.get(position + 1).getPeriodDate());
        LogUtil.e("ArticleAdapter", "position:" + position + " result:" + result);
        return !result;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public class SmallViewHolder {
        public final View root;
        TextView tv_articleTitle;
        ImageView iv_article;
        View v_divider;
        SuperShapeLinearLayout ll_content;

        public SmallViewHolder(View root) {
            this.root = root;
            tv_articleTitle = root.findViewById(R.id.tv_articleTitle);
            iv_article = root.findViewById(R.id.iv_article);
            v_divider = root.findViewById(R.id.v_divider);
            ll_content = root.findViewById(R.id.ll_content);
        }
    }

    public class BigViewHolder {
        public final View root;
        SuperShapeTextView tv_article_date;
        TextView tv_articleTitle;
        SuperShapeImageView iv_article;

        public BigViewHolder(View root) {
            this.root = root;
            tv_article_date = root.findViewById(R.id.tv_article_date);
            tv_articleTitle = root.findViewById(R.id.tv_articleTitle);
            iv_article = root.findViewById(R.id.iv_article);
        }
    }
}
