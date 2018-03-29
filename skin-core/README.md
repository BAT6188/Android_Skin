### `Android` 换肤

思路

首先要拿到显示的 `View`

然后筛选哪些控件是需要更换的

    这里是通过查找控件的属性里有没有background等来筛选的

最后应用皮肤包

    将皮肤包下载到sd卡中，然后通过 `AssetManager` 加载，获取里面的资源

#### Activity

 `Activity` 可以通过设置 `LayoutInflater` 的 `factory` 或者 `factory2` 来获取 `View`

#### Fragment

 `Fragment` 查看源码发现他自己的 `LayoutInflater` 是通过 `onGetLayoutInflater` 方法来获得的

 发现是通过一个 `mHost.onGetLayoutInflater()` 获得的，而 `mHost` 是 `FragmentActivity#HostCallbacks`

 在 `final FragmentController mFragments = FragmentController.createController(new HostCallbacks());` 创建的

 而 `LayoutInflater` 是 `FragmentActivity.this.getLayoutInflater().cloneInContext(FragmentActivity.this)` 来的

 `LayoutInflater` 的具体实现类是 `PhoneLayoutInflater`，clone 的目的是为了拼接包名

 到最后发现 `Fragment` 的 `factory2` 和 `Activity` 的是同一个，所以不用特意去管 `Fragment`

#### `状态栏` 和 `NavigationBar`

这两个都是在 `style` 里面配置颜色就可以

#### 字体

在皮肤包的 `assets` 下放字体文件，然后在 `strings.xml` 配置即可

注意：`typeface` 为全局字体
      `typeface2` 为个别的字体

#### 自定义控件

这里因为不知道自定义控件的属性到底是什么，所以讲问题抛出去，让自定义控件自己解决(实现 `SkinViewSupport` 接口)，

在换肤的时候调用即可