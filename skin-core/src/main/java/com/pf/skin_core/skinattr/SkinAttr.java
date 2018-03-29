package com.pf.skin_core.skinattr;

import android.view.View;

/**
 * @author zhaopf
 * @version 1.0
 * @QQ 1308108803
 * @date 2018/3/25
 * 为了扩展，当新添加需要换肤的属性的时候，具体的换肤操作需要实现这个接口
 */
public interface SkinAttr {
    /**
     * 换肤
     *
     * @param skinAttrName 要换肤的属性
     * @param view         要换肤的控件
     * @param resId        资源id
     */
    void applySkin(String skinAttrName, View view, int resId);
}