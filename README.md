## HeadFirst

### About Jetpack

* Use support libraries: [Reference](https://developer.android.com/jetpack/androidx/migrate)

```
    implementation 'androidx.appcompat:appcompat:1.0.0'
    implementation 'androidx.constraintlayout:constraintlayout:1.1.2'
```

### Where to find the android support library configurations?
* [Support Library Packages](https://developer.android.com/topic/libraries/support-library/packages)


### Questions
1. [Parcelable V.S. Serializable](https://android.jlelse.eu/parcelable-vs-serializable-6a2556d51538)
2. 

### 2. Most apps need to repond to the user in some way

1.

```
<Button 
...
android:onClick="onClickFindBeer"
/>


//
// If you want a method to respond to a button click
// It must be public, have a void return type, and
// take a single View parameter
public void onClickFindBeer(View view) {

}
```

### 3. State your Intent


* Basic Intent:

	* Intent to start a new Activity

	
	```
	Intent intent = new Intent(Context, TargetActivity.class);
	startActivity(intent);
	```
	
	* `putExtra()` puts extra info in an intent

	
	```
	intent.putExtra("message", value);
	```
	
	* retrieve extra info from an intent
	
	```
	Intent intent = getIntent();
	String message = intent.getStringExtra("message");
	```


* Intent Types

	0. `Intent Resolution`. When calling startActivity(intent); Android gets the `intent`, then Android has to figure out which activity, or activities can handle it. This process is called 'intent resolution'.

	1. Explict intent
	
	**Android has clear instructions about what to do.**
	
	Sample:
	
	```
	Intent intent = new Intent(Context, TargetActivity.class);
	startActivity(intent);
	```
	
	
	2. Implict intent

	**Android uses the info in the intent to figure out which components are able to receive it. Android does this by checking the `intent-filter` in every app's AndroidManifest.xml**
	
	Sample Intent:
	
	```
	Intent intent = new Intent(Intent.ACTION_SEND);
	intent.setType("text/plain");
	intent.putExtra(Intent.EXTRA_TEXT, messageText);
	```

	Sample configuration in AndroidManifest.xml:
	
	```
	<activity android:name="ShareActivity">
		<intent-filter>
			<action android:name="android.intent.action.SEND" /> <!-- This tells Android this Activity can handle SEND action -->
			<category android:name="android.intent.category.DEFAULT" /> <!-- If your activity wants to handle implict intent, then it must contains the DEFAULT category -->
			<data android:mimeType="text/plain" /> <!-- This activity can handle these two types data --> 
			<data android:mimeType="image/*" />
		</intent-filter>
	</activity>
	```
	
	BTW: The main Activity of an App, it's configuration is like this:
	
	1. `<action android:name = "android.intent.action.MAIN" />`
	2. `<category android:name = "android.intent.category.LAUNCHER" />`
	
	```
	<activity name="XXActivity">
		<intent-filter>
			<action android:name="android.intent.action.MAIN" />
			<category android:name="android.intent.category.LAUNCHER" />
		</intent-filter>
	</activity>
	```
	
	
	3. Multiple Activities:
	
	* `startActivity(intent)` may return multiple matched Activities, and user can choose one to launch, and user may choose `Always` to launch with that Activity. 
	* `Intent.createChooser(intent, 'Send message via...')` use this method to always show the matched activities list

	* Inside `createChooser`, it does:
	
	```
	Intent intent = new Intent(Intent.ACTION_CHOOSER);
	intent.putExtra(Intent.EXTRA_INTENT, srcIntent);
	intent.putExtra(Intent.EXTRA_TITLE, "Title here!");
	...
	return intent;
	```
	
	* Thus the returned intent will always be an implict Intent whose action is `ACTION_CHOOSER`.
	* Use `intent.resolveActivity(packageManager)` to check if there is any Activities on the device are able to receive the intent.
	* Inside `intent.resolveActivity(packageManager)` it calls `pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)` to get the `ResolveInfo`, if it is not null, construct a new `ComponentName` with the packageName and className, and finally returns it. Or else if returns null, `startActivity` will throw `ActivityNotFoundException`.


### 4. Being an Activity



* only the main Android thread can update the user interface, if any other thread tries to do so, you get a CalledFromWrongThreadException
* `handler.post(Runnable)` / `handler.postDelayed(Runnable, long)`
* LifeCycle methods
	1. onCreate - onCreate() gets called when the activity first created, it's where you do your normal initialize work
	2. onStart - gets called when activity is visible to user
	3. onResume - gets called when activity is able to interact with user
	4. onPause - gets called when activity is partly hidden 
	5. onStop - gets called when activity is hidden to user
	6. onSaveInstanceState -- The onSaveInstanceState() method gets called before onDestroy()
	7. onDestroy

* Lifecycle case: paused and device rotates
	1. Before rotate:
	2. onCreate
	3. onStart
	4. ~~onResume~~ not called because partially visible
	5. Device rotates
	6. onSaveInstanceState
	7. onStop
	8. onDestroy
	9. onCreate
	10. onStart

* Lifecycle case: bypass onResume and onPause (不调用 onResume 和 onPause 的一个场景)
	1. If an activity stops or gets destroyed before it appears in the foreground, the onStart() method is followed by the onStop() method. onResume and onPause are bypassed


* onSaveInstanceState() - called before onStop()
* 
```
2018-12-05 06:14:12.413 19037-19037/com.chinalwb.c4_lifecycle E/XX: onCreate
2018-12-05 06:14:12.430 19037-19037/com.chinalwb.c4_lifecycle E/XX: onStart
2018-12-05 06:14:12.434 19037-19037/com.chinalwb.c4_lifecycle E/XX: onResume
2018-12-05 06:14:17.267 19037-19037/com.chinalwb.c4_lifecycle E/XX: onPause
2018-12-05 06:14:17.267 19037-19037/com.chinalwb.c4_lifecycle E/XX: **onSaveInstanceState**
2018-12-05 06:14:17.270 19037-19037/com.chinalwb.c4_lifecycle E/XX: onStop
2018-12-05 06:14:17.271 19037-19037/com.chinalwb.c4_lifecycle E/XX: onDestroy
2018-12-05 06:14:17.297 19037-19037/com.chinalwb.c4_lifecycle E/XX: onCreate
2018-12-05 06:14:17.320 19037-19037/com.chinalwb.c4_lifecycle E/XX: onStart
2018-12-05 06:14:17.323 19037-19037/com.chinalwb.c4_lifecycle E/XX: onResume
```	

* onRestart() -- Called when your activity has been stopped but just before it gets started again
	- onRestart 的调用时机：
		1. when 当前的Activity 启动了第二个Activity，从第二个Activity点击返回按钮返回到前一个Activity的时候，前一个Activity的onRestart()会被调用
		2. when 用户点击了Home，然后很快再点开app的时候，这个 Activity 的 onRestart() 会被调用
		3. when 用户点击了 Recent Apps, 然后再选中 app 的时候，这个 Activity 的 onRestart() 会被调用


### 5. Views and View Groups

1. LinearLayout
	- android:layout_gravity - 指定自己本身相对于父view所处的位置
	- android:gravity - 指定自己view内部内容的位置

	

### 6. ConstraintLayout
	
	`app:layout_constraintRight_toRightOf='parent'`
	
### 7. ListView
	
* An Adapter acts as a bridge between a view and a data source.
	
* Use of `android:entries`
	
```
	<string-array name="options">
		<item>Drinks</item>
       <item>Food</item>
       <item>Store</item>
	</string>
	
	<ListView 
		...
		android:entries="@array/options" // 这种情况不需要使用 adapter, 在ListView内部直接设定了 ArrayAdapter
	/>
```

* Use of `ArrayAdapter`

```
ArrayAdapter<Drink> listAdapter = new ArrayAdapter<>(
	this, // context
	android.R.layout.simple_list_item_1, // 内置layout 只包含了一个TextView
	Drink.drinks // ArrayAdapter 默认调用这个数组中每个元素的 toString 方法
);
```

### 8. Material Theme Toolbar

* Theme 应用在Application 或 Activity 中，用来让App中的页面看起来样式是统一的
* Support Library: 有些新功能只有在新的API版本中才能使用，比如Material Theme是在API Level 21中引入的，那么就存在一个问题，如果我们使用了这个Theme，默认只能在Android 5.0以上的设备上运行，那如果在旧设备上怎么处理呢？答案就是 使用 Support Library. 所以可以理解 Support Library 就是让旧版本的手机上能用上新版本的一些特性而出现的。
* 常见的Support Libraries有：
	1. v4 Support Library
	2. v7 AppCompat Library
	3. v7 Cardview Library
	4. Constraint Layout Library
	5. v7 RecyclerView Library
	6. Design Support Library

* Support Libraries 最低可以支持到哪个版本？
	- 不同的Support Library版本支持的最低版本不一样
	- 在Support Library 24.2.0之前，v4前缀的最低可以支持到API level 4, v7前缀的最低可以支持到API level 7
	- 在24.2.0发布之后，最低支持的版本改为了API level 9
	- 以后发布的support libraries最低支持版本还会继续增大

* android:layout_height="?attr/actionBarSize" -- ?attr/xx - ?attr 前缀的属性表示从当前theme中引用一个属性值
* android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar" -- ThemeOverlay 是一个特殊的theme属性，他能在当前theme基础上改变一些属性值
* ShareActionProvider
	- 如果你的Activity想要在Toolbar的menu上加分享按钮，那么可以用ShareActionProvider来做
	- 用ShareActionProvider不需要给menu item的定义中加上 icon 图片，因为ShareActionProvider自己会加上图标
	- 除了在 menu_main.xml 中加上 ShareActionProvider 的menu item之外，还需要在Activity中进行设置
	- `onCreateOptionsMenu` 方法中，用 `MenuItemCompt.getActionProvider(menuItem)` 来获得一个`ShareActionProvider`的引用
	- 调用 ShareActionProvider的 setShareIntent(intent) 方法
	- 把 intent 传递给 ShareActionProvider 之后，在 ShareActionProvider 内部会根据传入的 Intent 列出所有可以处理这个 intent 的 apps，用户点击某个app的时候，就会把当初传递给 ShareActionProvider 的那个intent 传递给最终选定的那个APP


### 9. Fragments

* Fragment#onCreateView(inflater, container, savedInstanceState)

```
public View onCreateView(LayoutInflater inflater, // 用来解析layout xml 文件	ViewGroup container, // 将fragment添加到这个控件中
	Bundle savedInstanceState) { // 之前保存下来的 fragment 的状态	return inflater.inflate(
		R.layout.xxx, // 要解析的 xml 文件
		container,  // parent 控件。解析 layout 文件所用的父控件，有两个用途：1. 用来协助计算 layout xml 中根控件的大小 2. 如果第三个参数 attachToRoot 为 true，则需要把解析出来的那个view加到父控件中
		false); // 是否加到父控件中
}
```

* 每个 Fragment 都必须有一个无参数的构造方法。这是因为Android在需要的时候会用调用这个无参构造方法来对fragment进行重新初始化。如果没有的话在执行这个重新初始化的过程当中就会抛出异常。

* Activity lifecycle V.S. Fragment lifecycle

Activity|Fragment
---|:--
onCreate(1)|onAttach(Context) // Fragment gets attached to Activity
onCreate(2)|onCreate(Bundle savedInstanceState) // similar to Activity's onCreate, can be used to do the iniital setup of the fragment
onCreate(3)|onCreateView(LayoutInflater, ViewGroup, Bundle) // Fragment uses a layout inflator to create view
onCreate(4)|onActivityCreated(Bundle) // Called when the activity's onCreate() has completed
onStart()|onStart() // Called when fragment becomes visible
onResume()|onResume() // Called when fragment is visible and actively running -- i.e.: called when fragment is visible and gets focus
onPause()|onPause() // Called when fragment is no longer interacting with the user
onStop()|onStop() // Called when fragment is no longer visible to the user
onDestroy(1)|onDestroyView() // Fragment wants to clear away any resources associated with its view
onDestroy(2)|onDestroy() // Fragment clears away any resources created by fragment itself
onDestroy(3)|onDetach() // Called when fragment finally loses contact with the activity

* Fragment#getView() --> returns the root view of the fragment

* A1: 为什么Fragment没有findViewById() 方法？
	- Q1: 因为Fragment不是view
	- A2: 那为什么Activity有findViewById()方法？
	- Q2: 因为Activity#findViewById是调用了 getWindow().findViewById(), 而getWindow()返回的Window对象本身也不是View，但是Window#findViewById()是调用了Window的getDecorView()#findViewById(), 而 getDecorView() 返回了一个View对象
	- A3: 那 DecorView 又是什么呢？
	- Q3: DecorView是整个Window的根View

### 10. Tablet

* res/layout-large 文件夹只有在大屏幕的设备上才会用到
* if you want to create a layout that willl only be used by very large tablets in landscape mode, you would create a folder called **layout-xlarge-land** and put the layout file in that folder
* Android会在App运行的时候根据运行的设备自动去寻找最匹配的配置文件，如果没有最匹配的则找比当前期望小的配置项，如果只有比当前大的配置项的话Android不会用那些文件，App会crash。-- Android decides at runtime which resources to use by checking the spec of the device and looking for the best match. If there is no exact match, it will use resource designed for a smaller than the current one. If resources are only available for screens larger than the current one, Android won't use them and the app will crash.
* AndroidManifest.xml, use `supports-screens` to specify which screen size you want or don't want to support

```
<supports-screens android:smallScreens="false" />
```

* Use `FrameLayout` as fragment container when you want to change the fragment at runtime
* Using FragmentTransaction to change the fragment
	1. begingTransaction
		`getSupportFragmentManager().beginTransaction()`
	2. specify the changes
		* transaction.add(R.id.fragment_container, fragment)
		* transaction.replace(R.id.fragment_container, fragment)
		* transaction.remove(fragment)
		* transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE) // 指定动画
		* transaction.addToBackStack(null)
	3. commit
		* transaction.commit();

	
* Fragment#onSaveInstanceState(Bundle outState) // 当Fragment被销毁的之前回调这个方法来保存状态
* Fragment#onCreate(Bundle savedInstanceState) // 当Fragment重建的时候之前保存的状态会传递到 savedInstanceState 里面


### 11. Dynamic Fragment - Nested Fragments

* `onCreate(Bundle savedInstanceState)` if `savedInstanceState` is null, this means that Activity is being created for the first time; or else if the `savedInstanceState` is not null, that means Activity is being recreated after having been destroyed.

* `getFragmentManager()` returns the fragment manager associated with the fragment's parent activity. Any fragment transaction you create using this fragment manager is added to the back stack as a separate transaction.

* `getChildFragmentManager()` returns the fragment manager associated with the fragment's parent fragment. Any fragment transaction you create using this fragment manager is added to the back stack insdie the parent fragment transaction, not a separate transaction.

* About the lifecycle of Activity and Fragment
	- Activity#onCreate(Bundle savedInstanceState) 对应了 Fragment 的4个生命周期回调方法：
	- Fragment#onAttach(Context context)
	- Frgment#onCreate(Bundle savedInstanceState) 做Fragment的初始化操作
	- Fragment#onCreateView(LayoutInflater, ViewGroup, Bundle savedInstanceState) 创建fragment 的 view
	- Fragment#onActivityCreated(Bundle savedInstanceState)

### 12. ViewPager

* `FragmentPagerAdapter` - 专门用来把Fragment以page的方式加到ViewPager中。当ViewPager中包含的页面相对较少的时候比较适用，因为用户访问过的所有的Fragment都被存放在内存中。
* `FragmentStatePagerAdapter` - 如果ViewPager中有大量的pages，则使用 FragmentStatePagerAdapter.

* 覆盖 `FragmentPagerAdapter` 的两个方法:
	- `getCount()` 返回view的数量
	- `getItem(int position)` 根据position返回Fragment
	- `getPageTitle(int position)` 根据position返回title
	- `pager.setAdapter(FragmentPagerAdapter)`

* AppBarLayout and TabLayout
	- TabLayout is to display tabs
	- AppBarLayout is to group tabs and toolbar together

* Design Support library is for Material Design purpose

* CoordinatorLayout is to coordinate animations between views
	- 所有的那些你想让他们互相合作来完成动画的view都必须放到CoordinatorLayout中
	- A CoordinatorLayout allows the behavior of one view to affect the behavior of another - 用 CoordinatorLayout 来实现当某一个view的行为改变的同时也去改变另外一个view的行为

* app:layout_behavior // 当用户滑动这个控件的时候，我希望其他控件可以响应
```
<ViewPager
	...
	app:layout_behavior="@string/appbar_scrolling_view_behavior" // This tells CoordinatorLayout you want to react to when user scrolling the content
/>
```

* app:layout_scrollFlags // 当其他控件滑动的时候，这里设定如何响应

```
<Toolbar
	...
	app:layout_scrollFlags="scroll|enterAlways"
/>
```
	
- layout_scrollFlags: scroll 表示当用户滑动其他控件的时候，这个控件(Toolbar)也跟着一起滑动。如果没有设定这个值，这个控件就不会滑动。
- enterAlways: 当用户滑动其他view的时候，这个view会快速滑动到他最初的位置。如果没有设定这个值，他也会滑动，但是会比较慢。
- exitUntilCollapsed: This means we want the toolbar to collapse until it's done collapsing

* `NestedScrollView` - comes from Design Support Library
* CoordinatorLayout only listens for nested scroll events. So you MUST use `NestedScrollView` if you want CoordinatorLayout works; one of the other view works with CoordinatorLayout is RecyclerView.
* `NestedScrollView` can only have one direct child

* Collapsing Toolbar - 可以滑动的toolbar
* Collapsing Toolbar 的最初状态是展开的大图模式，当用户向上滑动的时候，他就开始收缩，直到完全不可见。
	- app:contentScrim="?attr/colorPrimary"

* App bar layout attributes
	- android:layout_height = "300dp" // 为App bar的内容设定一个高度，这是 collapsing toolbar 展开时的最大高度
	- android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar"

* Toolbar attributes
	- app:layout_collapseMode="pin" // 保持toolbar上的控件固定在顶部
	- app:layout_collapseMode="parallax" // collapsing toolbar 中的image用这个mode

----
### CoordinatorLayout使用的几个要素
1. CoordinatorLayout - CoordinatorLayout可以使其中一部分子view响应另外一个子view的滑动事件
2. AppBarLayout - **theme** 确保toolbar上的文字颜色不被AppBarLayout的引入而改变
3. Toolbar - **layout_scrollFlags="scroll|enterAlways"** 当其他子view滚动的时候，这个view会跟着一起滚动
4. TabLayout - **layout_scrollFlags="scroll|enterAlways"** 当其他子view滚动的时候，这个view会跟着一起滚动
5. ViewPager - **layout_behavior="@string/appbar_scrolling_view_behavior"** CoordinatorLayout会根据这个view的滑动事件来通知其他跟随滑动的子view一起滑动
6. NestedScrollView - **可滚动的内容需要放到这个layout中**，CoordinatorLayout只会监听这个Layout的滚动事件然后做出响应。（另外一个跟CoordinatorLayout一起配合工作的是 RecyclerView）


### CollapsingToolbarLayout使用的几个要素
1. CollapsingToolbarLayout - 用在AppBarLayout只内，同时 AppBarLayout 用在CoordinatorLayout 之内
2. 需要为CollapsingToolbarLayout加上 (scrollFlags) **app:layout_scrollFlags="scroll|exitUntilCollapsed"** 属性
3. CollaspingToolbarLayout 的子view需要提供 **layout_collapseMode** 属性，该属性有3个选项： 1. parallax 2. pin 3. none
4. 同样因为一切的协调者都是由 CoordinatorLayout 负责的，所以必须有一个 NestedScrollView来包围要显示的主体内容

### 13. RecyclerView


#### 1. RecyclerView 使用的几个要素
1. Adapter - The recycler view uses the adapter to display the data. It includes a layout manager to specify how the data should be positioned.
	- Adapter has two main jobs:
	- 1. Craete views
	- 2. Bind each view to a piece of data
	- Q: Why doesn't Android provide ready-made adapters for recycler views?
	- A: Because recycler view adapters don't just specify the data that will appear. They also specify the views that will be used for each item in the collection. That means that recycl      er view adapters are both more powerful, and less general, than the list view adapters.
2. LayoutManager - There are a number of built-in layout managers you can use that allow you to position items in a linear list or grid
	- RecyclerViewe uses a layout manager to arrange its views
3. `recyclerView.setAdapter(adapter)` // set adapter to recycler view

#### 2. CardView
1. In layout xml:
	
	```
	<CardView
		...
		xmlns:card_view="http://schemas.android.com/apk/res-auto"
		...
		card_view:cardElevation="2dp" // 设定card view高度
		card_view:cardCornerRadius="4dp" // 设定card view圆角
	>
	... // CardView can only have one direct child (similar to NestedScrollView!)
	</CardView>
	```

2. CardView can only have one direct child.


#### 3. Define the Adapter

1. Define a class extends RecyclerView.Adapter
2. Define a static inner class extends RecyclerView.ViewHolder
3. Define RecyclerView.Adapter constructor to store the data of the RecyclerView
4. Implement RecyclerView#getItemCount() method
5. Define the adapter's view holder: the view holder is used to define what view or views the recycler view should use for each data item it's given
	- CardView
	- ViewHolder constructor, take the cardview as input param
	- call super(v) in constructor
6. Override onCreateViewHolder() method
	- onCreateViewHolder method gets called when the recycler view requires a new view holder
	- onCreateViewHolder takes two params: ViewGroup parent (the recylerview itself in fact) and int viewType
	- onCreateViewHolder returns an instance of ViewHolder
7. Override onBindViewHolder() method
	- onBindViewHolder method gets called whenever the recycler view needs to display data in a view holder
	- onBindViewHolder method takes two params: ViewHolder: the view holder the data needs to bound to, and int position: the position in the data set of the data that needs to be bound
	- The recycler view calls onBindViewHolder when it wants to use (or reuse) a view holder for a new piece of data
8. At last, adapter must be used with RecyclerView. Like: `recyclerView.setAdapter(adapter)`

> #### Response to user click
> 1. Unlike ListView extends AbsListView (extends AdapterView extends ViewGroup), RecyclerView extends ViewGroup
> 2. So RecyclerView has no built-in method like `onItemClickedListener`
> 3. We need to set up the click listener in Adapter like `cardView.setOnClickListener(new View.OnClickListener() { ... })`
> 4. We can also introduce an interface in the adapter, then whenever the adapter being used, it can also call the setListener to set different click listener for different scenarios


#### 4. Specify the layout manager

1. LinearLayoutManager
	- Its constructor takes one parameter, a Context
	
	```
	LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
	recyclerView.setLayoutManager(layoutManager);
	```
	
2. GridLayoutManager
	- Its constructor takes 2 params: one is Context, the other one is int value specifying the number of columns the grid should have
	- Also you can change the grid orientation. To do this, you add two more params to the constructor, the orientation and whether you want the views to appear in reverse order 
	
	```
	GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2); // 2 means the grid layout has 2 columns
	
	// or
	GridLayoutManager layoutManager = new GridLayoutManager(
		getActivity(),
		1, // Column size
		GridLayoutManager.HORIZONTAL, // orientation
		false // reverse or not, false means no reverse
	);
	
	recyclerView.setLayoutManager(layoutManager);
	```
	
3. Staggered grid layout manager
	- Its constructor takes two params, an int value for the number of columns or rows, the other one is the orientation.
	
	```
	StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(
		2, // columns or rows
		StaggeredGridLayoutManager.VERTICAL // orientation
	);
	recyclerView.setLayoutManager(layoutManager);
	```
	
4. `recyclerView.setLayoutManager(layoutManager)`




```
class CaptionedImagesAdapter extends RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder> {
	
	public CaptionedImagesAdapter(object[] data) {
		this.data = data;
	}
	
	@Override
	public int getItemCount() {
		return this.data.length;
	}
	
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		CardView cv = LayoutInflator.from(parent.getContext())
			.inflate(R.layout.card_captioned_image, // the layout of the view hodler
			parent,
			false);
			
		return new ViewHolder(cv);	
	}
	
	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		CardView cardView = holder.cardView;
		ImageView imageView = (ImageView) cardView.findViewById(R.id.info_image);
		Drawable drawable = ContextCompat.getDrawable(cardView.getContext(), imageIds[position]);
		imageView.setImageDrawable(drawable);
		imageView.setContentDescription(captions[position]);
		TextView textView = cardView.findViewById(R.id.info_text);
		textView.setText(captions[position]);
		
		// This is a hard coded click listener
		cardView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = xx;
				startActivity(intent);
			}
		});
		
		cardView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (listener != null) {
					listener.onClick(position);
				}
			}
		});
	}
	
	Listener listener;
	
	interface Listener {
		void onClick(int position);
	}
	
	void setListener(Listener listener) {
		this.listener = listener;
	}
	
	public static class ViewHolder extends RecyclerView.ViewHolder {
		// Define a ViewHolder as inner class
		// ViewHolder is used to specify which views should be used for each data item
		CardView cardView;
		public ViewHolder(CardView view) {
			super(view);
			this.cardView = view;
		}
	}
}
```


### 14. Navigation Drawers

* Go over points
	1. Create toolbar layout:
		```
		<android.support.v7.widget.Toolbar
			xmlns:android="xx"
			android:layout="match_parent"
			android:height="?attr/actionBarSize"
			android:background="?attr/colorPrimary"
			android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar"
		/>
		```

	2. Add a NoActionBar theme:
		```
		<style name="AppTheme" parent="Theme.AppCompat.Light.NoActionBar">
			<!-- Use NoActionBar theme because we want our Toolbar to replace the default app bar -->
			<item name="colorPrimary">...</item>
		</style>
		```
		
#### Navigation drawer layout
1. The header
	- The header is a FrameLayout, contains ImageView and LinearLayout.
	- The LinearLayout's layout_gravity="bottom|left"
2. The options are in menus.xml
	- Each option is a menu-item
	- `<item` must be within a <menu /> tag
	- `<item` can be just a string, and contains other sub-menus
	- items can be within a `<group` tag
	- `<group android:checkableBehavior="single" ... />`

#### How to create a navigation drawer?
1. `DrawerLayout` - use this as the root element of the Activity's layout
2. DrawerLayout needs to contain:
	- The activity's content as the first element
	- A navigation view that defines the drawer as the second
3. `NavigationView`
	- `android:layout_gravity=“start”` - this defines the drawer will be at left
	- `app:headerLayout="@layout/nav_header"` - this includes the drawer's header
	- `app:menu:@menu/menu_nav` - this is the menu resource file containing the drawer's options

4. Add a drawer toggle
	- Define two string items in strings.xml, one is for `nav_open_drawer`, the other one is for `nav_close_drawer`
	- Create a new instance of the `ActionBarDrawerToggle`
		- The constructor of `ActionBarDrawerToggle` takes 5 params:
		- current activity
		- the drawer layout
		- the toolbar layout
		- the IDs of the two string resources above
	- DrawerLayout.addDrawerListener(toggle) // Add the toggle to DrawerLayout
	- toggle.syncState() // Change drawer icon upon opening and closing

5. Respond to the user clicking items in the drawer
	- `NavigationView.OnNavigationItemSelectedListener` - Let the Activity implements interface `NavigationView.OnNavigationItemSelectedListener`
	- NavigationView
	- navigationView.setNavigationItemSelectedListener(this)
	- Override `onNavigationItemSelected(MenuItem item)`
	- In `OnNavigationItemSelected()`, replace the fragment and close drawer
	- Replacing fragment, there are 4 steps: 1. getSupportFragmentManager() 2. fragmentManager.beginTransaction 3. FragmentTransaction.replace(id, fragment) 4. fragmentTransaction.commit()
	- Close drawer: drawer.closeDrawer(GravityCompat.START);
5. Close drawer when user clicks Back button
	```
	@Override
	public void onBackPressed() {
		DrawerLayout drawer = xx;
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			// close drawer
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}
	```
	

### 15. SQLite

* Why SQLite on Android?
	1. It's lightweight. A SQLite database is just a file.
	2. It's optmized for a single user
	3. It's stable and fast. Transaction supported

* Where is the database stored?
	- /data/data/<YOUR_PACKAGE_ID>/databases

* Every database consists of two files
	1. Database file
	2. Journal file

* Built-in SQLite classes in Android
	1. The SQLite Helper - enables you to create and manage databases. You create one by extending the SQLiteOpenHelper class
	2. The SQLite Database - The SQLiteDatabase class gives you access to the database. It's like a SQLConnection in JDBC
	3. Curosr - A cursor lets you read from and write to the database. It's like a ResultSet in JDBC.

	
* Q: SQLite database has no username and password on the database, how is it kept secure?
	- The directory /data/data/<PACKAGE_ID>/databases is only readable by the app itself. The database is secured down at the operating system level.

* Create the SQLite helper
	- extends SQLiteOpenHelper
	- Override onCreate - onCreate() gets called when the database first gets created on the device. Create tables in this method.
	- Override onUpgrade - onUpgrade() gets called when the database needs to be upgraded.

* Specify the database
	- we need to give the database a name. Without a name, the database will disappear oncethe atabase is closed
	- we ned to provide the version of the database. The version needs to be an integer value starting at 1. SQLite helper uses this version number to determine whether the database needs to be upgraded (or created).

* Android: It's a convention to call your primary key columns _id. Android expects there to be an `_id` column on your data.

* onCreate(SQLiteDatabase db) - This method called when the database is created
	- db.execSQL(sqlStringHere); - Use `SQLiteDatabase.execSQL(String sql)` to exec sql
	- db.insert("database", null, drinkValues)
	- `drinkValues` is `ContentValues`.
	- contentValues.put('columnName', value) - save the data being inserted into contentValues first
	- The user installs the app and launches it. When the app needds to access the database, hte SQLite helper checks to see if the database already exists.
	- If the database doesn't exist, it gets created, by the db name and db version you provided in the constructor of SQLiteOpenHelper.
	- When the database is created, the onCreate() method in the SQLiteOpenHelper is called -- create tables and insert initial data here.

* When you need to change an app's database, there are two key scenarios you have to deal with.
	- One is that is the first time user installs the app on the device. For this case, onCreate() method gets called.
	- The other one is user has already installed the app, and he is trying to installs a new version of your app that includes a different version of the database. SQLiteOpenHelper will call either the onUpgrade() or onDowngrade() method.
	- If the DB_VERSION you specified in the SQLiteOpenHelper constructor is higher than that of the database, it calls `SQLiteOpenHelper onUpgrade()` or else if the DB_VERSION is lower than that of the database, it calls `onDowngrade()` method.

* onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)

* onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)

