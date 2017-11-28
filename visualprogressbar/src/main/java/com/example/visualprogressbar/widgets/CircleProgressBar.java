package com.example.visualprogressbar.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;


import com.example.visualprogressbar.R;

import java.util.Locale;

/**
 * Created by laixiaolong on 2017/11/25.
 */

public class CircleProgressBar extends View
{
    private RectF mFillTrackRectF;
    private Paint mFillTrackPaint;
    private int mProgress;
    private int mMax;

    private Paint mWrapPaint;
    private RectF mWrapperRectF;
    private int mWrapColor;
    private int mFillTrackColor;
    private int mWrapSuccessColor;
    private int mFillTrackSuccessColor;
    private float mRadius;
    private float mCircleStrokeWidth;

    private Paint mLabelTextPaint;
    private float mLabelTextSize;
    private int mLabelTextColor;
    private int mLabelTextSuccessColor;
    private String mLabelTextSuffix;
    private String mLabelTextPrefix;
    private boolean mLabelTextVisible;
    private int mShapeStyle;
    private int mCircleWidth;
    private int mCircleHeight;
    private Rect mTextBounds;

    public static final int SHAPE_CIRCLE = 2;
    public static final int SHAPE_ARC = 3;

    private int mCircleLeft;
    private int mCircleRight;
    private int mCircleBottom;
    private int mCircleTop;


    public int getCircleWidth()
    {
        return mCircleWidth;
    }

    public void setCircleWidth(int circleWidth)
    {
        this.mCircleWidth = circleWidth;
        invalidate();
    }

    public int getCircleHeight()
    {
        return mCircleHeight;
    }

    public void setCircleHeight(int circleHeight)
    {
        this.mCircleHeight = circleHeight;
        invalidate();
    }

    public float getCircleStrokeWidth()
    {
        return mCircleStrokeWidth;
    }

    public void setCircleStrokeWidth(float circleStrokeWidth)
    {
        this.mCircleStrokeWidth = circleStrokeWidth;
        invalidate();
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

    public int getWrapperSuccessColor()
    {
        return mWrapSuccessColor;
    }

    public void setWrapperSuccessColor(int barWrapSuccessColor)
    {
        this.mWrapSuccessColor = barWrapSuccessColor;
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


    public CircleProgressBar(Context context)
    {

        this(context, null);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        setupAttrs(context, attrs, defStyleAttr);
        initialize();
    }

    private void setupAttrs(Context context, AttributeSet attrs, int defStyleAttr)
    {
        Resources.Theme theme = context.getTheme();
        TypedArray typedArray =
                theme.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar, defStyleAttr, 0);

        mMax = typedArray.getInteger(R.styleable.CircleProgressBar_max, 100);
        mProgress = typedArray.getInteger(R.styleable.CircleProgressBar_progress, 0);

        mWrapColor = typedArray.getColor(R.styleable.CircleProgressBar_wrapperColor, Color.LTGRAY);
        mFillTrackColor =
                typedArray.getColor(R.styleable.CircleProgressBar_fillTrackColor, Color.MAGENTA);
        mLabelTextColor =
                typedArray.getColor(R.styleable.CircleProgressBar_labelTextColor, Color.MAGENTA);
        mWrapSuccessColor =
                typedArray.getColor(R.styleable.CircleProgressBar_wrapperSuccessColor, Color.MAGENTA);
        mFillTrackSuccessColor =
                typedArray.getColor(R.styleable.CircleProgressBar_fillTrackSuccessColor, Color.MAGENTA);
        mLabelTextSuccessColor =
                typedArray.getColor(R.styleable.CircleProgressBar_labelTextSuccessColor, Color.MAGENTA);
        mShapeStyle = typedArray.getInt(R.styleable.CircleProgressBar_circleShapeStyle, SHAPE_ARC);


        mLabelTextSize =
                typedArray.getDimension(R.styleable.CircleProgressBar_labelTextSize, sp2px(12f));
        mRadius = typedArray.getDimension(R.styleable.CircleProgressBar_circleRadius, dp2px(22f));

        mCircleStrokeWidth =
                typedArray.getDimension(R.styleable.CircleProgressBar_circleStrokeWidth, dp2px(5f));

        mLabelTextVisible =
                typedArray.getBoolean(R.styleable.CircleProgressBar_labelTextVisible, true);

        mLabelTextSuffix = typedArray.getString(R.styleable.CircleProgressBar_labelTextSuffix);
        mLabelTextPrefix = typedArray.getString(R.styleable.CircleProgressBar_labelTextPrefix);
        mLabelTextSuffix = TextUtils.isEmpty(mLabelTextSuffix) ? "" : mLabelTextSuffix;
        mLabelTextPrefix = TextUtils.isEmpty(mLabelTextPrefix) ? "" : mLabelTextPrefix;


        typedArray.recycle();
    }

    //initialization
    private void initialize()
    {
        mWrapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWrapPaint.setColor(mWrapColor);
        mWrapPaint.setStyle(Paint.Style.STROKE);
        mWrapPaint.setStrokeWidth(mCircleStrokeWidth);
        mWrapperRectF = new RectF();

        mFillTrackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFillTrackPaint.setColor(mFillTrackColor);
        mFillTrackPaint.setStrokeWidth(mCircleStrokeWidth);
        mFillTrackPaint.setStyle(Paint.Style.STROKE);
        mFillTrackRectF = new RectF();


        mLabelTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLabelTextPaint.setColor(mLabelTextColor);
        mLabelTextPaint.setTextSize(mLabelTextSize);
        mTextBounds = new Rect();
        // ensure label text can be display properly
        final float textWidth =
                mLabelTextPaint.measureText(String.format("%s00%s", mLabelTextPrefix, mLabelTextSuffix));
        mRadius = Math.max(textWidth * 0.9f, mRadius);

        mLabelTextPaint.setStyle(Paint.Style.FILL);
        mLabelTextPaint.setTextAlign(Paint.Align.CENTER);

    }

    public int getWrapperColor()
    {
        return mWrapColor;
    }

    public void setWrapperColor(int barWrapColor)
    {
        this.mWrapColor = barWrapColor;
        invalidate();
    }

    public int getFillColor()
    {
        return mFillTrackColor;
    }

    public void setFillColor(int barFillColor)
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

        switch (mShapeStyle) {
            case SHAPE_ARC:
                return Math.round(mRadius * 2 + mCircleStrokeWidth);
            default:
                return super.getSuggestedMinimumHeight();
        }


    }


