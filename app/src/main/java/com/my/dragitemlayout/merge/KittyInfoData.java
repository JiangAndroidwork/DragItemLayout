package com.my.dragitemlayout.merge;

/**
 * 猫咪信息
 * created by Jiang on 2020/6/14 10:04
 */
public class KittyInfoData {
    private int level;//等级
    private String json;//动画


    public KittyInfoData(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public KittyInfoData setLevel(int level) {
        this.level = level;
        return this;
    }

    public String getJson() {
        return json;
    }

    public KittyInfoData setJson(String json) {
        this.json = json;
        return this;
    }
}