* Update the table record
	
	```
	ContentValues drinkValues = new ContentValues();
	drinkValues.put("DESCRIPTION", "Tasty");
	db.update("TABLE_NAME", // The table to update
		drinkValues,  // NEW RECORD TO CHANGE
		"NAME = ? OR DESCRIPTION = ?", // WHERE
		new String[] { "Latte", "Our best drip coffee" } // WHERE clause values
		)
	```

* Delete the table record

	```
	db.delete(
		"DRINK",
		"NAME = ?",
		new String[] { "Latte" }
	);
	```
	
* Add new columns to table use SQL
	
	```
	ALTER TABLE DRINK
	ADD COLUMN FAVORITE NUMBERIC
	```

* Rename table
	
	```
	ALTER TABLE DRINK
	RENAME TO FOO
	```

* Delete tables by dropping them

	```
	DROP TABLE DRINK
	```


```
CREATE TABLE DRINK (_id INTEGER PRIMARY KEY AUTOINCREAMENT,
	NAME TEXT,
	DESCRIPTION TEXT,
	IMAGE_RESOURCE_ID INTEGER)
```

```
public class StarbuzzDatabaseHelper extends SQLiteOpenHelper {
	
	private static final String DB_NAME = "x";
	private static final int DB_VERSION = 1;
	
	StarbuzzDatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		updateMyDatabase(db, 0, DB_VERSION);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		updateMyDatabase(db, oldVersion, newVersion);
	}
	
	private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion < 1) {
			// First time install
		}
		if (oldVersion < 2) {
			// This code will only run for user updates the app so 
			// SQLite will call onUpgrade with the oldVersion = 1
		}
	}
}
```

