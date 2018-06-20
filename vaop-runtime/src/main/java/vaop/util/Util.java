package vaop.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.CodeSignature;

/**
 * @author yuansui
 * @since 2018/6/19
 */
public class Util {
    /**
     * 获取方法的描述信息
     *
     * @param joinPoint
     * @return
     */
    public static String getMethodDescribeInfo(final ProceedingJoinPoint joinPoint) {
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        Class<?> cls = codeSignature.getDeclaringType(); //方法所在类
        String methodName = codeSignature.getName();    //方法名
        return getClassName(cls) + "->" + methodName;
    }

    public static String getClassName(Class<?> cls) {
        if (cls.isAnonymousClass()) {
            return getClassName(cls.getEnclosingClass());
        }
        return cls.getSimpleName();
    }
}
