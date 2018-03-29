package com.pf.skin_core;

import android.app.Activity;
import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.text.TextUtils;

import com.pf.skin_core.utils.SkinPreference;
import com.pf.skin_core.utils.SkinResources;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Observable;

/**
 * @author zhaopf
 * @version 1.0
 * @QQ 1308108803
 * @date 2018/3/24
 */
public class SkinManager extends Observable {
    /**
     * 单例
     */
    private static SkinManager instance;
    /**
     * application
     */
    private Application application;
    /**
     * 生命周期回调
     */
    private SkinActivityLifecycle activityLifecycle;

    private SkinManager(Application application) {
        this.application = application;
        // 初始化perference
        SkinPreference.init(application);
        // 初始化resources
        SkinResources.init(application);
        // 生命周期回调
        activityLifecycle = new SkinActivityLifecycle();
        // 注册 activity 生命周期回调
        application.registerActivityLifecycleCallbacks(activityLifecycle);
        // 加载皮肤包
        loadSkin(SkinPreference.getInstance().getSkin());
    }

    public static SkinManager getInstance() {
        return instance;
    }

    /**
     * 初始化
     *
     * @param application
     */
    public static void init(Application application) {
        if (null == instance) {
            synchronized (SkinManager.class) {
                if (null == instance) {
                    instance = new SkinManager(application);
                }
            }
        }
    }

    /**
     * 加载皮肤包并更新
     *
     * @param skinPath 皮肤包路径
     */
    public boolean loadSkin(String skinPath) {
        // 使用默认的皮肤
        if (TextUtils.isEmpty(skinPath) || !new File(skinPath).exists()) {
            SkinPreference.getInstance().setSkin("");
            SkinResources.getInstance().reset();
            // 应用皮肤包
            setChanged();
            // 通知观察者
            notifyObservers();
            return false;
        } else {
            try {
                AssetManager assetManager = AssetManager.class.newInstance();
                // 将皮肤包添加到资源管理去
                Method addAssetPathMethod = assetManager.getClass()
                        .getDeclaredMethod("addAssetPath", String.class);
                addAssetPathMethod.setAccessible(true);
                addAssetPathMethod.invoke(assetManager, skinPath);

                Resources appResources = application.getResources();
                Resources skinResources = new Resources(assetManager,
                        appResources.getDisplayMetrics(), appResources.getConfiguration());
                // 获取外部皮肤包的包名
                PackageManager appPackageManager = application.getPackageManager();
                PackageInfo info = appPackageManager
                        .getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES);
                String pkgName = info.packageName;
                // 保存资源和包名
                SkinResources.getInstance().applySkin(skinResources, pkgName);
                // 保存当前使用的皮肤包
                SkinPreference.getInstance().setSkin(skinPath);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        // 应用皮肤包
        setChanged();
        // 通知观察者
        notifyObservers();
        return true;
    }

    /**
     * 更新皮肤
     *
     * @param activity
     */
    public void updateSkin(Activity activity) {
        if (null != activityLifecycle) {
            activityLifecycle.updateSkin(activity);
        }
    }
}