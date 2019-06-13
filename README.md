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
 
  #### WISERActivity和WISERBiz
   ** 所有自己App项目中的Activity 继承 WISERActivity 可传递泛型Biz类也可以传递IBiz接口类，是处理业务逻辑类，例如：  
     public class MyActivity expand WISERActivity<MyBiz>{}  
     或者  
     public class MyActivity expand WISERActivity<IMyBiz>{}   
     MyBiz是你的业务类实现类,IMyBiz是你的业务接口类，所有的业务实现类都要继承WISERBiz，所有的业务接口类需要继承IWISERBiz。   
   * 业务实现类需要传递Activity，例如：
     public class MyBiz expand WISERBiz<MyActivity>{}  
     如此MyActivity和MyBiz或者IMyBiz就处于绑定关系，可互相调用对方的方法属性。
   * 继承WISERActivity之后会实现两个方法 builder方法和initData方法，builder方法处理默认界面UI绘制根据需求传递属性等等，initData处理加载数据
  #### WISERFragment和WISERBiz
   * Fragment继承WISERFragment，同样业务类继承WISERBiz，同WISERActivity
  #### WISERService和WISERBiz
   * Service继承WISERService，同样业务类继承WISERBiz，同WISERActivity
  #### WISERBuilder 视图构造者
   * WISERActivity和WISERFragment界面显示效果同时有WISERBuilder构造
## 未完待续
