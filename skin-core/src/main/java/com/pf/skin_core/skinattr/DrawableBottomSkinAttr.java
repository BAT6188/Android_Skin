package com.pf.skin_core.skinattr;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.pf.skin_core.utils.SkinResources;

/**
 * @author zhaopf
 * @version 1.0
 * @QQ 1308108803
 * @date 2018/3/25
 */
public class DrawableBottomSkinAttr implements SkinAttr {
    /**
     * @param skinAttrName 要换肤的属性
     * @param view         要换肤的控件
     * @param resId        资源id
     */
    @Override
    public void applySkin(String skinAttrName, View view, int resId) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            Drawable[] compoundDrawables = textView.getCompoundDrawables();
            Drawable bottom = SkinResources.getInstance().getDrawable(resId);
            textView.setCompoundDrawablesWithIntrinsicBounds(compoundDrawables[0],
                    compoundDrawables[1], compoundDrawables[2], bottom);
        }
    }
}