### 16. SQLite Cursor

* SQLiteOpenHelper

```
class StarbuzzDatabaseHelper extends SQLiteOpenHelper {
	StarbuzzDatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}	
}
```

```
StartbuzzDatabaseHelper helper = new StarbuzzDatabaseHelper(this); // this could be Activity
SQLiteDatabase db = helper.getReadableDatabase();
```

* Get a SQLiteDatabase instance

	- getReadableDatabase() // Readonly to the database 
```
helper.getReadableDatabase();
```

	- getWritableDatabase() // use getWritableDatabase if needs to write data into database

* Cursor cursor = db.query(..)

```
Cursor cursor = db.query(
	"tableName",
	new String[] {"_id", "NAME", "DESC"}, // The columns to return in cursor
	null, // WHERE
	null, // WHERE VALUES
	null, 
	null, 
	null // SORT BY 
	// set null for these 5 params
);
```

* Cursor - SORT BY - by default returns records in _id asc order
	- set the last param as "NAME ASC" to sort by NAME ASC
	- the last param can also sort by multiple columns like: `FAVORITE DESC, NAME` -- This will sort by favorite by descending and name ascending
	
* Cursor - WHERE - filtering the records you want to return
	
```
Cursor cursor = db.query(
	"tableName",
	new String[] {"_id", "NAME", "DESC"}, // The columns to return in cursor
	"NAME = ?", // WHERE
	new String[] {"Latte"}, // WHERE values
	null,
	null,
	"NAME ASC"
 );
```
	-
	- The value of condications must be an array of Strings, even if the column contains some other type of data 

