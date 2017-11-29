package com.example.visualprogressbar.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by laixiaolong on 2017/11/29.
 */

public abstract class AbsView extends View
{
    protected static float sPaddingAdd;

    public AbsView(Context context)
    {
        this(context, null);
    }

    public AbsView(Context context, @Nullable AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public AbsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        extractAttrs(context, attrs, defStyleAttr);
        sPaddingAdd = dp2px(5);
        initialize();
    }

    protected abstract void extractAttrs(Context context, AttributeSet attrs, int defStyleAttr);


    protected abstract void initialize();


    @Override
    protected abstract void onDraw(Canvas canvas);


    @Override
    protected abstract void onLayout(boolean changed, int left, int top, int right, int bottom);


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int modeW = MeasureSpec.getMode(widthMeasureSpec);
        final int sizeW = MeasureSpec.getSize(widthMeasureSpec);

        final int modeH = MeasureSpec.getMode(heightMeasureSpec);
        final int sizeH = MeasureSpec.getSize(heightMeasureSpec);

        int width = getSuggestedMinimumWidth() + getPaddingEnd() + getPaddingStart();
        int height = getSuggestedMinimumHeight() + getPaddingTop() + getPaddingBottom();
        width = Math.max(width, getMinimumWidth());
        height = Math.max(height, getMinimumHeight());

        if (modeH == MeasureSpec.AT_MOST && modeW == MeasureSpec.AT_MOST) {
            setMeasuredDimension(width, height);
        } else if (modeW == MeasureSpec.AT_MOST) {
            setMeasuredDimension(width, sizeH);
        } else if (modeH == MeasureSpec.AT_MOST) {
            setMeasuredDimension(sizeW, height);
        } else {
            setMeasuredDimension(Math.max(sizeW, width), Math.max(sizeH, height));
        }
    }

    protected float dp2px(float dp)
    {
        final float densityScale = getResources().getDisplayMetrics().density;
        return densityScale * dp + 0.5f;
    }

    protected float sp2px(float sp)
    {
        final float densityScale = getResources().getDisplayMetrics().scaledDensity;
        return densityScale * sp;
    }

}
