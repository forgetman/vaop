package aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * 采用RxJava异步处理
 *
 * @author yuansui
 * @since 2018/5/11 0011
 */
@Aspect
public class SyncAspect extends BaseAspect {

    @Pointcut("@within(aspect.annotation.Sync)||@annotation(aspect.annotation.Sync)")
    public void methodCut() {
    }

    @Around(START_SYNTHETIC + "methodCut()")
    public void around(final ProceedingJoinPoint joinPoint) {
        Flowable.create(e -> {
            try {
                joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
