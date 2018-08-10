# Frame
一个轻量级Android MVP框架
1、Application中onCreate方法中初始化框架：WISERHelper.newBind().Inject(this, BuildConfig.DEBUG);
2、在build.gradle中注入butterknife依赖库：
    // 控件注册
    implementation 'com.jakewharton:butterknife:8.8.1'
    annotationProcessor 'com.jakewharton:butterknife-compiler:8.8.1'