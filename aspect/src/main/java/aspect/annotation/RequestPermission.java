package aspect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yuansui
 * @since 2018/6/6
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestPermission {
    /**
     * 请求失败时显示的toast信息
     */
    String message() default "";

    String[] request();
}
