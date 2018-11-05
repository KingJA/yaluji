package com.kingja.yaluji.view;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.kingja.popwindowsir.BasePop;
import com.kingja.popwindowsir.PopConfig;
import com.kingja.yaluji.R;
import com.squareup.timessquare.CalendarCellDecorator;
import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.DefaultDayViewAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Description:TODO
 * Create Time:2018/3/22 13:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class DatePop extends BasePop {

    private OnDateSelectedListener onDateSelectedListener;
    private CalendarPickerView calendar;

    public DatePop(Context context) {
        super(context);
    }

    public DatePop(Context context, PopConfig popConfig) {
        super(context, popConfig);
    }

    @Override
    protected void initPop() {

    }

    @Override
    protected void initView(View contentView) {
        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        calendar = contentView.findViewById(R.id.calendar_view);
        TextView tv_confirm = contentView.findViewById(R.id.tv_confirm);
        TextView tv_cancel = contentView.findViewById(R.id.tv_cancel);
        calendar.setCustomDayView(new DefaultDayViewAdapter());
        calendar.setDecorators(Collections.<CalendarCellDecorator>emptyList());
        calendar.setOnInvalidDateSelectedListener(null);
        calendar.init(new Date(), nextYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.MULTIPLE);
        tv_confirm.setOnClickListener(v -> {
            if (onDateSelectedListener != null) {
                onDateSelectedListener.onDateSelected(getSelectedDates(calendar));
            }
        });
        tv_cancel.setOnClickListener(v -> {
            if (onDateSelectedListener != null) {
                onDateSelectedListener.onDateSelected("");
                calendar.init(new Date(), nextYear.getTime())
                        .inMode(CalendarPickerView.SelectionMode.MULTIPLE);
            }
        });
    }

    private String getSelectedDates(CalendarPickerView calendar) {
        List<Date> selectedDates = calendar.getSelectedDates();
        StringBuffer sb = new StringBuffer();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < selectedDates.size(); i++) {
            if (i != selectedDates.size() - 1) {
                sb.append(format.format(selectedDates.get(i)));
                sb.append(",");
            } else {
                sb.append(format.format(selectedDates.get(i)));
            }
        }
        return sb.toString();
    }

    @Override
    protected View getLayoutView() {
        return View.inflate(context, R.layout.pop_date, null);
    }


    public interface OnDateSelectedListener{
        void onDateSelected(String dates);
    }

    public void setOnDateSelectedListener(OnDateSelectedListener onDateSelectedListener) {
        this.onDateSelectedListener = onDateSelectedListener;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = 1.0f;
        ((Activity) context).getWindow().setAttributes(lp);
    }
}
