package me.loshine.gallerypicker.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * 描    述：
 * 作    者：loshine1992@gmail.com
 * 时    间：2016/12/22
 */
public class GridSquareLayout extends FrameLayout {

    public GridSquareLayout(Context context) {
        super(context);
    }

    public GridSquareLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridSquareLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public GridSquareLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @SuppressWarnings("all")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 核心就是下面这块代码块啦
        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
        ViewGroup.LayoutParams lp = getLayoutParams();
        lp.height = width;
        setLayoutParams(lp);
    }
}
