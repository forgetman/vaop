package vaop;

import android.os.Looper;

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

    @Pointcut("within(@vaop.annotation.MainThread *)")
    public void withinAnnotatedClass() {
    }

    @Pointcut("execution(!synthetic * *(..)) && withinAnnotatedClass()")
    public void methodInsideAnnotatedType() {
    }

    @Pointcut("execution(@vaop.annotation.MainThread * *(..)) || methodInsideAnnotatedType()")
    public void method() {
    }

    @Around("method()")
    public void around(final ProceedingJoinPoint joinPoint) throws Throwable {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            joinPoint.proceed();
        } else {
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
