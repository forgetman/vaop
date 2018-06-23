# VAOP [![](https://jitpack.io/v/forgetman/aspect.svg)](https://jitpack.io/#forgetman/aspect)
an aop project with some common usage

Update
------
+ v1.0.1: fix a crash bug on SDK8.0 when request permissions
+ v1.0.0: add 'DebugLog', 'SingleClick'
+ v0.1.5: abandon rxandroid, use thread pool
+ v0.1.2: adjust the permission request rule, message support resource id
+ v0.1.1: add 'Permission'

Dependency
------
use [aspectjx](https://github.com/HujiangTechnology/gradle_plugin_android_aspectjx) instead of [aspectj](https://github.com/eclipse/org.aspectj), because it is more easier to use and support Kotlin

Warning
-------
you must compile with java8, set to your `mudule.gradle`
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
  implementation 'com.github.forgetman.aspect:vaop-runtime:$version'
}
```
replace `$version` to the specified version number

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
-dontwarn vaop.**
-keep class vaop.** {*;}
```

How to use
----------
there are several annotations define, use like this
```java
public class Test {
    public Test() {
        func();
    }
    
    @Duration
    private void func() {
        // do something
    }
}
```

it also support Kotlin, use like this
```kotlin
class Test {
    init {
        func()
    }
    
    @Duration
    fun func() {
        // do something
    }
}
```

+ Duration: log the duration of method when it be invoked
+ MainThread: invoke method in main thread
+ IOThread: invoke method in IO thread
+ Safe: invoke method with try catch inside
+ Permission: request the permission before method is invoked
+ SingleClick: prevent a multi click on the view during a short time
+ DebugLog: record the arguments and returned value of method

Advise
----
here is a suggestion of using aspect in your project: new a `aop.gradle` file
```groovy
ext.versions += [
        aspectj: '1.9.1'
]
ext.paths += [
        aop: 'com.hujiang.aspectjx:gradle-android-plugin-aspectjx:2.0.1' // https://github.com/HujiangTechnology/gradle_plugin_android_aspectjx
]
ext.plugs += [
        aop: 'android-aspectjx'
]
ext.deps += [
        aop: 'com.github.forgetman.aspect:vaop-runtime:$version'
]
```

and then apply it to your `build.gradle`
```groovy
buildscript {
    apply from: 'aop.gradle'
    dependencies {
        // ...
        classpath paths.aop
    }
}
```
you can use it like this
```groovy
apply plugin: plugs.aop
dependencies {
  implementation deps.aop
}
```

