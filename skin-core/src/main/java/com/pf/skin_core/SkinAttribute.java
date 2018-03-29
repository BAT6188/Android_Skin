package com.pf.skin_core;

import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.pf.skin_core.skinattr.SkinAttr;
import com.pf.skin_core.utils.SkinThemeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaopf
 * @version 1.0
 * @QQ 1308108803
 * @date 2018/3/24
 */
public class SkinAttribute {
    /**
     * 字体
     */
    private Typeface mTypeface;
    /**
     * 需要换肤的view 和属性
     */
    private List<SkinView> skinViews = new ArrayList<>();

    public SkinAttribute(Typeface typeface) {
        this.mTypeface = typeface;
    }

    /**
     * 筛选符合条件的view
     * 只有设置了 background 等的才换肤
     *
     * @param view
     * @param attrs
     */
    public void load(View view, AttributeSet attrs) {
        // 要换肤的属性名
        List<String> allAttrNames = SkinConfig.getAllAttrName();
        List<SkinPair> skinPairs = new ArrayList<>();
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            // 属性名
            String attributeName = attrs.getAttributeName(i);
            if (allAttrNames.contains(attributeName)) {
                // 获取值
                String attributeValue = attrs.getAttributeValue(i);
                // 写死了不管(例如：#ffffff)
                if (attributeValue.startsWith("#")) {
                    continue;
                }
                // 资源 id
                int resId;
                if (attributeValue.startsWith("?")) {
                    // attr Id
                    int attrId = Integer.parseInt(attributeValue.substring(1));
                    // 获取 主题 style 中的对应attrId 的资源id
                    resId = SkinThemeUtils.getResId(view.getContext(), new int[]{attrId})[0];
                } else if (attributeValue.startsWith("@")) {
                    // @123456789
                    resId = Integer.parseInt(attributeValue.substring(1));
                } else if (SkinConfig.TypeFace.equals(attributeName)) {
                    if (attributeValue.length() >= 1) {
                        resId = Integer.parseInt(attributeValue.substring(1));
                    } else {
                        // 这里表示设置了空值,意思就是想用默认的，不想用皮肤包里的
                        resId = -1;
                    }
                } else {
                    resId = 0;
                }
                if (resId != 0) {
                    // 可以被替换的属性
                    SkinPair skinPair = new SkinPair(attributeName, resId,
                            SkinConfig.getSkinAttr(attributeName));
                    skinPairs.add(skinPair);
                }
            }
        }
        // 将View与之对应的可以动态替换的属性集合 放入 集合中
        if (!skinPairs.isEmpty()
                // 替换字体
                || view instanceof TextView
                // 自定义控件
                || view instanceof SkinViewSupport) {
            SkinView skinView = new SkinView(view, skinPairs);
            // 换肤
            skinView.applySkin(mTypeface);
            // 加入缓存
            skinViews.add(skinView);
        }
    }

    /**
     * 换肤
     *
     * @param typeface
     */
    public void applySkin(Typeface typeface) {
        for (SkinView skinView : skinViews) {
            skinView.applySkin(typeface);
        }
    }

    /**
     * 每个页面可能有多个控件需要换肤
     */
    private static class SkinView {
        /**
         * 要换肤的控件
         */
        View view;
        /**
         * 要换肤的属性集合
         */
        List<SkinPair> skinPairList;

        public SkinView(View view, List<SkinPair> skinPairList) {
            this.view = view;
            this.skinPairList = skinPairList;
        }

        public void applySkin(Typeface typeface) {
            // 修改字体
            applySkinTypeface(typeface);
            // 自定义控件换肤
            applySkinSupport();
            // 换肤
            for (SkinPair skinPair : skinPairList) {
                // 具体的换肤
                skinPair.skinAttr.applySkin(skinPair.attributeName, view, skinPair.resId);
            }
        }

        /**
         * 自定义控件换肤
         */
        private void applySkinSupport() {
            if (view instanceof SkinViewSupport) {
                ((SkinViewSupport) view).applySkin();
            }
        }

        /**
         * 修改字体
         *
         * @param typeface
         */
        private void applySkinTypeface(Typeface typeface) {
            if (view instanceof TextView) {
                ((TextView) view).setTypeface(typeface);
            }
        }
    }

    /**
     * 一个 view 可能设置了多个可以换肤的属性:background,textColor......
     */
    private static class SkinPair {
        /**
         * 要换肤的属性名
         */
        String attributeName;
        /**
         * 要替换的资源id
         */
        int resId;
        /**
         * 具体的换肤策略
         */
        SkinAttr skinAttr;

        public SkinPair(String attributeName, int resId, SkinAttr skinAttr) {
            this.attributeName = attributeName;
            this.resId = resId;
            this.skinAttr = skinAttr;
        }
    }
}