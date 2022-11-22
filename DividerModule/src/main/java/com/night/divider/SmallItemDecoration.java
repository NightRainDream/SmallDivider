package com.night.divider;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SmallItemDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "SmallItemDecoration";
    private final int mItemDecorationWidth;
    private final int mItemDecorationWidthColor;
    private final Paint mPaint = new Paint();

    /**
     * RecyclerView分割线
     *
     * @param width 分割线宽度
     */
    public SmallItemDecoration(final int width) {
        this(width, Color.TRANSPARENT);
    }

    /**
     * RecyclerView分割线
     *
     * @param width 分割线宽度
     * @param color 分割线颜色
     */
    public SmallItemDecoration(final int width, final @ColorInt int color) {
        this.mItemDecorationWidth = width;
        this.mItemDecorationWidthColor = color;
        mPaint.setStrokeWidth(mItemDecorationWidth);
        mPaint.setColor(mItemDecorationWidthColor);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setAntiAlias(true);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mItemDecorationWidth < 0) {
            return;
        }
        RecyclerView.LayoutManager mLayoutManager = parent.getLayoutManager();
        int itemPosition = parent.getChildAdapterPosition(view);
        if (mLayoutManager instanceof GridLayoutManager) {
            GridLayoutManager mGridLayoutManager = (GridLayoutManager) mLayoutManager;
            int mSpanCount = mGridLayoutManager.getSpanCount();
            //第一行，额外预留顶部空间
            if (itemPosition < mSpanCount) {
                outRect.set(0, mItemDecorationWidth, 0, 0);
            }
            //第一列，额外预留左侧空间
            if (((itemPosition + mSpanCount) % mSpanCount) == 0) {
                outRect.set(mItemDecorationWidth, outRect.top, 0, 0);
            }
            //所有的Item在右侧和底部预留空间
            outRect.set(outRect.left, outRect.top, mItemDecorationWidth, mItemDecorationWidth);
        } else if (mLayoutManager instanceof LinearLayoutManager) {
            if (((LinearLayoutManager) mLayoutManager).getOrientation() == LinearLayoutManager.HORIZONTAL) {
                //横向LinearLayoutManager
                if (itemPosition == 0) {
                    return;
                }
                outRect.set(mItemDecorationWidth, 0, 0, 0);
            } else {
                //纵向LinearLayoutManager
                if (itemPosition == 0) {
                    return;
                }
                outRect.set(0, mItemDecorationWidth, 0, 0);
            }
        }
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mItemDecorationWidthColor == Color.TRANSPARENT) {
            return;
        }
        RecyclerView.LayoutManager mLayoutManager = parent.getLayoutManager();
        if (mLayoutManager instanceof GridLayoutManager) {
            int spanCount = ((GridLayoutManager) mLayoutManager).getSpanCount();
            drawGrid(c, parent, spanCount);
        } else if (mLayoutManager instanceof LinearLayoutManager) {
            if (((LinearLayoutManager) mLayoutManager).getOrientation() == LinearLayoutManager.HORIZONTAL) {
                drawHorizontal(c, parent);
            } else {
                drawVertical(c, parent);
            }
        }
    }

    /**
     * 绘制纵向列表分割线
     *
     * @param c      绘制容器
     * @param parent RecyclerView
     */
    private void drawGrid(Canvas c, RecyclerView parent, final int spanCount) {
        float mDecorationWidth = mItemDecorationWidth / 2F;
        int mChildCount = parent.getChildCount();
        for (int i = 0; i < mChildCount; i++) {
            View mChild = parent.getChildAt(i);
            Log.i(TAG, "drawGrid: " + mChild.getVisibility());
            int itemPosition = parent.getChildAdapterPosition(mChild);
            RecyclerView.LayoutParams mChildLayoutParams = (RecyclerView.LayoutParams) mChild.getLayoutParams();
            final float mLeft = mChild.getLeft();
            final float mTop = mChild.getTop();
            final float mRight = mChild.getRight();
            final float mBottom = mChild.getBottom();
            //第一行，绘制顶部分割线
            if (itemPosition < spanCount) {
                float mStartX = mLeft - mChildLayoutParams.leftMargin - mItemDecorationWidth;
                float mStartY = mTop - mChildLayoutParams.topMargin - mDecorationWidth;
                float mEndX = mRight + mChildLayoutParams.rightMargin;
                if (itemPosition == (spanCount - 1)) {
                    mEndX = mRight + mChildLayoutParams.rightMargin + mItemDecorationWidth;
                }
                c.drawLine(mStartX, mStartY, mEndX, mStartY, mPaint);
            }
            //第一列，绘制左侧分割线
            if (((itemPosition + spanCount) % spanCount) == 0) {
                float startX = mLeft - mChildLayoutParams.leftMargin - mDecorationWidth;
                float startY = mTop - mChildLayoutParams.topMargin;
                float endY = mBottom + mChildLayoutParams.bottomMargin + mItemDecorationWidth;
                c.drawLine(startX, startY, startX, endY, mPaint);
            }
            float rightStartX = mRight + mChildLayoutParams.rightMargin + mDecorationWidth;
            float rightStartY = mTop - mChildLayoutParams.topMargin;
            float rightEnxY = mBottom + mChildLayoutParams.bottomMargin;
            c.drawLine(rightStartX, rightStartY, rightStartX, rightEnxY, mPaint);

            float bottomStartX = mLeft - mChildLayoutParams.leftMargin;
            float bottomStartY = mBottom + mChildLayoutParams.bottomMargin + mDecorationWidth;
            float bottomEndX = mRight + mChildLayoutParams.rightMargin + mItemDecorationWidth;
            c.drawLine(bottomStartX, bottomStartY, bottomEndX, bottomStartY, mPaint);
        }
    }


    /**
     * 绘制纵向列表分割线
     *
     * @param c      绘制容器
     * @param parent RecyclerView
     */
    private void drawVertical(Canvas c, RecyclerView parent) {
        float mDecorationWidth = mItemDecorationWidth / 2F;
        int mChildCount = parent.getChildCount();
        for (int i = 0; i < mChildCount; i++) {
            if (i == 0) {
                continue;
            }
            View mChild = parent.getChildAt(i);
            RecyclerView.LayoutParams mChildLayoutParams = (RecyclerView.LayoutParams) mChild.getLayoutParams();
            float mLeft = mChild.getLeft();
            float mTop = mChild.getTop();
            float mRight = mChild.getRight();
            float mStartX = mLeft - mChildLayoutParams.leftMargin;
            float mStartY = mTop - mChildLayoutParams.topMargin - mDecorationWidth;
            float mEndX = mRight + mChildLayoutParams.rightMargin;
            c.drawLine(mStartX, mStartY, mEndX, mStartY, mPaint);
        }
    }

    /**
     * 绘制横向列表分割线
     *
     * @param c      绘制容器
     * @param parent RecyclerView
     */
    private void drawHorizontal(Canvas c, RecyclerView parent) {
        float mDecorationWidth = mItemDecorationWidth / 2F;
        int mChildCount = parent.getChildCount();
        for (int i = 0; i < mChildCount; i++) {
            if (i == 0) {
                continue;
            }
            View mChild = parent.getChildAt(i);
            RecyclerView.LayoutParams mChildLayoutParams = (RecyclerView.LayoutParams) mChild.getLayoutParams();
            float mLeft = mChild.getLeft();
            float mTop = mChild.getTop();
            float mBottom = mChild.getBottom();
            float startX = mLeft - mChildLayoutParams.leftMargin - mDecorationWidth;
            float startY = mTop - mChildLayoutParams.topMargin;
            float endY = mBottom + mChildLayoutParams.bottomMargin;
            c.drawLine(startX, startY, startX, endY, mPaint);
        }
    }
}
