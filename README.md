# aspect [![](https://jitpack.io/v/forgetman/aspect.svg)](https://jitpack.io/#forgetman/aspect)
an aop project

Third part
------
I use [aspectjx](https://github.com/HujiangTechnology/gradle_plugin_android_aspectjx) instead of [aspectj](https://github.com/eclipse/org.aspectj), because it is more easier to use and support Kotlin

Download
--------

```groovy
dependencies {
  implementation 'com.github.forgetman:aspect:0.0.2'
}
```
add it in your root `build.gradle` at the end of repositories:

```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

add the plugin to your `buildscript`:

```groovy
buildscript {
  dependencies {
    classpath 'com.hujiang.aspectjx:gradle-android-plugin-aspectjx:2.0.1'
  }
}
```



and then apply it in your module:

```groovy
apply plugin: 'android-aspectjx'
```

if you are using proguard, add this to your `proguard`:

```proguard
-dontwarn aspect.**
-keep class aspect.** {*;}
```
