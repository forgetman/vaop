package vaop;

import android.text.TextUtils;
import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import vaop.annotation.Duration;
import vaop.util.Interval;

/**
 * 根据方法, 打印消耗的时间
 *
 * @author yuansui
 * @since 2018/5/11
 */
@Aspect
public class DurationAspect extends BaseAspect {

    @Pointcut(START + "Duration" + END_METHOD)
    public void methodCut() {
    }

    @Pointcut(START + "Duration" + END_CONSTRUCTOR)
    public void constructorCut() {
    }

    @Around("methodCut() || constructorCut()")
    public Object durationMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        Duration duration = methodSignature.getMethod().getAnnotation(Duration.class);
        if (duration != null && !duration.enable()) {
            return joinPoint.proceed();
        }

        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        final Interval interval = new Interval();
        interval.start();
        Object result = joinPoint.proceed();
        interval.stop();

        if (TextUtils.isEmpty(className)) {
            className = "Anonymous class";
        }
        Log.d(className, methodName + "() 持续 " + interval.getElapsedTime() + " 毫秒");

        return result;
    }
}
