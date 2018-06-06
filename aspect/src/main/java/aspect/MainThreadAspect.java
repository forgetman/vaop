package aspect;

import android.os.Handler;
import android.os.Looper;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 采用RxJava异步处理
 *
 * @author yuansui
 * @since 2018/5/11 0011
 */
@Aspect
public class MainThreadAspect extends BaseAspect {

    @Pointcut("@within(aspect.annotation.MainThread)||@annotation(aspect.annotation.MainThread)")
    public void methodCut() {
    }

    @Around(START_SYNTHETIC + "methodCut()")
    public void around(final ProceedingJoinPoint joinPoint) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            try {
                joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }
}
