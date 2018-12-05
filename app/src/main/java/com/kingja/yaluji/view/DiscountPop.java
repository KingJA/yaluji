package com.kingja.yaluji.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.popwindowsir.BasePop;
import com.kingja.yaluji.R;
import com.kingja.yaluji.adapter.DiscountTabAdapter;
import com.kingja.yaluji.model.entiy.Discount;
import com.kingja.yaluji.util.LogUtil;
import com.kingja.yaluji.util.NoDoubleClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:TODO
 * Create Time:2018/11/10 22:50
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class DiscountPop extends BasePop {

    private List<Discount> discountList;
    private String buyLimit = "";
    private String discountOrder = "";
    private boolean showNoLimit = true;
    private OnDiscountSelectedLintener onDiscountSelectedLintener;
    private TextView tv_noLimit;
    private ImageView iv_tab_sel;
    private ChangeNumberView cnv_discount;

    public DiscountPop(Context context) {
        super(context);
    }

    private void initData() {
        showNoLimit = true;
        discountList = new ArrayList<>();
        discountList.add(new Discount("综合排序", ""));
        discountList.add(new Discount("全额抵扣", "0"));
        discountList.add(new Discount("抵扣额升序", "1"));
        discountList.add(new Discount("抵扣额降序", "2"));
        discountList.add(new Discount("折扣率升序", "3"));
        discountList.add(new Discount("折扣率降序", "4"));
    }

    @Override
    protected void initPop() {

    }

    @Override
    protected void initView(View contentView) {
        initData();
        FixedGridView gv_discount = contentView.findViewById(R.id.gv_discount);
        tv_noLimit = contentView.findViewById(R.id.tv_noLimit);
        iv_tab_sel = contentView.findViewById(R.id.iv_tab_sel);
        cnv_discount = contentView.findViewById(R.id.cnv_discount);
        TextView tv_reset = contentView.findViewById(R.id.tv_reset);
        TextView tv_confirm = contentView.findViewById(R.id.tv_confirm);

        DiscountTabAdapter discountTabAdapter = new DiscountTabAdapter(context, discountList);
        gv_discount.setAdapter(discountTabAdapter);
        discountTabAdapter.selectItem(0);
        cnv_discount.setOnChangeNumberListener(new ChangeNumberView.OnChangeNumberListener() {
            @Override
            public void onChangeNumber(int number) {
                buyLimit = String.valueOf(number);
                showNoLimit = false;
                resetLimitTab();
            }
        });
        gv_discount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Discount discount = (Discount) adapterView.getItemAtPosition(position);
                discountOrder = discount.getDiscountCode();
                discountTabAdapter.selectItem(position);
            }
        });
        tv_reset.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (onDiscountSelectedLintener != null) {
                    buyLimit = "";
                    discountOrder = "";
                    onDiscountSelectedLintener.onDiscountSelected(discountOrder, buyLimit);
                    dismiss();
                }

            }
        });
        tv_confirm.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (onDiscountSelectedLintener != null) {
                    onDiscountSelectedLintener.onDiscountSelected(discountOrder, buyLimit);
                    dismiss();
                }
            }
        });
        LogUtil.e("initView","1showNoLimit:"+showNoLimit);
        tv_noLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNoLimit = !showNoLimit;
                LogUtil.e("点击","3showNoLimit:"+showNoLimit);
                resetLimitTab();
            }
        });
        resetLimitTab();
        LogUtil.e("initView","2showNoLimit:"+showNoLimit);
    }

    private void resetLimitTab() {
        LogUtil.e("resetLimitTab","showNoLimit:"+showNoLimit);
        iv_tab_sel.setVisibility(showNoLimit ? View.VISIBLE : View.GONE);
        tv_noLimit.setBackgroundResource(showNoLimit ? R.drawable.shape_visitor_sel : R.drawable
                .shape_visitor_nor);
        tv_noLimit.setTextColor(showNoLimit ? ContextCompat.getColor(context, R.color.orange_hi) :
                ContextCompat.getColor(context, R.color.c_6));
        buyLimit = showNoLimit ? "" : String.valueOf(cnv_discount.getNumber());
//        cnv_discount.setChangeable(!showNoLimit);
    }

    @Override
    protected View getLayoutView() {
        return View.inflate(context, R.layout.pop_discount, null);
    }

    public interface OnDiscountSelectedLintener {
        void onDiscountSelected(String discountOrder, String buyLimit);
    }

    public void setOnDiscountSelectedLintener(OnDiscountSelectedLintener onDiscountSelectedLintener) {
        this.onDiscountSelectedLintener = onDiscountSelectedLintener;
    }
}
