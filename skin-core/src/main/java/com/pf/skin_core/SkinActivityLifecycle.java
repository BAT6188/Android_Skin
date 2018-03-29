package com.pf.skin_core;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.view.LayoutInflater;

import com.pf.skin_core.utils.SkinThemeUtils;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * @author zhaopf
 * @version 1.0
 * @QQ 1308108803
 * @date 2018/3/24
 * activity 生命周期回调
 */
public class SkinActivityLifecycle implements Application.ActivityLifecycleCallbacks {
    /**
     * 每个activity对应一个view创建工厂
     */
    private HashMap<Activity, SkinLayoutFactory2> mLayoutFactory2Map =
            new HashMap<Activity, SkinLayoutFactory2>();

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        // 更新状态栏
        SkinThemeUtils.updateStatusBar(activity);
        // 获取布局加载器
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        try {
            // 如果代码中已经设置了view加载工厂，
            // 如果再设置的话LayoutInflater中的mFactorySet就是true，那么反射改为false
            Field mFactorySetField = LayoutInflater.class.getDeclaredField("mFactorySet");
            mFactorySetField.setAccessible(true);
            mFactorySetField.setBoolean(layoutInflater, false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        // 设置 view 加载工厂
        SkinLayoutFactory2 factory2 = new SkinLayoutFactory2(activity);
        LayoutInflaterCompat.setFactory2(layoutInflater, factory2);
        // 一个activity对应一个factory2
        mLayoutFactory2Map.put(activity, factory2);
        // 注册观察者
        SkinManager.getInstance().addObserver(factory2);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        SkinLayoutFactory2 skinLayoutFactory2 = mLayoutFactory2Map.remove(activity);
        // 注销观察者
        SkinManager.getInstance().deleteObserver(skinLayoutFactory2);
    }

    /**
     * 更新皮肤
     *
     * @param activity
     */
    public void updateSkin(Activity activity) {
        SkinLayoutFactory2 skinLayoutFactory2 = mLayoutFactory2Map.get(activity);
        skinLayoutFactory2.update(null, null);
    }
}