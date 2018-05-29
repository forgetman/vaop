package aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.io.PrintWriter;
import java.io.StringWriter;

import logger.L;

/**
 * 防止崩溃
 *
 * @author yuansui
 */
@Aspect
public class SafeAspect extends BaseAspect {

    @Pointcut("@within(aspect.annotation.Safe)||@annotation(aspect.annotation.Safe)")
    public void methodCut() {
    }

    @Around(START_SYNTHETIC + "methodCut()")
    public Object around(final ProceedingJoinPoint joinPoint) {
        Object result = null;
        try {
            result = joinPoint.proceed(joinPoint.getArgs());
        } catch (Throwable e) {
            L.d(getStringFromException(e));
        }
        return result;
    }

    private static String getStringFromException(Throwable ex) {
        StringWriter errors = new StringWriter();
        ex.printStackTrace(new PrintWriter(errors));
        return errors.toString();
    }
}