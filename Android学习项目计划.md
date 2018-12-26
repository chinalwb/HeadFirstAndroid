### Android学习项目计划

##### Issues:
1. Retrofit Builder 的正确使用方式，单例模式？还是每次请求都要build一个新的对象？
2. Retrofit 如何对请求结果缓存？
3. Retrofit 配合 RxJava?
4. 屏幕旋转的时候list如何保存状态?
5. CoordinatorLayout 工作原理
6. Layout behavior 工作原理
7. DrawerLayout Fragment切换的时候fragment状态保存且切换回来不需要重新加载如何实现？
8. Fragment#setRetainFragment(true) 的原理？

#### 大课题
1. 屏幕旋转时各种状态的保存与恢复？


[玩Android](http://www.wanandroid.com/index)

1. 启动Splash Activity
	- ViewPager
	- 熟悉Activity的启动模式
2. 主Activity [√]
	- 侧滑菜单 NavigationDrawer [√]
		- 首页
		- 公众号
			- TabLayout + ViewPager [√]
		- 本地文章 （SQLite）
	- Fragment [√]
3. 文章列表 [√]
	- 搜索
	- Retrofit [√] / RxJava / RxAndroid
	- RecyclerView [√] / CardView [√]
4. 文章详情
	- 分享
	- 保存文章（Service / IntentService / AsyncTask）
	- CoordinatorLayout
	- Intent的使用
	- Bundle
5. 文章列表缓存
	- SQLite
6. 本地文章列表与编辑
	- 编辑之后用Broadcast 来通知list页面
	- 发送一个notification。。专门实验用
7. MVP [√]

X. Content Provider
	- 访问其他APP的Content Provider
	- 提供Content Provider接口让其他APP来调用

------

#### 未覆盖到的：
1. * Bound Service
2. * ProGuard

#### 可以替换Jetpack功能的：
* room

#### 可以选择的设计模式：
* Repository pattern -- 远程和本地数据库的切换


-----
#### 我的笔记

1. Fragment 生命周期回调方法的执行
	- 场景：首次加载
		1. onAttach
		2. onCreate
		3. onCreateView
		4. onActivityCreated
		5. onStart
		6. onResume
	- 场景：从可见到不可见（Home 或 Recent Apps）
		1. onPause
		2. onSaveInstanceState
		3. onStop
	- 场景：从正常可见状态进行屏幕旋转
		1. onPause
		2. onSaveInstanceState
		3. Activity#onSaveInstanceState
		4. onStop
		5. onDestroy
		6. onDetach
		7. **Activity**#onDestroy
		8. Fragment空构造
		9. onAttach
		10. onCreate
		11. **Activity**#onCreate
		12. onCreateView
		13. onActivityCreated

2. ActionBar --> Toolbar
	- 步骤
		1. Toolbar是support design包中的类
		2. 先要确认Application Theme 是：NoActionBar 类型的。比如 `Theme.AppCompat.Light.NoActionBar`
		3. 在layout文件中定义 Toolbar。定义时需要注意：
			1. width = match_parent
			2. height = ?attr/actionBarSize -- ?attr 表示使用当前theme中的这个属性
			3. theme = "Theme.AppCompat.Light.DarkActionBar" -- 这个theme确保toolbar看起来是跟没修改之前的ActionBar是一样的
		4. 在Activity中得到Toolbar的引用并调用 setSupportActionBar(Toolbar)

3. CoordinatorLayout 使用
	- 要点
		1. 引入CoordinatorLayout （一般作为根布局）
		2. 在CoordinatorLayout中加入子布局
			1. AppBarLayout - 其中包含Toolbar
			2. 具体要显示的内容
		3. 为可滑动的控件加上 `layout_behavior="@string/appbar_scrolling_view_behavior"` 属性，监听触发滑动事件
		4. 为需要协调滑动的控件 加上 `layout_scrollFlags="scrll|enterAlways"` 属性，来响应主控件的滑动事件 -- 其他的取值有：`enterAlwaysCollapsed`, `exitUntilCollapsed`, `snap`, `snapMargins`

4. DrawerLayout 使用
	- 要点
		1. layout XML 中主要组成部分是： `DrawerLayout` 和  `NavigationView`
		2. DrawerLayout 在layout中不需要做特殊配置，只需要指定height 、 width等基本属性即可。他包含了两个直接的子view，一个是不考虑任何NavigationView显示的状态下的Activity的布局，另一个是 NavigationView
		3. NavigationView 包含了两个主要组件：一个是 app:headerLayout 另一个是 app:menu
		4. app:headerLayout 需要指定一个layout文件，最终显示在navigation view画出菜单的上部
		5. app:menu 需要指定一个 /res/menu/xxx_menu.xml menu文件
		6. 在menu文件中，主要由两部分组成：一个是 <group />, 另一个是 <item />
		7. group标签规定了一组menu， 在group之外的item会跟group之间有一条分割线。item 元素内还可以继续包含menu标签，此时的group就是一个子group。group有一个属性：checkableBehavior, 他可选值有三种：1. all (显示为CheckBox样式， 多选) 2. single (显示为 radio 样式, 单选)  3. none (没有样式)

5. Fragment#setRetainFragment(true) 在设备状态发生改变的时候保存Fragment不被销毁重建。
