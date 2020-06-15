package com.my.dragitemlayout.merge;

import android.graphics.Rect;

/**
 * created by Jiang on 2020/6/14 01:35
 */
public class PositionItem {
    public int j;//行
    public int k;//列
    public int left;
    public int top;
    public int right;
    public int bottom;
    public Rect rect;

    public int index ;//在集合中的位置
    @Override
    public String toString() {
        return "PositionItem{" +
                "j=" + j +
                ", k=" + k +
                ", left=" + left +
                ", top=" + top +
                ", right=" + right +
                ", bottom=" + bottom +
                '}';
    }
}
