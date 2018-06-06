package aspect;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * 采用RxJava异步处理
 *
 * @author yuansui
 * @since 2018/5/11 0011
 */
@Aspect
public class SubThreadAspect extends BaseAspect {

    @Pointcut("@within(aspect.annotation.SubThread)||@annotation(aspect.annotation.SubThread)")
    public void methodCut() {
    }

    @Around(START_SYNTHETIC + "methodCut()")
    public void around(final ProceedingJoinPoint joinPoint) {
        new Thread() {
            @Override
            public void run() {
                super.run();

                MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
                String className = methodSignature.getDeclaringType().getSimpleName();
                Log.d(className, "new thread id = " + android.os.Process.myTid());

                try {
                    joinPoint.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }.start();
    }
}
