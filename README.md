# Frame
一个高级Android MVP框架
## 集成
   * Application中onCreate方法中初始化框架：
     WISERHelper.newBind().setWiserBind(new MyBind()).Inject(this, BuildConfig.DEBUG);  
     setWiserBind()方法扩展自己的Bind类 如果不需要可直接初始化WiserFrame架构的Bind,然后去掉该方法就可以了。
   * 需要在自己的app module的build.gradle中注入butterknife依赖库：
     implementation 'com.jakewharton:butterknife:8.8.1'  
     annotationProcessor 'com.jakewharton:butterknife-compiler:8.8.1'
   * 项目下的build.gradle配置
     allprojects { repositories { ... maven { url 'https://jitpack.io' } } }
   * app目录下build.gradle配置
     dependencies { implementation 'com.github.Wiser-Wong:Frame:1.6.1' }
## 使用说明
 
 ### 界面
  * WISERActivity和WISERBiz
    * 所有自己App项目中的Activity 继承 WISERActivity 可传递泛型Biz类也可以传递IBiz接口类，是处理业务逻辑类，例如：  
      public class MyActivity expand WISERActivity<MyBiz>{}  
      或者  
      public class MyActivity expand WISERActivity<IMyBiz>{}   
      MyBiz是你的业务类实现类,IMyBiz是你的业务接口类，所有的业务实现类都要继承WISERBiz，所有的业务接口类需要继承IWISERBiz。   
    * 业务实现类需要传递Activity，例如：
      public class MyBiz expand WISERBiz<MyActivity>{}  
      如此MyActivity和MyBiz或者IMyBiz就处于绑定关系，可互相调用对方的方法属性。
    * 继承WISERActivity之后会实现两个方法 builder方法和initData方法，builder方法处理默认界面UI绘制根据需求传递属性等等，initData处理加载数据
  * WISERFragment和WISERBiz
    * Fragment继承WISERFragment，同样业务类继承WISERBiz，同WISERActivity
  * WISERBuilder 视图构造者
    * WISERActivity和WISERFragment界面显示效果同时有WISERBuilder构造
    * WISERBuilder属性：
      * builder.layoutId(R.layout.id);构造界面布局
      * builder.layoutView(view);构造界面布局
      * builder.layoutEmptyId(R.layout.empty);构造空布局
      * builder.layoutErrorId(R.layout.error);构造错误布局
      * builder.layoutLoadingId(R.layout.loading);构造加载布局
      * builder.layoutBarId(R.layout.title);标题布局
      * builder.titleViewId(@IdRes int titleViewId, String titleName);标题布局titleid以及显示内容
      * builder.backViewId(@IdRes int backViewId);标题布局返回id
      * builder.titleBarViewId(@IdRes int titleViewId, String titleName, @IdRes int backViewId);标题布局titleid，标题内容以及返回id
      * builder.swipeBack(true);滑动退出Activity
      * builder.tintIs(true);状态栏是否可修改
      * builder.tintColor(color);状态栏颜色
      * builder.tintFitsSystem(true);填充系统状态栏
      * builder.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);设置屏幕方向
      * builder.isRootLayoutRefresh(true,false);第一个参数true显示下拉刷新，第二个参数是否默认刷新
      * builder.setColorSchemeColors(Color.BLUE, Color.RED, Color.GREEN);刷新组件颜色值
      * builder.recycleView().recycleViewId(R.id.recycleViewId);设置RecycleView控件
      * builder.recycleView().recycleViewLinearManager(LinearLayoutManager.VERTICAL, null);//还有很多组方法可自行查阅
      * builder.recycleView().recycleViewGridManager(2,LinearLayoutManager.VERTICAL,null);//还有很多组方法可自行查阅
      * builder.recycleView().recycleViewStaggeredGridManager(2,LinearLayoutManager.VERTICAL, new WISERStaggeredDivider(20, 0, 20, 0), null);瀑布流设置
      * builder.recycleView().recycleAdapter(new MWISERAdapter(this));设置适配器
      * builder.recycleView().isFooter(true);设置是否显示上拉加载
      * builder.recycleView().footerLayoutId(R.layout.footer);设置自定义footer布局
      * builder.recycleView().setOnFooterCustomListener(this);设置footer监听状态
  * WISERAdapter<Object,WISERHolder> 适配器
    * 填充数据：WISERActivity或WISERFragment 通过setItems(List);
  * WISERHolder<Object>
 ### WISERService
  * WISERService和WISERBiz
    * Service继承WISERService，同样业务类继承WISERBiz，同WISERActivity
 ### WISERManage 管理类（可扩展）
  * WISERLogManage 日志管理
  * WISERToastManage Toast管理
  * WISERHandlerExecutor Handler管理
  * WISERActivityManage Activity管理
  * WISERActivityManage 业务类管理
  * WISERDisplay 功能管理
  * WISERHttpManage HTTP管理
  * WISERThreadPoolManage 线程池管理
  * WISERPermissionManage 权限管理
  * WISERFileCacheManage 文件管理
  * WISERJobServiceManage jobService管理
  * WISERDownUploadManage 下载上传管理
  * WISERUIManage UI管理
 ### WISERHelper 帮助类（可扩展）
  * 管理WISERManage中管理类实例
  * 初始化Frame
  * 功能方法等等
 ### 网络请求 OKHTTP3 Retrofit2 RxJava2
  * @POST("/") Call<String> getData(@Query("key") String value);
  * @POST("/") Observable<String> getData(@Query("key") String value);
  * WISERBiz中调用
    * Observable 
	
          httpObservableIO(http(IHttp.class).getData(value).subscribe(httpDisposableObserver(new          WISERRxJavaDisposableObserver<String>() {
					@Override protected void onSuccess(String s) {
						
					}

					@Override protected void onFail(Throwable e) {
						
					}

					@Override protected boolean isDfException(boolean isDfException) {
						return true;//是否默认提示异常信息 可不添加
					}
				}));
    * Call
    
         	Call<String> call = http(IHttp.class).getData();
		  String s = httpBody(call);
                                                                                           
## 未完待续
