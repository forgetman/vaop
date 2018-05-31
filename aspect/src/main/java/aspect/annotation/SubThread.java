package aspect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yuansui
 * @since 2018/5/11 0011
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface SubThread {
}
