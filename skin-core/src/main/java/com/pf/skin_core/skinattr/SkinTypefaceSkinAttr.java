package com.pf.skin_core.skinattr;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.pf.skin_core.utils.SkinResources;

/**
 * @author zhaopf
 * @version 1.0
 * @QQ 1308108803
 * @date 2018/3/25
 * 替换字体
 */
public class SkinTypefaceSkinAttr implements SkinAttr {
    /**
     * @param skinAttrName 要换肤的属性
     * @param view         要换肤的控件
     * @param resId        资源id
     */
    @Override
    public void applySkin(String skinAttrName, View view, int resId) {
        if (view instanceof TextView) {
            Typeface typeface = SkinResources.getInstance().getTypeface(resId);
            ((TextView) view).setTypeface(typeface);
        }
    }
}