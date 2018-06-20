package vaop;

import android.util.Log;
import android.view.View;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import vaop.annotation.SingleClick;
import vaop.util.ClickUtil;
import vaop.util.Util;

@Aspect
public class SingleClickAspectJ {

    @Pointcut("within(@vaop.annotation.SingleClick *)")
    public void withinAnnotatedClass() {
    }

    @Pointcut("execution(!synthetic * *(..)) && withinAnnotatedClass()")
    public void methodInsideAnnotatedType() {
    }

    @Pointcut("execution(@vaop.annotation.SingleClick * *(..)) || methodInsideAnnotatedType()")
    public void method() {
    }

    @Around("method() && @annotation(singleClick)")
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint, SingleClick singleClick) throws Throwable {
        View view = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof View) {
                view = (View) arg;
                break;
            }
        }
        if (view != null) {
            if (!ClickUtil.isFastDoubleClick(view, singleClick.value())) {
                joinPoint.proceed();//不是快速点击，执行原方法
            } else {
                Log.d(Util.getMethodDescribeInfo(joinPoint), ":发生快速点击，View id:" + view.getId());
            }
        }
    }


}
