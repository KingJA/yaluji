package com.kingja.yaluji.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.yaluji.R;
import com.kingja.yaluji.imgaeloader.ImageLoader;

/**
 * Description:TODO
 * Create Time:2018/7/27 0027 上午 8:49
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ViewHolder {
    private final SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;
    private Context mContext;

    private ViewHolder(Context context, ViewGroup parent, int layoutId,
                       int position) {
        this.mContext = context;
        this.mPosition = position;
        this.mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        mConvertView.setTag(this);
    }

    public static ViewHolder get(Context context, View convertView,
                                 ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, position);
        }
        return (ViewHolder) convertView.getTag();
    }

    public View getConvertView() {
        return mConvertView;
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public ViewHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    public ViewHolder setText(int viewId, int text) {
        return setText(viewId, String.valueOf(text));
    }

    public ViewHolder setText(int viewId, double text) {
        return setText(viewId, String.valueOf(text));
    }

    public ViewHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);

        return this;
    }

    public ViewHolder setBackgroundResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setBackgroundResource(drawableId);
        return this;
    }

    public ViewHolder setBackgroundColor(int viewId, int colorId) {
        View view = getView(viewId);
        view.setBackgroundColor(colorId);
        return this;
    }

    public ViewHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

    public ViewHolder setVisibility(int viewId, boolean visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility ? View.VISIBLE : View.GONE);
        return this;
    }

    public ViewHolder setImageByUrl(int viewId, String url) {
        ImageView view = getView(viewId);
        ImageLoader.getInstance().loadImage(mContext, url, view);
        return this;
    }
    public ViewHolder setRoundImageByUrl(int viewId, String url,int connerWidth) {
        ImageView view = getView(viewId);
        ImageLoader.getInstance().loadRoundImage(mContext, url, R.drawable.ic_placeholder, view,connerWidth);
        return this;
    }
    public ViewHolder setCircleByUrl(int viewId, String url) {
        ImageView view = getView(viewId);
        ImageLoader.getInstance().loadCircleImage(mContext, url, R.drawable.ic_placeholder, view);
        return this;
    }

    public int getPosition() {
        return mPosition;
    }
}
