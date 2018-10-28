package com.kingja.yaluji.imgaeloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.kingja.yaluji.R;
import com.kingja.yaluji.constant.Constants;
import com.kingja.yaluji.util.LogUtil;

/**
 * Description：TODO
 * Create Time：2017/3/9 11:08
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class GlideLoader implements IImageLoader {
    private  final String TAG =getClass().getSimpleName() ;

    @Override
    public void loadImage(Context context, String url, int resourceId, ImageView view) {
        Glide.with(context)
                .load(Constants.BASE_URL + url)
                .centerCrop()
                .placeholder(resourceId == -1 ? R.mipmap.ic_img_placeholder : resourceId)
                .error(R.mipmap.ic_img_fail)
                .crossFade()
                .into(view);
    }

    @Override
    public void loadRoundImage(Context context, String url, int resourceId, ImageView view, int connerWidth) {
        LogUtil.e(TAG,"图片地址:"+Constants.BASE_URL + url);
        Glide.with(context)
                .load(Constants.BASE_URL + url)
                .placeholder(resourceId == -1 ? R.mipmap.ic_img_placeholder : resourceId)
                .error(R.mipmap.ic_img_fail)
                .crossFade()
                .transform(new CenterCrop(context), new GlideRoundTransform(context, connerWidth))
                .into(view);
    }

    @Override
    public void loadCircleImage(Context context, String url, int resourceId, ImageView view) {
        Glide.with(context)
                .load(Constants.BASE_URL + url)
                .centerCrop()
                .placeholder(resourceId == -1 ? R.mipmap.ic_img_placeholder : resourceId)
                .error(R.mipmap.ic_img_fail)
                .crossFade()
                .transform(new CenterCrop(context), new GlideCircleTransform(context))
                .into(view);
    }
}
