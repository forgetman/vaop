package aspect;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Locale;

import aspect.annotation.Duration;
import aspect.util.StopWatch;

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
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = joinPoint.proceed();
        stopWatch.stop();

        if (className == null || className.isEmpty()) {
            className = "Anonymous class";
        }

        Log.d(className, buildLogMessage(methodName, stopWatch.getElapsedTime()));

        return result;
    }

    private static String buildLogMessage(String methodName, long duration) {
        // FIXME: 把单位好好转换一下
        return String.format(Locale.CHINESE, "%s() 持续 %d 毫秒", methodName, duration);
    }
}
