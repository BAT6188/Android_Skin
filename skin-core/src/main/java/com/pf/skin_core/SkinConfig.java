package com.pf.skin_core;

import android.text.TextUtils;

import com.pf.skin_core.skinattr.BackgroundSkinAttr;
import com.pf.skin_core.skinattr.DrawableBottomSkinAttr;
import com.pf.skin_core.skinattr.DrawableLeftSkinAttr;
import com.pf.skin_core.skinattr.DrawableRightSkinAttr;
import com.pf.skin_core.skinattr.DrawableTopSkinAttr;
import com.pf.skin_core.skinattr.SkinAttr;
import com.pf.skin_core.skinattr.SkinTypefaceSkinAttr;
import com.pf.skin_core.skinattr.SrcSkinAttr;
import com.pf.skin_core.skinattr.TextColorSkinAttr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author zhaopf
 * @version 1.0
 * @QQ 1308108803
 * @date 2018/3/25
 */
public class SkinConfig {
    /**
     * 需要换肤的属性和具体的换肤操作
     */
    private static final HashMap<String, SkinAttr> mSkinAttrs = new HashMap<>();
    /**
     * 要换肤的属性的集合
     */
    private static final List<String> mSkinAttrNames = new ArrayList<>();
    /**
     * 字体
     */
    public static final String TypeFace = "skinTypeface";

    static {
        mSkinAttrs.put("background", new BackgroundSkinAttr());
        mSkinAttrs.put("src", new SrcSkinAttr());
        mSkinAttrs.put("textColor", new TextColorSkinAttr());
        mSkinAttrs.put("drawableLeft", new DrawableLeftSkinAttr());
        mSkinAttrs.put("drawableTop", new DrawableTopSkinAttr());
        mSkinAttrs.put("drawableRight", new DrawableRightSkinAttr());
        mSkinAttrs.put("drawableBottom", new DrawableBottomSkinAttr());
        mSkinAttrs.put(TypeFace, new SkinTypefaceSkinAttr());
    }

    /**
     * 添加需要换肤的属性和换肤策略
     *
     * @param attrName 要换肤的属性名
     * @param skinAttr 换肤的策略
     */
    public static void addSkinAttr(String attrName, SkinAttr skinAttr) {
        if (!TextUtils.isEmpty(attrName) && null != skinAttr) {
            if (!mSkinAttrNames.contains(attrName)) {
                mSkinAttrNames.add(attrName);
            }
            mSkinAttrs.put(attrName, skinAttr);
        }
    }

    /**
     * 获取所有要换肤的属性名
     *
     * @return
     */
    public static List<String> getAllAttrName() {
        if (mSkinAttrNames.isEmpty()) {
            // 先清空之前的
            mSkinAttrNames.clear();
            Set<String> attrNameSet = mSkinAttrs.keySet();
            Iterator<String> iterator = attrNameSet.iterator();
            while (iterator.hasNext()) {
                mSkinAttrNames.add(iterator.next());
            }
        }
        return mSkinAttrNames;
    }

    /**
     * 根据属性名获取相应的换肤策略
     *
     * @param attrName
     * @return
     */
    public static SkinAttr getSkinAttr(String attrName) {
        return mSkinAttrs.get(attrName);
    }
}