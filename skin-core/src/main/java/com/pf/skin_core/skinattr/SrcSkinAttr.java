package com.pf.skin_core.skinattr;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.pf.skin_core.utils.SkinResources;

/**
 * @author zhaopf
 * @version 1.0
 * @QQ 1308108803
 * @date 2018/3/25
 * 替换src属性
 */
public class SrcSkinAttr implements SkinAttr {
    /**
     * @param skinAttrName 要换肤的属性
     * @param view         要换肤的控件
     * @param resId        资源id
     */
    @Override
    public void applySkin(String skinAttrName, View view, int resId) {
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            Object src = SkinResources.getInstance().getBackground(resId);
            if (src instanceof Integer) {
                imageView.setImageDrawable(new ColorDrawable((Integer) src));
            } else {
                imageView.setImageDrawable((Drawable) src);
            }
        }
    }
}