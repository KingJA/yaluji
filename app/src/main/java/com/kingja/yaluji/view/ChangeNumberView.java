package com.kingja.yaluji.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * Description:TODO
 * Create Time:2018/7/11 15:13
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ChangeNumberView extends View {

    private int mWidth;
    private int mHeight;
    private int mStrokeWidth = 2;
    private int mStrokeRadius = 6;
    private Paint mStrokePaint;
    private int currentNumber = 1;
    private int maxNumber = 5;
    private int currentStatus;
    private float bothLeft;
    private float bothRight;
    private float top;
    private float bottom;
    private float addLeft;
    private float addRight;
    private float reduceLeft;
    private float reduceRight;
    private Paint mActionStrokePaint;
    private Paint addPaint;
    private Paint numberPaint;
    private String addText = "+";
    private String reduceText = "-";
    private float reduceTextX;
    private float addTextX;
    private float textY;
    private float perWidth;
    private Paint reducePaint;

    private int disabledColor = 0xffbebebe;
    private int ableColor = 0xffff0000;
    private OnChangeNumberListener onChangeNumberListener;

    public ChangeNumberView(Context context) {
        this(context, null);
    }

    public ChangeNumberView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChangeNumberView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initChangeCountView();
    }

    private void initChangeCountView() {
        mStrokePaint = new Paint();
        mStrokePaint.setColor(disabledColor);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setAntiAlias(true);
        mStrokePaint.setStrokeWidth(mStrokeWidth);

        mActionStrokePaint = new Paint();
        mActionStrokePaint.setColor(ableColor);
        mActionStrokePaint.setStyle(Paint.Style.STROKE);
        mActionStrokePaint.setAntiAlias(true);
        mActionStrokePaint.setStrokeWidth(mStrokeWidth);

        numberPaint = new Paint();
        numberPaint.setColor(0xff333333);
        numberPaint.setTextSize(sp2px(15));

        addPaint = new Paint();
        addPaint.setColor(disabledColor);
        addPaint.setTextSize(sp2px(15));

        reducePaint = new Paint();
        reducePaint.setColor(disabledColor);
        reducePaint.setTextSize(sp2px(15));
    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        perWidth = mWidth / 3.0f;
        addLeft = perWidth;
        addRight = mWidth - mStrokeWidth * 0.5f;
        reduceLeft = mStrokeWidth * 0.5f;
        reduceRight = perWidth * 2.0f;
        bothLeft = mStrokeWidth * 0.5f;
        top = mStrokeWidth * 0.5f;
        bothRight = mWidth - mStrokeWidth * 0.5f;
        bottom = mHeight - mStrokeWidth * 0.5f;

        float addTextWidth = addPaint.measureText(addText);
        float reduceTextWidth = addPaint.measureText(reduceText);
        reduceTextX = perWidth / 2.0f - reduceTextWidth / 2.0f;
        addTextX = reduceRight + perWidth / 2.0f - addTextWidth / 2.0f;
        Paint.FontMetrics fontMetrics = addPaint.getFontMetrics();
        textY = mHeight / 2.0f + (fontMetrics.descent - fontMetrics.ascent) * 0.5f - fontMetrics.descent;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(new RectF(bothLeft, top, bothRight, bottom), mStrokeRadius, mStrokeRadius, mStrokePaint);
        canvas.drawLine(addLeft, top, addLeft, bottom, mStrokePaint);
        canvas.drawLine(reduceRight, top, reduceRight, bottom, mStrokePaint);
        setOperTextColor();
        canvas.drawText(reduceText, reduceTextX, textY, reducePaint);
        canvas.drawText(addText, addTextX, textY, addPaint);
        String number = String.valueOf(currentNumber);
        float numberWidth = numberPaint.measureText(number);
        float numberTextX = perWidth + perWidth / 2.0f - numberWidth / 2.0f;
        canvas.drawText(number, numberTextX, textY, numberPaint);

        switch (currentStatus) {
            case Status.DISABLED:
                break;
            case Status.ADDABLE:
                canvas.drawRoundRect(new RectF(addLeft, top, addRight, bottom), mStrokeRadius, mStrokeRadius,
                        mActionStrokePaint);
                canvas.drawLine(reduceRight, top, reduceRight, bottom, mActionStrokePaint);
                break;
            case Status.REDUCEABLE:
                canvas.drawRoundRect(new RectF(reduceLeft, top, reduceRight, bottom), mStrokeRadius, mStrokeRadius,
                        mActionStrokePaint);
                canvas.drawLine(addLeft, top, addLeft, bottom, mActionStrokePaint);
                canvas.drawLine(reduceRight, top, reduceRight, bottom, mActionStrokePaint);
                break;
            case Status.BOTHABLE:
                canvas.drawRoundRect(new RectF(bothLeft, top, bothRight, bottom), mStrokeRadius, mStrokeRadius,
                        mActionStrokePaint);
                canvas.drawLine(addLeft, top, addLeft, bottom, mActionStrokePaint);
                canvas.drawLine(reduceRight, top, reduceRight, bottom, mActionStrokePaint);
                break;
            default:
                break;
        }

    }

    public void setOperTextColor() {
        switch (currentStatus) {
            case Status.DISABLED:
                addPaint.setColor(disabledColor);
                reducePaint.setColor(disabledColor);
                break;
            case Status.ADDABLE:
                addPaint.setColor(ableColor);
                reducePaint.setColor(disabledColor);
                break;
            case Status.REDUCEABLE:
                addPaint.setColor(ableColor);
                reducePaint.setColor(ableColor);
                break;
            case Status.BOTHABLE:
                addPaint.setColor(ableColor);
                reducePaint.setColor(ableColor);
                break;
            default:
                break;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            float x = event.getX();
            if (x > bothLeft && x < addLeft) {
                if (currentNumber > 1) {
                    currentNumber--;
                    reDraw();
                    return true;
                }
            }

            if (x > reduceRight && x < bothRight) {
                if (currentNumber < maxNumber) {
                    currentNumber++;
                    reDraw();
                    return true;
                }
            }
        }
        return true;
    }

    private void reDraw() {
        if (onChangeNumberListener != null) {
            onChangeNumberListener.onChangeNumber(currentNumber);
        }
        if (maxNumber == 1) {
            currentStatus = Status.DISABLED;
        } else if (currentNumber == maxNumber && currentNumber > 1) {
            currentStatus = Status.REDUCEABLE;
        } else if (currentNumber < maxNumber && currentNumber > 1) {
            currentStatus = Status.BOTHABLE;
        } else if (currentNumber < maxNumber && currentNumber == 1) {
            currentStatus = Status.ADDABLE;
        }
        invalidate();
    }

    public int getNumber() {
        return currentNumber;
    }

    public void setMaxNumber(int maxNumber) {
        this.maxNumber=maxNumber;
        invalidate();
    }

    interface Status {
        int DISABLED = 1;
        int ADDABLE = 2;
        int REDUCEABLE = 3;
        int BOTHABLE = 4;
    }

    public interface OnChangeNumberListener{
        void onChangeNumber(int number);
    }

    public void setOnChangeNumberListener(OnChangeNumberListener onChangeNumberListener) {
        this.onChangeNumberListener = onChangeNumberListener;
    }
}
