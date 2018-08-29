# Frame
一个轻量级Android MVP框架
1、Application中onCreate方法中初始化框架：WISERHelper.newBind().setWiserBind(new AtBind()).Inject(this, BuildConfig.DEBUG);
2、在build.gradle中注入butterknife依赖库：
    // 控件注册
    implementation 'com.jakewharton:butterknife:8.8.1'
    annotationProcessor 'com.jakewharton:butterknife-compiler:8.8.1'
3、allprojects { repositories { ... maven { url 'https://jitpack.io' } } }
dependencies { implementation 'com.github.Wiser-Wong:ShadowLayout:1.0.0' }