```
Cursor cursor = db.query(
	"tableName",
	new String[] {"colA", "colB", "colC"}, // columns to return
	"_id = ?",  // where
	new String[] {Integer.toString(1)}, // where values
	null,
	null,
	"NAME ASC" // Sort by
);
```

* Navigate cursor
	- cursor.moveToFirst
	- cursor.moveToLast
	- cursor.moveToPrevious
	- cursor.moveToNext
	- cursor.getString(0) // Gets the first column value as string
	- cursor.getInt(1) // Gets the 2nd column value as int

* Use CursorAdapter to navigate cursor and map data to listview items

```
SimpleCursorAdapter adapter = new SimpleCursorAdapter(
	Context context, // can be activity
	int layout, // android.R.layout.simple_list_item_1
	Cursor cursor, // Cursor returned by db.query(...)
	String[] fromColumns, // The columns to take from the cursor
	int[] toViews, // Display the value of the column to which view
	int flags // the default value is 0. The alternative is to set it to FLAG_REGISTER_CONTENT_OBSERVER to register a content observer that will be notified when content changes.
);
```

* Close the cursor and database when activity is destroyed.

```
public void onDestroy() {
	super.onDestroy();
	cursor.close();
	db.close();
}
```

### 17. Cursor update and AsyncTask


