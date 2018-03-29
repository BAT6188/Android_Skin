package com.pf.skin_core.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author zhaopf
 * @version 1.0
 * @QQ 1308108803
 * @date 2018/3/24
 */
public class SkinPreference {
    /**
     * 单例
     */
    private static SkinPreference instance;
    /**
     * 存储
     */
    private final SharedPreferences mPreference;
    /**
     * 存储文件名
     */
    private static final String SKIN_SHARED = "skins";
    /**
     * 存储字段名
     */
    private static final String KEY_SKIN_PATH = "skin-path";

    private SkinPreference(Context context) {
        mPreference = context.getSharedPreferences(SKIN_SHARED, Context.MODE_PRIVATE);
    }

    public static void init(Context context) {
        if (null == instance) {
            synchronized (SkinPreference.class) {
                if (null == instance) {
                    instance = new SkinPreference(context);
                }
            }
        }
    }

    public static SkinPreference getInstance() {
        return instance;
    }

    public void setSkin(String skinPath) {
        mPreference.edit().putString(KEY_SKIN_PATH, skinPath).commit();
    }

    public String getSkin() {
        return mPreference.getString(KEY_SKIN_PATH, null);
    }
}