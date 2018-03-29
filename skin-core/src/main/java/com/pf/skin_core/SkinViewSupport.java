package com.pf.skin_core;

/**
 * @author zhaopf
 * @version 1.0
 * @QQ 1308108803
 * @date 2018/3/24
 */
public interface SkinViewSupport {
    /**
     * 自定义控件换肤，因为无法知道自定义控件有什么属性，所以让自定义控件自己去换肤
     */
    void applySkin();
}