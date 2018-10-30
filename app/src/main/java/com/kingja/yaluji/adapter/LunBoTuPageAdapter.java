package com.kingja.yaluji.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kingja.yaluji.imgaeloader.ImageLoader;
import com.kingja.yaluji.model.entiy.LunBoTu;
import com.kingja.yaluji.util.NoDoubleClickListener;
import com.kingja.yaluji.util.ToastUtil;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2018/8/21 0021 下午 4:12
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class LunBoTuPageAdapter extends PagerAdapter {
    private Context context;
    private List<LunBoTu> lunBoTuList;

    public LunBoTuPageAdapter(Context context, List<LunBoTu> lunBoTuList) {
        this.context = context;
        this.lunBoTuList = lunBoTuList;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getCount() {
        return lunBoTuList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                ToastUtil.showText(lunBoTuList.get(position).getId());
            }
        });
        ImageLoader.getInstance().loadImage(context, lunBoTuList.get(position).getBanner(), imageView);
        container.addView(imageView);
        return imageView;
    }
}