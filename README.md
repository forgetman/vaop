# aspect [![](https://jitpack.io/v/forgetman/aspect.svg)](https://jitpack.io/#forgetman/aspect)
an aop project

Dependency
------
I use [aspectjx](https://github.com/HujiangTechnology/gradle_plugin_android_aspectjx) instead of [aspectj](https://github.com/eclipse/org.aspectj), because it is more easier to use and support Kotlin

Warning
-------
You must use java8 in your `mudule.gradle`
```groovy
compileOptions {
    sourceCompatibility JavaVersion.VERSION_1_8
    targetCompatibility JavaVersion.VERSION_1_8
}
```

Download
--------

```groovy
dependencies {
  implementation 'com.github.forgetman:aspect:0.0.4'
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

here is a suggestion of using aspect in your project: new a `aspect.gradle` file
```groovy
ext.versions += [
        aspectj: '1.9.1',
]

ext.paths += [
        aspect: 'com.hujiang.aspectjx:gradle-android-plugin-aspectjx:2.0.1', // https://github.com/HujiangTechnology/gradle_plugin_android_aspectjx
]

ext.plugs += [
        aspect: 'android-aspectjx',
]
```

if you are using proguard, add this to your `proguard`:

```proguard
-dontwarn aspect.**
-keep class aspect.** {*;}
```
