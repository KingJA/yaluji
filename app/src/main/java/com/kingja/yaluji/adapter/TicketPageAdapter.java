package com.kingja.yaluji.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.yaluji.R;

/**
 * Description:TODO
 * Create Time:2018/12/5 20:02
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class TicketPageAdapter extends FragmentPagerAdapter {
    private final Context context;
    private Fragment[] fragments;
    private int[] tabImgs;
    private String[] titles;

    public TicketPageAdapter(AppCompatActivity context, Fragment[] fragments, int[] tabImgs, String[] titles) {
        super(context.getSupportFragmentManager());
        this.context = context;
        this.fragments = fragments;
        this.tabImgs = tabImgs;
        this.titles = titles;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_ticket, null);
        ImageView iv_tab = view.findViewById(R.id.iv_tab);
        TextView tv_tab = view.findViewById(R.id.tv_tab);
        iv_tab.setBackgroundResource(tabImgs[position]);
        tv_tab.setText(titles[position]);
        return view;
    }
}