* SQLiteOpenHelper
	- onUpgrade - change DB_VERSION to 2, then onUpgrade method gets called
	
* db.update
	- SQLiteOpenHelper#getWritableDatabase() -> returns a SQLiteDatabase instance
	- SQLiteDatabase#update()

	```
	int updatedCount = db.update(
		"tableName" // the table to upadte
		contentValues // the ContentValue contains the columns needs to be updated
		"xx = ?" // where
		new String[] { .. } // where valeus
	)
	```

* Filtering the records
	- get a readable database
	- db.query(..)
	
	```
	SQLiteDatabase db = SQLiteOpenHelper.getReadableDatabase();
	db.query(
		"tableName" // The table to query
		new String[] {..} // The columns to return in the cursor
		"xx = ?" // where
		new String[] { .. } // where values
		group by
		having by
		order by
	)
	```

* CursorAdapter#changeCursor(newCursor) // this will update the data CursorAdapter displaying

#### AsyncTask

```
private class MyAsyncTask extends AsyncTask<Param, Progress, Result> {

	protected void onPreExecute() {
		// Gets called before doInBackground starts
		// Be used to set up the task
		// Called on main thread
		// No input params
		// void return type
	}
	
	protected Result doInBackground(Params... params) {
		// Run in background thread
		// Takes input param
		// And return Result type
	}
	
	protected void onProgressUpdate(Progress... values) {
		// Run on main thread
		// Called if publishProgress() is issued in doInBackground()
		// 
	}
	
	protected void onPostExecute(Result result) {
		// Run on main thread
	} 	
}
```


