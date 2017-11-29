package com.example.visualprogressbar.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.example.visualprogressbar.R;

import java.util.Locale;

/**
 * Created by laixiaolong on 2017/11/25.
 */

public class LinearProgressBar extends AbsView
{
    private RectF mFillTrackRectF;
    private Paint mFillTrackPaint;
    private int mProgress;
    private int mMax;

    private Paint mBarWrapPaint;
    private RectF mBarWrapperRectF;
    private int mBarWrapColor;
    private int mFillTrackColor;
    private int mBarWrapSuccessColor;
    private int mFillTrackSuccessColor;
    private float mRingRadius;
    private float mHorizontalBarHeight;
    private float mBarWrapStrokeWidth;
    private float mVerticalBarWidth;

    private Paint mLabelTextPaint;
    private float mLabelTextSize;
    private int mLabelTextColor;
    private int mLabelTextSuccessColor;
    private String mLabelTextSuffix;
    private String mLabelTextPrefix;
    private boolean mLabelTextVisible;
    private int mShapeStyle;

    public static final int SHAPE_LINEAR_HORIZONTAL = 0;
    public static final int SHAPE_LINEAR_VERTICAL = 1;

    private int mHorizontalBarWidth;
    private int mBarLeft;
    private int mBarRight;
    private int mBarBottom;
    private int mBarTop;
    private int mVerticalBarHeight;

    public LinearProgressBar(Context context)
    {
        super(context);
    }

    public LinearProgressBar(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    public LinearProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }


    public int getShapeStyle()
    {
        return mShapeStyle;
    }

    public void setShapeStyle(int shapeStyle)
    {
        this.mShapeStyle = shapeStyle;
        invalidate();
    }

    public int getLabelTextSuccessColor()
    {
        return mLabelTextSuccessColor;
    }

    public void setLabelTextSuccessColor(int labelTextSuccessColor)
    {
        this.mLabelTextSuccessColor = labelTextSuccessColor;
        invalidate();
    }

    public int getBarWrapSuccessColor()
    {
        return mBarWrapSuccessColor;
    }

    public void setBarWrapSuccessColor(int barWrapSuccessColor)
    {
        this.mBarWrapSuccessColor = barWrapSuccessColor;
        invalidate();
    }

    public int getFillTrackSuccessColor()
    {
        return mFillTrackSuccessColor;
    }

    public void setFillTrackSuccessColor(int fillTrackSuccessColor)
    {
        this.mFillTrackSuccessColor = fillTrackSuccessColor;
        invalidate();
    }

    public String getLabelTextPrefix()
    {
        return mLabelTextPrefix;
    }

    public void setLabelTextPrefix(String labelTextPrefix)
    {
        this.mLabelTextPrefix = labelTextPrefix;
        invalidate();
    }

    public String getLabelTextSuffix()
    {
        return mLabelTextSuffix;
    }

    public void setLabelTextSuffix(String labelTextUnit)
    {
        this.mLabelTextSuffix = labelTextUnit;
        invalidate();
    }

    public float getBarHeight()
    {
        return mHorizontalBarHeight;
    }

    public void setBarHeight(int mBarHeight)
    {
        this.mHorizontalBarHeight = mBarHeight;
        invalidate();
    }


    @Override
    protected void extractAttrs(Context context, AttributeSet attrs, int defStyleAttr)
    {
        Resources.Theme theme = context.getTheme();
        TypedArray typedArray =
                theme.obtainStyledAttributes(attrs, R.styleable.LinearProgressBar, defStyleAttr, 0);

        mMax = typedArray.getInteger(R.styleable.LinearProgressBar_max, 100);
        mProgress = typedArray.getInteger(R.styleable.LinearProgressBar_progress, 0);

        mBarWrapColor =
                typedArray.getColor(R.styleable.LinearProgressBar_wrapperColor, Color.MAGENTA);
        mFillTrackColor =
                typedArray.getColor(R.styleable.LinearProgressBar_fillTrackColor, Color.MAGENTA);
        mLabelTextColor =
                typedArray.getColor(R.styleable.LinearProgressBar_labelTextColor, Color.MAGENTA);
        mBarWrapSuccessColor =
                typedArray.getColor(R.styleable.LinearProgressBar_wrapperSuccessColor, Color.MAGENTA);
        mFillTrackSuccessColor =
                typedArray.getColor(R.styleable.LinearProgressBar_fillTrackSuccessColor, Color.MAGENTA);
        mLabelTextSuccessColor =
                typedArray.getColor(R.styleable.LinearProgressBar_labelTextSuccessColor, Color.MAGENTA);
        mShapeStyle =
                typedArray.getInt(R.styleable.LinearProgressBar_linearShapeStyle, SHAPE_LINEAR_HORIZONTAL);


        mLabelTextSize =
                typedArray.getDimension(R.styleable.LinearProgressBar_labelTextSize, sp2px(12f));
        mHorizontalBarHeight =
                typedArray.getDimension(R.styleable.LinearProgressBar_horizontalBarHeight, dp2px(5f));
        mVerticalBarWidth =
                typedArray.getDimension(R.styleable.LinearProgressBar_verticalBarWidth, dp2px(5f));
        mRingRadius = typedArray.getDimension(R.styleable.LinearProgressBar_barRadius, dp2px(3f));

        mBarWrapStrokeWidth =
                typedArray.getDimension(R.styleable.LinearProgressBar_barWrapStrokeWidth, dp2px(1f));

        mLabelTextVisible =
                typedArray.getBoolean(R.styleable.LinearProgressBar_labelTextVisible, true);

        mLabelTextSuffix = typedArray.getString(R.styleable.LinearProgressBar_labelTextSuffix);
        mLabelTextPrefix = typedArray.getString(R.styleable.LinearProgressBar_labelTextPrefix);
        mLabelTextSuffix = TextUtils.isEmpty(mLabelTextSuffix) ? "" : mLabelTextSuffix;
        mLabelTextPrefix = TextUtils.isEmpty(mLabelTextPrefix) ? "" : mLabelTextPrefix;

        typedArray.recycle();
    }


