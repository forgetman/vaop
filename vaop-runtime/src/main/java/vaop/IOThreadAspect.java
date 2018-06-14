package vaop;

import android.os.Looper;
import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.concurrent.Callable;

import vaop.annotation.IOThread;
import vaop.annotation.util.ThreadType;
import vaop.util.AppExecutors;

/**
 * @author yuansui
 * @since 2018/5/11 0011
 */
@Aspect
public class IOThreadAspect extends BaseAspect {

    @Pointcut("within(@aspect.annotation.IOThread *)")
    public void withinAnnotatedClass() {
    }

    @Pointcut("execution(!synthetic * *(..)) && withinAnnotatedClass()")
    public void methodInsideAnnotatedType() {
    }

    @Pointcut("execution(@aspect.annotation.IOThread * *(..)) || methodInsideAnnotatedType()")
    public void method() {
    }  //方法切入点

    @SuppressWarnings("unchecked")
    @Around("method() && @annotation(ioThread)")//在连接点进行方法替换
    public Object aroundJoinPoint(final ProceedingJoinPoint joinPoint, IOThread ioThread) throws Throwable {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            return joinPoint.proceed();
        } else {
            Object result = null;
            switch (ioThread.value()) {
                case ThreadType.SINGLE:
                case ThreadType.DISK: {
                    result = AppExecutors.get().singleIO().submit((Callable) () -> getProceedResult(joinPoint)).get();
                }
                break;
                case ThreadType.FIXED:
                case ThreadType.NETWORK: {
                    result = AppExecutors.get().poolIO().submit((Callable) () -> getProceedResult(joinPoint)).get();
                }
                break;
            }
            return result;
        }
    }

    /**
     * 获取执行结果
     *
     * @param joinPoint
     */
    private Object getProceedResult(ProceedingJoinPoint joinPoint) {
        try {
            Log.d("www", "thread id = " + Thread.currentThread().getId());
            Log.d("www", "thread id = " + Thread.currentThread().getName());
            return joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }
}
