package vaop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import vaop.annotation.util.ThreadType;

/**
 * @author yuansui
 * @since 2018/5/11 0011
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IOThread {

    /**
     * @return 子线程的类型，默认是多线程池
     */
   /* @ThreadType */int value() default ThreadType.FIXED;
}
