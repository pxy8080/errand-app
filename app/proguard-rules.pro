-keep class **_FragmentFinder { *; }
-keep class androidx.fragment.app.* { *; }


-printmapping mapping.txt
#生成的mapping.txt在app/build/outputs/mapping/release路径下，移动到/app路径下
#修复后的项目使用，保证混淆结果一致
#-applymapping mapping.txt
#hotfix
-keep class com.taobao.sophix.**{*;}
-keep class com.ta.utdid2.device.**{*;}
-dontwarn com.alibaba.sdk.android.utils.**
#防止inline
-dontoptimize

-keepclassmembers class com.erradns.Sophix.MainApplication {
    public <init>();
}
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**

# 如果不使用android.support.annotation.Keep则需加上此行
# -keep class com.my.pkg.SophixStubApplication$RealApplicationStub

