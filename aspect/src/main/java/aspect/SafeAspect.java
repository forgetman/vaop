package aspect;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 防止崩溃
 *
 * @author yuansui
 */
@Aspect
public class SafeAspect extends BaseAspect {

    private static final String TAG = SafeAspect.class.getSimpleName();

    @Pointcut("@within(aspect.annotation.Safe)||@annotation(aspect.annotation.Safe)")
    public void methodCut() {
    }

    @Around(START_SYNTHETIC + "methodCut()")
    public Object around(final ProceedingJoinPoint joinPoint) {
        Object result = null;
        try {
            result = joinPoint.proceed(joinPoint.getArgs());
        } catch (Throwable e) {
            Log.e(TAG, getStringFromException(e));
        }
        return result;
    }

    private static String getStringFromException(Throwable ex) {
        StringWriter errors = new StringWriter();
        ex.printStackTrace(new PrintWriter(errors));
        return errors.toString();
    }
}