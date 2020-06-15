package com.my.dragitemlayout.merge;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.my.dragitemlayout.R;


/**
 * created by Jiang on 2020/6/14 01:36
 */
public class MergeItemView extends FrameLayout {
    private PositionItem positionItem;
    private KittyInfoData kittyInfoData;
    private TextView tvText;

    public MergeItemView(@NonNull Context context, int level) {
        super(context);
        setKittyInfoData(level);
        initView();
    }


    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_merge_item, this);
        tvText = view.findViewById(R.id.tv_test);
        init();
    }

    public MergeItemView setKittyInfoData(int level) {
        if (kittyInfoData == null || kittyInfoData.getLevel() != level) {
            this.kittyInfoData = new KittyInfoData(level);

        }
        return this;
    }

    public MergeItemView setKittyInfoData(KittyInfoData infoData) {
        this.kittyInfoData = infoData;
        init();
        return this;
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        if (kittyInfoData != null) {
            this.setVisibility(VISIBLE);
            tvText.setText("猫咪" + kittyInfoData.getLevel());
        } else {
            this.setVisibility(GONE);
        }
    }


    /**
     * 获取猫咪信息
     *
     * @return
     */
    public KittyInfoData getKittyInfoData() {
        return kittyInfoData;
    }


    public void setPositionItem(PositionItem positionItem) {
        this.positionItem = positionItem;
    }

    public PositionItem getPositionItem() {
        return positionItem;
    }
}
