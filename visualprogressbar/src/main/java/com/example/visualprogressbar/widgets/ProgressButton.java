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

import com.example.visualprogressbar.R;

/**
 * Created by laixiaolong on 2017/11/29.
 */

public class ProgressButton extends AbsView
{
    private RectF mFillTrackRectF;
    private Paint mFillTrackPaint;

    private Paint mWrapPaint;
    private RectF mWrapperRectF;
    private int mWrapColor;
    private int mFillTrackColor;
    private int mWrapSuccessColor;
    private int mFillTrackSuccessColor;
    private float mCornerRadius;
    private float mBoardWidth;


    private Paint mLabelTextPaint;
    private float mLabelTextSize;
    private int mLabelTextColor;
    private int mLabelTextSuccessColor;
    private String mLabelTextSuffix;
    private String mLabelTextPrefix;
    private boolean mLabelTextVisible;

    private int mShapeStyle;
    private float mBtnWidth;
    private float mBtnHeight;
    private Rect mTextBounds;

    private String mBtnText;
    private int mBtnTextColor;
    private float mBtnTextSize;

    private int mBtnLeft;
    private int mBtnRight;
    private int mBtnBottom;
    private int mBtnTop;

    public static final int SHAPE_RECT = 4;
    public static final int SHAPE_ROUND_RECT = 5;


    public ProgressButton(Context context)
    {
        super(context);
    }

    public ProgressButton(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    public ProgressButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void extractAttrs(Context context, AttributeSet attrs, int defStyleAttr)
    {
        Resources.Theme theme = context.getTheme();
        TypedArray typedArray =
                theme.obtainStyledAttributes(attrs, R.styleable.ProgressButton, defStyleAttr, 0);

        mWrapColor = typedArray.getColor(R.styleable.ProgressButton_wrapperColor, Color.MAGENTA);
        mFillTrackColor =
                typedArray.getColor(R.styleable.ProgressButton_fillTrackColor, Color.WHITE);
        mLabelTextColor =
                typedArray.getColor(R.styleable.ProgressButton_labelTextColor, Color.MAGENTA);
        mWrapSuccessColor =
                typedArray.getColor(R.styleable.ProgressButton_wrapperSuccessColor, Color.MAGENTA);
        mFillTrackSuccessColor =
                typedArray.getColor(R.styleable.ProgressButton_fillTrackSuccessColor, Color.MAGENTA);
        mLabelTextSuccessColor =
                typedArray.getColor(R.styleable.ProgressButton_labelTextSuccessColor, Color.MAGENTA);
        mBtnTextColor =
                typedArray.getColor(R.styleable.ProgressButton_android_textColor, Color.MAGENTA);
        mShapeStyle = typedArray.getInt(R.styleable.ProgressButton_btnShapeStyle, SHAPE_ROUND_RECT);


        mLabelTextSize =
                typedArray.getDimension(R.styleable.ProgressButton_labelTextSize, sp2px(12f));
        mBtnTextSize =
                typedArray.getDimension(R.styleable.ProgressButton_android_textSize, sp2px(14f));
        mCornerRadius =
                typedArray.getDimension(R.styleable.ProgressButton_cornerRadius, dp2px(10f));

        mBoardWidth = typedArray.getDimension(R.styleable.ProgressButton_boardWidth, dp2px(2f));

        mLabelTextVisible =
                typedArray.getBoolean(R.styleable.ProgressButton_labelTextVisible, true);

        mBtnText = typedArray.getString(R.styleable.ProgressButton_android_text);
        mLabelTextSuffix = typedArray.getString(R.styleable.ProgressButton_labelTextSuffix);
        mLabelTextPrefix = typedArray.getString(R.styleable.ProgressButton_labelTextPrefix);
        mLabelTextSuffix = TextUtils.isEmpty(mLabelTextSuffix) ? "" : mLabelTextSuffix;
        mLabelTextPrefix = TextUtils.isEmpty(mLabelTextPrefix) ? "" : mLabelTextPrefix;


        typedArray.recycle();
    }


    @Override
    protected void initialize()
    {
        mWrapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWrapPaint.setColor(mWrapColor);
        mWrapPaint.setStyle(Paint.Style.STROKE);
        mWrapPaint.setStrokeWidth(mBoardWidth);
        mWrapperRectF = new RectF();

        mFillTrackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFillTrackPaint.setColor(mFillTrackColor);
        mFillTrackPaint.setStrokeWidth(mBoardWidth);
        mFillTrackPaint.setStyle(Paint.Style.STROKE);
        mFillTrackRectF = new RectF();


        mLabelTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLabelTextPaint.setColor(mLabelTextColor);
        mLabelTextPaint.setTextSize(mLabelTextSize);
        mLabelTextPaint.setTextAlign(Paint.Align.CENTER);
        mLabelTextPaint.setStyle(Paint.Style.FILL);
        mTextBounds = new Rect();

        // ensure label text can be display properly
        final float textWidth =
                mLabelTextPaint.measureText(String.format("%s100%s", mLabelTextPrefix, mLabelTextSuffix));
        mBtnHeight = Math.max(mBtnHeight, getBtnTextHeight() * 2);
        mBtnWidth = Math.max(textWidth * 0.9f, mBtnWidth);

    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        //canvas.translate(getWidth()/2, getHeight()/2);

        mWrapperRectF.set(mBtnLeft + sPaddingAdd, mBtnTop + sPaddingAdd, mBtnRight - sPaddingAdd, mBtnBottom - sPaddingAdd);
        canvas.drawRoundRect(mWrapperRectF, mCornerRadius, mCornerRadius, mWrapPaint);


        canvas.drawText(mBtnText, getWidth() / 2f, getHeight() / 2f + mTextBounds.height() / 2.0f, mLabelTextPaint);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        mBtnLeft = getPaddingStart();
        mBtnRight = getRight() - getLeft() - getPaddingEnd();
        mBtnTop = getPaddingTop();
        mBtnBottom = getBottom() - getTop() - getPaddingBottom();
        mBtnWidth = mBtnRight - mBtnLeft;
        mBtnHeight = mBtnBottom - mBtnTop;

    }


    @Override
    protected int getSuggestedMinimumWidth()
    {
        mLabelTextPaint.setTextSize(mBtnTextSize);
        float textWidth = mLabelTextPaint.measureText(mBtnText);
        return Math.round(mBtnWidth + mBoardWidth * 2 + textWidth);
    }

    @Override
    protected int getSuggestedMinimumHeight()
    {
        return Math.round(mBtnWidth + mBoardWidth * 2 + getBtnTextHeight());
    }

    private int getBtnTextHeight()
    {
        mLabelTextPaint.setTextSize(mBtnTextSize);
        mLabelTextPaint.getTextBounds("A", 0, 1, mTextBounds);
        mLabelTextPaint.setTextSize(mLabelTextSize);
        return mTextBounds.height();
    }
}