* Execute the AsyncTask

```
new MyAsyncTask().execute(Params..);
```


### 17. Started Service

* Android built-in services include:
	- notification service
	- location service
	- alarm service
	- download service

	
* There are 3 types of services:
	1. Started Service
		- A started service can run in the background indefinitely. (可以一直运行)
		- Even if the activity that started it is destroyed. (即便是启动这个service的Activity已经销毁了，started service也可以继续运行)
		- If you want to download a large file, you would probably use a started service.(大文件下载可以用 started service 来实现)
	2. Bound Service
		- A bound service is bound to another application component such as an activity. (Bound Service是用来绑定到其他APP的组件，比如绑定到另外一个APP的Activity)
		- The activity can interact with the bound service, send requests, and gets results.(绑定这个service的Activity可以向 bound service 发送请求然后得到结果)
		- When the components are no longer bound, the service is destroyed. (当所有绑定这个service 的组件都解除了对他的绑定，那么bound service就会销毁)
		- If you want to create an odometer to measure the distance traveled by a vehicle, you'd probably use a bound service. In this way, any activities bound to the service could keep asking the service for updates on the distance traveled.(如果你想做一个里程表来记录车辆行驶的距离，就可以用 bound service 来实现，任意一个绑定到这个service 的Activity都可以调用bound service的接口来得到已经行驶的距离)
	3. Scheduled Service
		- A scheduled service is one that's scheduled to run at a particular time. From API 21, you can schedule jobs to run at an appropriate time. (Scheduled service可以指定代码在某个具体时间来执行)

		
