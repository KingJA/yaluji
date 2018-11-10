package com.kingja.yaluji.view.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingja.supershapeview.view.SuperShapeTextView;
import com.kingja.yaluji.R;
import com.kingja.yaluji.adapter.BaseRvAdaper;
import com.kingja.yaluji.adapter.VisitorTabAdapter;
import com.kingja.yaluji.base.App;
import com.kingja.yaluji.base.DaggerBaseCompnent;
import com.kingja.yaluji.constant.Constants;
import com.kingja.yaluji.event.AddVisitorEvent;
import com.kingja.yaluji.event.PrfectVisitorEvent;
import com.kingja.yaluji.model.entiy.Visitor;
import com.kingja.yaluji.page.answer.detail.QuestionDetailActivity;
import com.kingja.yaluji.page.visitor.list.VisitorListActivity;
import com.kingja.yaluji.page.visitor.single.VisitorSingleContract;
import com.kingja.yaluji.page.visitor.single.VisitorSinglePresenter;
import com.kingja.yaluji.util.LoginChecker;
import com.kingja.yaluji.util.ToastUtil;
import com.kingja.yaluji.view.RvItemDecoration;
import com.kingja.yaluji.view.StringTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description:TODO
 * Create Time:2018/11/7 0:27
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class QuestionSuccessDialog extends BaseDialog implements VisitorSingleContract.View, BaseRvAdaper
        .OnItemClickListener<Visitor> {
    @BindView(R.id.tv_ticketTitle)
    TextView tvTicketTitle;
    @BindView(R.id.stv_get_ticket)
    SuperShapeTextView stvGetTicket;
    @Inject
    VisitorSinglePresenter visitorSinglePresenter;
    @BindView(R.id.rv_tourist)
    RecyclerView rvTourist;
    @BindView(R.id.tv_visitor_name)
    StringTextView tvVisitorName;
    @BindView(R.id.tv_visitor_phone)
    StringTextView tvVisitorPhone;
    @BindView(R.id.tv_visitor_idcode)
    StringTextView tvVisitorIdcode;
    @BindView(R.id.ssll_visitor_info)
    LinearLayout ssllVisitorInfo;
    private VisitorTabAdapter visitorTabAdapter;
    private List<Visitor> visitors = new ArrayList<>();
    private String touristId;
    private OnVisitorSelectedListener onVisitorSelectedListener;
    private String payPrice;
    private final String quantity;


    @OnClick({R.id.stv_get_ticket})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.stv_get_ticket:
                if (onVisitorSelectedListener != null) {
                    onVisitorSelectedListener.onVisitorSelected(touristId);
                }
                dismiss();
                break;
            default:
                break;
        }
    }

    public QuestionSuccessDialog(@NonNull Context context, String payPrice, String quantity) {
        super(context);
        this.payPrice = payPrice;
        this.quantity = quantity;
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void initData() {
        tvTicketTitle.setText(String.format("获取%s元抵值券%s张", payPrice, quantity));
        visitorTabAdapter = new VisitorTabAdapter(getContext(), visitors);
        visitorTabAdapter.setOnItemClickListener(this);
        visitorSinglePresenter.getVisitors(Constants.PAGE_FIRST, Constants.PAGE_SIZE_100);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        rvTourist.setLayoutManager(layoutManager);
        rvTourist.setAdapter(visitorTabAdapter);
        rvTourist.setItemAnimator(new DefaultItemAnimator());
        rvTourist.addItemDecoration(new RvItemDecoration(getContext(), RvItemDecoration.LayoutStyle.HORIZONTAL_LIST,
                12, 0x00ffffff));
    }

    @Override
    protected void initView() {
        DaggerBaseCompnent.builder()
                .appComponent(App.getContext().getAppComponent())
                .build()
                .inject(this);
        visitorSinglePresenter.attachView(this);
        EventBus.getDefault().register(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_question_success;
    }

    @Override
    public void onGetVisitorsSuccess(List<Visitor> visitors) {
        if (visitors.size() == 0) {
            ssllVisitorInfo.setVisibility(View.GONE);
        } else if (hasDefault(visitors)) {
            ssllVisitorInfo.setVisibility(View.VISIBLE);
            fillVisitorInfo(visitors.get(0));
        } else {
            ssllVisitorInfo.setVisibility(View.GONE);
        }
        visitorTabAdapter.setData(getSelectVisitor(visitors));
    }

    private void fillVisitorInfo(Visitor visitor) {
        ssllVisitorInfo.setVisibility(View.VISIBLE);
        tvVisitorName.setText(visitor.getName());
        tvVisitorPhone.setText(visitor.getMobile());
        tvVisitorIdcode.setText(visitor.getIdcode());
        touristId = visitor.getId();
    }

    private List<Visitor> getSelectVisitor(List<Visitor> visitors) {
        if (visitors.size() > 2) {
            visitors = visitors.subList(0, 2);
        }
        Visitor addVisitor = new Visitor();
        addVisitor.setName("新增/更换 >");
        visitors.add(addVisitor);
        for (Visitor visitor : visitors) {
            if (visitor.getIsdefault() == 1) {
                visitor.setSelected(true);
            }
        }
        return visitors;
    }

    private boolean hasDefault(List<Visitor> visitors) {
        for (Visitor visitor : visitors) {
            if (visitor.getIsdefault() == 1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onItemClick(Visitor visitor, int position) {
        if (position == visitorTabAdapter.getItemCount() - 1) {
            Intent intent = new Intent(getContext(), VisitorListActivity.class);
            intent.putExtra(Constants.Extra.FromTitketDetail, true);
            LoginChecker.goActivity((Activity) getContext(), intent);
        } else {
            visitorTabAdapter.select(position);
            fillVisitorInfo(visitor);
        }
    }

    public interface OnVisitorSelectedListener {
        void onVisitorSelected(String touristId);
    }

    public void setOnVisitorSelectedListener(OnVisitorSelectedListener onVisitorSelectedListener) {
        this.onVisitorSelectedListener = onVisitorSelectedListener;
    }

    @Subscribe
    public void addVisitor(AddVisitorEvent visitorEvent) {
        if (visitorTabAdapter.has(visitorEvent)) {
            ToastUtil.showText("已经存在该游客信息");
            return;
        }
        visitorTabAdapter.addFirst(visitorEvent);
        fillVisitorInfo(visitorEvent);
        rvTourist.scrollToPosition(0);
    }

    @Subscribe
    public void prfectVisitors(PrfectVisitorEvent visitorEvent) {
        fillVisitorInfo(visitorEvent);
        visitorTabAdapter.prfectVisitor(visitorEvent);
    }
}
