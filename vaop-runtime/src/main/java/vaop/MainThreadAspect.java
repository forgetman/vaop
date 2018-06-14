package vaop;

import android.os.Looper;
import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import vaop.util.AppExecutors;

/**
 * 采用RxJava异步处理
 *
 * @author yuansui
 * @since 2018/5/11 0011
 */
@Aspect
public class MainThreadAspect extends BaseAspect {

    @Pointcut("within(@aspect.annotation.MainThread *)")
    public void withinAnnotatedClass() {
    }

    @Pointcut("execution(!synthetic * *(..)) && withinAnnotatedClass()")
    public void methodInsideAnnotatedType() {
    }

    @Pointcut("execution(@aspect.annotation.MainThread * *(..)) || methodInsideAnnotatedType()")
    public void method() {
    }

    @Around("method()")
    public void around(final ProceedingJoinPoint joinPoint) throws Throwable {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            joinPoint.proceed();
        } else {
            Log.d("www", "thread id = " + Thread.currentThread().getId());
            Log.d("www", "thread id = " + Thread.currentThread().getName());
            AppExecutors.get().mainThread().execute(() -> {
                try {
                    joinPoint.proceed();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