    @Override
    protected int getSuggestedMinimumHeight()
    {
        switch (mShapeStyle) {
            case SHAPE_ARC:
                return Math.round(mRadius * 2 + mCircleStrokeWidth);
            default:
                return super.getSuggestedMinimumHeight();
        }
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        switch (mShapeStyle) {
            case SHAPE_CIRCLE:
                drawCircle(canvas);
                break;
            case SHAPE_ARC:
                drawArc(canvas);
                break;
        }

    }

    private void drawArc(Canvas canvas)
    {
        canvas.translate(getWidth() / 2, getHeight() / 2);

        final int angle = 360 * mProgress / mMax;
        mFillTrackPaint.setColor(mProgress == mMax ? mFillTrackSuccessColor : mFillTrackColor);
        mFillTrackRectF.set(-mRadius, -mRadius, mRadius, mRadius);
        mWrapperRectF.set(mFillTrackRectF);

        canvas.drawArc(mWrapperRectF, 0f, 360f, false, mWrapPaint);

        canvas.drawArc(mFillTrackRectF, 0f, angle, false, mFillTrackPaint);

        if (!mLabelTextVisible) return;

        final String text =
                String.format(Locale.getDefault(), "%s%d%s", mLabelTextPrefix, mProgress * 100 / mMax, mLabelTextSuffix);
        mLabelTextPaint.getTextBounds(text, 0, 1, mTextBounds);
        mLabelTextPaint.setColor(mProgress == mMax ? mLabelTextSuccessColor : mLabelTextColor);
        canvas.drawText(text, 0f, Math.round(mTextBounds.height() / 2.0f), mLabelTextPaint);
    }

    private void drawCircle(Canvas canvas)
    {
        canvas.translate(getWidth() / 2, getHeight() / 2);

    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        mCircleLeft = getPaddingStart();
        mCircleRight = getRight() - getLeft() - getPaddingEnd();
        mCircleTop = getPaddingTop();
        mCircleBottom = getBottom() - getTop() - getPaddingBottom();
        mCircleWidth = mCircleRight - mCircleLeft;
        mCircleHeight = mCircleBottom - mCircleTop;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int modeW = MeasureSpec.getMode(widthMeasureSpec);
        final int sizeW = MeasureSpec.getSize(widthMeasureSpec);

        final int modeH = MeasureSpec.getMode(heightMeasureSpec);
        final int sizeH = MeasureSpec.getSize(heightMeasureSpec);

        if (modeH == MeasureSpec.AT_MOST && modeW == MeasureSpec.AT_MOST) {
            final int width = getSuggestedMinimumWidth() + getPaddingEnd() + getPaddingStart();
            final int height = getSuggestedMinimumHeight() + getPaddingTop() + getPaddingBottom();
            setMeasuredDimension(width, height);
        } else if (modeW == MeasureSpec.AT_MOST) {
            final int width = getSuggestedMinimumWidth() + getPaddingEnd() + getPaddingStart();
            setMeasuredDimension(width, sizeH);
        } else if (modeH == MeasureSpec.AT_MOST) {
            final int height = getSuggestedMinimumHeight() + getPaddingTop() + getPaddingBottom();
            setMeasuredDimension(sizeW, height);
        } else {
            setMeasuredDimension(sizeW, sizeH);
        }
    }

    private float dp2px(float dp)
    {
        final float densityScale = getResources().getDisplayMetrics().density;
        return densityScale * dp + 0.5f;
    }

    private float sp2px(float sp)
    {
        final float densityScale = getResources().getDisplayMetrics().scaledDensity;
        return densityScale * sp;
    }


}