* Started Service && IntentService
	- 可以 extends IntentService 来实现一个 Started Service
	- override IntentService 里面的 onHandleIntent(Intent intent) 方法
	- 在IntentService内部的 onCreate 方法中，创建并启动了一个 HandlerThread.
	- 并用新创建的 handlerThread.getLooper 作为构造方法的参数创建了一个 ServiceHandler。
	- 在ServiceHandler extends Handler 的内部 Override 了Handler的 handleMessage 方法
	- handleMessage方法中调用了自己的抽象方法 onHandleIntent(Intent) 方法
	- handleMessage方法传入的是 (Intent) message.obj 
	- message是在 Intent#onStart(Intent, int startId) 方法中创建出来的：
	
	```
	public void onStart(Intent intent, int startId) {
		Message message = Message.obtain();
		message.obj = intent;
		message.arg1 = startedId;
		mServiceHandler.sendMessage(message);
	}
	```


* Service
	- Lifecycles
	- onCreate
	- onStartCommand
	- onDestroy

	- Started Service # onStartCommand 方法的返回值
	- `START_STICKY` - System will try to recreate the service. With a null intent sent to onStart (Music playback?)
	- `START_NON_STICKY` - System will not try to recreate the service
	- `START_REDELIVER_INTENT` - System will recreate the service and redeliver the original intent to the Service. This makes sense if there is work to do and confirm if it is completed.



* Notification Service
	- NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE);
	- NotificationCompat.Builder
	- NotificationChannel

	- targetSDK >= Android 8 (26) 之后，要显示通知必须应用NotificationChannel

```
NotificationChannel = new NotificationChannel(
	id, // String 
	name, // String 
	importance, // int NotificationManager.IMPORTANCE_HIGH  --- A head-up notification
);
```

```
// >=26
if (Android.VERSION.SDK_INT >= Android.VERSION_CODES.O) {
	NotificationCompat.Builder = new NotificationCompat.Builder(Context, channelId);
} else {
	NotificationCompat.Builder = new NotificationCompat.Builder(context);
}

buider.setSmallIcon(int)
	.setContentTitle(title)
	.setContentText(content)
	.setPriority(int)
	.setVibrate(new long[]{0, 1000})
	.setAutoCancel()
	.setContentIntent(pendingIntent);
;

Intent intent = new Intent(this, MusicServivce.class);
intent.putExtra("xxx", R.raw.meet);
PendingIntent pendingIntent = PendingIntent.getService(
	context,
	requestCode,
	intent,
	PendingIntent.FALG_UPDATE_CURRENT // FLAG
);
```


### 19. Bound Service

* Bound Service needs to override `onBind`

```
public class OdometerService extends Service {
	
	private final IBinder binder = new OdometerBinder();
	private final Random random = new Random();
	
	@Override
	public IBinder onBind(Intent intent) {
		return this.binder;	
	}
	
	public class OdometerBinder extends Binder {
		OdometerService getOdometer() {
			return OdometerService.this;
		}
	}
	
	public double getDistance() {
		return random.nextDouble();
	}
}
```

* ServiceConnection - a ServiceConnection is an interface that enables your activity to bind to a service.
	- onServiceConnected(ComponentName componentName, IBinder binder) - runs when the service is connected
		- ComponentName identifies the service. It includes the service package and class names
		- IBinder defined by the service. OdometerBInder.
	- onServiceDisconnected() - runs when the service is disconncted

* bindService() to bind the service
	- onStart() - This is appropriate if you only need to interact with the service when it's visible
	- onCreate() - Do this if you need to receive updates from the service even when the activity's stopped.??

```
protected void onStart() {
	super.onStart();
	Intent intent = new Intent(this, OdometerService.class);
	bindService(intent, connection, Context.BIND_AUTO_CREATE);
}
```

* unbindService() to unbind from the service
	- If bindService in onStart, then unbindService() in onStop
	- If bindService in onCreate, then unbindService() in onDestroy


#### Bound Service 重点使用步骤
1. 定义一个class extends Service
2. override里面的 onBind方法 `public IBinder onBind(Intent intent)` 返回一个IBinder对象
3. 定义一个class extends Binder `public class xxBinder extends Binder` 并定义一个方法，返回 Bound Service 实例
4. 在Bound Service类中定义需要的方法 供调用者来访问
5. 在Activity中的 onCreate 或 onStart 方法中，调用 bindService(Intent, ServiceConnection, Context.BIND_AUTO_CREATE) 方法
6. Intent - 是一个explicit intent，指定要绑定的 service 的class
7. ServiceConnection - 是定义的一个匿名类， 

```
private ServiceConnection = new ServiceConnection() {
	public void onServiceConnected(ComponentName componentName, IBinder binder) {
		// 这个方法是一个回调, 当Activity绑定到Service的时候被调用
		// ComponentName 包含了 package name 和 class name, 也就是 Service 对象
		// Binder 就是Service类中的继承于 Binder 的子类
	}
	
	public void onServiceDisconnected(ComponentName componentName) {
		// 这也是回调方法，当Activity和service解除绑定的时候被调用
	}
}
```
8. 调用 Service 中 IBinder 对象的方法来得到service的对象实例，然后调用这个service的方法来得到想要的内容
9. Activity的onStop方法中或者onDestroy中调用 unbindService(connection) 方法


* Bound Service 生命周期
	1. The component calls `bindService()` and the service gets created
	2. onCreate() - service initialization
	3. onBind(Intent) - returns IBinder, from which the caller can get an instanceof the service
	4. onUnbind() - runs when all components have unbound from the service
	5. onDestroy() - called when no component are bound to the service and it's about to be destroyed
	6. After the onDestroy() has run, the service is destroyed

	
* Runtime permission request
	- Permission -- `<users-permission android:name="android.permission.ACCESS_FINE_LOCATION" />`
	- Runtime SDK version >= 23, then request permission at runtime
	- Runtime SDK version <= 22, then request permission when it's installed


* LocationListener
	- onLocationChanged(Location location) // location parameter describes the current location.
	- onProviderDisabled
	- onProviderEnabled
	- onStatusChanged

* LocationManager
	- get access to Android's Location Service
	- specify a location provider
	- requests location provider to send location updates to the location listener
	
	- `getBestProvider(new Criteria(), true)`
	- 
* LocationProvider
	- Send location updates to location listener
	- Two main options:
	- 1. GPS: use GPS sensor
	- 2. Network: use Wi-Fi, Bluetooth, or mobile networks

* request location updates
	
	```
	locationManager.requestLocationUpdates(
		provider, // location provider string
		1000, // minimum time interval between updates in milliseconds
		1, // the minimum distance between updates in meters
		listener // the location listener
	)
	```
	