    //initialization
    @Override
    protected void initialize()
    {
        mBarWrapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBarWrapPaint.setColor(mBarWrapColor);
        mBarWrapPaint.setStyle(Paint.Style.STROKE);
        mBarWrapPaint.setStrokeWidth(mBarWrapStrokeWidth);
        mBarWrapperRectF = new RectF();

        mFillTrackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFillTrackPaint.setColor(mFillTrackColor);
        mFillTrackPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mFillTrackRectF = new RectF();

        mLabelTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLabelTextPaint.setColor(mLabelTextColor);
        mLabelTextPaint.setTextSize(mLabelTextSize);
        mLabelTextPaint.setStyle(Paint.Style.FILL);
    }

    public int getBarWrapColor()
    {
        return mBarWrapColor;
    }

    public void setBarWrapColor(int barWrapColor)
    {
        this.mBarWrapColor = barWrapColor;
        invalidate();
    }

    public int getBarFillColor()
    {
        return mFillTrackColor;
    }

    public void setBarFillColor(int barFillColor)
    {
        this.mFillTrackColor = barFillColor;
        invalidate();
    }

    public float getLabelTextSize()
    {
        return mLabelTextSize;
    }

    public void setLabelTextSize(float labelTextSize)
    {
        this.mLabelTextSize = labelTextSize;
        mLabelTextPaint.setTextSize(labelTextSize);
        invalidate();
    }

    public int getLabelTextColor()
    {
        return mLabelTextColor;
    }

    public void setLabelTextColor(int labelTextColor)
    {
        this.mLabelTextColor = labelTextColor;
        invalidate();
    }

    public boolean isLabelTextVisible()
    {
        return mLabelTextVisible;
    }

    public void setLabelTextVisible(boolean mLabelTextVisible)
    {
        this.mLabelTextVisible = mLabelTextVisible;
        invalidate();
    }

    public int getMax()
    {
        return mMax;
    }

    /**
     * 最大值
     *
     * @param max 最大值必须大于0，不能小于等于0
     */
    public void setMax(int max)
    {
        if (max <= 0)
            throw new IllegalArgumentException("max must greater than 0,can not be 0 or negative.");
        this.mMax = max;
    }

    public void setProgress(int progress)
    {
        if (mMax <= 0)
            throw new IllegalArgumentException("max must greater than 0,can not be 0 or negative.");
        if (progress < 0) progress = 0;
        if (progress > mMax) progress = mMax;

        mProgress = progress;
        invalidate();
    }


    @Override
    protected int getSuggestedMinimumWidth()
    {
        final String text =
                String.format(Locale.getDefault(), "%s100%s", mLabelTextPrefix, mLabelTextSuffix);
        float textWidth = mLabelTextPaint.measureText(text);
        switch (mShapeStyle) {
            case SHAPE_LINEAR_HORIZONTAL: {
                if (!mLabelTextVisible)
                    return Math.round(mHorizontalBarHeight + mBarWrapStrokeWidth * 2);
                return Math.round(textWidth + mBarWrapStrokeWidth * 2 + sPaddingAdd*2);
            }
            case SHAPE_LINEAR_VERTICAL: {
                if (!mLabelTextVisible)
                    return Math.round(mVerticalBarWidth + mBarWrapStrokeWidth * 2);
                return Math.round(textWidth + mBarWrapStrokeWidth * 2 + textWidth + 5);
            }
            default:
                return super.getSuggestedMinimumHeight();
        }
    }


