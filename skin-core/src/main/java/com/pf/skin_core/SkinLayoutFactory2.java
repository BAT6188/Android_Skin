package com.pf.skin_core;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.pf.skin_core.utils.SkinThemeUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * @author zhaopf
 * @version 1.0
 * @QQ 1308108803
 * @date 2018/3/24
 */
public class SkinLayoutFactory2 implements LayoutInflater.Factory2, Observer {
    /**
     * 系统控件前面的包名
     */
    private static final String[] mClassPrefixList = {
            "android.widget.",
            "android.view.",
            "android.webkit."
    };
    /**
     * view 两个参数的构造方法，反射的时候用
     */
    private static final Class<?>[] mConstructorSignature = new Class[]{
            Context.class, AttributeSet.class};
    /**
     * 缓存，减少了反射的次数
     */
    private static final HashMap<String, Constructor<? extends View>> sConstructorMap =
            new HashMap<String, Constructor<? extends View>>();
    /**
     * 那个activity 的view工厂
     */
    private Activity activity;
    /**
     * 属性处理
     */
    private SkinAttribute skinAttribute;

    public SkinLayoutFactory2(Activity activity) {
        this.activity = activity;
        // 获取字体
        Typeface typeface = SkinThemeUtils.getSkinTypeface(activity);
        skinAttribute = new SkinAttribute(typeface);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        // 反射得到view
        View view = createViewFromTag(name, context, attrs);
        // 自定义view
        if (null == view) {
            view = createView(name, context, attrs);
        }
        // 筛选符合条件的view
        skinAttribute.load(view, attrs);
        return view;
    }

    private View createViewFromTag(String name, Context context, AttributeSet attrs) {
        // 包含了 . 代表是自定义控件
        if (-1 != name.indexOf(".")) {
            return null;
        }
        View view = null;
        for (int i = 0; i < mClassPrefixList.length; i++) {
            view = createView(mClassPrefixList[i] + name, context, attrs);
            if (null != view) {
                break;
            }
        }
        return view;
    }

    /**
     * 反射得到view 的实例
     *
     * @param name
     * @param context
     * @param attrs
     * @return
     */
    private View createView(String name, Context context, AttributeSet attrs) {
        Constructor<? extends View> constructor = sConstructorMap.get(name);
        if (null == constructor) {
            try {
                Class<? extends View> aClass = context.getClassLoader()
                        .loadClass(name)
                        .asSubclass(View.class);
                constructor = aClass.getDeclaredConstructor(mConstructorSignature);
                // 加入缓存
                sConstructorMap.put(name, constructor);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        if (null != constructor) {
            try {
                return constructor.newInstance(context, attrs);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return null;
    }

    @Override
    public void update(Observable o, Object arg) {
        // 更新状态栏
        SkinThemeUtils.updateStatusBar(activity);
        // 获取字体
        Typeface skinTypeface = SkinThemeUtils.getSkinTypeface(activity);
        // 换肤
        skinAttribute.applySkin(skinTypeface);
    }
}