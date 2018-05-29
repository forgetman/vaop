package aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Locale;

import aspect.annotation.Trace;
import aspect.util.StopWatch;
import logger.L;

/**
 * 根据方法, 打印消耗的时间
 *
 * @author yuansui
 * @since 2018/5/11
 */
@Aspect
public class TraceAspect extends BaseAspect {

    @Pointcut(START + "Trace" + END_METHOD)
    public void methodCut() {
    }

    @Pointcut(START + "Trace" + END_CONSTRUCTOR)
    public void constructorCut() {
    }

    @Around("methodCut() || constructorCut()")
    public Object traceMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        Trace trace = methodSignature.getMethod().getAnnotation(Trace.class);
        if (trace != null && !trace.enable()) {
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

        L.d(className, buildLogMessage(methodName, stopWatch.getElapsedTime()));

        return result;
    }

    private static String buildLogMessage(String methodName, long duration) {
        // FIXME: 把单位好好转换一下
        return String.format(Locale.CHINESE, "%s() 持续 %d 毫秒", methodName, duration);
    }
}
