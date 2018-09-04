# Frame
一个轻量级Android MVP框架
## WiserFrame集成
#### 1、Application中onCreate方法中初始化框架：WISERHelper.newBind().setWiserBind(new MyBind()).Inject(this, BuildConfig.DEBUG); setWiserBind()方法扩展自己的Bind类 如果不需要可直接初始化WiserFrame架构的Bind 去掉该方法就可以了。
#### 2、需要在自己的app module的build.gradle中注入butterknife依赖库：implementation 'com.jakewharton:butterknife:8.8.1'   annotationProcessor 'com.jakewharton:butterknife-compiler:8.8.1'
    
#### 3、allprojects { repositories { ... maven { url 'https://jitpack.io' } } } dependencies { implementation 'com.github.Wiser-Wong:Frame:1.1.02' }

## WiserFrame使用说明
#### 1.所有自己项目中的Activity 继承 WISERActivity 可传递泛型Biz类 是处理业务逻辑类例如 public class MyActivity expand WISERActivity<MyBiz>{} MyBiz是你的业务类  所有的业务类都要继承WISERBiz 可传递Activity绑定界面 例如 public class MyBiz expand WISERBiz<MyActivity>{} 如此MyActivity和MyBiz就处于绑定关系 可互相调用对方的方法属性。
#### 2.继承WISERActivity之后会实现两个方法 builder方法和initData方法，builder方法处理默认界面UI绘制根据需求传递属性等等，initData处理加载数据
## 未完待续
