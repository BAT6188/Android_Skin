package com.pf.skin_core.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

/**
 * @author zhaopf
 * @version 1.0
 * @QQ 1308108803
 * @date 2018/3/24
 */
public class SkinResources {
    /**
     * 单例
     */
    private static SkinResources instance;
    /**
     * 原来的app中的resources
     */
    private Resources mAppResources;
    /**
     * 皮肤包里的resources
     */
    private Resources mSkinResources;
    /**
     * 皮肤包的包名
     */
    private String mSkinPkgName;
    /**
     * 是否使用默认的皮肤(app中的)
     */
    private boolean isDefaultSkin = true;

    private SkinResources(Context context) {
        mAppResources = context.getResources();
    }

    /**
     * 初始化
     *
     * @param context
     */
    public static void init(Context context) {
        if (null == instance) {
            synchronized (SkinResources.class) {
                if (null == instance) {
                    instance = new SkinResources(context);
                }
            }
        }
    }

    public static SkinResources getInstance() {
        return instance;
    }

    /**
     * 换肤
     *
     * @param resources
     * @param pkgName
     */
    public void applySkin(Resources resources, String pkgName) {
        mSkinResources = resources;
        mSkinPkgName = pkgName;
        isDefaultSkin = TextUtils.isEmpty(mSkinPkgName) || null == mSkinResources;
    }

    /**
     * 使用app中默认的皮肤
     */
    public void reset() {
        mSkinResources = null;
        mSkinPkgName = "";
        isDefaultSkin = true;
    }

    public int getIdentifier(int resId) {
        if (isDefaultSkin) {
            return resId;
        }
        // 在皮肤包中不一定就是 当前程序的 id
        // 获取对应id 在当前的名称 colorPrimary
        // R.drawable.ic_launcher
        String resType = mAppResources.getResourceTypeName(resId); // drawable
        String resName = mAppResources.getResourceEntryName(resId); // ic_launcher
        int skinId = mSkinResources.getIdentifier(resName, resType, mSkinPkgName);
        return skinId;
    }

    /**
     * 获取颜色
     *
     * @param resId
     * @return
     */
    public int getColor(int resId) {
        if (isDefaultSkin) {
            return mAppResources.getColor(resId);
        }
        int skinId = getIdentifier(resId);
        if (skinId == 0) {
            return mAppResources.getColor(resId);
        }
        return mSkinResources.getColor(skinId);
    }

    public ColorStateList getColorStateList(int resId) {
        if (isDefaultSkin) {
            return mAppResources.getColorStateList(resId);
        }
        int skinId = getIdentifier(resId);
        if (skinId == 0) {
            return mAppResources.getColorStateList(resId);
        }
        return mSkinResources.getColorStateList(skinId);
    }

    /**
     * 获取drawable
     *
     * @param resId
     * @return
     */
    public Drawable getDrawable(int resId) {
        // 如果没有皮肤就是用默认的
        if (isDefaultSkin) {
            return mAppResources.getDrawable(resId);
        }
        int skinId = getIdentifier(resId);
        if (skinId == 0) {
            return mAppResources.getDrawable(resId);
        }
        return mSkinResources.getDrawable(skinId);
    }

    /**
     * 可能是Color 也可能是drawable
     *
     * @return
     */
    public Object getBackground(int resId) {
        String resourceTypeName = mAppResources.getResourceTypeName(resId);
        if (resourceTypeName.equals("color")) {
            return getColor(resId);
        } else {
            // drawable
            return getDrawable(resId);
        }
    }

    /**
     * 获取string
     *
     * @param resId
     * @return
     */
    public String getString(int resId) {
        try {
            if (isDefaultSkin) {
                return mAppResources.getString(resId);
            }
            int skinId = getIdentifier(resId);
            if (skinId == 0) {
                return mAppResources.getString(skinId);
            }
            return mSkinResources.getString(skinId);
        } catch (Resources.NotFoundException e) {
        }
        return null;
    }

    /**
     * 获取字体
     *
     * @param resId
     * @return
     */
    public Typeface getTypeface(int resId) {
        String skinTypefacePath = getString(resId);
        if (TextUtils.isEmpty(skinTypefacePath)) {
            return Typeface.DEFAULT;
        }
        try {
            Typeface typeface;
            if (isDefaultSkin) {
                typeface = Typeface.createFromAsset(mAppResources.getAssets(), skinTypefacePath);
                return typeface;
            }
            typeface = Typeface.createFromAsset(mSkinResources.getAssets(), skinTypefacePath);
            return typeface;
        } catch (RuntimeException e) {
        }
        return Typeface.DEFAULT;
    }
}