* Before request location updates, you need to request permission at runtime for SDK version >= 23
	- Use `ContextCompat.checkSelfPermission(Context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED`
	
```
	LocationManager locationManager = (LocationManager) getSystemService (Context.LOCATION_SERVICE);
	if (ContextCompat.checkSelfPermission(this, PERMISSION_STRING) == PackageManager.PERMISSION_GRANTED) {
		String provider = locationManager.getBestProvider(new Criteria(), true);
		if (provider != null) {
			locationManager.requestLocationUpdates(
				provider,
				1000,
				1,
				listener
			);
		}
	} 

```

* LocationListener - calculate the distance traveled

```
	LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location) {
			if (lastLocation == null) {
				lastLocation = location;
			}
			
			distanceInMeters += location.distanceTo(lastLocation);
			lastLocation = location;
		}
	}
```


* Stop the listener getting location updates `LocationManager.removeUpdates(LocationListener)`

```
	if (ContextCompat.checkSelfPermission(this, PERMISSION_STRING)
		== PackageManager.PERMISSION_GRANTED) {
		locationManager.removeUpdates(listener);
	}
```

* requestPermissions V.S. checkSelfPermissions
	- ActivityCompat.requestPermissions(Context, new String[] {权限列表}, requestCode) // 请求权限
	- checkSelfPermissions - 检查权限是否已经被授权
	
	- `requestPermissions()` can only be called from an Activity, cannot request permissions from a service

```
	// 如果没有权限！则去请求一下！
	if (ContextCompat.checkSelfPermissions(this, PERMISSION_STRING) != PackageManager.PERMISSION_GRANTED) {
		ActivityCompat.requestPermissions(
			this,
			new String[] {请求权限列表},
			requestCode
		);
	} else {
		// 如果已经有了权限，则 bindService
		Intent intent = new Intent(this, OdometerService.class);
		bindService(intent, ServiceConnection, Context.BIND_AUTO_CREATE);
	}
```


* Check the user's response to the permission request by overriding `onRequestPermissionsResult()`, there are 3 parameters:
	- int request code
	- String[] permissions
	- int[] grant results


```
public void onRequestPermissionsResult(
	int requestCode,
	String[] requestPermissions,
	int[] grantResults) {
	
	switch(requestCode) {
		case PERMISSION_REQUEST_CODE:
			if (grantResults.length > 0
				&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				// 用户已经授权
				// 现在可以 bindService 了
				Intent intent = new Intent(this, OdometerService.class);
				bindService(intent, ServiceConnection, Context.BIND_AUTO_CREATE);
			} else {
				// 用户拒绝授权
			}
	}	
}
```

### Gradle

* Each time you create a new project, Android Studio creates two files called build.gradle
* One is in the project folder, contains the gradle version and which repository to use
* If you want to install a 3rd party plug-in or specify another place to download libraries, chagne the gradle file in the project folder
* The other one is your app's main gradle file, lives inside the app folder. This file tells Gradle how to build all of the code

### The Android Runtime

* ART means Android Runtime
* Java source code will be compiled into .class then into .dex
* .dex file is executable bytecodes run on Dalvik
* A single dex file can only contain as many as 65535 methods, you need to split the dex into smaller ones with multidex technique
* .dex file are created by a tool called dx - d8 is the latest tool
* DEX files are zipped into APK files, so APK contains everything that make up the app, images, sounds, metadata, .dex and so on.
* Sign the app before submitting to play store, use jarsigner, also need to run the jarsigner through a tool called zipalign.
* Android Debug Bridge (adb) - all communications between the development machine and the android device take place over the ADB
* adb is a tool on the dev machine
* And on Android device, there is a adbd - Android Debug Bridge Daemon
* Run adb means to start a server (port 5037) on the dev machine
* `adb install xx.apk` will transfer the apk file to Android device and install the app into /data/app directory.
* For running the apk file on device, Android needs to turn the apk file into a process running in memory
* When Android tries to run your apk, it will fork a new process from Zygote, then the new process will load the apk into memory and run
* Before running the apk, Android extracts the classes.dex from apk and place it into a separated directory. 
* Android will not use the classes.dex file directly, instead Android will conver the dex file into native machine code. The tool convert dex into native machine code (odex) is dex2oat.
* The converted file is stored into a directory named something like: `/data/dalvik-cache/data@app@com.xx.yy@base.apk@classes.dex`
* The oat file then can be loaded as a native library by the application process. Then the app appears on the screen.

### Android Debug Bridge

* adb talks to adbd (server:5037) for each command
* adb shell -- open a shell -- if there are multiple devices, use `adb -s <DEVICE> shelll`
* pm - package management tool
	- `pm list packages`
* ps - process status
	- ps 
* dexdump - display details of an APK
	- dexdump -d /data/app/xx/base.apk
* lsof - list a process open files and other connections
	- lsof -p 1234 -- show what process with id 1234 is doing
* screencap
	- screencap -p /sdcard/xx.png - run in shell
	- adb pull /sdcard/xx.png - run from dev machine
* top
	- top -m 5

----

* Each of these commands works from an interactive shell prompt, like: `adb shell pm list packages`

* Kill adb server - ` adb kill-server`
* Get the output from logcat - `adb logcat`
* adb pull <PATH ON PHONE> <PATH_ON_DEV_MACHINE> - `adb pull /default.prop 1.txt`

### Android emulator and iPhone simulator

* iPhone simulator runs the code which have been compiled on Mac, it simulates a device running the iOS operating system
* Android emulator is based on QEMU (Quick Emulator) to emulate the entire Android hardware device. 他解释执行了那些本应该在Android设备上执行的代码。 他还模拟了所有的硬件设备，比如存储系统、显示系统等等。


### Leftovers
 
2. Content Provider
	- Use intent to start activities in other apps
	- Use Content Provider to contact with other apps data
	- 
3. Loaders - helps you load data
	- runs on separated threads in the background
	- makes it easier to manage threads by providing callback methods
4. Sync adapters - synchronize data between an Android device and a web server
5. Broadcasts, kinds of system broadcasts includes:
	- headphones removed or inserted
	- low on power
	- incoming call
	- system booted
6. Webview
	- `webview.loadUrl()`
7. Settings
	- Use the Preferences API
	- Allow you to add individual preference and record a value for each preference
8. Animation
	- Property animation
	- View animations
	- Activity transitions
9. App widgets
10. Automated testing
	- Unit tests - run out of the app
	- Espresso - a separated package installed to interact with your app in the same way as a user


	










