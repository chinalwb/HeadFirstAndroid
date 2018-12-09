## HeadFirst

### About Jetpack

* Use support libraries: [Reference](https://developer.android.com/jetpack/androidx/migrate)

```
    implementation 'androidx.appcompat:appcompat:1.0.0'
    implementation 'androidx.constraintlayout:constraintlayout:1.1.2'
```


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
	6. onStop
	7. onSaveInstanceState
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
onCreate(2)|onCreateBundle(Bundle) // similar to Activity's onCreate, can be used to do the iniital setup of the fragment
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


 
	
