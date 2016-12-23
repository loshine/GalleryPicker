package me.loshine.gallerypicker.widget;

import android.content.Context;
import android.support.percent.PercentRelativeLayout;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * 描    述：
 * 作    者：longs@13322.com
 * 时    间：2016/12/23
 */
public class SquareRelativeLayout extends PercentRelativeLayout {

    public SquareRelativeLayout(Context context) {
        super(context);
    }

    public SquareRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = getMeasuredWidth();
        setMeasuredDimension(size, size);
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.width = size;
        setLayoutParams(layoutParams);
    }
}
