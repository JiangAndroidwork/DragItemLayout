package com.my.dragitemlayout.merge;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.customview.widget.ViewDragHelper;


import com.my.dragitemlayout.LogUtils;
import com.my.dragitemlayout.R;

import java.util.ArrayList;
import java.util.List;


/**
 * created by Jiang on 2020/6/14 00:44
 */
public class MergeLayout extends FrameLayout {
    public static final int j = 4;
    public static final int k = 3;

    private int padding = 20;
    private int topLength = 500;
    private String TAG = "拖拽";
    private ViewDragHelper mViewDragHelper;
    private TextView tvLaji;
    private List<MergeItemView> mItemList = new ArrayList<>();


    public MergeLayout(@NonNull Context context) {
        this(context, null);
    }

    public MergeLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof MergeItemView) {
                MergeItemView itemView = (MergeItemView) childAt;
                PositionItem positionItem = itemView.getPositionItem();

                int j = positionItem.j;
                int k = positionItem.k;
                int viewTop = (k) * (childAt.getMeasuredHeight() + padding) + topLength;
                int viewLeft = (j) * (childAt.getMeasuredWidth() + padding) + padding;
                int viewRight = viewLeft + childAt.getMeasuredWidth();
                int viewBottom = viewTop + childAt.getMeasuredHeight();

                positionItem.left = viewLeft;
                positionItem.top = viewTop;
                positionItem.right = viewRight;
                positionItem.bottom = viewBottom;
                childAt.layout(viewLeft, viewTop, viewRight, viewBottom);
            } else if (childAt instanceof TextView) {
                childAt.layout(0, 1200, childAt.getMeasuredWidth(), 1200 + childAt.getMeasuredHeight());
            } else {
                int viewTop = (i / 4) * (childAt.getMeasuredHeight() + padding) + topLength;
                int viewLeft = (i % 4) * (childAt.getMeasuredWidth() + padding) + padding;
                int viewRight = viewLeft + childAt.getMeasuredWidth();
                int viewBottom = viewTop + childAt.getMeasuredHeight();
                childAt.layout(viewLeft, viewTop, viewRight, viewBottom);
            }
        }
    }

    private void initView() {
        for (int i = 0; i < k; i++) {
            for (int l = 0; l < j; l++) {
                View view = new View(getContext());
                view.setBackgroundResource(R.drawable.shape_bg);
                LayoutParams fl = new LayoutParams(200, 200);
                addView(view, fl);
            }
        }
        mItemList.clear();
        for (int i = 0; i < k; i++) {
            for (int l = 0; l < j; l++) {
                MergeItemView view = new MergeItemView(getContext(), (1 + i * j + l));
                PositionItem positionItem = new PositionItem();
                positionItem.j = l;
                positionItem.k = i;
                positionItem.index = i * j + l;
                view.setPositionItem(positionItem);
                LayoutParams fl = new LayoutParams(200, 200);
                addView(view, fl);
                mItemList.add(view);
            }
        }


        tvLaji = new TextView(getContext());
        tvLaji.setText("垃圾桶");
        tvLaji.setTextSize(17);
        tvLaji.setGravity(Gravity.CENTER);
        tvLaji.setPadding(20, 20, 20, 20);
        LayoutParams fl = new LayoutParams(200, 200);
        addView(tvLaji, fl);
        //指定好需要处理拖动的ViewGroup和回调 就可以开始使用了
        mViewDragHelper = ViewDragHelper.create(this, 2.0f, new DefaultDragHelper());
    }

    /**
     * 初始化数据
     *
     * @param mDatas
     * @return
     */
    public MergeLayout refreshDatas(List<KittyInfoData> mDatas) {

        invalidate();
        return this;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    /**
     * 拖拽回到原位置需要重写scroll方法
     */
    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    private class DefaultDragHelper extends ViewDragHelper.Callback {


        private int dragTop;
        private int dragLeft;

        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {

            return child instanceof MergeItemView && !mViewDragHelper.continueSettling(true);
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            if (top > getHeight() - child.getMeasuredHeight()) // 上边界
            {
                top = getHeight() - child.getMeasuredHeight();
            } else if (top < 0) // 上边界
            {
                top = 0;
            }
            return top;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (left > getWidth() - child.getMeasuredWidth()) // 右侧边界
            {
                left = getWidth() - child.getMeasuredWidth();
            } else if (left < 0) // 左侧边界
            {
                left = 0;
            }
            return left;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            this.dragLeft = left;
            this.dragTop = top;
            LogUtils.d(TAG, "onViewPositionChanged()--" + "left:" + left + ",top:" + top + ",dx:" + dx + ",dy:" + dy + "mLeftSize--");
            invalidate();
        }

        /**
         * 当captureview被捕获时回调
         *
         * @param capturedChild
         * @param activePointerId
         */
        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }

        /**
         * 手指离开屏幕
         * 后续View 的坐标处理
         * 比如  滑到超过一半 直接滑到满屏 又或者滑到不到一半的时候
         * 还原坐标
         *
         * @param releasedChild
         * @param xvel
         * @param yvel
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {

            if (releasedChild instanceof MergeItemView) {
                MergeItemView itemView = (MergeItemView) releasedChild;
                PositionItem positionItem = itemView.getPositionItem();
                LogUtils.d(TAG, "onViewReleased()--xv:" + xvel + ",yv:" + yvel + "\n" +
                        "view初始状态" + dragLeft + "====" + dragTop);
                itemView.layout(positionItem.left, positionItem.top, positionItem.right, positionItem.bottom);
//                mViewDragHelper.settleCapturedViewAt(positionItem.left, positionItem.top);

                //判断是否在垃圾箱
                int lajiLeft = 0;
                int lajiTop = 1200;
                int lajiBottom = 1400;
                int lajiRight = 200;

                int dragBottom = dragTop + positionItem.bottom - positionItem.top;
                int dragRight = dragLeft + positionItem.right - positionItem.left;

                Rect rect = new Rect(dragLeft, dragTop, dragRight, dragBottom);
                if (viewsIntersect(tvLaji, rect)) {
                    LogUtils.d("放入垃圾桶了==");
//                    removeView(itemView);
                    itemView.setKittyInfoData(null);
                } else {
                    LogUtils.d("没有放入垃圾桶==");
                }

                managerDrag(itemView, rect);
                //上拉
                invalidate();
                for (int i = 0; i < mItemList.size(); i++) {
                    MergeItemView itemView1 = mItemList.get(i);
                    KittyInfoData kittyInfoData = itemView1.getKittyInfoData();
                    if (kittyInfoData == null) {
                        LogUtils.d("数据排列==", "null");
                    } else {
                        LogUtils.d("数据排列==", kittyInfoData.getLevel() + "");
                    }
                }

            }


        }


        /**
         * 当触摸到边缘的时候会调用
         *
         * @param edgeFlags
         * @param pointerId
         */
        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            super.onEdgeTouched(edgeFlags, pointerId);
            LogUtils.d(TAG, "onEdgeTouched()");
        }

        @Override
        public boolean onEdgeLock(int edgeFlags) {
            LogUtils.d(TAG, "onEdgeLock()");
            return super.onEdgeLock(edgeFlags);
        }

        /**
         * 当触摸到边缘的时候会调用
         *
         * @param edgeFlags
         * @param pointerId 可以指定触摸边缘的子View
         */
        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            super.onEdgeDragStarted(edgeFlags, pointerId);
            LogUtils.d(TAG, "onEdgeDragStarted()");
        }

        /**
         * 返回当前移动的View 的position
         *
         * @param index
         * @return
         */
        @Override
        public int getOrderedChildIndex(int index) {
            LogUtils.d("当前移动的view==", index + "");
            return super.getOrderedChildIndex(index);
        }


    }

    /**
     * 拖拽判断
     *
     * @param itemView 被拖拽的view
     * @param rect     被拖拽 手指抬起的位置
     */
    private void managerDrag(MergeItemView itemView, Rect rect) {
        for (MergeItemView view : mItemList) {
            if (!view.equals(itemView)) {
                //有相交
                boolean b = dragChange(view, rect);
                if (b) {
                    changeItem(view, itemView);
                }
            }
        }
    }

    /**
     * 判断拖拽的中心点是否在目标内
     *
     * @param view
     * @param rect
     * @return
     */
    private boolean dragChange(MergeItemView view, Rect rect) {
        Rect targetR = new Rect(view.getPositionItem().left, view.getPositionItem().top, view.getPositionItem().right, view.getPositionItem().bottom);

        return targetR.contains(rect.centerX(), rect.centerY());
    }

    /**
     * 交换
     *
     * @param view
     * @param itemView
     */
    private void changeItem(MergeItemView view, MergeItemView itemView) {
        KittyInfoData iLevel = view.getKittyInfoData();
        view.setKittyInfoData(itemView.getKittyInfoData());
        itemView.setKittyInfoData(iLevel);
        invalidate();
    }

    public static boolean viewsIntersect(View view1, Rect rect) {
        final int[] view1Loc = new int[2];
        view1.getLocationOnScreen(view1Loc);
        final Rect view1Rect = new Rect(view1Loc[0], view1Loc[1], view1Loc[0]
                + view1.getWidth(), view1Loc[1] + view1.getHeight());
        int[] view2Loc = new int[2];
        return view1Rect.intersect(rect);
    }
}
