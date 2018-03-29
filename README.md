### Android换肤

`app` 是要安装的应用程序

`app-skin` 是皮肤包(就是一个apk，不需要签名，不需要安装)

`skin-core` 是换肤核心代码

1、替换图片，字体颜色这些

    在皮肤包中定义和主程序中名字一样的颜色值或者图片即可

2、替换字体

    在皮肤包中 `strings.xml` 中配置 `typeface` 路径，指向assets路径下

    在 `TextView` 中设置 `typeface` 属性即可

    如果不想替换字体，设为空即可

3、替换自定义控件

    需要让自定义控件实现 `SkinViewSupport` 接口，自己实现换肤功能

4、扩展要替换的属性

    类实现 `SkinAttr` 接口，然后调用 `SkinConfig.addSkinAttr(attrName, skinAttr)` 即可

### 使用

    1、在 `Application` 中初始化

    2、自定义 `View` 需要实现 `SkinViewSupport`接口，然后在 `Activity` 的 `onCreate` 方法调用 `SkinManager.getInstance().updateSkin(this);`

    3、生成 `app-skin` 的 `apk` 文件，将后缀改为 `skin`，然后放入手机 `sdCard` 根目录下

    4、运行 `app` 模块