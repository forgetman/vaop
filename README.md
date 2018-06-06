# aspect [![](https://jitpack.io/v/forgetman/aspect.svg)](https://jitpack.io/#forgetman/aspect)
an aop project

Update
------
+ v0.1.0:    permission request

Dependency
------
I use [aspectjx](https://github.com/HujiangTechnology/gradle_plugin_android_aspectjx) instead of [aspectj](https://github.com/eclipse/org.aspectj), because it is more easier to use and support Kotlin

Warning
-------
You must compile with java8, set to your `mudule.gradle`
```groovy
compileOptions {
    sourceCompatibility JavaVersion.VERSION_1_8
    targetCompatibility JavaVersion.VERSION_1_8
}
```

and it is based on aspectj-v1.9.1

Download
--------

```groovy
dependencies {
  implementation 'com.github.forgetman:aspect:0.1.0'
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

Advise
----
here is a suggestion of using aspect in your project: new a `aspect.gradle` file
```groovy
ext.paths += [
        aspect: 'com.hujiang.aspectjx:gradle-android-plugin-aspectjx:2.0.1', // https://github.com/HujiangTechnology/gradle_plugin_android_aspectjx
]
ext.plugs += [
        aspect: 'android-aspectjx',
]
ext.deps += [
        aspect: 'com.github.forgetman:aspect:0.1.0'
]
```

and then apply it to your `build.gradle`
```groovy
buildscript {
    apply from: 'aspect.gradle'
    dependencies {
        // ...
    }
}
```
you can use it like this
```groovy
apply plugin: plugs.aspect
dependencies {
  implementation deps.aspect
}
```

