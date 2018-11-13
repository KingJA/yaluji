package com.kingja.yaluji.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kingja.yaluji.imgaeloader.ImageLoader;
import com.kingja.yaluji.model.entiy.LunBoTu;
import com.kingja.yaluji.util.LogUtil;
import com.kingja.yaluji.util.NoDoubleClickListener;
import com.kingja.yaluji.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:TODO
 * Create Time:2018/8/21 0021 下午 4:12
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class LunBoTuPageAdapter extends PagerAdapter {
    private List<ImageView> lunBoTuList = new ArrayList<>();

    public LunBoTuPageAdapter(List<ImageView> lunBoTuList) {
        this.lunBoTuList = lunBoTuList;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(lunBoTuList.get(position % lunBoTuList.size()));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = lunBoTuList.get(position % lunBoTuList.size());
        if(imageView.getParent()!=null){
            ((ViewPager)imageView.getParent()).removeView(imageView);
        }
        container.addView(imageView);
        return imageView;
    }
}
