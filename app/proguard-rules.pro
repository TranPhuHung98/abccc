# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\Android\Android-setup\android-sdk\android-sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the ProGuard
# include property in project.properties.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}

# Application classes that will be serialized/deserialized over Gson, keepclassmembers
-keepnames class com.stdiohue.basestructure.model.** { *; }

#Butterknife

#-dontwarn butterknife.internal.**
#-keep class **$$ViewInjector { *; }
#-keepnames class * { @butterknife.InjectView *;}

#ButterKnife end

#okhttp
-dontwarn com.squareup.okhttp.*
-dontwarn com.squareup.okhttp.internal.huc.**
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
#okhttp end

#Retrofit

#-dontwarn retrofit.appengine.UrlFetchClient
#-dontwarn retrofit.**
#-keep class retrofit.** { *; }
#-keepattributes Exceptions
#
#-keepclasseswithmembers class * {
#    @retrofit.http.* <methods>;
#}
#-dontwarn rx.**
#Retrofit 2 End

#GSON
-keep class com.google.gson.** { *; }
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
#GSON END

#GreenDao
#-keepclassmembers class * extends de.greenrobot.dao.AbstractDao {
#    public static java.lang.String TABLENAME;
#}
#-keep class **$Properties
#GreenDao end

#EventBus
#-keepclassmembers class ** {
#    public void onEvent*(**);
#}
#Eventbus end

#MPChart
#-keep class com.github.mikephil.charting.** { *; }
#-dontwarn io.realm.**
#MPChart end

#Splunk Mint
-keep class com.splunk.** { *; }
#Splunk end

#Distimo
-dontwarn com.distimo.sdk.**
#Distimo end

#Okio
-dontwarn okio.**
#Okio end

#Adobe Analytics
-dontwarn com.adobe.mobile.**
#Adobe end

#Others
-dontwarn com.google.common.collect.**
-dontwarn com.google.common.cache.**
-dontwarn com.google.common.primitives.UnsignedBytes$**
-dontwarn javax.annotation.**
#others end


# LeakCanary
#-keep class org.eclipse.mat.** { *; }
#-keep class com.squareup.leakcanary.** { *; }

