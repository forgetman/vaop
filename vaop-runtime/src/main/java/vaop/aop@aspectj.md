ASPECTJ全面解析：https://blog.csdn.net/woshimalingyi/article/details/73252013  
@annotation用法：https://blog.csdn.net/gongzi2311/article/details/50781451

####原理
编译期对源码进行修改，生成内部类和拦截调用  
java代码：
```java
@Permission(value = permission.CAMERA, message = "打开相机权限")
public void testCameraPermission() {
    Log.d(TAG, "testCameraPermission");
}
```
反编译代码：
```java
@Permission(message = "打开相机权限", value = {"android.permission.CAMERA"})
public void testCameraPermission() {
    JoinPoint makeJP = Factory.makeJP(ajc$tjp_4, this, this);
    PermissionAspect aspectOf = PermissionAspect.aspectOf();
    ProceedingJoinPoint linkClosureAndJoinPoint = new AjcClosure9(new Object[]{this, makeJP}).linkClosureAndJoinPoint(69648);
    Annotation annotation = ajc$anno$2;
    if (annotation == null) {
        annotation = MainActivity.class.getDeclaredMethod("testCameraPermission", new Class[0]).getAnnotation(Permission.class);
        ajc$anno$2 = annotation;
    }
    aspectOf.interceptPermissionRequest(linkClosureAndJoinPoint, (Permission) annotation);
}
```

自定义注解中不传递信息时可以考虑使用编译时注解

####使用方法
> * project.build.gradle 中加入`classpath 'com.hujiang.aspectjx:gradle-android-plugin-aspectjx:2.0.1'`
> * app.build.gradle 中加入`apply plugin: 'android-aspectjx'`
> * 编写拦截类，在拦截逻辑类上标注[@Aspect]()
> * 拦截方法中使用[@Pointcut]()、[@Aspect]()、[@Before]()、[@After]()等注解标记
> * 标记注解传参可以用[call]()、[execution]()、[within]()等多个条件进行组合
> * 使用[@annotation(value)]()获取自定义注解的信息，[value]() 的命名一定要跟方法的入参同名

示例：
```java
@Pointcut("execution(@vaop.annotation.Permission * *(..)) && @annotation(value)")
public void methodCut(Permission value) {}  
  
@Around("methodCut(value)")
public void interceptPermissionRequest(final ProceedingJoinPoint joinPoint, final Permission value){}
```
[methodCut]()使用与否不影响生成代码，即直接使用  
`@Around("execution(@vaop.annotation.Permission * *(..)) && @annotation(value)")`也是可以的

拦截类只能使用java进行编写，用kotlin会报错……


######切面匹配模式 
call(<注解？> <修饰符?> <返回值类型> <类型声明?>.<方法名>(参数列表) <异常列表>？)  
```java
//表示匹配 com.davidkuper.MainActivity类中所有被@Describe注解的public void方法。  
@Pointcut("call(@Describe public void com.davidkuper.MainActivity.init(Context))")
```

#####关键调用
被拦截的调用被表现为一个joinPoint,根据不同拦截条件，joinPoint的用法也不同，这个库里面主要是针对方法内部做了拦截`execution`  
调用`joinPoint.proceed()`可以执行被拦截方法中的内容，依靠这一点实现了类似反射的免除回调