    @Override
    protected int getSuggestedMinimumHeight()
    {
        final String text =
                String.format(Locale.getDefault(), "%s100%s", mLabelTextPrefix, mLabelTextSuffix);
        float textWidth = mLabelTextPaint.measureText(text);
        switch (mShapeStyle) {
            case SHAPE_LINEAR_HORIZONTAL: {
                return mLabelTextVisible ? Math.round(sp2px(mLabelTextSize) + mHorizontalBarHeight + mBarWrapStrokeWidth * 2 + textWidth) : Math.round(mHorizontalBarHeight);
            }
            case SHAPE_LINEAR_VERTICAL: {
                return Math.round(textWidth + mBarWrapStrokeWidth * 2 + sPaddingAdd*2);
            }
            default:
                return super.getSuggestedMinimumHeight();
        }
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        switch (mShapeStyle) {
            case SHAPE_LINEAR_HORIZONTAL:
                drawLinearHorizontal(canvas);
                break;
            case SHAPE_LINEAR_VERTICAL:
                drawLinearVertical(canvas);
                break;
        }

    }


    private void drawLinearVertical(Canvas canvas)
    {
        canvas.translate(getWidth() / 2, 0);

        // bar line wrapper
        mBarWrapPaint.setColor(mProgress == mMax ? mBarWrapSuccessColor : mBarWrapColor);
        mBarWrapperRectF.set(-mVerticalBarWidth / 2  , mBarTop+ sPaddingAdd, mVerticalBarWidth / 2, mBarBottom- sPaddingAdd);
        canvas.drawRoundRect(mBarWrapperRectF, mRingRadius, mRingRadius, mBarWrapPaint);

        final int position = mBarBottom - mVerticalBarHeight * mProgress / mMax;
        //根据进度画实心颜色
        mFillTrackPaint.setColor(mProgress == mMax ? mFillTrackSuccessColor : mFillTrackColor);
        mFillTrackRectF.set(-mVerticalBarWidth / 2, position+sPaddingAdd, mVerticalBarWidth / 2, mBarBottom-sPaddingAdd);
        canvas.drawRoundRect(mFillTrackRectF, mRingRadius, mRingRadius, mFillTrackPaint);

        //  draw label text
        if (mLabelTextVisible) drawLinearProgressLabel(canvas, position, true);
    }

    private void drawLinearHorizontal(Canvas canvas)
    {
        canvas.translate(0, getHeight() / 2);

        mBarWrapPaint.setColor(mProgress == mMax ? mBarWrapSuccessColor : mBarWrapColor);
        mBarWrapperRectF.set(mBarLeft+ sPaddingAdd, -(mHorizontalBarHeight / 2), mBarRight- sPaddingAdd, mHorizontalBarHeight / 2);
        canvas.drawRoundRect(mBarWrapperRectF, mRingRadius, mRingRadius, mBarWrapPaint);

        final int position = mBarLeft + mHorizontalBarWidth * mProgress / mMax;
        //根据进度画实心颜色
        mFillTrackPaint.setColor(mProgress == mMax ? mFillTrackSuccessColor : mFillTrackColor);
        mFillTrackRectF.set(mBarLeft+ sPaddingAdd, -(mHorizontalBarHeight / 2), position-sPaddingAdd, mHorizontalBarHeight / 2);
        canvas.drawRoundRect(mFillTrackRectF, mRingRadius, mRingRadius, mFillTrackPaint);

        //根据进度绘制提示标签
        if (mLabelTextVisible) drawLinearProgressLabel(canvas, position, false);
    }

    private void drawLinearProgressLabel(Canvas canvas, int position, boolean vertical)
    {

        final String text =
                String.format(Locale.getDefault(), "%s%d%s", mLabelTextPrefix, mProgress * 100 / mMax, mLabelTextSuffix);
        float textWidth = mLabelTextPaint.measureText(text);

        mLabelTextPaint.setColor(mProgress == mMax ? mLabelTextSuccessColor : mLabelTextColor);
        mLabelTextPaint.setTextSize(mLabelTextSize);
        if (!vertical) {
            final int end = (int) (mHorizontalBarWidth - textWidth);
            // position always point to the center of text , and text always in (0 , mHorizontalBarWidth)
            float pos = (position <= textWidth / 2) ? position : position - textWidth / 2;
            pos = position >= end ? end : pos;
            canvas.drawText(text, pos, -mHorizontalBarHeight - 2, mLabelTextPaint);
        } else {
            final int size = (int) sp2px(mLabelTextSize);
            final int top = mBarTop + size / 2;
            // position always point to the center of text , and text always in (0 , mHorizontalBarWidth)
            float pos = (position >= mBarTop) ? position : position - size / 2;
            pos = position <= top ? top : pos;
            canvas.drawText(text, mVerticalBarWidth, pos, mLabelTextPaint);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        mBarLeft = getPaddingStart();
        mBarRight = getRight() - getLeft() - getPaddingEnd();
        mBarTop = getPaddingTop() + (int) mBarWrapStrokeWidth;
        mBarBottom = getBottom() - getTop() - getPaddingBottom();
        mHorizontalBarWidth = mBarRight - mBarLeft;
        mVerticalBarHeight = mBarBottom - mBarTop;
    }

}
