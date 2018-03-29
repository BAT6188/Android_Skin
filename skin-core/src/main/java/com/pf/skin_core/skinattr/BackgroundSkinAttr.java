package com.pf.skin_core.skinattr;

import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.view.View;

import com.pf.skin_core.utils.SkinResources;

/**
 * @author zhaopf
 * @version 1.0
 * @QQ 1308108803
 * @date 2018/3/25
 * 替换背景颜色
 */
public class BackgroundSkinAttr implements SkinAttr {
    /**
     * @param skinAttrName 要换肤的属性
     * @param view         要换肤的控件
     * @param resId        资源id
     */
    @Override
    public void applySkin(String skinAttrName, View view, int resId) {
        Object background = SkinResources.getInstance().getBackground(resId);
        // color
        if (background instanceof Integer) {
            view.setBackgroundColor((Integer) background);
        } else {
            ViewCompat.setBackground(view, (Drawable) background);
        }
    }
}