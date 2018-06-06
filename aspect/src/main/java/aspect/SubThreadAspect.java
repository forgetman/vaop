package aspect;

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
public class SubThreadAspect extends BaseAspect {

    @Pointcut("@within(aspect.annotation.SubThread)||@annotation(aspect.annotation.SubThread)")
    public void methodCut() {
    }

    @Around(START_SYNTHETIC + "methodCut()")
    public void around(final ProceedingJoinPoint joinPoint) {
//        Flowable.create(e -> {
//            Looper.prepare();
//            try {
//                joinPoint.proceed();
//            } catch (Throwable throwable) {
//                throwable.printStackTrace();
//            }
//            Looper.loop();
//        }, BackpressureStrategy.BUFFER)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe();

    }
}
