package com.kingja.yaluji.view;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kingja.popwindowsir.BasePop;
import com.kingja.popwindowsir.PopConfig;
import com.kingja.yaluji.R;
import com.kingja.yaluji.adapter.BaseLvAdapter;
import com.kingja.yaluji.model.entiy.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:TODO
 * Create Time:2018/3/22 14:30
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class CityPop extends BasePop {

    private List<City> cities = new ArrayList<>();
    private List<City.Strict> districts = new ArrayList<>();
    private CityAdapter cityAdapter;
    private DistrictAdapter districtAdapter;
    private ListView lv_selector_city;
    private ListView lv_selector_district;
    private OnAreaSelectListener onAreaSelectListener;

    public CityPop(Context context, PopConfig popConfig) {
        super(context, popConfig);
    }

    @Override
    protected void initPop() {

    }

    public void setCities(List<City> cities) {
        City city = new City();
        city.setCityName("不限");
        this.cities = cities;
        this.cities.add(0,city);
        cityAdapter = new CityAdapter(context, this.cities);
        districtAdapter = new DistrictAdapter(context, districts);
        lv_selector_city.setAdapter(cityAdapter);
        lv_selector_district.setAdapter(districtAdapter);
        cityAdapter.setData(cities);
        lv_selector_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityAdapter.selectItem(position);
                if (position == 0) {
                    if (onAreaSelectListener != null) {
                        onAreaSelectListener.onAreaSelect("","不限");
                    }
                }else{
                    City city = (City) parent.getItemAtPosition(position);
                    if (city != null&&city.getStricts()!=null&&city.getStricts().size()>0) {
                        districtAdapter.setData(getDistricts(city));
                    }
                }
            }
        });

        lv_selector_district.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                districtAdapter.selectItem(position);
                City.Strict district = (City.Strict) parent.getItemAtPosition(position);
                if (onAreaSelectListener != null) {
                    onAreaSelectListener.onAreaSelect(district.getId(),district.getName());
                }
            }
        });
        cityAdapter.selectItem(0);
    }

    private List<City.Strict> getDistricts(City city) {
        List<City.Strict> stricts = city.getStricts();
        if (stricts != null && stricts.size() > 0) {
            if (city.getCityName().equals(stricts.get(0).getName())) {
                return stricts;
            }
        }

        City.Strict strict = new City.Strict();
        strict.setId(city.getCityId());
        strict.setName(city.getCityName());
        stricts.add(0,strict);
        return stricts;
    }

    @Override
    protected void initView(View contentView) {
        lv_selector_city = contentView.findViewById(R.id.lv_selector_city);
        lv_selector_district = contentView.findViewById(R.id.lv_selector_district);

    }

    @Override
    protected View getLayoutView() {
        return View.inflate(context, R.layout.pop_city_selector, null);
    }

    class CityAdapter extends BaseLvAdapter<City> {

        public CityAdapter(Context context, List<City> list) {
            super(context, list);
        }

        @Override
        public View simpleGetView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View
                        .inflate(context, R.layout.single_text, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tvCityName.setText(list.get(position).getCityName());
            if (position == selectPosition) {
                viewHolder.tvCityName.setText(list.get(position).getCityName());
            }
            viewHolder.tvCityName.setTextColor(position == selectPosition ? ContextCompat.getColor(context, R.color
                    .red_hi) : ContextCompat.getColor(context, R.color.c_3));
            viewHolder.tvCityName.setBackgroundColor(position == selectPosition ? ContextCompat.getColor(context, R
                    .color
                    .white_hi) : ContextCompat.getColor(context, R.color.tran_hi));
            return convertView;
        }

        public class ViewHolder {
            public final View root;
            public TextView tvCityName;

            public ViewHolder(View root) {
                this.root = root;
                tvCityName = root.findViewById(R.id.tv);
            }
        }
    }

    class DistrictAdapter extends BaseLvAdapter<City.Strict> {

        public DistrictAdapter(Context context, List<City.Strict> list) {
            super(context, list);
        }

        @Override
        public View simpleGetView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View
                        .inflate(context, R.layout.item_spiner, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tv_spiner.setText(list.get(position).getName());
            viewHolder.iv_spiner_arrow.setVisibility(position == selectPosition ? View.VISIBLE : View.GONE);
            return convertView;
        }

        public class ViewHolder {
            public final View root;
            public TextView tv_spiner;
            public ImageView iv_spiner_arrow;


            public ViewHolder(View root) {
                this.root = root;
                tv_spiner = root.findViewById(R.id.tv_spiner);
                iv_spiner_arrow = root.findViewById(R.id.iv_spiner_arrow);
            }
        }
    }
    public interface OnAreaSelectListener{
        void onAreaSelect(String areaId, String areaName);
    }

    public void setOnAreaSelectListener(OnAreaSelectListener onAreaSelectListener) {
        this.onAreaSelectListener = onAreaSelectListener;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = 1.0f;
        ((Activity) context).getWindow().setAttributes(lp);
    }
}
