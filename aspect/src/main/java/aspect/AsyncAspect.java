package aspect;

import android.os.Looper;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 采用RxJava异步处理
 *
 * @author yuansui
 * @since 2018/5/11 0011
 */
@Aspect
public class AsyncAspect extends BaseAspect {

    @Pointcut("@within(aspect.annotation.Async)||@annotation(aspect.annotation.Async)")
    public void methodCut() {
    }

    @Around(START_SYNTHETIC + "methodCut()")
    public void around(final ProceedingJoinPoint joinPoint) {
        Flowable.create(e -> {
            Looper.prepare();
            try {
                joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            Looper.loop();
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

    }
}
