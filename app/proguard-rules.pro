# 混合时不使用大小写混合，混合后的类名为小写
-dontusemixedcaseclassnames

# 指定不去忽略非公共库的类
-dontskipnonpubliclibraryclasses
# 这句话能够使我们的项目混淆后产生映射文件
# 包含有类名->混淆后类名的映射关系
-verbose
# 指定不去忽略非公共库的类成员
-dontskipnonpubliclibraryclassmembers
# 不做预校验，preverify是proguard的四个步骤之一，Android不需要preverify，去掉这一步能够加快混淆速度。
-dontpreverify
# 保留Annotation不混淆
-keepattributes *Annotation*,InnerClasses
# 避免混淆泛型
-keepattributes Signature
# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable
# 指定混淆是采用的算法，后面的参数是一个过滤器
# 这个过滤器是谷歌推荐的算法，一般不做更改
-optimizations !code/simplification/cast,!field/*,!class/merging/*

#############################################
#
# Android开发中一些需要保留的公共部分
#
#############################################

# 保留我们使用的四大组件，自定义的Application等等这些类不被混淆
# 因为这些子类都有可能被外部调用
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Appliction
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService


# 保留support下的所有类及其内部类
-keep class android.support.** {*;}

# 保留继承的
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**

# 保留R下面的资源
-keep class **.R$* {*;}

# 保留本地native方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

# 保留在Activity中的方法参数是view的方法，
# 这样以来我们在layout中写的onClick就不会被影响
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}

# 保留枚举类不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# 保留我们自定义控件（继承自View）不被混淆
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# 保留Parcelable序列化类不被混淆
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# 保留Serializable序列化的类不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# 对于带有回调函数的onXXEvent、**On*Listener的，不能被混淆
-keepclassmembers class * {
    void *(**On*Event);
    void *(**On*Listener);
}

# webView处理，项目中没有使用到webView忽略即可
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
    public *;
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.webView, jav.lang.String);
}



#========================================第三方库================================


# bugly+update
-dontwarn com.tencent.**
-keep  class com.tencent.**{*;}
-keep class android.support.**{*;}


# matisse
-dontwarn com.squareup.picasso.**
-dontwarn com.bumptech.glide.**
-keep  class com.zhihu.matisse.**{*;}
-dontwarn com.zhihu.matisse.**

# bugly
-keep  class okhttp3.**{*;}
-dontwarn okhttp3.**


# 极光推送
-dontoptimize
-dontpreverify
-dontwarn cn.jpush.**
-dontwarn cn.jiguang.**
-keep class cn.jpush.** { *; }
-keep class cn.jiguang.** { *; }
-keep class * extends cn.jpush.android.helpers.JPushMessageReceiver { *; }
-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }

# loadsir
-dontwarn com.kingja.loadsir.**
-keep class com.kingja.loadsir.** {*;}


-dontwarn javax.annotation.**
-dontwarn javax.inject.**
# OkHttp3
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}
-keep class okio.**{*;}
-dontwarn okio.**
# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
# RxJava RxAndroid

-dontwarn sun.misc.**
-dontwarn io.reactivex.**
-keep class io.reactivex.** { *; }

-dontwarn org.reactivestreams.**
-keep class org.reactivestreams.** { *; }

-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# Gson
-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**
-keepattributes EnclosingMethod
# 实体
-keep class com.kingja.yaluji.model.entiy.**{*;}

 # dagger
-dontwarn dagger.**
-keep class dagger.** { *; }
-dontwarn com.squareup.javapoet.**
-dontwarn com.google.common.**

# flowlayout
-dontwarn com.zhy.view.**
-keep class com.zhy.view.** { *; }
# 自定义View
-dontwarn com.kingja.yaluji.view.**
-keep class com.kingja.yaluji.view.** { *; }
# popwindowsir
-dontwarn com.kingja.popwindowsir.**
-keep class com.kingja.popwindowsir.** { *; }
# lifecycle
-dontwarn android.arch.lifecycle.**
-keep class android.arch.lifecycle.** { *; }
# materialdialogs
-dontwarn com.afollestad.materialdialogs.**
-keep class com.afollestad.materialdialogs.** { *; }
# timessquare
-dontwarn com.squareup.timessquare.**
-keep class com.squareup.timessquare.** { *; }
# it.sephiroth.android
-dontwarn it.sephiroth.android.**
-keep class it.sephiroth.android.** { *; }
# RangeSeekBar
-dontwarn com.jaygoo.widget.**
-keep class com.jaygoo.widget.** { *; }
# javax.inject
-dontwarn javax.inject.**
-keep class javax.inject.** { *; }
# android.app
-dontwarn android.app.**
-keep class android.app.** { *; }
# android.app
-dontwarn com.bumptech.**
-keep class com.bumptech.** { *; }
# android.app
-dontwarn com.bumptech.**
-keep class com.bumptech.** { *; }

# luban
-dontwarn top.zibin.luban.**
-keep class top.zibin.luban.** { *; }
# alipay
-dontwarn android.net.**
-keep class android.net.SSLCertificateSocketFactory{*;}

# alipay
-dontwarn com.flipboard:bottomsheet.**
-keep class com.flipboard:bottomsheet.** { *; }
# 注解
-keep class * extends java.lang.annotation.Annotation { *; }
-keep interface * extends java.lang.annotation.Annotation { *; }

-keep interface com.kingja.yaluji.adapter.DontObfuscateInterface{public *;}
-keep interface * extends com.kingja.yaluji.adapter.DontObfuscateInterface {
<methods>;
<fields>;
}
# eventbus3.0
-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
# enent
-keep class com.kingja.yaluji.event.**{*;}
